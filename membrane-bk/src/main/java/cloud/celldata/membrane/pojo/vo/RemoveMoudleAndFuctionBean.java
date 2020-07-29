package cloud.celldata.membrane.pojo.vo;

import java.util.List;

/**
 * 删除 模块 功能 实体
 */
public class RemoveMoudleAndFuctionBean {


    //应用ID
    private Integer clientId;

    //模块ID 或者功能ID list;
    private List<Integer> ids;


    public Integer getClientId() {
        return clientId;
    }

    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }

    public List<Integer> getIds() {
        return ids;
    }

    public void setIds(List<Integer> ids) {
        this.ids = ids;
    }
}
