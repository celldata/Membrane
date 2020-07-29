package cloud.celldata.membrane.pojo.vo;

/**
 * 应用类实体
 *
 * @ProjectName: membrane
 * @Package: cloud.celldata.membrane.pojo.vo.bean
 * @ClassName: ClientBean
 * @Description: 应用类实体
 * @Author: jiwang
 * @CreateDate: 2020/5/27 16:14
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/5/27 16:14
 */
public class ClientBean {

    // 平台id
    private Integer clientId;

    // 平台名称
    private String clientName;

    //appId
    private String appId;

    //应用图片地址
    private String imgUrl;

    //app Secret
    private String secret;

    //app key
    private String appKey;

    //应用描述
    private String appDesc;

    public String getAppDesc() {
        return appDesc;
    }

    public void setAppDesc(String appDesc) {
        this.appDesc = appDesc;
    }

    public Integer getClientId() {
        return clientId;
    }

    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }


    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }
}
