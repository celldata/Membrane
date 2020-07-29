package cloud.celldata.membrane.mapper;



import cloud.celldata.membrane.pojo.vo.role.DataAuthorityBean;
import cloud.celldata.membrane.pojo.vo.role.DataInfoBean;

import java.util.List;

/**
 * 功能权限Mapper
 */
public interface DataAuthorityMapper {

     /**
      * 根据平台id查询所有数据权限
      * @param clientId 平台id
      * @return 数据权限列表
      */
     List<DataAuthorityBean> selectDataAuthorityByClientId(Integer clientId);

     /**
      * 根据角色id查询权限信息
      * @param roleId 角色id
      * @return 数据权限列表
      */
     List<DataInfoBean> selctDataAuthorityByRoleId(Integer roleId);

}
