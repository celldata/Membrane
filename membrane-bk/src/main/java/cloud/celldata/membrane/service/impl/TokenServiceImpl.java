package cloud.celldata.membrane.service.impl;

import cloud.celldata.membrane.excep.MembraneException;
import cloud.celldata.membrane.mapper.ApiMapper;
import cloud.celldata.membrane.mapper.ClientMapper;
import cloud.celldata.membrane.mapper.UserManagementMapper;
import cloud.celldata.membrane.mapper.UserMapper;
import cloud.celldata.membrane.pojo.AuthorityRequest;
import cloud.celldata.membrane.pojo.AuthorityResponse;
import cloud.celldata.membrane.pojo.ResponseCode;
import cloud.celldata.membrane.pojo.bo.TokenBo;
import cloud.celldata.membrane.pojo.entity.ClientEntity;
import cloud.celldata.membrane.pojo.vo.TokenKeyBean;
import cloud.celldata.membrane.pojo.bo.token.TokenKeyResponse;
import cloud.celldata.membrane.service.TokenService;
import cloud.celldata.membrane.utils.JWTUtils;
import cloud.celldata.membrane.utils.RedisUtil;
import com.alibaba.fastjson.JSON;
import io.jsonwebtoken.Claims;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * token管理业务逻辑层
 *
 * @ProjectName: membrane
 * @Package: cloud.celldata.membrane.service.impl
 * @ClassName: TokenServiceImpl
 * @Description: java类作用描述
 * @Author: jiwang
 * @CreateDate: 2020/5/15 10:19
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/5/15 10:19
 */

@Service
@Transactional
public class TokenServiceImpl implements TokenService {
    private static final Logger logger = LoggerFactory.getLogger(TokenServiceImpl.class);

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private ClientMapper clientMapper;

    @Autowired
    private ApiMapper apiMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserManagementMapper userManagementMapper;

    @Autowired
    private LoginTokenServiceImpl loginTokenService;

    private static final Long EFFECTIVE_TIME = 15L;

    @Value("${membrane.secret}")
    private String membraneSecret;

    @Value("${membrane.tokenkey_validity}")
    private Long tokenkeyValidity;

    @Value("${membrane.issuer}")
    private String issuer;






    /**
     * 向redis 存业务token
     * sso-- sso_token 与其对应token
     * 业务平台-- appkey_tokenKey与其对应值
     * @param userId 用户id
     * @param applicationId 应用平台id
     * @param token token
     * @return token及key
     */
    /*@Override
    public String setToken(Integer userId,String applicationId, String token,String userName) {

        String appTokenKey = getAppTokenRedisKey("membrane");
        String tokenKey = null;
        redisUtil.hmSet(getUserKey(userId), appTokenKey, token);
             *//*放置是否权限变更、整体时间设置
            redisUtil.hmSet(getUserKey(userId), "valid", false);*//*
        redisUtil.setHashTime(getUserKey(userId), EFFECTIVE_TIME, TimeUnit.MINUTES);


        return getTokenKey(applicationId,userId);
    }*/


    /**
     * 根据key获取redisKey
     * @param key key
     * @return redisKey
     */
    private String getAppTokenRedisKey(String key){
        return key + "_token";
    }

    /**
     * 根据userId获取key
     * @param userId 用户唯一标识
     * @return key
     */
    private String getUserKey(Integer userId){
        return "user_" + userId;
    }

    /**
     * 根据外部数据源ID 和 用户名 获取key
     * @param externalAuthId 外部数据源ID
     * @param userName 用户名
     * @return key
     */
    private String getExternalAuthUserKey(Integer externalAuthId, String userName) {
        return "externalUser_"+externalAuthId+"_"+userName;
    }

    /**
     * 根据key生成tokenKey
     * @param key key
     * @return tokenKey
     */
    private String getTokenKeyKey(String key){
        return key + "_tokenKey";
    }

