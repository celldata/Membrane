package cloud.celldata.membrane.pojo.enumeration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 是否是编辑用户，还是改变用户状态枚举
 */
public enum UserChangeFlagEnum {

    COPYREADER(0, "编辑用户"),
    CHANGE(1, "改变用户状态");

    // 状态码
    private Integer code;

    // 状态
    private String name;

    /**
     * 根据状态码查询状态
     * @param code 状态码
     * @return 状态
     */
    public static UserChangeFlagEnum getStatusByCode(Integer code) {
        for (UserChangeFlagEnum status : UserChangeFlagEnum.values()) {
            if (status.getCode().equals(code)) {
                return status;
            }
        }
        return null;
    }


    protected static final List<Map<String, Object>> userChangeFlagMapList = new ArrayList<>(2);


    static {
        for (UserChangeFlagEnum status : UserChangeFlagEnum.values()) {
            Map<String, Object> entry = new HashMap<>(1);
            entry.put("code",status.getCode());
            entry.put("name",status.getName());
            userChangeFlagMapList.add(entry);
        }
    }

    UserChangeFlagEnum(Integer code, String name) {
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
