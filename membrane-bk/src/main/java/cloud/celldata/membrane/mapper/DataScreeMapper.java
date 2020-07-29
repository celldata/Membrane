package cloud.celldata.membrane.mapper;

import cloud.celldata.membrane.pojo.entity.DataScreeEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 条件配置 Mapper
 *
 * @author wyw
 * @date 2020-07-20
 **/
public interface DataScreeMapper {

    /**
     * 添加条件配置
     *
     * @param dataScreeEntity 数据权限 条件配置实体
     * @param userId 用户ID
     */
    void addScree(@Param("dataScreeEntity") DataScreeEntity dataScreeEntity, @Param("userId") Integer userId);

    /**
     * 编辑条件配置
     *
     * @param dataScreeEntity 数据权限 条件配置实体
     * @param userId 用户ID
     */
    void updateScree(@Param("dataScreeEntity") DataScreeEntity dataScreeEntity, @Param("userId") Integer userId);

    /**
     * 根据ClientId分页查询条件配置列表
     *
     * @param clientId 应用ID
     * @return 同一应用下条件配置列表
     */
    List<DataScreeEntity> selectScreesByClientId(@Param("clientId") Integer clientId);

    /**
     * 根据roleId查询条件配置列表
     *
     * @param roleId 角色ID
     * @return 同一角色ID下条件配置列表
     */
    List<DataScreeEntity> selectScreesByRoleId(@Param("roleId") Integer roleId);


    /**
     * 根据roleIdList查询条件配置列表
     *
     * @param idList 角色ID List
     * @return 同一角色ID下条件配置列表
     */
    List<DataScreeEntity> selectScreesByRoleIdList(@Param("idList") List<Integer> idList);

    /**
     * 删除条件配置
     *
     * @param idList 条件配置ID列表
     * @param userId 用户ID
     */
    void removeScree(@Param("idList") List<Integer> idList, @Param("userId") Integer userId);

    /**
     * 按表达式名称计数
     *
     * @param id 条件配置ID
     * @param scrName 表达式名称
     * @return 相同表达式名称记录条数
     */
    Integer countScree(@Param("id") Integer id, @Param("scrName") String scrName);

}