    private String getEmailVerification(Integer userID){
        return userID+"_EmailVerification";
    }
    private String getPhoneVerification(Integer userID){
        return userID+"_PhoneVerification";
    }

    @Override
    public void setRedisTimesByUser(Integer userId) {
        redisUtil.setHashTime(getUserKey(userId), EFFECTIVE_TIME, TimeUnit.MINUTES);
    }

    @Override
    public void setVerification(String verification, Integer userId, Integer falg) {
        switch (falg){
            case 0:
                redisUtil.set(getEmailVerification(userId),verification,1800L);
                break;
            case 1:
                redisUtil.set(getPhoneVerification(userId),verification,1800L);
                break;
            default:
                logger.error("非法参数");
                break;
        }
    }

    /**
     *
     * @param userId 用户ID
     * @param falg 标识 0代表邮箱验证 1代表手机验证
     * @return
     */
    @Override
    public Boolean verificationValid(Integer userId, Integer falg) {
        switch (falg){
            case 0:
                return redisUtil.exists(getEmailVerification(userId));
            case 1:
                return redisUtil.exists(getPhoneVerification(userId));
            default:
                logger.error("非法参数");
                return true;
        }
    }

    @Override
    public String getVerification(Integer userId, Integer falg) {
        switch (falg){
            case 0:
                return String.valueOf(redisUtil.get(getEmailVerification(userId)));
            case 1:
                return String.valueOf(redisUtil.get(getPhoneVerification(userId)));
            default:
                return null;
        }

    }

    @Override
    public void deleteVerification(Integer userId, Integer falg) {
        switch (falg){
            case 0:
                 redisUtil.remove(getEmailVerification(userId));
                break;
            case 1:
                 redisUtil.remove(getPhoneVerification(userId));
                break;
            default:
                logger.error("非法参数");
                break;
        }
    }

    /**
     * 根据tokenKey取对应的token(此tokenKey为appKey_tokenKey)
     * @param tokenKey tokenKey
     * @return token
     */
    @Override
    public TokenBo getTokenForTokenKey(String tokenKey) {
        logger.info("tokenKey为;{}", tokenKey);
        boolean exists = redisUtil.exists(tokenKey);
        if (Boolean.FALSE.equals(exists)){
            throw new MembraneException(ResponseCode.AUTHORITY_ERROR);
        }
        Object resultMap = redisUtil.getAllObjectForHashKey(tokenKey);
        Map<String, Object> map = JSON.parseObject(JSON.toJSONString(resultMap), Map.class);
        String appId = String.valueOf(map.get("appId"));
        Integer userId = (Integer) map.get("userId");
        String userName = String.valueOf(map.get("userName"));

        //删除tokenKey
        redisUtil.remove(tokenKey);
        String tokenByUserId = loginTokenService.createTokenByUserId(null,appId,userId,userName);
        TokenBo tokenBo = new TokenBo();
        tokenBo.setAppId(appId);
        tokenBo.setToken(tokenByUserId);
        tokenBo.setUserId(userId);


        return tokenBo;
    }

