package cloud.celldata.membrane.pojo.entity;

/**
 * 条件配置与资源和属性的关系实体类
 *
 * @author wyw
 * @date 2020-07-22
 **/
public class DataScreeAttributeRelationEntity {

    //主键ID
    private Integer id;

    //条件ID
    private Integer screeId;

    //资源ID
    private Integer configId;

    //资源名称
    private String configName;

    //属性ID
    private Integer attributeId;

    //属性名称
    private String attributeName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getScreeId() {
        return screeId;
    }

    public void setScreeId(Integer screeId) {
        this.screeId = screeId;
    }

    public Integer getConfigId() {
        return configId;
    }

    public void setConfigId(Integer configId) {
        this.configId = configId;
    }

    public Integer getAttributeId() {
        return attributeId;
    }

    public void setAttributeId(Integer attributeId) {
        this.attributeId = attributeId;
    }

    public String getConfigName() {
        return configName;
    }

    public void setConfigName(String configName) {
        this.configName = configName;
    }

    public String getAttributeName() {
        return attributeName;
    }

    public void setAttributeName(String attributeName) {
        this.attributeName = attributeName;
    }

    public static final class DataScreeAttributeEntityBuilder {
        //主键ID
        private Integer id;
        //条件ID
        private Integer screeId;
        //资源ID
        private Integer configId;
        //属性ID
        private Integer attributeId;

        private DataScreeAttributeEntityBuilder() {
        }

        public static DataScreeAttributeEntityBuilder aDataScreeAttributeEntity() {
            return new DataScreeAttributeEntityBuilder();
        }

        public DataScreeAttributeEntityBuilder withId(Integer id) {
            this.id = id;
            return this;
        }

        public DataScreeAttributeEntityBuilder withScreeId(Integer screeId) {
            this.screeId = screeId;
            return this;
        }

        public DataScreeAttributeEntityBuilder withConfigId(Integer configId) {
            this.configId = configId;
            return this;
        }

        public DataScreeAttributeEntityBuilder withAttributeId(Integer attributeId) {
            this.attributeId = attributeId;
            return this;
        }

        public DataScreeAttributeRelationEntity build() {
            DataScreeAttributeRelationEntity dataScreeAttributeEntity = new DataScreeAttributeRelationEntity();
            dataScreeAttributeEntity.setId(id);
            dataScreeAttributeEntity.setScreeId(screeId);
            dataScreeAttributeEntity.setConfigId(configId);
            dataScreeAttributeEntity.setAttributeId(attributeId);
            return dataScreeAttributeEntity;
        }
    }

}
