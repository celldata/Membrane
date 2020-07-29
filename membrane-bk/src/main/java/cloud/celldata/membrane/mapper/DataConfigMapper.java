package cloud.celldata.membrane.mapper;

import cloud.celldata.membrane.pojo.entity.DataConfigEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 数据权限基础配置Mapper
 *
 * @ProjectName: membrane
 * @Package: cloud.celldata.membrane.mapper
 * @ClassName: DataConfigMapper
 * @Description: 数据权限基础配置Mapper
 * @Author: jiwang
 * @CreateDate: 2020/7/16 16:29
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/7/16 16:29
 */
public interface DataConfigMapper {

    /**
     * 查询数据权限基础配置名称是否重复
     * @param clientId 应用ID
     * @param name 名称
     * @param id id
     * @return
     */
    Integer countDataConfigName(@Param("clientId")Integer clientId,
                                @Param("name")String name,
                                @Param("id")Integer id);

    /**
     * 增加数据权限基础配置
     * @param dataConfigEntity 数据权限基础配置实体
     * @param userId 用户ID
     */
    void addDataConfig(@Param("dataConfig")DataConfigEntity dataConfigEntity,
                       @Param("userId")Integer userId);

    /**
     * 修改数据权限基础配置
     * @param dataConfigEntity 数据权限基础配置实体
     * @param userId  用户ID
     */
    void updateDataConfig(@Param("dataConfig")DataConfigEntity dataConfigEntity,
                          @Param("userId")Integer userId);

    /**
     * 删除 数据权限基础配置
     * @param idList 基础配置ID list
     * @param userId 用户id
     */
    void removeDataConfig(@Param("idList")List<Integer> idList, @Param("userId")Integer userId);

    /**
     * 查询 数据权限 基础配置list
     * @param clientId 应用ID
     * @param type 资源 类型 0代表顶级资源 1代表非顶级资源
     * @return
     */
    List<DataConfigEntity> selectDateConfigEntity(@Param("clientId")Integer clientId,
                                                  @Param("type")Integer type);

    /**
     * 统计 数据权限 基础配置 数量
     * @param clientId 应用ID
     * @param type 资源 类型 0代表顶级资源 1代表非顶级资源
     * @return 数量
     */
    Integer countDataConfig(@Param("clientId")Integer clientId,
                            @Param("type")Integer type);
}
