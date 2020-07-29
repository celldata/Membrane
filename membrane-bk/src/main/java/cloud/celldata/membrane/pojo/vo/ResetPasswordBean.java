package cloud.celldata.membrane.pojo.vo;

import cloud.celldata.membrane.pojo.enumeration.ResetPasswordFlagEnum;

/**
 * 修改重置密码参数实体
 *
 * @ProjectName: membrane
 * @Package: cloud.celldata.membrane.pojo.vo.bean
 * @ClassName: ResetPasswordBean
 * @Description: 修改重置密码参数实体
 * @Author: jiwang
 * @CreateDate: 2020/5/19 10:28
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/5/19 10:28
 */
public class ResetPasswordBean {

    // 用户id
    private Integer userId;

    // 原始密码
    private String oldPassword;

    // 新密码
    private String newPassword;

    // appId
    private String appId;

    //0代表重置密码，1代表修改密码
    private ResetPasswordFlagEnum flag;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public ResetPasswordFlagEnum getFlag() {
        return flag;
    }

    public void setFlag(ResetPasswordFlagEnum flag) {
        this.flag = flag;
    }
}
