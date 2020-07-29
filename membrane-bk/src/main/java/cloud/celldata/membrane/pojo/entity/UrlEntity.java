package cloud.celldata.membrane.pojo.entity;

import java.util.Date;

/**
 * 功能权限功能对应URL
 *
 * @ProjectName: membrane
 * @Package: cloud.celldata.membrane.pojo.entity
 * @ClassName: UrlEntity
 * @Description: 功能权限功能对应URL
 * @Author: jiwang
 * @CreateDate: 2020/5/28 16:59
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/5/28 16:59
 */
public class UrlEntity {

    //url表主键ID
    private Integer id;

    //功能对应URL
    private String functionUrl;

    //功能对应类型 0[非查询]，1[查询]
    private Integer type;

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

    public String getFunctionUrl() {
        return functionUrl;
    }

    public void setFunctionUrl(String functionUrl) {
        this.functionUrl = functionUrl;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
