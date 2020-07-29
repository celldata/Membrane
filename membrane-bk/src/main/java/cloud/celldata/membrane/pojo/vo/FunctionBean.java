package cloud.celldata.membrane.pojo.vo;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;
import java.util.List;

/**
 * 功能实体
 *
 * @ProjectName: membrane
 * @Package: cloud.celldata.membrane.pojo.vo.bean
 * @ClassName: FunctionBean
 * @Description: 功能实体
 * @Author: jiwang
 * @CreateDate: 2020/5/28 15:27
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/5/28 15:27
 */


public class FunctionBean implements Serializable{
    private static final long serialVersionUID = 1L;

    //功能ID
    private Integer functionId;

    //应用ID
    private Integer clientId;

    //功能名称
    private String functionName;

    //功能类型 0[非查询]，1[查询]
    private Integer apiType;

    //功能对应URL 一个功能可能对应多个URL
    private List<String> apiUrlList;

    //功能对应模块
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer moduleId;

    public Integer getFunctionId() {
        return functionId;
    }

    public void setFunctionId(Integer functionId) {
        this.functionId = functionId;
    }

    public Integer getClientId() {
        return clientId;
    }

    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }

    public String getFunctionName() {
        return functionName;
    }

    public void setFunctionName(String functionName) {
        this.functionName = functionName;
    }

    public Integer getApiType() {
        return apiType;
    }

    public void setApiType(Integer apiType) {
        this.apiType = apiType;
    }

    public List<String> getApiUrlList() {
        return apiUrlList;
    }

    public void setApiUrlList(List<String> apiUrlList) {
        this.apiUrlList = apiUrlList;
    }

    public Integer getModuleId() {
        return moduleId;
    }

    public void setModuleId(Integer moduleId) {
        this.moduleId = moduleId;
    }

}
