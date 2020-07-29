package cloud.celldata.membrane.pojo.vo.role;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

/**
 * 标签实体
 *
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LabelBean {

    // 标签id
    private Integer id;

    // 标签名称
    private String label;

    // 是否有数据权限
    private Integer haveData;

    // 下一级标签列表
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<LabelBean> children;

    public Integer getHaveData() {
        return haveData;
    }

    public void setHaveData(Integer haveData) {
        this.haveData = haveData;
    }

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

    public List<LabelBean> getChildren() {
        return children;
    }

    public void setChildren(List<LabelBean> children) {
        this.children = children;
    }
}
