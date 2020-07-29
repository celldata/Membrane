package cloud.celldata.membrane.pojo;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 响应参数常量字典
 *
 */
public class ResponseCode {
    private static String unknownErrorMessageKey = "unknownErrorMessageKey";

    public static String getMessageKeyByCode(Integer value) {

        List<Field> fields = Arrays.asList(ResponseCode.class.getFields());
        List<Field> matchFields = fields.stream().filter(field -> {
            try{
                return Integer.parseInt(field.get(null).toString()) == value;
            }catch(Exception e){
                return false;}
        }).collect(Collectors.toList());
        Field matchField = matchFields.size() > 0 ? matchFields.get(0) : null;
        if(matchField != null){
            return  matchField.getName();
        }else {
            return unknownErrorMessageKey;
        }
    }

    public static String APP_KEY= "SSO";
    public static String FAILURE = "操作失败";
    public static String RESET_PASSWORD="abc123";

    public static final Integer SUCCESS_PROCESSED = 0;
    public static final Integer GENERIC_FAILURE = 1;
    public static final Integer USER_LOGIN_FAILURE = 1004;
    public static final Integer USER_NAME_IS_NULL = 1005;
    public static final Integer PASSWORD_IS_NULL = 1006;
    public static final Integer USER_FULL_NAME_IS_NULL = 1007;
    public static final Integer USER_EMAIL_IS_NULL = 1008;
    public static final Integer USER_CELL_IS_NULL = 1009;
    public static final Integer USER_EMAIL_INCORRECT = 1010;
    public static final Integer USER_CELL_INCORRECT = 1011;
    public static final Integer ROLE_IS_NULL = 2001 ;
    public static final Integer USER_EXISTS = 2008;
    public static final Integer USER_CELL_EXISTS = 2002;
    public static final Integer USER_EMAIL_EXISTS = 2003;
    public static final Integer SMS_SERVER_ERROR = 2004;
    public static final Integer SMS_AGENT_ERROR = 2005;
    public static final Integer SMS_SEND_ERROR = 2006;
    public static final Integer ROLE_NAME_EXISTS = 2007;
    public static final Integer USER_IS_INACTIVE = 3001;
    public static final Integer APP_ID_IS_NULL = 4001;
    public static final Integer APP_ID_IS_INVALID =4010;
    public static final Integer JWT_DECODE_FAILURE = 4002;
    public static final Integer ORIGIN_PASSWORD_IS_NULL = 106;
    public static final Integer NEW_PASSWORD_IS_NULL = 107;
    public static final Integer PASSWORD_INCONSISTENT = 108;
    public static final Integer ORIGIN_PASSWORD_INCORRECT = 109;
    public static final Integer PASSWORD_NOT_CHANGE = 110;
    public static final Integer PASSWORD_SCHEMA_INCORRECT = 111;
    public static final Integer AUTHORITY_ERROR = 1021;
    public static final Integer CLAIMS_UPDATED = 1022;
    public static final Integer TOKEN_IS_INVALID = 4099;
    public static final Integer EMAIL_SEND_ERROR = 4012;
    public static final Integer USER_NAME_NOT_EXIST = 4013;
    public static final Integer VERIFICATION_IS_INVALID = 4014;
    public static final Integer VERIFICATION_IS_EXPIRED = 4015;
    public static final Integer PARAMETER_INVALID = 1049;
    public static final Integer APP_NAME_EXISTS = 5001;
    public static final Integer APP_ID_EXISTS = 5002;
    public static final Integer MODULE_NAME_EXISTS = 5003;
    public static final Integer MODULE_NOT_EMPTY = 5004;
    public static final Integer FUNCTION_NAME_EXISTS = 5005;
    public static final Integer APP_NOT_EMPTY = 5006;
    public static final Integer APPP_ROLE_NOT_EMPTY = 5007;
    public static final Integer EXTERNAL_AUTH_NAME_EXISTS = 5008;
    public static final Integer EXTERNAL_AUTH_HAVE_ALREADY_USED = 5009;
    public static final Integer EXTERNAL_AUTH_USER_VALIDATE_FAILURE = 5010;
    public static final Integer EXTERNAL_AUTH_ERROR = 5011;
    public static final Integer DATA_CONFIG_NAME_EXISTS = 5012;
    public static final Integer DATA_CONFIG_NOT_EMPTY = 5013;



    public static final Integer ILLEGAL_PARAMETER = 6001;
    public static final Integer DATA_ATTRIBUTE_REPEAT = 6002;
    public static final Integer DATA_SCREE_REPEAT = 6003;
    public static final Integer ATTRIBUTE_USED_BY_RULE = 6004;

}

