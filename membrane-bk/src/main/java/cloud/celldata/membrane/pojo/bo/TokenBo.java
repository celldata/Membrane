package cloud.celldata.membrane.pojo.bo;

/**
 * TokenKey转换实体
 *
 * @ProjectName: membrane
 * @Package: cloud.celldata.membrane.pojo.bo
 * @ClassName: TokenKeyBo
 * @Description: tokenkey转换实体
 * @Author: jiwang
 * @CreateDate: 2020/6/10 15:03
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/6/10 15:03
 */
public class TokenBo {

    private String token;

    private String appId;

    private Integer userId;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }
}
