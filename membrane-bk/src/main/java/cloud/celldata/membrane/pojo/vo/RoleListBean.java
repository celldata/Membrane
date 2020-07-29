package cloud.celldata.membrane.pojo.vo;

/**
 * 角色列表实体
 *
 */
public class RoleListBean {

    // 角色id
    private Integer roleId;

    // 角色名称
    private String roleName;

    // 平台名称
    private String clinetName;

    // 平台id
    private Integer clientId;

    // 数据权限
    private String dataAuthority;

    // 功能权限
    private String functionAuthority;

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

    public String getClinetName() {
        return clinetName;
    }

    public void setClinetName(String clinetName) {
        this.clinetName = clinetName;
    }

    public Integer getClientId() {
        return clientId;
    }

    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }

    public String getDataAuthority() {
        return dataAuthority;
    }

    public void setDataAuthority(String dataAuthority) {
        this.dataAuthority = dataAuthority;
    }

    public String getFunctionAuthority() {
        return functionAuthority;
    }

    public void setFunctionAuthority(String functionAuthority) {
        this.functionAuthority = functionAuthority;
    }

}
