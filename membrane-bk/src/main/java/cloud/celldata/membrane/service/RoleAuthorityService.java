package cloud.celldata.membrane.service;


import cloud.celldata.membrane.pojo.vo.role.RoleAuthorityBasicBean;
import cloud.celldata.membrane.pojo.vo.role.RoleUpdateAuthorityBean;

/**
 * 角色权限业务逻辑层接口
 *
 */
public interface RoleAuthorityService {

    /**
     * 根据角色id查询角色基本信息
     * @param roleId 角色id
     * @return 角色基本信息
     */
    RoleAuthorityBasicBean selectRoleBasic(Integer roleId);

    /**
     * 进行角色信息编辑前的查询
     * @param roleId 角色id
     * @return 角色信息
     */
    RoleUpdateAuthorityBean selectBasicAfterUpdateByRoleId(Integer roleId);

}
