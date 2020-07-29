package cloud.celldata.membrane.utils;

import java.util.Random;

/**
 * 手机、邮箱验证码工具类
 * @ProjectName: membrane
 * @Package: cloud.celldata.membrane.utils
 * @ClassName: VerificationUtil
 * @Description: 手机 邮箱验证码
 * @Author: jiwang
 * @CreateDate: 2020/5/21 15:49
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/5/21 15:49
 */
public class VerificationUtil {

    private VerificationUtil() {
        throw new IllegalStateException("Utility class");
    }

    private static final String RULE_INVITATION_CODE = "23456789ABCDEFGHJKLMNPQRSTUVWXYZ";
    private static final int RULE_INVITATION_CODE_LENGTH = 32;
    private static final int RULE_EMAIL_CODE_LENGTH=8;

    //获取手机验证码
    public static String getPhoneVerification(){
        return String.valueOf(new Random().nextInt(899999) + 100000);
    }

    //获取邮箱验证码
    public static String getEmailVerification(){
        Random random = new Random();
        StringBuilder stringBuffer = new StringBuilder();
        for (int i = 0; i < RULE_EMAIL_CODE_LENGTH; i++) {
            int i1 = random.nextInt(RULE_INVITATION_CODE_LENGTH);
            char c = RULE_INVITATION_CODE.charAt(i1);
            stringBuffer.append(c);
        }
        return stringBuffer.toString();
    }

    //获取隐藏email
    public static String getHiddenEmail(String email){
        return email.replaceAll("(\\w?)(\\w+)(\\w)(@\\w+\\.[a-z]+(\\.[a-z]+)?)", "$1****$3$4");
    }

    //获取隐藏手机号
    public static String getHiddenPhone(String phone){
        return phone.replaceAll("(\\d{3})\\d{4}(\\d{4})","$1****$2");
    }



}
