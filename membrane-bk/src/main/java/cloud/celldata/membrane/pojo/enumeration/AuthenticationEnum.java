package cloud.celldata.membrane.pojo.enumeration;

import com.alibaba.fastjson.annotation.JSONType;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 认证方式状态枚举
 */
@JSONType(serializeEnumAsJavaBean = true)
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum AuthenticationEnum {

    CAS(0, "CAS"),
    OAUTH(1, "OAUTH"),
    SAML(2, "SAML");


    // 状态码
    private Integer authenticationCode;

    // 状态
    private String name;

    /**
     * 根据状态码查询状态
     * @param code 状态码
     * @return 状态
     */
    public static AuthenticationEnum getStatusByCode(Integer code) {
        for (AuthenticationEnum status : AuthenticationEnum.values()) {
            if (status.getCode().equals(code)) {
                return status;
            }
        }
        return null;
    }

    // 认证方式列表
    public static List<Map<String, Object>> authenticationMapList = new ArrayList<>(3);

    // 认证方式列表
    static {
        for (AuthenticationEnum status : AuthenticationEnum.values()) {
            Map<String, Object> entry = new HashMap<>(1);
            entry.put("authenticationCode",status.getCode());
            entry.put("name",status.getName());
            authenticationMapList.add(entry);
        }
    }

    AuthenticationEnum(Integer code, String name) {
        this.authenticationCode = code;
        this.name = name;
    }

    public Integer getCode() {
        return authenticationCode;
    }

    public String getName() {
        return name;
    }

}