    /**
     * 接口鉴权
     * @param authorityRequest 鉴权实体
     * @return 鉴权返回结果
     */
    @Override
    public AuthorityResponse getAuthorityData(AuthorityRequest authorityRequest) {
        AuthorityResponse authorityResponse = new AuthorityResponse();
        // 获取token
        String token = authorityRequest.getToken();
        // 获取请求路径
        String url = authorityRequest.getUrl();
        // 获取appId
        String appId = authorityRequest.getAppId();

        if (StringUtils.isEmpty(appId)) {
            throw new MembraneException(ResponseCode.APP_ID_IS_NULL);
        }
        if (StringUtils.isEmpty(token)) {
            throw new MembraneException(ResponseCode.AUTHORITY_ERROR);
        }
        // 获取appSecret
        ClientEntity clientEntity = new ClientEntity();
        clientEntity.setAppId(appId);
        ClientEntity client = clientMapper.getClient(clientEntity);
        if (client == null){
            throw new MembraneException(ResponseCode.AUTHORITY_ERROR);
        }
        // 解析token
        Claims claims;
        try {
            claims = JWTUtils.parseJWT(token, client.getAppSecret());
        } catch (Exception e) {
            logger.info("解析失败========================================================");
            logger.info("token值为:" + token);
            throw new MembraneException(ResponseCode.AUTHORITY_ERROR);
        }
        // 获取userId
        Integer userId;

        Object objUserId = claims.get("user_id");
        try {
             userId = Integer.parseInt(objUserId.toString());
            logger.info("解析token:" + token + "获得userId:" + userId);
        } catch (Exception e) {
            throw new MembraneException(ResponseCode.AUTHORITY_ERROR);
        }

        if (!redisUtil.exists(getUserKey(userId))){
            throw new MembraneException(ResponseCode.AUTHORITY_ERROR);
        }
        List<String> uriListForAppId = apiMapper.selectAllUriListForAppId(client.getId());
        if (uriListForAppId.stream().anyMatch(s -> s.equals(url))){
            //请求地址在应用下对应有此地址
            authorityResponse.setIsExistAPI(true);
            authorityResponse.setRequestType(apiMapper.selectApiTypeByAppIdAndURI(client.getId(), url));
            List<Integer> isALLApiByUserIdAndAppId = userMapper.selectIsALLApiByUserIdAndAppId(client.getId(), userId);
            if (isALLApiByUserIdAndAppId.stream().anyMatch(integer -> integer==1)){
                //该用户下有角色拥有全部权限
                authorityResponse.setIsHaveAPI(true);
            }else {
                //该用户下角色未拥有全部权限
                //首先查出对应该用户角色下对应的URIList
                List<String> uriListByAppIdAndUserId = userMapper.selectUriListByAppIdAndUserId(client.getId(), userId);
                if (uriListByAppIdAndUserId.stream().anyMatch(s -> s.equals(url))){
                    //有部分权限
                    authorityResponse.setIsHaveAPI(true);
                }else {
                    //没有部分权限
                    authorityResponse.setIsHaveAPI(false);
                }
            }
        }else {
            //请求地址在此应用下不存在
            authorityResponse.setIsExistAPI(false);
        }
        authorityResponse.setUserId(userId);

        return authorityResponse;
    }

    @Override
    public String getTokenKey(String appId,Integer userId,String userName) {
        /*String tokenKey=null;
        //如果是业务平台登录增加返回tokenKey
        if(appId != null && !"".equals(appId)) {
            Integer countAppId = clientMapper.countClientByAppId(appId,null);
            //判断APPID是否合法
            if (countAppId == 0){
                throw new MembraneException(ResponseCode.APP_ID_IS_INVALID);
            }
            tokenKey = UUID.randomUUID().toString().replaceAll("-","");
            redisUtil.hmSet(tokenKey,appId,userId);
            redisUtil.setHashTime(tokenKey,tokenkeyValidity,TimeUnit.SECONDS);
        }*/
        String tokenKey = UUID.randomUUID().toString().replaceAll("-","");
        redisUtil.hmSet(tokenKey,"appId",appId);
        redisUtil.hmSet(tokenKey,"userId",userId);
        redisUtil.hmSet(tokenKey,"userName",userName);
        redisUtil.setHashTime(tokenKey,tokenkeyValidity,TimeUnit.SECONDS);
        return tokenKey;
    }

