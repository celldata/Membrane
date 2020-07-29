package cloud.celldata.membrane.pojo.vo;

/**
 鉴权信息实体
 */
public class TokenKeyBean {

    // tokenKey
    private String tokenKey;

    //权限平台Token
    private String authorization;

    //appId
    private String appId;

    public String getAuthorization() {
        return authorization;
    }

    public void setAuthorization(String authorization) {
        this.authorization = authorization;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getTokenKey() {
        return tokenKey;
    }

    public void setTokenKey(String tokenKey) {
        this.tokenKey = tokenKey;
    }
}
