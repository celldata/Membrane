package cloud.celldata.membrane.pojo.entity;

import java.util.Date;

/**
 * 功能权限实体
 */
public class ApiEntity {

    // 功能权限id
    private Integer id;

    // 父级节点id
    private Integer parentId;

    // 平台id
    private Integer clientId;

    // 是否逻辑删除：0表示删除，1表示未删除
    private Integer enableFlag;

    // 创建者id
    private Integer creatorId;

    // 创建时间
    private Date createTime;

    // 编辑者id
    private Integer updateId;

    // 编辑时间
    private Date updateTime;

    // 功能权限名称
    private String apiName;

    // 层级
    private Integer apiNum;

    //是否是叶子节点 1代表是叶子节点 非0代表不是
    private Integer isTree;

    private Integer type;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public Integer getClientId() {
        return clientId;
    }

    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }

    public Integer getEnableFlag() {
        return enableFlag;
    }

    public void setEnableFlag(Integer enableFlag) {
        this.enableFlag = enableFlag;
    }

    public Integer getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Integer creatorId) {
        this.creatorId = creatorId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getUpdateId() {
        return updateId;
    }

    public void setUpdateId(Integer updateId) {
        this.updateId = updateId;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getApiName() {
        return apiName;
    }

    public void setApiName(String apiName) {
        this.apiName = apiName;
    }

    public Integer getApiNum() {
        return apiNum;
    }

    public void setApiNum(Integer apiNum) {
        this.apiNum = apiNum;
    }

    public Integer getIsTree() {
        return isTree;
    }

    public void setIsTree(Integer isTree) {
        this.isTree = isTree;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