    /**
     * 获取tokenKey
     * @param tokenKeyBean 实体
     * @return tokenkey
     */
    @Override
    public TokenKeyResponse getTokenKeyBySSOToken(TokenKeyBean tokenKeyBean) {

        TokenKeyResponse tokenKeyResponse = new TokenKeyResponse();

        //判断权限平台SSO token是否过期
        Boolean result = loginTokenService.validateMembraneToken(tokenKeyBean.getAuthorization());
        if (!result){
            throw new MembraneException(ResponseCode.AUTHORITY_ERROR);
        }
        //解密SSO token
        Claims claims = JWTUtils.parseJWT(tokenKeyBean.getAuthorization(), membraneSecret);
        if (String.valueOf(claims.get("issuer")).equals(issuer)){
            //权限平台 颁发token
            Integer userId = Integer.valueOf(String.valueOf(claims.get("user_id")));
            //查询用户目标应用下是否有角色。
            List<Integer> roleIdsByUserIdAndAppId = userManagementMapper.selectRoleIdsByUserIdAndAppId(userId, tokenKeyBean.getAppId());
            if (roleIdsByUserIdAndAppId.size() == 0){
                //目标应用下没有角色
                tokenKeyResponse.setHaveAccess(false);
            }else {
                //目标应用下有角色
                tokenKeyResponse.setHaveAccess(true);
                String tokenKey = getTokenKey(tokenKeyBean.getAppId(), userId,null);
                tokenKeyResponse.setTokenKey(tokenKey);
            }
        }else{
            //外部认证颁发的
            String userName = String.valueOf(claims.get("user_name"));
            String userId = String.valueOf(claims.get("user_id"));
            String[] split = userId.split("_");
            //从外部认证的userId中 拿出 外部数据源ID
            Integer externalAuthId = Integer.valueOf(split[0]);
            ClientEntity clientEntity = new ClientEntity();
            clientEntity.setAppId(tokenKeyBean.getAppId());
            ClientEntity client = clientMapper.getClient(clientEntity);
            //判断应用是否使用外部数据源
            if (client.getAuthentication()==1){
                //判断外部数据源 ID 和 目标应用 外部数据源是否一致
                if (externalAuthId == client.getExternalAuthId()){
                    //如果一直说明  具备登录目标应用的权限 生成tokenKey
                    String tokenKey = getTokenKey(tokenKeyBean.getAppId(), null,userName);
                    tokenKeyResponse.setTokenKey(tokenKey);
                }
            }
        }
        return tokenKeyResponse;
    }

    /**
     * 根据appToken生成timeKey
     * @param key appToken
     * @return timeKey
     */
    private String getAppTokenTimeKey(String key){
        return key + "_token_time";
    }


    /**
     * 向redis 存tokenKey
     * @param userId 用户id
     * @param key key
     * @param tokenKey tokenKye
     */

    private void setTokenKey(Integer userId, String key, String tokenKey) {
        redisUtil.hmSet(getUserKey(userId), key, tokenKey);
    }

    @Override
    public Boolean tokenValid(String redisKey, Integer appId, String token) {
        Object userMap = redisUtil.getAllObjectForHashKey(redisKey);
        Map map = JSON.parseObject(JSON.toJSONString(userMap), Map.class);
        logger.info("token值为;{}", JSON.toJSONString(token));
        logger.info("redis中存值为;{}", JSON.toJSONString(map));
        if(map.isEmpty()){
            throw new MembraneException(ResponseCode.AUTHORITY_ERROR);
        }
        /*Object valid = redisUtil.hmGet(getUserKey(userId), "valid");
        if(Boolean.valueOf(String.valueOf(valid))){
            throw new MembraneException(ResponseCode.CLAIMS_UPDATED);
        }*/
        Object result=null;
        if (appId ==null){
            result= redisUtil.hmGet(redisKey, getAppTokenRedisKey("membrane"));
        }else {
            //业务平台token


        }

        logger.info("校验token值为:" + result);
        token = token.trim();
        return token.equals(String.valueOf(result));
    }

    @Override
    public void removeUserToken(Integer userId) {
        redisUtil.remove(getUserKey(userId));
    }

    @Override
    public void removeExternalAuthUserToken(Integer externalAuthId, String userName) {
        redisUtil.remove(getExternalAuthUserKey(externalAuthId,userName));
    }


}
