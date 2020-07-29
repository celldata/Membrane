package cloud.celldata.membrane.service.impl;

import cloud.celldata.membrane.excep.MembraneException;
import cloud.celldata.membrane.mapper.*;
import cloud.celldata.membrane.pojo.ResponseCode;
import cloud.celldata.membrane.pojo.bo.TokenBo;
import cloud.celldata.membrane.pojo.entity.*;
import cloud.celldata.membrane.pojo.vo.AuthorityAndTokenBean;
import cloud.celldata.membrane.pojo.vo.LoginOffTokenBean;
import cloud.celldata.membrane.pojo.vo.ModuleBean;
import cloud.celldata.membrane.service.AppService;
import cloud.celldata.membrane.service.LoginTokenService;
import cloud.celldata.membrane.service.TokenService;
import cloud.celldata.membrane.utils.JWTUtils;
import cloud.celldata.membrane.utils.RedisUtil;
import com.alibaba.fastjson.JSON;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 登录token管理业务逻辑层
 *
 * @ProjectName: membrane
 * @Package: cloud.celldata.membrane.service.impl
 * @ClassName: LoginTokenServiceImpl
 * @Description: java类作用描述
 * @Author: jiwang
 * @CreateDate: 2020/5/14 16:40
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/5/14 16:40
 */
@Service
@Transactional
public class LoginTokenServiceImpl implements LoginTokenService {

    @Value("${membrane.expire_time}")
    private long activeTime;

    @Autowired
    private UserManagementMapper userManagementMapper;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private DataScreeMapper dataScreeMapper;

    @Autowired
    private AppService appService;

    @Value("${membrane.secret}")
    private String membraneSecret;

    @Autowired
    private ClientMapper clientMapper;

    @Autowired
    private ExternalAuthMapper externalAuthMapper;

    @Value("${membrane.tokenkey_validity}")
    private Long tokenkeyValidity;

    @Value("${membrane.issuer}")
    private String issuer;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private RoleAuthorityMapper roleAuthorityMapper;

    private static final Long EFFECTIVE_TIME = 15L;

    /**
     * 执行登陆操作 存入token 返回数据
     * @param userId 用户id
     * @param appId appId
     * @param uri uri
     * @param userName 用户名
     * @return 用户登录成功信息
     */
    @Override
    public UserLoginTokenEntity login(ClientEntity client, ExternalAuthEntity externalAuth,
                                      Integer userId, String userName, String appId, String uri) {
        String tokenKey =null;
        // 先删除之前的token解除登陆
        if (userId != null){
            //权限平台用户
            tokenService.removeUserToken(userId);
        }else {
            //外部数据源用户
            tokenService.removeExternalAuthUserToken(client.getExternalAuthId(),userName);
        }
        // 生成权限管理平台Token 并存入 redis
        String tokenByUserId = createTokenByUserId(externalAuth,null,userId,userName);
        UserLoginTokenEntity userLoginTokenEntity = new UserLoginTokenEntity();

        // 业务平台登录返回 tokenKey
        if (!StringUtils.isEmpty(appId)){
            //表示登录 业务平台 需要返回业务平台 tokenKey
            //userLoginTokenEntity.setTokenKey(tokenKey);
             tokenKey = tokenService.getTokenKey(appId, userId, userName);
        }
        userLoginTokenEntity.setUrl(uri);
        userLoginTokenEntity.setToken(tokenByUserId);
        userLoginTokenEntity.setTokenKey(tokenKey);
        Map<String, Object> userInfo = new HashMap<>();
        userInfo.put("id", userId);
        userInfo.put("username", userName);
        userLoginTokenEntity.setUserInfo(userInfo);

        return userLoginTokenEntity;
    }

