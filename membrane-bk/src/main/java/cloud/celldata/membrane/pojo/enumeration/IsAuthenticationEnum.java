package cloud.celldata.membrane.pojo.enumeration;

import com.alibaba.fastjson.annotation.JSONType;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 应用"未认证-已认证"状态枚举
 */
@JSONType(serializeEnumAsJavaBean = true)
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum IsAuthenticationEnum {

    UNVERIFIED(0, "内部认证"),
    VERIFIED(1, "外部认证", AuthenticationEnum.authenticationMapList);

    // 状态码
    private Integer authenticationCode;

    // 状态
    private String name;

    //认证方式
    private List<Map<String, Object>> authenticationEnumList;

    /**
     * 根据状态码查询状态
     * @param code 状态码
     * @return 状态
     */
    public static IsAuthenticationEnum getStatusByCode(Integer code) {
        for (IsAuthenticationEnum status : IsAuthenticationEnum.values()) {
            if (status.getCode().equals(code)) {
                return status;
            }
        }
        return null;
    }

    // "未认证-已认证"列表
    public static List<Map<String, Object>> isAuthenticationMapList = new ArrayList<>(2);

    // 未认证-已认证"列表
    static {
        for (IsAuthenticationEnum status : IsAuthenticationEnum.values()) {
            Map<String, Object> entry = new HashMap<>(1);
            entry.put("authenticationCode",status.getCode());
            entry.put("name",status.getName());
            entry.put("authenticationEnumList",status.getAuthenticationEnumList());
            isAuthenticationMapList.add(entry);
        }
    }

    IsAuthenticationEnum(Integer code, String name,List<Map<String, Object>> authenticationEnumList) {
        this.authenticationCode = code;
        this.name = name;
        this.authenticationEnumList = authenticationEnumList;

    }
    IsAuthenticationEnum(Integer code, String name) {
        this.authenticationCode = code;
        this.name = name;
    }

    public Integer getCode() {
        return authenticationCode;
    }

    public String getName() {
        return name;
    }

    public List<Map<String, Object>> getAuthenticationEnumList() {
        return authenticationEnumList;
    }
}
