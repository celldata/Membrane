package cloud.celldata.membrane.mapper;

import cloud.celldata.membrane.pojo.bo.PrivilegeRoleDTO;
import cloud.celldata.membrane.pojo.entity.DataApiEntity;
import cloud.celldata.membrane.pojo.entity.RoleDataEntity;

import java.util.List;

/**
 * 数据权限管理Mapper
 */
public interface DataApiMapper {

    /**
     * 根据角色ID获取角色信息
     * @param privilegeRoleDTO 查询条件
     * @return 角色列表
     */
    List<DataApiEntity> getDataByRoleId(PrivilegeRoleDTO privilegeRoleDTO);

    /**
     * 添加角色相关数据权限
     * @param roleDataEntity 数据权限
     * @return 新增记录条数
     */
    int addRoleData(RoleDataEntity roleDataEntity);

}