    @Override
    public Boolean validateMembraneToken(String ssoToken) {
        if(StringUtils.isEmpty(ssoToken)){
            throw new MembraneException(ResponseCode.AUTHORITY_ERROR);
        }
        // 解密sso_token
        Claims claims;
        try {
            claims = JWTUtils.parseJWT(ssoToken, membraneSecret);
        } catch (Exception e) {
            return false;
        }
        if (null == claims) {
            return false;
        }
        String redisUserId = null;
        Object objUserId = claims.get("user_id");
        if (null == objUserId) {
            return false;
        }
        if (String.valueOf(claims.get("issuer")).equals(issuer)){
            Integer userId = Integer.valueOf(String.valueOf(objUserId));
            tokenService.setRedisTimesByUser(userId);
            redisUserId = "user_" + userId;
            redisUtil.setHashTime(redisUserId, EFFECTIVE_TIME, TimeUnit.MINUTES);
        }else {
            String userId = String.valueOf(objUserId);
            redisUserId = "externalUser_" + userId;
            redisUtil.setHashTime(redisUserId, EFFECTIVE_TIME, TimeUnit.MINUTES);
        }
            return tokenService.tokenValid(redisUserId, null, ssoToken);
    }


    /**
     * 根据tokenKey 获取 带权限token
     * @param tokenKey tokenKey
     * @return token
     */
    @Override
    public AuthorityAndTokenBean getAuthorityToken(String tokenKey) {

        if (StringUtils.isEmpty(tokenKey)) {
            throw new MembraneException(ResponseCode.AUTHORITY_ERROR);
        }
        AuthorityAndTokenBean authorityAndTokenBean = new AuthorityAndTokenBean();
        // 根据tokenKey 获取 APPID 和token
        TokenBo tokenForTokenKey = tokenService.getTokenForTokenKey(tokenKey);
        authorityAndTokenBean.setAuthorityToken(tokenForTokenKey.getToken());
        //根据用户ID 查出对应角色
        List<Integer> ids = userManagementMapper.selectRoleIdsByUserIdAndAppId(tokenForTokenKey.getUserId(), tokenForTokenKey.getAppId());
        Integer isAllApi = roleMapper.selectRoleIdListIsAllAPI(ids);
        if (isAllApi!=0){
            //全部功能权限
            authorityAndTokenBean.setAllData(true);
        }else {
            //部分功能权限
            List<ModuleBean> moduleBeans = roleAuthorityMapper.selectModuleAndFunctionByRoleIdList(ids);
            authorityAndTokenBean.setAllData(false);
            authorityAndTokenBean.setMenu(Base64Utils.encodeToString(JSON.toJSONString(moduleBeans).getBytes()));
        }

        return authorityAndTokenBean;
    }


    /**
     * 根据appKey 和 权限map 生成带权限的token
     * @param userId  用户id
     * @param appId  平台appId
     * @return token
     */
    /*@Override
    public String createAuthorityTokenByAppKey(Integer userId, String appId) {
        // 根据appId查询平台信息
        *//*ClientEntity clientEntity = new ClientEntity();
        clientEntity.setAppId(appId);
        ClientEntity client = clientMapper.getClient(clientEntity);
        if(null == client){
            throw new MembraneException(ResponseCode.ILLEGAL_APP_ID_CODE, ResponseCode.ILLEGAL_APP_ID_MSG);
        }*//*
        //String appKey = client.getAppKey();

        // 根据userId、clientId查询权限
        UserEntity userEntity = userManagementMapper.selectUserById(userId);
        // 对权限集合进行加密
        MembraneTokenEntity token = new MembraneTokenEntity();
        token.setApplicationId(appId);
        token.setUserId(userId);
        token.setUser_name( userEntity.getUserName());
        return JWTUtils.createJWT(token, membraneSecret);
    }*/

