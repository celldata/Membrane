package cloud.celldata.membrane.pojo.vo.role;

import java.util.List;

/**
 * 角色实体
 *
 */
public class RoleApiBean {

    // 角色id
    private Integer roleId;

    // 角色名称
    private String roleName;

    // 平台id
    private Integer clientId;

    // 平台名称
    private String clientName;

    //是否为全部功能权限: 1代表全部权限 非1代表部分权限
    private Integer isAllApi;

    //是否为全部数据权限: 1代表全部权限 非1代表部分权限
    private Integer isAllData;


    public Integer getIsAllData() {
        return isAllData;
    }

    public void setIsAllData(Integer isAllData) {
        this.isAllData = isAllData;
    }

    // 接口列表
    private List<ApiBean> apis;

    public Integer getIsAllApi() {
        return isAllApi;
    }

    public void setIsAllApi(Integer isAllApi) {
        this.isAllApi = isAllApi;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
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

    public List<ApiBean> getApis() {
        return apis;
    }

    public void setApis(List<ApiBean> apis) {
        this.apis = apis;
    }
}
