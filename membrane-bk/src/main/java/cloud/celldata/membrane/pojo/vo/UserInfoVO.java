package cloud.celldata.membrane.pojo.vo;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;
import java.util.List;

/**
 * 用户信息实体
 *
 * @author wyw
 * @date 2019-04-24
 **/
public class UserInfoVO {

    // 用户id
    private Integer id;

    // 用户名
    private String userName;

    // 姓名
    private String fullName;

    // 手机号
    private String telephone;

    // 邮箱
    private String email;

    // 角色
    private String role;

    // 更新时间
    @JsonFormat(pattern="yyyy.MM.dd HH:mm:ss", timezone="GMT+8")
    private Date updateTime;

    // 更新者
    private String updater;

    // 状态
    private OptionVO status;

    // 角色列表
    private List<SelectedClientRoleVO> selectedRole;

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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getUpdater() {
        return updater;
    }

    public void setUpdater(String updater) {
        this.updater = updater;
    }

    public OptionVO getStatus() {
        return status;
    }

    public void setStatus(OptionVO status) {
        this.status = status;
    }

    public List<SelectedClientRoleVO> getSelectedRole() {
        return selectedRole;
    }

    public void setSelectedRole(List<SelectedClientRoleVO> selectedRole) {
        this.selectedRole = selectedRole;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
