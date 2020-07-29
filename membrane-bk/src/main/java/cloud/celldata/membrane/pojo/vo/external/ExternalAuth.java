package cloud.celldata.membrane.pojo.vo.external;

import java.util.Date;

/**
 * 外部认证实体父类
 *
 * @ProjectName: membrane
 * @Package: cloud.celldata.membrane.pojo.vo.external
 * @ClassName: external
 * @Description: java类作用描述
 * @Author: 外部认证实体父类
 * @CreateDate: 2020/6/22 15:36
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/6/22 15:36
 */
public class ExternalAuth {

    //外部认证id
    private Integer id;

    //外部认证 类型
    private Integer type;

    //外部认证名
    private String name;

    //外部认证描述
    private String desc;

    //创建时间
    private Date createTime;

    //创建用户名
    private String createName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreateName() {
        return createName;
    }

    public void setCreateName(String createName) {
        this.createName = createName;
    }
}
