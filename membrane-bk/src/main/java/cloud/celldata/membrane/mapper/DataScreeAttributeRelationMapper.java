package cloud.celldata.membrane.mapper;

import cloud.celldata.membrane.pojo.entity.DataScreeAttributeRelationEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 条件配置与资源和属性的关系 Mapper
 *
 * @author wyw
 * @date 2020-07-22
 **/
public interface DataScreeAttributeRelationMapper {

    void batchAddRelations(@Param("relations") List<DataScreeAttributeRelationEntity> dataScreeAttributeEntities,
                           @Param("userId") Integer userId);

    void batchDeleteRelations(@Param("screeIds") List<Integer> screeIds, @Param("userId") Integer userId);

    List<DataScreeAttributeRelationEntity> selectByScreeIds(@Param("screeIds") List<Integer> screeIds);

    Integer countByAttIds(@Param("attIds") List<Integer> attIds);

}
