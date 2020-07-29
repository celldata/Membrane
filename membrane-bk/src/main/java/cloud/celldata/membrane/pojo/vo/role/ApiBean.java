package cloud.celldata.membrane.pojo.vo.role;

/**
 * 功能权限信息实体
 *
 */
public class ApiBean {

    // 功能权限id
    private Integer apiId;

    // 功能权限描述
    private String apiDesc;

    // 功能层级
    private Integer apiNum;

    // 功能权限父id 用以区分最上级
    private Integer parentId;

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public Integer getApiId() {
        return apiId;
    }

    public void setApiId(Integer apiId) {
        this.apiId = apiId;
    }

    public String getApiDesc() {
        return apiDesc;
    }

    public void setApiDesc(String apiDesc) {
        this.apiDesc = apiDesc;
    }

    public Integer getApiNum() {
        return apiNum;
    }

    public void setApiNum(Integer apiNum) {
        this.apiNum = apiNum;
    }
}
