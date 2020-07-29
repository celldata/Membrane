package cloud.celldata.membrane.pojo.vo;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

/**
 * 级联下拉框选项实体
 *
 * @author wyw
 * @date 2019-04-24
 **/
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MultiSelectorOptionVO {

    // 值
    private Integer value;

    // 选项名称
    private String label;

    // 下一级选项列表
    private List<MultiSelectorOptionVO> children;

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public List<MultiSelectorOptionVO> getChildren() {
        return children;
    }

    public void setChildren(List<MultiSelectorOptionVO> children) {
        this.children = children;
    }

    public static final class MultiSelectorOptionVOBuilder {
        private Integer value;
        private String label;
        private List<MultiSelectorOptionVO> children;

        private MultiSelectorOptionVOBuilder() {
        }

        public static MultiSelectorOptionVOBuilder aMultiSelectorOptionVO() {
            return new MultiSelectorOptionVOBuilder();
        }

        public MultiSelectorOptionVOBuilder withValue(Integer value) {
            this.value = value;
            return this;
        }

        public MultiSelectorOptionVOBuilder withLabel(String label) {
            this.label = label;
            return this;
        }

        public MultiSelectorOptionVOBuilder withChildren(List<MultiSelectorOptionVO> children) {
            this.children = children;
            return this;
        }

        public MultiSelectorOptionVO build() {
            MultiSelectorOptionVO multiSelectorOptionVO = new MultiSelectorOptionVO();
            multiSelectorOptionVO.setValue(value);
            multiSelectorOptionVO.setLabel(label);
            multiSelectorOptionVO.setChildren(children);
            return multiSelectorOptionVO;
        }
    }
}
