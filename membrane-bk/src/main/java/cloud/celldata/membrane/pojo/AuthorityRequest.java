package cloud.celldata.membrane.pojo;

/**
 * 鉴权参数实体
 */
public class AuthorityRequest {

	// appId
	private String appId;

	// 接口地址
	private String url;

	// token
	private String token;

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

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

}