    /**
     * 根据userId 和 appId 生成sso_token或者应用平台token 并存入 redis
     * @param userId 用户id
     * @param appId 应用ID
     * @param userName 用户名
     * @return token
     */
    @Override
    public String createTokenByUserId(ExternalAuthEntity externalAuth,String appId,Integer userId,String userName) {

        MembraneTokenEntity tokenInfo = new MembraneTokenEntity();
        String token = null;
        String appTokenKey = null;
        String userKey = null;
        //判断生成的是业务平台Token还是 权限管理平台Token
        if (appId ==null){
            //生成权限平台Token
            tokenInfo.setUser_name(userName);
            if (userId == null){
                //如果是外部数据源的用户 登录 会出现 userId 为空的情况
                tokenInfo.setUserId(externalAuth.getId()+"_"+userName);
                tokenInfo.setIssuer(externalAuth.getName());

                userKey = "externalUser_"+externalAuth.getId()+"_"+userName;
            }else {
                tokenInfo.setUserId(String.valueOf(userId));
                tokenInfo.setIssuer(issuer);
                userKey = "user_"+userId;
            }
            tokenInfo.setApplication_name("membrane");
            tokenInfo.setApplicationId(null);
            token = JWTUtils.createJWT(tokenInfo, membraneSecret);
            appTokenKey = "membrane_token";
            //权限管理平台Token 存储到Redis
            redisUtil.hmSet(userKey, appTokenKey, token);
            //过期时间
            redisUtil.setHashTime(userKey, EFFECTIVE_TIME, TimeUnit.MINUTES);
        }else {
            //生成业务平台Token
            ClientEntity clientEntity = new ClientEntity();
            clientEntity.setAppId(appId);
            ClientEntity client = clientMapper.getClient(clientEntity);
            tokenInfo.setApplication_name(client.getAppName());
            tokenInfo.setApplicationId(appId);
            if (userId !=null){
                UserEntity user = userManagementMapper.selectUserById(userId);
                tokenInfo.setUser_name(user.getUserName());
                tokenInfo.setUserId(String.valueOf(user.getId()));
                tokenInfo.setIssuer(issuer);
                tokenInfo.setScopes(getScopes(userId,appId));
                userKey = "user_"+userId;
            }else {
                externalAuth = externalAuthMapper.selectAllExternalAuth(client.getExternalAuthId(),
                        client.getVerification(), null).get(0);
                //如果是外部用户登录 因为在 权限管理平台数据库没有用户数据 所以userId为空
                //使用 认证源ID 和用户名保证唯一 生成 userId
                tokenInfo.setUserId(externalAuth.getId()+"_"+userName);
                tokenInfo.setIssuer(externalAuth.getName());
                tokenInfo.setUser_name(userName);
                userKey = "externalUser_"+externalAuth.getId()+"_"+userName;
            }
            token = JWTUtils.createJWT(tokenInfo, client.getAppSecret());
            appTokenKey = client.getAppId()+"_token";
            //权限管理平台Token 存储到Redis
            redisUtil.hmSet(userKey, appTokenKey, token);
            //过期时间
            redisUtil.setHashTime(userKey, EFFECTIVE_TIME, TimeUnit.MINUTES);
        }
        return token;
    }

    /**
     * 退出登录 删除redis中的token
     * @param loginOffTokenBean 退出登录参数（详见实体属性）
     */
    @Override
    public void removeToken(LoginOffTokenBean loginOffTokenBean) {
        //String appId = loginOffTokenBean.getAppId();
        String token = loginOffTokenBean.getToken();
        // 根据appId查询
       /* ClientEntity clientEntity = new ClientEntity();
        clientEntity.setAppId(appId);
        ClientEntity client = clientMapper.getClient(clientEntity);
        if(null == client){
            throw new MembraneException(ResponseCode.USELESS_APP_ID_CODE, ResponseCode.USELESS_APP_ID_MSG);
        }*/
        Claims claims;
        try {
            claims = JWTUtils.parseJWT(token, membraneSecret);
        } catch (Exception e) {
            throw new MembraneException(ResponseCode.TOKEN_IS_INVALID);
        }
        Integer userId = Integer.valueOf(String.valueOf(claims.get("user_id")));
        tokenService.removeUserToken(userId);
    }


    //获取数据权限
    private String getScopes(Integer userId ,String appId){

        //获取数据权限Id List
        List<Integer> ids = userManagementMapper.selectRoleIdsByUserIdAndAppId(userId, appId);
        Integer isAllData = roleMapper.selectRoleIdListIsAllData(ids);

        if (isAllData==0){
            //部分数据权限
            List<String> objects = new ArrayList<>();
            if (ids.size()!=0){
                List<DataScreeEntity> dataScreeEntities = dataScreeMapper.selectScreesByRoleIdList(ids);
                for (DataScreeEntity dataScreeEntity : dataScreeEntities) {
                    objects.add(dataScreeEntity.getScrJson());
                }
                String encodeToString = Base64Utils.encodeToString(JSON.toJSONString(dataScreeEntities).getBytes());
                return encodeToString;
            }

        }
        return null;
    }
}
