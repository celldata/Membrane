package cloud.celldata.membrane.pojo.entity;

import java.util.Date;

/**
 * 数据权限实体
 */
public class DataApiEntity {

    // 数据权限id
    private Integer id;

    // 数据权限名称
    private String name;

    // 数据权限描述
    private String desc;

    // 平台id
    private Integer clientId;

    // 是否全部数据权限
    private Integer isAll;

    // 是否逻辑删除：0已删除，1未删除
    private Integer enableFlag;

    // 创建者id
    private Integer creatorId;

    // 创建时间
    private Date createTime;

    // 更新者id
    private Integer updateId;

    // 更新时间
    private Date updateTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Integer getClientId() {
        return clientId;
    }

    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }

    public Integer getIsAll() {
        return isAll;
    }

    public void setIsAll(Integer isAll) {
        this.isAll = isAll;
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
}
