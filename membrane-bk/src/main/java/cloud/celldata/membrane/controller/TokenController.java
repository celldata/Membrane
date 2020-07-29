package cloud.celldata.membrane.controller;

import cloud.celldata.membrane.excep.MembraneException;
import cloud.celldata.membrane.pojo.AuthorityRequest;
import cloud.celldata.membrane.pojo.AuthorityResponse;
import cloud.celldata.membrane.pojo.ResponseCode;
import cloud.celldata.membrane.pojo.vo.LoginBean;
import cloud.celldata.membrane.pojo.vo.LoginOffTokenBean;
import cloud.celldata.membrane.pojo.vo.ResponseBean;
import cloud.celldata.membrane.pojo.entity.UserLoginTokenEntity;
import cloud.celldata.membrane.pojo.vo.AuthorityAndTokenBean;
import cloud.celldata.membrane.pojo.vo.TokenKeyBean;
import cloud.celldata.membrane.pojo.bo.token.TokenKeyResponse;
import cloud.celldata.membrane.service.LoginTokenService;
import cloud.celldata.membrane.service.TokenService;
import cloud.celldata.membrane.service.UserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

/**
 * 令牌管理控制器
 *
 * @ProjectName: membrane
 * @Package: cloud.celldata.membrane.controller
 * @ClassName: TokenController
 * @Description: token相关类
 * @Author: jiwang
 * @CreateDate: 2020/5/14 11:04
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/5/14 11:04
 */

@RestController
@CrossOrigin
@RequestMapping("/api/token")
public class TokenController extends BaseController{

    @Autowired
    private UserService userService;

    @Autowired
    private LoginTokenService loginTokenService;

    @Autowired
    private TokenService tokenService;

    /**
     * 登录
     * @param loginBean 登录信息
     * @return 操作状态
     */
    @ApiOperation(value ="/login", notes = "登录")
    @PostMapping(value = "/login")
    public ResponseBean login(@RequestBody LoginBean loginBean){
        try {
            if (StringUtils.isEmpty(loginBean.getUserName())){
                throw new MembraneException(ResponseCode.USER_NAME_IS_NULL);
            }
            if(StringUtils.isEmpty(loginBean.getPassword())){
                throw new MembraneException(ResponseCode.PASSWORD_IS_NULL);
            }
            UserLoginTokenEntity token = userService.login(loginBean);
            return getResponse(ResponseCode.SUCCESS_PROCESSED, token);
        } catch (MembraneException e) {
            e.printStackTrace();
            return getResponse(e.getCode());
        } catch (Exception e) {
            e.printStackTrace();
            return getResponse(ResponseCode.GENERIC_FAILURE);
        }
    }


    /**
     * 退户登录 删除redis中的token
     * @param loginOffTokenBean 退出登录信息（详见实体信息）
     * @return 操作状态
     */
    @ApiOperation(value ="/logout", notes = "退出")
    @PostMapping(value = "/logout")
    public ResponseBean logout(@RequestBody LoginOffTokenBean loginOffTokenBean){
        loginTokenService.removeToken(loginOffTokenBean);
        return getResponse(ResponseCode.SUCCESS_PROCESSED);

    }


    /**
     * 根据tokenKey获取权限token
     * @param tokenKey tokenKey
     * @return token
     */
    @ApiOperation(value = "/exchange", notes = "根据tokenKey获取权限token")
    @GetMapping(value = "/exchange")
    public ResponseBean validateToken(@RequestParam String tokenKey) {
        try {
            AuthorityAndTokenBean authorityToken = loginTokenService.getAuthorityToken(tokenKey);
            return getResponse(ResponseCode.SUCCESS_PROCESSED, authorityToken);
        } catch (MembraneException e) {
            e.printStackTrace();
            return getResponse(e.getCode());
        } catch (Exception e) {
            e.printStackTrace();
            return getResponse(ResponseCode.GENERIC_FAILURE);
        }
    }

    @ApiOperation(value = "/getAuthorityData", notes = "接口鉴权")
    @PostMapping(value = "/getAuthorityData")
    public ResponseBean getAuthorityData(@RequestBody AuthorityRequest authorityRequest){
        try {
            AuthorityResponse authorityResponse = tokenService.getAuthorityData(authorityRequest);
            return getResponse(ResponseCode.SUCCESS_PROCESSED, authorityResponse);
        } catch (MembraneException e) {
            e.printStackTrace();
            return getResponse(e.getCode());
        } catch (Exception e) {
            e.printStackTrace();
            return getResponse(ResponseCode.GENERIC_FAILURE);
        }
    }

    @ApiOperation(value = "getTokenKey",notes = "根据AppId 和 ssoToken获取业务平台tokenKey")
    @PostMapping(value = "/getTokenKey")
    public ResponseBean getTokenKey(@RequestBody TokenKeyBean tokenKeyBean){
        try {
            TokenKeyResponse tokenKeyBySSOTokenAndAppId = tokenService.getTokenKeyBySSOToken(tokenKeyBean);
            return getResponse(ResponseCode.SUCCESS_PROCESSED, tokenKeyBySSOTokenAndAppId);
        } catch (MembraneException e) {
            e.printStackTrace();
            return getResponse(e.getCode());
        } catch (Exception e) {
            e.printStackTrace();
            return getResponse(ResponseCode.GENERIC_FAILURE);
        }
    }



}
