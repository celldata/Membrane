package cloud.celldata.membrane.pojo;


import java.util.List;

/**
 * 授权信息实体
 *
 */
public class AuthorityResponse {

	// 用户Id
	private Integer userId;

	// 用户名
	private String userName;

	// 手机号
	private String phone;

	// 邮箱
	private String email;

	// 是否有全部功能权限
	private Boolean allAPIAuthority;

	// 数据库中是否维护了当前URI
	private Boolean isExistAPI;

	// 用户是否有当前接口的功能权限
    private Boolean isHaveAPI;

    // 0：当前接口是增删改；1：当前接口是查询
    private Integer requestType;

    // 当前接口是否关联数据权限
    private Boolean isRelatedData;

    // 是否有全部数据权限
    private Boolean allDataAuthority;

    // 数据权限列表
    private List<String> dataList;

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Boolean getIsHaveAPI() {
		return isHaveAPI;
	}

	public void setIsHaveAPI(Boolean isHaveAPI) {
		this.isHaveAPI = isHaveAPI;
	}

	public List<String> getDataList() {
		return dataList;
	}

	public void setDataList(List<String> dataList) {
		this.dataList = dataList;
	}

	public Boolean getIsExistAPI() {
		return isExistAPI;
	}

	public void setIsExistAPI(Boolean isExistAPI) {
		this.isExistAPI = isExistAPI;
	}

	public Integer getRequestType() {
		return requestType;
	}

	public void setRequestType(Integer requestType) {
		this.requestType = requestType;
	}

	public Boolean getAllAPIAuthority() {
		return allAPIAuthority;
	}

	public void setAllAPIAuthority(Boolean allAPIAuthority) {
		this.allAPIAuthority = allAPIAuthority;
	}

	public Boolean getAllDataAuthority() {
		return allDataAuthority;
	}

	public void setAllDataAuthority(Boolean allDataAuthority) {
		this.allDataAuthority = allDataAuthority;
	}

	public Boolean getIsRelatedData() {
		return isRelatedData;
	}

	public void setIsRelatedData(Boolean isRelatedData) {
		this.isRelatedData = isRelatedData;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
