package cloud.celldata.membrane.service.impl;


import cloud.celldata.membrane.excep.MembraneException;
import cloud.celldata.membrane.mapper.ClientMapper;
import cloud.celldata.membrane.mapper.ExternalAuthMapper;
import cloud.celldata.membrane.mapper.UserMapper;
import cloud.celldata.membrane.pojo.RegularExpressionDictionary;
import cloud.celldata.membrane.pojo.ResponseCode;
import cloud.celldata.membrane.pojo.vo.LoginBean;
import cloud.celldata.membrane.pojo.entity.ClientEntity;
import cloud.celldata.membrane.pojo.entity.ExternalAuthUserEntity;
import cloud.celldata.membrane.pojo.entity.UserEntity;
import cloud.celldata.membrane.pojo.entity.UserLoginTokenEntity;
import cloud.celldata.membrane.pojo.enumeration.HaveAuthorityEnum;
import cloud.celldata.membrane.pojo.enumeration.UserStatusEnum;
import cloud.celldata.membrane.pojo.entity.ExternalAuthEntity;
import cloud.celldata.membrane.service.ExternalAuthService;
import cloud.celldata.membrane.service.LoginTokenService;
import cloud.celldata.membrane.service.UserService;
import cloud.celldata.membrane.utils.Sha256;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.naming.NamingException;
import java.util.ArrayList;
import java.util.List;

/**
 * 用户管理业务逻辑层
 */

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private LoginTokenService loginTokenService;

    @Autowired
    private ClientMapper clientMapper;

    @Autowired
    private ExternalAuthService externalAuthService;

    @Autowired
    private ExternalAuthMapper externalAuthMapper;

    @Override
    public List<UserEntity> getUserByUser(UserEntity userEntity) {
        return null;
    }

    /**
     * 登陆
     * @param loginBean 登陆参数
     * @return token
     */
    @Override
    public UserLoginTokenEntity login(LoginBean loginBean) {
        List<UserEntity> userList = new ArrayList<UserEntity>();
        //是否是内部数据源
        boolean userForSSO = true;
        //应用实体
        ClientEntity client =null;
        //外部认证数据源实体
        ExternalAuthEntity externalAuth = null;
        //用户ID
        Integer id = null;

        UserEntity userEntity = new UserEntity();
        // 判断是登录权限管理平台还是 登录业务平台
        if (!StringUtils.isEmpty(loginBean.getAppId())) {
            //登录业务平台
            ClientEntity clientEntity = new ClientEntity();
            clientEntity.setAppId(loginBean.getAppId());
            client = clientMapper.getClient(clientEntity);
            //判断业务平台 使用的外部数据源 还是内部数据源
            if (client == null){
                //查询结果为空 说明 应用不存在 APPId不存在
                throw new MembraneException(ResponseCode.APP_ID_IS_INVALID);
            }
            if (client.getAuthentication() != 0) {
                userForSSO = false;
                externalAuth = externalAuthMapper.selectAllExternalAuth(client.getExternalAuthId(),
                        client.getVerification(), null).get(0);
            }
        }
        //对用户名，密码进行校验
        //userForSSO 为true 表示使用内部数据源登录
        if (userForSSO) {
            String calcPassWord = Sha256.getSHA256(loginBean.getPassword());
            userEntity.setPassword(calcPassWord);
            if (loginBean.getUserName().matches(RegularExpressionDictionary.TELEPHONE_REGEX)) {
                //手机号登陆
                userEntity.setTelephone(loginBean.getUserName());
                userList = userMapper.getUserByUser(userEntity);
            } else if (loginBean.getUserName().matches(RegularExpressionDictionary.EMAIL_REGEX)) {
                //邮箱登陆
                userEntity.setEmail(loginBean.getUserName());
                userList = userMapper.getUserByUser(userEntity);
            } else {
                //用户名登陆
                userEntity.setUserName(loginBean.getUserName());
                userList = userMapper.getUserByUser(userEntity);
            }
            if (CollectionUtils.isEmpty(userList)) {
                // 用户名密码错误
                throw new MembraneException(ResponseCode.USER_LOGIN_FAILURE);
            }
            if (1 == userList.size() && UserStatusEnum.REACTIVE.getCode().equals(userList.get(0).getActiveFlag())) {
                throw new MembraneException(ResponseCode.USER_IS_INACTIVE);
            }
        } else {
            //使用外部数据源登录
            Boolean validateExternalAuthUser = false;
            try {
                validateExternalAuthUser = externalAuthService.validateExternalAuthUser(externalAuth,
                        loginBean.getUserName(), loginBean.getPassword());
            } catch (NamingException e) {
                throw new MembraneException(ResponseCode.EXTERNAL_AUTH_ERROR);
            }
            if (!validateExternalAuthUser) {
                //校验失败
                throw new MembraneException(ResponseCode.EXTERNAL_AUTH_USER_VALIDATE_FAILURE);
            }
            //外部数据源是否需要同步数据到权限管理平台 如果不同步表示仅做校验功能
            if (externalAuth.getSycUser() == 1) {
                userEntity.setUserName(loginBean.getUserName());
                userList = userMapper.getUserByUser(userEntity);
                if (userList.size() == 0) {
                        /*因为外部数据源数据不是实时更新，如果在外部数据源和SSO同步之后外部数据源增加了用户
                        会出现外部校验成功 但是 SSO 数据库并没有查出数据的情况*/
                    ExternalAuthUserEntity externalAuthUserEntity = new ExternalAuthUserEntity();
                    externalAuthUserEntity.setUserName(loginBean.getUserName());
                    externalAuthUserEntity.setExternalAuthId(client.getExternalAuthId());
                    externalAuthUserEntity.setVerification(client.getVerification());
                    List<ExternalAuthUserEntity> externalAuthUserList = new ArrayList<>();
                    externalAuthUserList.add(externalAuthUserEntity);
                    userMapper.addExternalAuthUser(externalAuthUserList);
                    userList = userMapper.getUserByUser(userEntity);
                }
            }
        }
        //功能权限是否具备查询

        // 获取用户的id (如果是外部认证用户 并且没有同步到权限平台的用户 并没有用户ID)
        if (!CollectionUtils.isEmpty(userList)){
            id = userList.get(0).getId();
        }
        // 进行登录
        UserLoginTokenEntity userLoginTokenEntity = loginTokenService.login(client,externalAuth,id,
                loginBean.getUserName(), loginBean.getAppId(), loginBean.getUrl());
        userLoginTokenEntity.setIsHavaAuthority(HaveAuthorityEnum.HAVEAUTHORITY.getCode());

        return userLoginTokenEntity;
    }


}
