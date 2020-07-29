package cloud.celldata.membrane.pojo.enumeration;

/**
 * 是否有功能权限枚举类型
 *
 */
public enum HaveAuthorityEnum {

    NOHAVEAUTHORITY(0, "没有权限"),
    HAVEAUTHORITY(1, "有该平台权限");

    // 类型编号
    private Integer code;

    // 类型名称
    private String name;

    HaveAuthorityEnum(Integer code, String name) {
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
