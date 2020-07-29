package cloud.celldata.membrane.pojo.vo.external;

import java.util.Map;

/**
 * 外部认证实体类
 *
 * @ProjectName: membrane
 * @Package: cloud.celldata.membrane.pojo.vo.external
 * @ClassName: ExternalAuthInfo
 * @Description: java类作用描述
 * @Author: jiwang
 * @CreateDate: 2020/6/22 16:34
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/6/22 16:34
 */
public class ExternalAuthInfo extends ExternalAuth {

    private Map parameters;

    public Map getParameters() {
        return parameters;
    }

    public void setParameters(Map parameters) {
        this.parameters = parameters;
    }
}
