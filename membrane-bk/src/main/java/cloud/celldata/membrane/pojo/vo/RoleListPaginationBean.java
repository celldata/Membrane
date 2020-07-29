package cloud.celldata.membrane.pojo.vo;



/**
 * 角色分页信息实体
 *
 */
public class RoleListPaginationBean extends PaginationBean {

    // 平台id
    private Integer clientId;

    public Integer getClientId() {
        return clientId;
    }

    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }
}
