package cloud.celldata.membrane.pojo.enumeration;

import com.alibaba.fastjson.annotation.JSONType;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * token校验方式
 * 
 */
@JSONType(serializeEnumAsJavaBean = true)
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum TokenCheckTypeEnum {

    HIGH_SECURITY(0, "安全优先"),
    HIGH_PERFORMANCE(1, "性能优先");

    // 状态码
    private Integer typeCode;

    // 状态
    private String typeName;

    /**
     * 根据状态码查询状态
     * @param code 状态码
     * @return 状态
     */
    public static TokenCheckTypeEnum getStatusByCode(Integer code) {
        for (TokenCheckTypeEnum status : TokenCheckTypeEnum.values()) {
            if (status.getCode().equals(code)) {
                return status;
            }
        }
        return null;
    }

    // "安全优先-性能优先"状态列表
    public static List<Map<String, Object>> tokenCheckTypes = new ArrayList<>(2);

    // 初始化安全优先-性能优先"状态列表
    static {
        for (TokenCheckTypeEnum status : TokenCheckTypeEnum.values()) {
            Map<String, Object> entry = new HashMap<>(1);
            entry.put("typeCode",status.getCode());
            entry.put("typeName",status.getName());
            tokenCheckTypes.add(entry);
        }
    }

    TokenCheckTypeEnum(Integer code, String name) {
        this.typeCode = code;
        this.typeName = name;
    }

    public Integer getCode() {
        return typeCode;
    }

    public String getName() {
        return typeName;
    }

}
