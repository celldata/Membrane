package cloud.celldata.membrane.pojo.vo.role;

import java.util.List;

/**
 * 角色实体
 *
 */
public class RoleAddBean {

    // 角色名称
    private String roleName;

    // 所属平台
    private Integer clientId;

    // 数据权限ids
    private List<Integer> dataAuthorityIds;

    // 是否全部数据权限
    private Boolean allDataAuthority;

    // 是否全部功能权限
    private Boolean allFunctionAuthority;

    // 功能权限ids
    private List<Integer>  functionAuthorityIds;

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

    public List<Integer> getDataAuthorityIds() {
        return dataAuthorityIds;
    }

    public void setDataAuthorityIds(List<Integer> dataAuthorityIds) {
        this.dataAuthorityIds = dataAuthorityIds;
    }

    public List<Integer> getFunctionAuthorityIds() {
        return functionAuthorityIds;
    }

    public void setFunctionAuthorityIds(List<Integer> functionAuthorityIds) {
        this.functionAuthorityIds = functionAuthorityIds;
    }

    public Boolean getAllDataAuthority() {
        return allDataAuthority;
    }

    public void setAllDataAuthority(Boolean allDataAuthority) {
        this.allDataAuthority = allDataAuthority;
    }

    public Boolean getAllFunctionAuthority() {
        return allFunctionAuthority;
    }

    public void setAllFunctionAuthority(Boolean allFunctionAuthority) {
        this.allFunctionAuthority = allFunctionAuthority;
    }

}
