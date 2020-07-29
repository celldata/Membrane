package cloud.celldata.membrane.mapper;


import cloud.celldata.membrane.pojo.vo.role.ApiBean;
import cloud.celldata.membrane.pojo.vo.role.LabelBean;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 功能权限Mapper
 */
public interface FunctionAuthorityMapper {

    /**
     * 根据roleId查询功能权限集合
     * @param roleId 角色id
     * @return 权限列表
     */
    List<ApiBean> selectFunctionAuthorityByRoleId(Integer roleId);

    /**
     * 根据平台id查询功能list
     * @param clientId 平台id
     * @return 功能权限列表
     */
    List<LabelBean> selectFunctionAuthorityByClientId(@Param("clientId") Integer clientId);

    /**
     * 根据叶子节点查询树结构(结构是3层)
     * @param ids 叶子节点id
     * @return 功能权限列表
     */
    List<LabelBean> selectThreeAuthorityByIds(@Param("ids") List<Integer> ids);

    /**
     * 根据叶子节点查询树结构(结构是2层)
     * @param ids 叶子节点id
     * @return 功能权限列表
     */
    List<LabelBean> selectTwoAuthorityByIds(@Param("ids") List<Integer> ids);

}
