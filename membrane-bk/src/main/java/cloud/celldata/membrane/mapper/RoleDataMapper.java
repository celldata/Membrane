package cloud.celldata.membrane.mapper;

import cloud.celldata.membrane.pojo.entity.RoleData;

import java.util.List;

/**
 * @ProjectName: membrane
 * @Package: cloud.celldata.membrane.mapper
 * @ClassName: RoleDataMapper
 * @Description: 角色对应数据权限Mapper
 * @Author: jiwang
 * @CreateDate: 2020/7/23 15:52
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/7/23 15:52
 */
public interface RoleDataMapper {

    /**
     * 插入角色功能权限
     * @param roleDataList 角色列表
     * @return 新增角色记录条数
     */
    int insertRoleData(List<RoleData> roleDataList);
}
