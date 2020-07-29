package cloud.celldata.membrane.service;


import cloud.celldata.membrane.pojo.vo.LoginOffTokenBean;
import cloud.celldata.membrane.pojo.entity.ClientEntity;
import cloud.celldata.membrane.pojo.entity.UserLoginTokenEntity;
import cloud.celldata.membrane.pojo.entity.ExternalAuthEntity;
import cloud.celldata.membrane.pojo.vo.AuthorityAndTokenBean;

/**
 * 登录token管理业务逻辑层接口
 */
public interface LoginTokenService {

    /**
     * 根据userId生成sso_token
     * @param userId 用户id
     * @param appId 应用id
     * @return token
     */
    String createTokenByUserId(ExternalAuthEntity externalAuth,String appId,Integer userId,String userName);

    /**
     * 退出登录  删除redis中的token
     * @param token token
     */
    void removeToken(LoginOffTokenBean token);

    /**
     * 根据appKey 和 权限map 生成带权限的token
     * @param userId  用户id
     * @param appId  平台appId
     * @return token
     */
    /*String createAuthorityTokenByAppKey(Integer userId, String appId);*/

    /**
     * 执行登陆操作 存入token 返回数据
     * @param userId 用户id
     * @param appId appId
     * @param uri uri
     * @return 用户登录成功信息
     */
    UserLoginTokenEntity login(ClientEntity client, ExternalAuthEntity externalAuthEntity,
                               Integer userId, String userName, String appId, String uri);

    /**
     * 校验sso_token是否有效
     * @param ssoToken token
     * @return 是否有效
     */
    Boolean validateMembraneToken(String ssoToken);

    /**
     * 根据tokenKey 获取 带权限token
     * @param tokenKey tokenKey
     * @return token
     */
    AuthorityAndTokenBean getAuthorityToken(String tokenKey);
}
