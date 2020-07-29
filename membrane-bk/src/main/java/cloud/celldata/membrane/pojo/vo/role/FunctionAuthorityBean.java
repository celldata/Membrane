package cloud.celldata.membrane.pojo.vo.role;

import cloud.celldata.membrane.pojo.vo.ModuleBean;

import java.util.List;

/**
 * 功能权限实体
 *
 */
public class FunctionAuthorityBean {

    // 是否全部功能权限
    private Boolean allFunctionAuthority;

    // 功能权限列表
    private List<ModuleBean> functionAuthority;

    // 有权限的功能id列表
    private List<Integer> haveAuthority;

    public Boolean getAllFunctionAuthority() {
        return allFunctionAuthority;
    }

    public void setAllFunctionAuthority(Boolean allFunctionAuthority) {
        this.allFunctionAuthority = allFunctionAuthority;
    }

    public List<ModuleBean> getFunctionAuthority() {
        return functionAuthority;
    }

    public void setFunctionAuthority(List<ModuleBean> functionAuthority) {
        this.functionAuthority = functionAuthority;
    }

    public List<Integer> getHaveAuthority() {
        return haveAuthority;
    }

    public void setHaveAuthority(List<Integer> haveAuthority) {
        this.haveAuthority = haveAuthority;
    }
}
