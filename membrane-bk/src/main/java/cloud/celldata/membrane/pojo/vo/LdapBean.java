package cloud.celldata.membrane.pojo.vo;

import cloud.celldata.membrane.pojo.vo.external.ExternalAuth;
import cloud.celldata.membrane.pojo.vo.external.ExternalAuthInfo;
import com.alibaba.fastjson.JSON;

/**
 * ldap实体类
 *
 * @ProjectName: membrane
 * @Package: cloud.celldata.membrane.pojo.vo.external
 * @ClassName: LdapBean
 * @Description: ldap实体类
 * @Author: jiwang
 * @CreateDate: 2020/6/22 15:39
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/6/22 15:39
 */
public class LdapBean extends ExternalAuth {

    //Ldap url(如：ldap://127.0.0.1:389)
    private String url;

    //Ldap UserDn,如：cn = job,dc=xxx,dc=com
    private String userDn;

    //Ldap服务器连接密码
    private String pwd;

    //过滤条件中使用$userName$替换用户名（uid=$userName$）
    private String filter;

    // ldap base
    private String base;

    //是否同步数据 0代表不同步 1代表同步
    private Integer sycUser;

    //是否使用定时任务同步数据 0代表不使用 1代表使用
    private Integer sycSche;

    //定时任务 多长时间同步一次数据
    private Integer sycFixed;

    public LdapBean(){}

    public LdapBean(ExternalAuthInfo externalAuthInfo){
        LdapBean ldapBean = JSON.parseObject(JSON.toJSONString(externalAuthInfo.getParameters()), LdapBean.class);
        this.url = ldapBean.getUrl();
        this.userDn = ldapBean.getUserDn();
        this.pwd = ldapBean.getPwd();
        this.filter = ldapBean.getFilter();
        this.base = ldapBean.getBase();
        this.sycUser = ldapBean.getSycUser();
        this.sycSche = ldapBean.getSycSche();
        this.sycFixed = ldapBean.getSycFixed();

        super.setName(externalAuthInfo.getName());
        super.setDesc(externalAuthInfo.getDesc());
        super.setType(externalAuthInfo.getType());
        super.setId(externalAuthInfo.getId());
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

    public String getFilter() {
        return filter;
    }

    public void setFilter(String filter) {
        this.filter = filter;
    }

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
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
