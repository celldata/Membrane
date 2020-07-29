package cloud.celldata.membrane.mapper;

import cloud.celldata.membrane.pojo.entity.DataAttributeEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 属性配置 Mapper
 *
 * @author wyw
 * @date 2020-07-16
 **/
public interface DataAttributeMapper {

    /**
     * 添加属性配置实体
     *
     * @param dataAttributeEntity 数据权限 属性配置实体
     * @param userId 用户ID
     */
    void addAttribute(@Param("dataAttributeEntity") DataAttributeEntity dataAttributeEntity,
                      @Param("userId") Integer userId);

    /**
     * 编辑属性配置实体
     *
     * @param dataAttributeEntity 数据权限 属性配置实体
     * @param userId 用户ID
     */
    void updateAttribute(@Param("dataAttributeEntity") DataAttributeEntity dataAttributeEntity,
                         @Param("userId") Integer userId);

    /**
     * 根据基础配置ID分页查询属性配置列表
     *
     * @param dataConfigId 基础配置ID
     * @return 同一基础配置下属性配置列表
     */
    List<DataAttributeEntity> selectAttributesByDataConfigId(@Param("dataConfigId") Integer dataConfigId);

    /**
     * 删除属性配置
     *
     * @param idList 属性配置ID列表
     * @param userId 用户ID
     */
    void removeAttribute(@Param("idList") List<Integer> idList, @Param("userId") Integer userId);

    /**
     * 按属性配置名称计数
     *
     * @param id 属性配置ID
     * @param dataAttributeName 属性配置名称
     * @return 相同属性配置名称记录条数
     */
    Integer countDataAttribute(@Param("id") Integer id, @Param("dataAttributeName") String dataAttributeName);

}
