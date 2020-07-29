package cloud.celldata.membrane.service;

import cloud.celldata.membrane.pojo.vo.RoleIdAndNameBean;
import cloud.celldata.membrane.pojo.vo.RoleListBean;
import cloud.celldata.membrane.pojo.entity.RoleEntity;
import cloud.celldata.membrane.pojo.vo.role.RoleAddBean;
import cloud.celldata.membrane.pojo.vo.role.RoleUpdateBean;

import java.util.List;

/**
 * 角色管理业务逻辑层接口
 *
 * @ProjectName: membrane
 * @Package: cloud.celldata.membrane.service
 * @ClassName: RoleService
 * @Description: java类作用描述
 * @Author: jiwang
 * @CreateDate: 2020/5/15 15:04
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/5/15 15:04
 */
public interface RoleService {

    /**
     * 获取所有角色信息
     * @param clientId 应用ID
     * @return 角色列表
     */
    List<RoleListBean> selectAllRole(Integer clientId);

    /**
     * 新增角色
     * @param roleAddBean 角色信息
     * @param creatorId 创建者id
     */
    void addRole(RoleAddBean roleAddBean, Integer creatorId);


    /**
     * 根据平台id获取角色信息
     * @param id 平台ID
     * @return 角色列表
     */
    List<RoleEntity> getRoleByClientId(Integer id);

    /**
     * 修改角色
     * @param roleUpdateBean 角色信息
     * @param updaterId 更新者id
     */
    void updateRole(RoleUpdateBean roleUpdateBean, Integer updaterId);

    /**
     * 删除角色
     * @param roleId 角色id
     * @param userId 用户id
     */
    void removeRole(int roleId, Integer userId);

    /**
     * 复制角色
     * @param roleId 待复制角色id
     * @param copyName 新角色名称
     * @param userId 用户id
     */
    void copyRole(int roleId,String copyName,Integer userId);

    /**
     * 根据应用Id查询 应用下角色列表
     *
     * @param clientId 应用ID
     * @return 角色列表
     */
    List<RoleIdAndNameBean> selectAllRoleForClientId(Integer clientId);
}
