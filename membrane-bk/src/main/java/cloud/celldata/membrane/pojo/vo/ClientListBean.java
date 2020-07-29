package cloud.celldata.membrane.pojo.vo;

import java.util.Date;
import java.util.List;

/**
 * 应用列表返回信息实体
 */
public class ClientListBean {

    // 平台id
    private Integer clientId;

    //认证状态
    private Integer authentication;

    //认证方式
    private Integer verification;

    //验证方式
    private Integer tokenCheckType;

    //Access Token 有效时间
    private Integer accessValidity;

    // 平台名称
    private String clientName;

    //创建时间
    private Date createTime;

    //修改(编辑)时间
    private Date updateTime;

    //appId
    private String appId;

    //应用图片地址
    private String url;

    //app Secret
    private String secret;

    //创建ID
    private Integer creatorId;

    //编辑ID
    private Integer updaterId;

    //应用描述
    private String appDesc;

    //创建用户 用户名
    private String creatorUserName;

    //功能权限
    private List<ModuleBean> moduleList;

    //角色列表
    private List<RoleIdAndNameBean> roleList;

    //外部数据源认证Id
    private Integer externalAuthId;

    public Integer getExternalAuthId() {
        return externalAuthId;
    }

    public void setExternalAuthId(Integer externalAuthId) {
        this.externalAuthId = externalAuthId;
    }

    public List<RoleIdAndNameBean> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<RoleIdAndNameBean> roleList) {
        this.roleList = roleList;
    }

    public String getCreatorUserName() {
        return creatorUserName;
    }

    public void setCreatorUserName(String creatorUserName) {
        this.creatorUserName = creatorUserName;
    }

    public String getAppDesc() {
        return appDesc;
    }

    public void setAppDesc(String appDesc) {
        this.appDesc = appDesc;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Integer creatorId) {
        this.creatorId = creatorId;
    }

    public Integer getUpdaterId() {
        return updaterId;
    }

    public void setUpdaterId(Integer updaterId) {
        this.updaterId = updaterId;
    }

    public Integer getClientId() {
        return clientId;
    }

    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }

    public Integer getAuthentication() {
        return authentication;
    }

    public void setAuthentication(Integer authentication) {
        this.authentication = authentication;
    }

    public Integer getVerification() {
        return verification;
    }

    public void setVerification(Integer verification) {
        this.verification = verification;
    }

    public Integer getTokenCheckType() {
        return tokenCheckType;
    }

    public void setTokenCheckType(Integer tokenCheckType) {
        this.tokenCheckType = tokenCheckType;
    }

    public Integer getAccessValidity() {
        return accessValidity;
    }

    public void setAccessValidity(Integer accessValidity) {
        this.accessValidity = accessValidity;
    }

    public List<ModuleBean> getModuleList() {
        return moduleList;
    }

    public void setModuleList(List<ModuleBean> moduleList) {
        this.moduleList = moduleList;
    }
}
