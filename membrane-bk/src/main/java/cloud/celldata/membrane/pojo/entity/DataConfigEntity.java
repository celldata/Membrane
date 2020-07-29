package cloud.celldata.membrane.pojo.entity;

import java.util.Date;

/**
 * 数据权限基础配置实体
 *
 * @ProjectName: membrane
 * @Package: cloud.celldata.membrane.pojo.entity
 * @ClassName: DataConfigEntity
 * @Description: 数据权限基础配置
 * @Author: jiwang
 * @CreateDate: 2020/7/16 16:07
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/7/16 16:07
 */
public class DataConfigEntity {

    //主键ID
    private Integer id;

    //应用ID
    private Integer clientId;

    //资源标识符
    private String tag;

    //描述
    private String desc;

    //资源名称
    private String name;

    //资源 类型 0代表顶级资源 1代表非顶级资源
    private Integer type;

    //创建用户
    private String userName;

    //创建时间
    private Date createTime;

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

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

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
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
