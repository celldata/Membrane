package cloud.celldata.membrane.pojo.vo;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;
import java.util.List;

/**
 * 模块实体
 *
 * @ProjectName: membrane
 * @Package: cloud.celldata.membrane.pojo.vo.bean
 * @ClassName: moduleBean
 * @Description: 模块实体
 * @Author: jiwang
 * @CreateDate: 2020/5/28 15:24
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/5/28 15:24
 */

public class ModuleBean implements Serializable {
    private static final long serialVersionUID = 1L;
    //模块ID
    private Integer moduleId;

    //应用ID
    private Integer clientId;

    //模块名
    private String moduleName;

    //模块对应功能
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<FunctionBean> functionList;

    public Integer getModuleId() {
        return moduleId;
    }

    public void setModuleId(Integer moduleId) {
        this.moduleId = moduleId;
    }

    public Integer getClientId() {
        return clientId;
    }

    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public List<FunctionBean> getFunctionList() {
        return functionList;
    }

    public void setFunctionList(List<FunctionBean> functionList) {
        this.functionList = functionList;
    }

}
