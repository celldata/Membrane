package cloud.celldata.membrane.pojo.entity;

import java.util.Date;

/**
 * 功能和URL关联关系实体类
 *
 * @ProjectName: membrane
 * @Package: cloud.celldata.membrane.pojo.entity
 * @ClassName: ApiUrlEntity
 * @Description: 功能和URL对应中间表
 * @Author: jiwang
 * @CreateDate: 2020/5/28 16:54
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/5/28 16:54
 */
public class ApiUrlEntity {

    //功能权限表Id
    private Integer apiId;

    //url表Id
    private Integer functionUrlId;

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


    public Integer getApiId() {
        return apiId;
    }

    public void setApiId(Integer apiId) {
        this.apiId = apiId;
    }

    public Integer getFunctionUrlId() {
        return functionUrlId;
    }

    public void setFunctionUrlId(Integer functionUrlId) {
        this.functionUrlId = functionUrlId;
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
