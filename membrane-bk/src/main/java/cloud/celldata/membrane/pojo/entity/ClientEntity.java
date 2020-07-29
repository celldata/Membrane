package cloud.celldata.membrane.pojo.entity;

/**
 * 平台信息实体
 */
public class ClientEntity {

    // 平台id
    private Integer id;

    // appId
    private String appId;

    // appKey
    private String appKey;

    // 平台名称
    private String appName;

    //appSecret
    private String appSecret;

    //appSecret 有效期 单位分钟
    private Integer appSecretValidity;

    //认证状态 0 代表内部认证 1代表外部认证
    private Integer authentication;

    //认证方式
    private Integer verification;

    //外部数据源ID
    private Integer externalAuthId;

    public String getAppSecret() {
        return appSecret;
    }

    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret;
    }

    public Integer getAppSecretValidity() {
        return appSecretValidity;
    }

    public void setAppSecretValidity(Integer appSecretValidity) {
        this.appSecretValidity = appSecretValidity;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public Integer getAuthentication() {
        return authentication;
    }

    public void setAuthentication(Integer authentication) {
        this.authentication = authentication;
    }

    public Integer getVerification() {
        return verification;
    }

    public void setVerification(Integer verification) {
        this.verification = verification;
    }

    public Integer getExternalAuthId() {
        return externalAuthId;
    }

    public void setExternalAuthId(Integer externalAuthId) {
        this.externalAuthId = externalAuthId;
    }
}
