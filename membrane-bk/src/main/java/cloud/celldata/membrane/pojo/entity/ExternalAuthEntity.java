package cloud.celldata.membrane.pojo.entity;

import java.util.Date;

/**
 * 外部认证源实体
 *
 * @ProjectName: membrane
 * @Package: cloud.celldata.membrane.pojo.vo.external
 * @ClassName: ExternalAuthEntity
 * @Description: 外部认证源实体
 * @Author: jiwang
 * @CreateDate: 2020/6/23 10:52
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/6/23 10:52
 */
public class ExternalAuthEntity {

    //外部认证id
    private Integer id;

    //外部认证 类型
    private Integer type;

    //外部认证名
    private String name;

    //ldap Base
    private String base;

    //Ldap url(如：ldap://127.0.0.1:389)
    private String url;

    //Ldap UserDn,如：cn = job,dc=xxx,dc=com
    private String userDn;

    //服务器连接密码
    private String pwd;

    //外部认证描述
    private String desc;

    //过滤条件中使用$userName$替换用户名（uid=$userName$）
    private String filter;

    //创建时间
    private Date createTime;

    //创建用户名
    private String createName;

    //是否同步数据 0代表不同步 1代表同步
    private Integer sycUser;

    //是否使用定时任务同步数据 0代表不使用 1代表使用
    private Integer sycSche;

    //定时任务 多长时间同步一次数据
    private Integer sycFixed;

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

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUserDn() {
        return userDn;
    }

    public void setUserDn(String userDn) {
        this.userDn = userDn;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getFilter() {
        return filter;
    }

    public void setFilter(String filter) {
        this.filter = filter;
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

    public Integer getSycUser() {
        return sycUser;
    }

    public void setSycUser(Integer sycUser) {
        this.sycUser = sycUser;
    }

    public Integer getSycSche() {
        return sycSche;
    }

    public void setSycSche(Integer sycSche) {
        this.sycSche = sycSche;
    }

    public Integer getSycFixed() {
        return sycFixed;
    }

    public void setSycFixed(Integer sycFixed) {
        this.sycFixed = sycFixed;
    }
}
