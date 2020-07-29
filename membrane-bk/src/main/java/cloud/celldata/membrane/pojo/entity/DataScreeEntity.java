package cloud.celldata.membrane.pojo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Date;
import java.util.List;

/**
 * 条件配置实体
 *
 * @author wyw
 * @date 2020-07-20
 **/
public class DataScreeEntity {

    //主键ID
    private Integer id;

    //应用ID
    private Integer clientId;

    //表达式名称
    private String scrName;

    //录入规则
    private String scrRule;

    //描述
    private String scrDesc;

    //表达式json串
    private String scrJson;

    //属性ID列表
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Integer[][] attIdList;

    //属性信息列表
    private List<DataScreeAttributeRelationEntity> attInfoList;

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

    public Integer getClientId() {
        return clientId;
    }

    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }

    public String getScrName() {
        return scrName;
    }

    public void setScrName(String scrName) {
        this.scrName = scrName;
    }

    public String getScrRule() {
        return scrRule;
    }

    public void setScrRule(String scrRule) {
        this.scrRule = scrRule;
    }

    public String getScrDesc() {
        return scrDesc;
    }

    public void setScrDesc(String scrDesc) {
        this.scrDesc = scrDesc;
    }

    public String getScrJson() {
        return scrJson;
    }

    public void setScrJson(String scrJson) {
        this.scrJson = scrJson;
    }

    public Integer[][] getAttIdList() {
        return attIdList;
    }

    public void setAttIdList(Integer[][] attIdList) {
        this.attIdList = attIdList;
    }

    public List<DataScreeAttributeRelationEntity> getAttInfoList() {
        return attInfoList;
    }

    public void setAttInfoList(List<DataScreeAttributeRelationEntity> attInfoList) {
        this.attInfoList = attInfoList;
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
