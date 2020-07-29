package cloud.celldata.membrane.pojo.entity;

import java.util.Date;

/**
 * 属性配置实体
 *
 * @author wyw
 * @date 2020-07-16
 **/
public class DataAttributeEntity {

    //主键ID
    private Integer id;

    //基础配置ID
    private Integer configId;

    //标识符
    private String attributeTag;

    //属性名
    private String attributeName;

    //数据类型 0 整数，1浮点，2布尔，3字符串，4枚举，5数组，6时间/日期
    private Integer attributeType;

    //描述
    private String attributeDesc;

    //是否参与组成规则: 0否 1是
    private Integer status;

    //创建用户
    private String userName;

    //创建时间
    private Date createTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getConfigId() {
        return configId;
    }

    public void setConfigId(Integer configId) {
        this.configId = configId;
    }

    public String getAttributeTag() {
        return attributeTag;
    }

    public void setAttributeTag(String attributeTag) {
        this.attributeTag = attributeTag;
    }

    public String getAttributeName() {
        return attributeName;
    }

    public void setAttributeName(String attributeName) {
        this.attributeName = attributeName;
    }

    public Integer getAttributeType() {
        return attributeType;
    }

    public void setAttributeType(Integer attributeType) {
        this.attributeType = attributeType;
    }

    public String getAttributeDesc() {
        return attributeDesc;
    }

    public void setAttributeDesc(String attributeDesc) {
        this.attributeDesc = attributeDesc;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
