package cloud.celldata.membrane.pojo.enumeration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户重置密码修改密码状态枚举
 */
public enum ResetPasswordFlagEnum {

    RESET(0, "重置密码"),
    MODIFY(1, "修改密码");

    // 状态码
    private Integer code;

    // 状态
    private String name;

    /**
     * 根据状态码查询状态
     * @param code 状态码
     * @return 状态
     */
    public static ResetPasswordFlagEnum getStatusByCode(Integer code) {
        for (ResetPasswordFlagEnum status : ResetPasswordFlagEnum.values()) {
            if (status.getCode().equals(code)) {
                return status;
            }
        }
        return null;
    }


    protected static final List<Map<String, Object>> resetPasswordFlagMapList = new ArrayList<>(2);


    static {
        for (ResetPasswordFlagEnum status : ResetPasswordFlagEnum.values()) {
            Map<String, Object> entry = new HashMap<>(1);
            entry.put("code",status.getCode());
            entry.put("name",status.getName());
            resetPasswordFlagMapList.add(entry);
        }
    }

    ResetPasswordFlagEnum(Integer code, String name) {
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
