package cloud.celldata.membrane.pojo.entity;

import java.util.Map;

/**
 * 用户登录成功后返回的令牌信息实体
 */
public class UserLoginTokenEntity {

    // sso平台token 前端存储
    private String token;

    // 返回给各个平台的tokenKey  需要通过tokenKey查询 平台token（包括权限）
    private String tokenKey;

    // 登陆成功后跳转的uri
    private String url;

    // 用户信息
    private Map userInfo;

    // 是否拥有登陆该平台的权限
    private Integer isHavaAuthority;

    // 是否拥有登陆SSO平台的权限
    private Integer isHavaSSOAuthority;

    public Integer getIsHavaAuthority() {
        return isHavaAuthority;
    }

    public void setIsHavaAuthority(Integer isHavaAuthority) {
        this.isHavaAuthority = isHavaAuthority;
    }

    public Integer getIsHavaSSOAuthority() {
		return isHavaSSOAuthority;
	}

	public void setIsHavaSSOAuthority(Integer isHavaSSOAuthority) {
		this.isHavaSSOAuthority = isHavaSSOAuthority;
	}

	public UserLoginTokenEntity() {
    }

    public UserLoginTokenEntity(String token, Map userInfo) {
        this.token = token;
        this.userInfo = userInfo;
    }

    public Map getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(Map userInfo) {
        this.userInfo = userInfo;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getTokenKey() {
        return tokenKey;
    }

    public void setTokenKey(String tokenKey) {
        this.tokenKey = tokenKey;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
