package cloud.celldata.membrane.controller;


import cloud.celldata.membrane.pojo.ResponseCode;
import cloud.celldata.membrane.pojo.vo.ResponseBean;
import cloud.celldata.membrane.utils.I18nService;
import cloud.celldata.membrane.utils.JWTUtils;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import javax.servlet.http.HttpServletRequest;

/**
 * 控制器基类
 */
public class BaseController {

    @Autowired
    protected I18nService i18nService;
    @Value("${membrane.secret}")
    private String membraneSecret;

    @Autowired
    private HttpServletRequest request;
    private Integer userId;
    private String accessToken;

    protected Integer getCurrentUserId(){
        this.accessToken = request.getHeader("Authorization");
        Claims claims = JWTUtils.parseJWT(accessToken, membraneSecret);
        this.userId = Integer.valueOf(String.valueOf(claims.get("user_id")));
        return userId;
    }

    protected String getAccessToken(){
        accessToken = request.getHeader("Authorization");
        Claims claims = JWTUtils.parseJWT(accessToken, membraneSecret);
        userId = Integer.valueOf(String.valueOf(claims.get("user_id")));
        return accessToken;
    }

//    /**
//     * 获取请求响应结果
//     * @param errCode 状态码
//     * @param errMessage 消息
//     * @param resultData 数据
//     * @param <T> 响应数据类型
//     * @return 请求响应
//     */
//    <T> ResponseBean<T> getResponse(int errCode, String errMessage, T resultData){
//        return new ResponseBean(errCode, errMessage, resultData);
//    }
//
//    /**
//     * 获取请求响应结果
//     * @param errCode 状态码
//     * @param errMessage 消息
//     * @param <T> 响应数据类型
//     * @return 请求响应
//     */
//    <T> ResponseBean<T> getResponse(int errCode, String errMessage){
//        return new ResponseBean(errCode, errMessage, null);
//    }

    /**
     * 获取请求响应结果
     * @param errCode 状态码
     * @return 请求响应
     */
    <T> ResponseBean<T> getResponse(int errCode){
        String errMessage = i18nService.getMessage(ResponseCode.getMessageKeyByCode(errCode));
        return new ResponseBean(errCode, errMessage, null);
    }


    /**
     * 获取请求响应结果
     * @param errCode 状态码
     * @param <T> 响应数据类型
     * @return 请求响应
     */
    <T> ResponseBean<T> getResponse(int errCode,T data){
        String errMessage = i18nService.getMessage(ResponseCode.getMessageKeyByCode(errCode));
        return new ResponseBean(errCode, errMessage, data);
    }


}
