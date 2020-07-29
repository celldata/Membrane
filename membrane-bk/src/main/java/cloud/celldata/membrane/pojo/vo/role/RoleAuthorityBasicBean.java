package cloud.celldata.membrane.pojo.vo.role;

/**
 * 角色详情实体
 *
 */
public class RoleAuthorityBasicBean {

    // 角色id
    private Integer roleId;

    // 角色名称
    private String roleName;

    // 平台id
    private Integer clientId;

    // 平台名称
    private String clientName;

    // 数据权限
    private AuthorityBean authorityBean;

    // 功能权限
    private FunctionAuthorityBean functionAuthorityBean;

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

    public AuthorityBean getAuthorityBean() {
        return authorityBean;
    }

    public void setAuthorityBean(AuthorityBean authorityBean) {
        this.authorityBean = authorityBean;
    }

    public FunctionAuthorityBean getFunctionAuthorityBean() {
        return functionAuthorityBean;
    }

    public void setFunctionAuthorityBean(FunctionAuthorityBean functionAuthorityBean) {
        this.functionAuthorityBean = functionAuthorityBean;
    }
}
