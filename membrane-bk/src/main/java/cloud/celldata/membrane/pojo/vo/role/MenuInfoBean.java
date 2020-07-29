package cloud.celldata.membrane.pojo.vo.role;

import java.util.List;

/**
 * 菜单信息实体
 *
 */
public class MenuInfoBean {

    // 菜单id
    private Integer id;

    // 菜单名称
    private String name;

    // 是否树节点
    private Integer tree;

    // 下一级菜单列表
    private List<MenuInfoBean> children;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getTree() {
        return tree;
    }

    public void setTree(Integer tree) {
        this.tree = tree;
    }

    public List<MenuInfoBean> getChildren() {
        return children;
    }

    public void setChildren(List<MenuInfoBean> children) {
        this.children = children;
    }
}
