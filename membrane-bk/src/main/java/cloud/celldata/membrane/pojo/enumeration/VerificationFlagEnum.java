package cloud.celldata.membrane.pojo.enumeration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 验证方式枚举
 */
public enum VerificationFlagEnum {

    EMAIL(0, "邮箱验证码验证"),
    PHONE(1, "手机验证码验证");

    // 状态码
    private Integer code;

    // 状态
    private String name;

    /**
     * 根据状态码查询状态
     * @param code 状态码
     * @return 状态
     */
    public static VerificationFlagEnum getStatusByCode(Integer code) {
        for (VerificationFlagEnum status : VerificationFlagEnum.values()) {
            if (status.getCode().equals(code)) {
                return status;
            }
        }
        return null;
    }


    protected static final List<Map<String, Object>> verificationFlagMapList = new ArrayList<>(2);


    static {
        for (VerificationFlagEnum status : VerificationFlagEnum.values()) {
            Map<String, Object> entry = new HashMap<>(1);
            entry.put("code",status.getCode());
            entry.put("name",status.getName());
            verificationFlagMapList.add(entry);
        }
    }

    VerificationFlagEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    public Integer getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

}
