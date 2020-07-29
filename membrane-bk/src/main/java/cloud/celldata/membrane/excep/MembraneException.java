package cloud.celldata.membrane.excep;

import cloud.celldata.membrane.pojo.ResponseCode;
import cloud.celldata.membrane.utils.I18nService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.Serializable;

/**
 * 业务异常
 */
public class MembraneException extends RuntimeException implements Serializable {

    private static final long serialVersionUID = 2405172041950251807L;

    // 异常状态码
    private final Integer code;

    private Object data;

    @Autowired
    I18nService i18nService;

    public void setData(Object data) {
        this.data = data;
    }



    public MembraneException(Integer code) {
        //super(message);
        this.code = code;
    }

    public MembraneException(Integer code, Object data) {
        //super(message);
        this.data = data;
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }

    public Object getData() {
        return data;
    }


}
