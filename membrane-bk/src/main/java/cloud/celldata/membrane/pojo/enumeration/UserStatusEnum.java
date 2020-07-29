package cloud.celldata.membrane.pojo.enumeration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户"启用-禁用"状态枚举
 */
public enum UserStatusEnum {

    ACTIVE(1, "已启用"),
    REACTIVE(2, "已禁用");

    // 状态码
    private Integer code;

    // 状态
    private String name;

    /**
     * 根据状态码查询状态
     * @param code 状态码
     * @return 状态
     */
    public static UserStatusEnum getStatusByCode(Integer code) {
        for (UserStatusEnum status : UserStatusEnum.values()) {
            if (status.getCode().equals(code)) {
                return status;
            }
        }
        return null;
    }

    // 用户"启用-禁用"状态列表
    public static List<Map<String, Object>> userStatusMapList = new ArrayList<>(2);

    // 初始化用户"启用-禁用"状态列表
    static {
        for (UserStatusEnum status : UserStatusEnum.values()) {
            Map<String, Object> entry = new HashMap<>(1);
            entry.put("code",status.getCode());
            entry.put("name",status.getName());
            userStatusMapList.add(entry);
        }
    }

    UserStatusEnum(Integer code, String name) {
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
