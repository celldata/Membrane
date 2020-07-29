package cloud.celldata.membrane.pojo.vo;

import cloud.celldata.membrane.pojo.enumeration.AuthenticationEnum;
import cloud.celldata.membrane.pojo.enumeration.IsAuthenticationEnum;
import cloud.celldata.membrane.pojo.enumeration.TokenCheckTypeEnum;

/**
 * 认证管理实体
 */
public class CertificationBean extends ClientBean {

    // 平台id
    private Integer clientId;

    //认证状态
    private IsAuthenticationEnum authentication;

    //认证方式
    private AuthenticationEnum verification;

    //验证方式
    private TokenCheckTypeEnum tokenCheckType;

    //Access Token 有效时间 单位分钟
    private Integer accessValidity;

    //外部数据源认证Id
    private Integer externalAuthId;

    public Integer getClientId() {
        return clientId;
    }

    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }

    public IsAuthenticationEnum getAuthentication() {
        return authentication;
    }

    public void setAuthentication(IsAuthenticationEnum authentication) {
        this.authentication = authentication;
    }

    public AuthenticationEnum getVerification() {
        return verification;
    }

    public void setVerification(AuthenticationEnum verification) {
        this.verification = verification;
    }

    public TokenCheckTypeEnum getTokenCheckType() {
        return tokenCheckType;
    }

    public void setTokenCheckType(TokenCheckTypeEnum tokenCheckType) {
        this.tokenCheckType = tokenCheckType;
    }

    public Integer getAccessValidity() {
        return accessValidity;
    }

    public void setAccessValidity(Integer accessValidity) {
        this.accessValidity = accessValidity;
    }

    public Integer getExternalAuthId() {
        return externalAuthId;
    }

    public void setExternalAuthId(Integer externalAuthId) {
        this.externalAuthId = externalAuthId;
    }
}
