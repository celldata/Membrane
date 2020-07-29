package cloud.celldata.membrane.pojo.entity;

import java.util.Date;

/**
 * 角色实体
 */
public class RoleEntity {

    // 角色id
    private Integer id;

    // 角色名称
    private String roleName;

    // 平台id
    private Integer clientId;

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

    //是否为全部权限: 1代表全部权限 非1代表部分权限
    private Integer isAllApi;

    //是否为全部数据圈：1 代表全部权限 非1代表部分权限
    private Integer isAllData;

    public Integer getIsAllData() {
        return isAllData;
    }

    public void setIsAllData(Integer isAllData) {
        this.isAllData = isAllData;
    }

    public Integer getIsAllApi() {
        return isAllApi;
    }

    public void setIsAllApi(Integer isAllApi) {
        this.isAllApi = isAllApi;
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
}
