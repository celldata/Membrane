package cloud.celldata.membrane.service;



import cloud.celldata.membrane.pojo.AuthorityRequest;
import cloud.celldata.membrane.pojo.AuthorityResponse;
import cloud.celldata.membrane.pojo.bo.TokenBo;
import cloud.celldata.membrane.pojo.vo.TokenKeyBean;
import cloud.celldata.membrane.pojo.bo.token.TokenKeyResponse;

/**
 * token管理业务逻辑层接口
 *
 */
public interface TokenService {

    /**
     * 向redis 存业务token
     * sso-- sso_token 与其对应token
     * 业务平台-- appkey_tokenKey与其对应值
     * @param userId 用户id
     * @param  applicationId, 如果为membrane自身的token则置空
     * @param token token
     * @param userName 用户名
     * @return tokenKey
     */
   /* String setToken(Integer userId, String applicationId, String token,String userName);*/



    /**
     * 根据userId 和key、token判断此平台token是否有效 key为APPKey
     * @param redisKey redis主键key
     * @param appid  应用ID
     * @param token token
     * @return 是否有效的token
     */
    Boolean tokenValid(String redisKey, Integer appid, String token);

    /**
     * 删除该用户整体在redis的缓存(注销)
     * @param userId 用户id
     */
    void removeUserToken(Integer userId);


    /**
     *
     * 删除该外部用户整体在redis的缓存(注销)
     * @param externalAuthId 外部数据源ID
     * @param userName 外部
     */
    void removeExternalAuthUserToken(Integer externalAuthId,String userName);

    /**
     * 根据userId更新时间
     * @param userId 用户id
     */
    void setRedisTimesByUser(Integer userId);


    /**
     *
     * @param verification 验证码
     * @param userId        用户ID
     * @param falg          0代表 邮箱 1代表手机
     */
    void setVerification(String verification,Integer userId,Integer falg);

    /**
     *
     * @param userId 用户ID
     * @param falg 0代表邮箱。1代表手机
     * @return
     */
    Boolean verificationValid(Integer userId,Integer falg);

    /**
     *
     * @param userId 用户ID
     * @param falg 0代表邮箱。1代表手机
     * @return
     */
    String getVerification(Integer userId,Integer falg);

    void deleteVerification(Integer userId,Integer falg);

    /**
     * 根据tokenKey取对应的token(此tokenKey为appKey_tokenKey)
     * @param tokenKey tokenKey
     * @return token
     */
    TokenBo getTokenForTokenKey(String tokenKey);

    /**
     * 接口鉴权
     * @param authorityRequest 鉴权实体
     * @return 鉴权返回结果
     */
    AuthorityResponse getAuthorityData(AuthorityRequest authorityRequest);


    /**
     * 根据APPid生成tokenKey
     * @param appId 应用ID
     * @param userId 用户ID
     * @param userName 用户名
     * @return tokenKey
     */
    String getTokenKey(String appId,Integer userId,String userName);

    /**
     * 获取tokenKey
     * @param tokenKeyBean 实体
     * @return tokenkey
     */
    TokenKeyResponse getTokenKeyBySSOToken(TokenKeyBean tokenKeyBean);
}
