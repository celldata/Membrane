package cloud.celldata.membrane.pojo.vo;

/**
 * 登录参数实体
 *
 */
public class LoginBean {

    // 用户名
    private String userName;

    // 密码
    private String password;

    // appId
    private String appId;

    // url
    private String url;


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
