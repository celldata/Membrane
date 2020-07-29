package cloud.celldata.membrane.pojo.vo;

/**
 * 查询应用下角色实体
 *
 * @ProjectName: membrane
 * @Package: cloud.celldata.membrane.pojo.vo.bean
 * @ClassName: RoleIdAndNameBean
 * @Description: 查询应用下角色实体
 * @Author: jiwang
 * @CreateDate: 2020/6/1 12:50
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/6/1 12:50
 */
public class RoleIdAndNameBean {

    //角色ID
    private Integer roleId;

    //角色名
    private String roleName;

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
}
