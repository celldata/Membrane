package cloud.celldata.membrane.mapper;

import cloud.celldata.membrane.pojo.bo.ExtraBO;
import cloud.celldata.membrane.pojo.entity.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 *  权限Mapper
 */
public interface RoleMapper {


    /**
     * 查询角色名是否存在
     * @param roleId 角色id
     * @param roleName 角色名称
     * @return 满足条件的角色个数
     */
    Integer selectRoleName(@Param("roleId") Integer roleId, @Param("roleName") String  roleName);

    /**
     * 新增角色 新增修改角色service在使用
     * @param role 角色实体
     */
    void addRole1(Role role);

    /**
     * 新增自定义参数
     * @param roleId 角色id
     * @param extraList 参数列表
     * @param creatorId 创建者id
     */
    void addRoleExtra(@Param("roleId") Integer roleId,
                      @Param("extraList") List<ExtraBO> extraList,
                      @Param("creatorId") Integer creatorId);


    /**
     * 查询角色所有功能权限对应ID
     * @param clientId 平台id
     * @return 权限Id
     */
    Integer selectAllApiId(int clientId);

    /**
     * 插入角色功能权限
     * @param roleApiList 角色列表
     * @return 新增角色记录条数
     */
    int insertRoleApi(List<RoleApi> roleApiList);


    /**
     * 查询角色所有数据权限对应ID
     * @param clientId 平台id
     * @return 权限id
     */
    Integer selectAllDataId(int clientId);

    /**
     * 插入角色数据权限
     * @param roleDataList 数据权限列表
     * @return 新增数据权限记录条数
     */
    int insertRoleData(List<RoleData> roleDataList);


    /**
     * 根据clientID查询所有角色
     * @param clientId 平台id
     * @return 角色列表
     */
    List<RoleEntity> selectRoleByClientId(ClientEntity clientId);

    /**
     * 根据角色ID查询平台信息
     * @param roleId 角色id
     * @return 平台角色信息
     */
    ClientEntity selectClientByRoleId(int roleId);

    /**
     * 修改角色  新增修改角色service在使用
     * @param role 角色实体
     */
    void updateRole1(Role role);

    /**
     * 删除自定义参数
     * @param roleId 角色id
     * @param updaterId 更新者id
     */
    void deleteExtra(@Param("roleId") Integer roleId, @Param("updaterId") Integer updaterId);


    /**
     * 更新角色前删除角色数据权限
     * @param roleId 角色id
     * @return 删除数据权限记录条数
     */
    int deleteRoleData(int roleId);

    /**
     * 角色相关用户的更新时间
     * @param updaterId 用户id
     * @param roleId 角色id
     * @return 更新用户记录条数
     */
    Integer updateUserUpdateTime(@Param("updaterId") Integer updaterId,@Param("roleId") Integer roleId);

    /**
     * 删除角色
     * @param roleId 角色id
     * @param updateId 用户id
     * @return 删除角色记录条数
     */
    int removeRole(@Param("roleId")int roleId,@Param("updateId")int updateId);

    /**
     * 删除角色相关连功能权限
     * @param roleId 角色id
     * @param updateId 用户id
     * @return 删除功能权限记录条数
     */
    int removeRoleApi(@Param("roleId")int roleId,@Param("updateId")int updateId);

    /**
     * 删除角色i相关联数据权限
     * @param roleId 角色id
     * @param updateId 用户id
     * @return 删除数据权限记录条数
     */
    int removeRoleData(@Param("roleId")int roleId,@Param("updateId")int updateId);

    /**
     * 删除角色相关用户
     * @param roleId 角色id
     * @param updateId 用户id
     * @return 删除用户记录条数
     */
    int removeRoleUser(@Param("roleId")int roleId,@Param("updateId")int updateId);

    /**
     * 根据角色ID查询角色信息
     * @param roleId 角色id
     * @return 角色信息
     */
    RoleEntity getRoleByRoleId(int roleId);

    /**
     * 根据roleName 模糊查询角色相关信息
     * @param roleName 角色名称关键字
     * @return 角色列表
     */
    List<RoleEntity> selectRoleByRoleName(String roleName);

    /**
     * 新增角色
     * @param role 角色实体
     */
    void addRole(RoleEntity role);

    /**
     * 查询角色是否有全部功能权限
     * @param idList 角色ID list
     * @return 0 没有全部功能权限 非0代表有功能权限
     */
    Integer selectRoleIdListIsAllAPI(@Param("idList")List<Integer> idList);


    /**
     * 查询角色是否有全部数据权限
     * @param idList 角色ID list
     * @return 0 没有全部数据权限 非0代表有数据权限权限
     */
    Integer selectRoleIdListIsAllData(@Param("idList")List<Integer> idList);
}
