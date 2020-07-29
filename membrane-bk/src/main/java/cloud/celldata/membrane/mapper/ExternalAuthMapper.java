package cloud.celldata.membrane.mapper;

import cloud.celldata.membrane.pojo.entity.ExternalAuthEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 外部数据源操作Mapper
 *
 * @ProjectName: membrane
 * @Package: cloud.celldata.membrane.mapper
 * @ClassName: ExternalAuthMapper
 * @Description: 外部数据源操作类
 * @Author: jiwang
 * @CreateDate: 2020/6/23 10:37
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/6/23 10:37
 */
public interface ExternalAuthMapper {


    /**
     * 新增外部认证
     * @param externalAuthEntity 外部认证实体
     * @param userId 用户Id
     */
    void addExternalAuth(@Param("auth")ExternalAuthEntity externalAuthEntity,@Param("userId") Integer userId);

    /**
     * 外部认证列表查询
     * @param id 主键查询
     * @param type 类型查询
     * @param name 名称模糊查询
     * @return 对象List
     */
    List<ExternalAuthEntity> selectAllExternalAuth(@Param("id")Integer id, @Param("type")Integer type, @Param("name")String name);

    /**
     * 查询修改认证源名称是否重复
     * @param id  主键ID
     * @param name  认证源名称
     * @return 数量
     */
    Integer countExternalAuthNameByName(@Param("id")Integer id, @Param("name")String name);

    /**
     * 外部认证源修改
     * @param externalAuthEntity 修改实体
     * @param userId 用户Id
     */
    void updateExternalAuth(@Param("auth")ExternalAuthEntity externalAuthEntity, @Param("userId")Integer userId);

    /**
     * 外部认证删除
     * @param idList 删除认证源 主键数组
     * @param userId 用户ID
     */
    void removeExternalAuth(@Param("idList")List<Integer> idList, @Param("userId")Integer userId);

    /**
     * 统计外部数据源被多少应用使用
     * @param idList 外部数据源id list
     * @return 使用数量
     */
    Integer countAppUseExternalAuth(@Param("idList")List<Integer> idList);
}
