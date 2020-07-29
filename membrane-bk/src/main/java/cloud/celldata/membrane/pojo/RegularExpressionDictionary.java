package cloud.celldata.membrane.pojo;

/**
 * 正则表达式字典
 *
 * @ProjectName: membrane
 * @Package: cloud.celldata.membrane.pojo
 * @ClassName: RegularExpressionDictionary
 * @Description: 正则表达式字典
 * @Author: jiwang
 * @CreateDate: 2020/5/19 15:07
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/5/19 15:07
 */
public class RegularExpressionDictionary {

    private RegularExpressionDictionary() {
        throw new IllegalStateException("Utility class");
    }

    public static final String PASSWORD_REGEX= "^[a-zA-Z][a-zA-Z0-9]{5,11}$";

    public static final String EMAIL_REGEX ="^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$";

    public static final String TELEPHONE_REGEX = "^[1][34578]\\d{9}$";

}
