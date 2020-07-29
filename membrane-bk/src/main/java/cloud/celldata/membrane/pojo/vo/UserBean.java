package cloud.celldata.membrane.pojo.vo;

import cloud.celldata.membrane.pojo.vo.SelectedClientRoleVO;

import java.util.List;

/**
 * 用户信息实体
 *
 **/
public class UserBean {

    // 用户id
    private Integer id;

    // 用户名
    private String userName;

    //密码
    private String passWord;

    // 姓名
    private String fullName;

    // 手机号
    private String telephone;

    // 邮箱
    private String email;

    // 角色列表
    private List<SelectedClientRoleVO> roles;

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public List<SelectedClientRoleVO> getRoles() {
        return roles;
    }

    public void setRoles(List<SelectedClientRoleVO> roles) {
        this.roles = roles;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
