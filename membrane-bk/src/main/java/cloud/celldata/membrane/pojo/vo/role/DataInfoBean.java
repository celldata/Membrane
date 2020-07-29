package cloud.celldata.membrane.pojo.vo.role;

/**
 * 数据权限实体
 *
 */
public class DataInfoBean {

    // 数据权限id
    private Integer id;

    // 数据权限名称
    private String dataAuthorityName;

    // 平台id
    private Integer clientId;

    // 是否全部权限
    private int isAll;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDataAuthorityName() {
        return dataAuthorityName;
    }

    public void setDataAuthorityName(String dataAuthorityName) {
        this.dataAuthorityName = dataAuthorityName;
    }

    public Integer getClientId() {
        return clientId;
    }

    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }

    public int getIsAll() {
        return isAll;
    }

    public void setIsAll(int isAll) {
        this.isAll = isAll;
    }
}
