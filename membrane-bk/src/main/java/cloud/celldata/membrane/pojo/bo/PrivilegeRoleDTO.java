package cloud.celldata.membrane.pojo.bo;

/**
 * 角色信息实体
 *
 **/
public class PrivilegeRoleDTO {

    // 平台id
    private Integer clientId;

    // 角色id
    private Integer roleId;

    public Integer getClientId() {
        return clientId;
    }

    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }
}
