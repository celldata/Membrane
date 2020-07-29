package cloud.celldata.membrane.pojo.vo;

import cloud.celldata.membrane.pojo.enumeration.VerificationFlagEnum;

/**
 * 忘记密码接收实体
 *
 * @ProjectName: membrane
 * @Package: cloud.celldata.membrane.pojo.vo.bean
 * @ClassName: ForgetPassWordBean
 * @Description: 忘记密码接收实体
 * @Author: jiwang
 * @CreateDate: 2020/5/21 17:02
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/5/21 17:02
 */
public class ForgetPassWordBean {

    // 用户名
    private String userName;

    //密码
    private String passWord;

    //验证码
    private String verification;

    //验证方式 0代表邮箱验证，1代表手机验证。
    private VerificationFlagEnum flag;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getVerification() {
        return verification;
    }

    public void setVerification(String verification) {
        this.verification = verification;
    }

    public VerificationFlagEnum getFlag() {
        return flag;
    }

    public void setFlag(VerificationFlagEnum flag) {
        this.flag = flag;
    }
}

