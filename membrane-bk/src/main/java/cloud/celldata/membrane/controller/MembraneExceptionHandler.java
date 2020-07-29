package cloud.celldata.membrane.controller;

import cloud.celldata.membrane.excep.MembraneException;
import cloud.celldata.membrane.pojo.vo.ResponseBean;
import cloud.celldata.membrane.pojo.ResponseCode;
import cloud.celldata.membrane.utils.I18nService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 异常捕获类
 *
 * @ProjectName: membrane
 * @Package: cloud.celldata.membrane.controller
 * @ClassName: MembraneExceptionHandler
 * @Description: 异常捕获类
 * @Author: jiwang
 * @CreateDate: 2020/5/14 14:04
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/5/14 14:04
 */
@ControllerAdvice
public class MembraneExceptionHandler {
    @Autowired
    I18nService i18nService;


    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public ResponseBean error(Exception e){
        Integer errorCode;
        String localizeMessageKey;
        Object data = null;
        if (e instanceof MembraneException) {
            MembraneException exception = (MembraneException) e;
            errorCode = exception.getCode();
            data = exception.getData();
        }else {
            errorCode = ResponseCode.GENERIC_FAILURE;
        }
        localizeMessageKey = ResponseCode.getMessageKeyByCode(errorCode);
        return new ResponseBean(errorCode, i18nService.getMessage(localizeMessageKey), data);
    }
}
