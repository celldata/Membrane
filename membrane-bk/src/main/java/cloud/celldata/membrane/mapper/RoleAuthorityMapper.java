package cloud.celldata.membrane.mapper;

import cloud.celldata.membrane.pojo.vo.ModuleBean;
import cloud.celldata.membrane.pojo.bo.ExtraBO;
import cloud.celldata.membrane.pojo.vo.role.RoleApiBean;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 权限Mapper
 */
public interface RoleAuthorityMapper {

    /**
     * 根据角色id查询角色基本信息
     * @param roleId 角色id
     * @return 角色基本信息
     */
    RoleApiBean selectRoleBasicByRoleId(Integer roleId);


    /**
     * 根据角色id查询自定义参数列表
     * @param roleId 角色id
     * @return 自定义参数列表
     */
    List<ExtraBO> selectExtraListByRoleId(Integer roleId);

    /**
     * 根据角色ID查询 对应部分功能权限ID
     *
     * @param roleId 角色ID
     * @return 部分功能权限ID List
     */
    List<Integer> selectRoleApiIDListByRoleId(Integer roleId);

    /**
     * 根据 应用ID 和 功能ID list查询
     *
     * @param clientID 应用ID
     * @param list     功能ID list查询
     * @return 部分功能权限
     */

    List<ModuleBean> selectModuleAndFunctionByApiIdList(@Param("clientId") Integer clientID, @Param("list") List<Integer> list);

    /**
     * 根据 角色ID list查询
     *
     * @param list  角色ID list查询
     * @return 部分功能权限
     */

    List<ModuleBean> selectModuleAndFunctionByRoleIdList(@Param("list") List<Integer> list);
}
