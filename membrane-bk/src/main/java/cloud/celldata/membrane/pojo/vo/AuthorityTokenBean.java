package cloud.celldata.membrane.pojo.vo;

import java.util.List;

/**
 * 鉴权信息实体
 *
 */
public class AuthorityTokenBean {

    // 功能权限id
    private Integer id;

    // 功能权限名称
    private String label;

    // 是否有数据权限
    private Integer haveData;

    // 数据权限名称集合
    private List<String> dataOauther;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Integer gethaveData() {
        return haveData;
    }

    public void sethaveData(Integer haveData) {
        this.haveData = haveData;
    }

    public List<String> getdataOauther() {
        return dataOauther;
    }

    public void setdataOauther(List<String> dataOauther) {
        this.dataOauther = dataOauther;
    }
}
