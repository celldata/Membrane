package cloud.celldata.membrane.controller;

import cloud.celldata.membrane.excep.MembraneException;
import cloud.celldata.membrane.pojo.RegularExpressionDictionary;
import cloud.celldata.membrane.pojo.ResponseCode;
import cloud.celldata.membrane.pojo.vo.ForgetPassWordBean;
import cloud.celldata.membrane.pojo.vo.ResetPasswordBean;
import cloud.celldata.membrane.pojo.vo.ResponseBean;
import cloud.celldata.membrane.pojo.vo.UserBean;
import cloud.celldata.membrane.pojo.entity.UserEntity;
import cloud.celldata.membrane.pojo.enumeration.ResetPasswordFlagEnum;
import cloud.celldata.membrane.pojo.enumeration.UserStatusEnum;
import cloud.celldata.membrane.pojo.vo.MultiSelectorOptionVO;
import cloud.celldata.membrane.pojo.vo.UserVO;
import cloud.celldata.membrane.service.UserManagementService;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;

/**
 * 用户管理控制器
 *
 * @ProjectName: membrane
 * @Package: cloud.celldata.membrane.controller
 * @ClassName: UserManagementController
 * @Description: 用户相关操作
 * @Author: jiwang
 * @CreateDate: 2020/5/15 15:58
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/5/15 15:58
 */

@RestController
@CrossOrigin
@RequestMapping("/api/user")
public class UserController extends BaseController{

    private static Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserManagementService userManagementService;

    /**
     * 用户查询
     * @param roleId 角色id
     * @param statusCode 状态编码
     * @param searchKey 模糊查询关键词
     * @param pageSize 页大小
     * @param pageIndex 页号
     * @return 用户集合数据
     */
    @ApiOperation(value = "/list", notes = "根据给定条件查询符合条件的用户集合")
    @GetMapping(value = "/list")
    public ResponseBean fetchUserInfoList(
                                          @RequestParam(required = false) Integer clientId,
                                          @RequestParam(required = false) Integer roleId,
                                          @RequestParam(required = false) Integer statusCode,
                                          @RequestParam(required = false) String searchKey,
                                          @RequestParam(required = false, defaultValue = "10") Integer pageSize,
                                          @RequestParam(required = false, defaultValue = "1") Integer pageIndex) {
        try {
            PageInfo userInfoList = userManagementService.selectUserInfoList(clientId,roleId,
                    statusCode, searchKey, pageSize, pageIndex);
            return getResponse(ResponseCode.SUCCESS_PROCESSED, userInfoList);
        } catch (MembraneException e) {
            return getResponse(e.getCode());
        } catch (Exception e) {
            return getResponse(ResponseCode.GENERIC_FAILURE);
        }
    }

    /**
     * 新增用户
     * @param userBean 用户信息
     * @return 操作结果
     */
    @ApiOperation(value = "/add", notes = "新增用户")
    @PostMapping(value = "/add")
    public ResponseBean addUser(@RequestBody UserBean userBean){
        try {
            UserEntity userEntity = null;
            userBeanIsEmpty(userBean);
            Integer userId = getCurrentUserId();
            if (!userBean.getEmail().matches(RegularExpressionDictionary.EMAIL_REGEX)){
                throw new MembraneException(ResponseCode.USER_EMAIL_INCORRECT);
            }
            if (!userBean.getTelephone().matches(RegularExpressionDictionary.TELEPHONE_REGEX)){
                throw new MembraneException(ResponseCode.USER_CELL_INCORRECT);
            }
            userEntity = userManagementService.addUser(userBean.getUserName(), userBean.getFullName(), userBean.getTelephone(),
                    userBean.getEmail(), userBean.getPassWord(), userBean.getRoles(), userId);
            return getResponse(ResponseCode.SUCCESS_PROCESSED,userEntity);
        } catch (MembraneException e) {
            return getResponse(e.getCode());
        }catch (Exception e){
            return getResponse(ResponseCode.GENERIC_FAILURE);
        }

    }
    /**
     * 修改重置密码
     * @param resetPasswordBean 修改重置密码参数（详见实体属性）
     * @return 操作结果
     */
    @ApiOperation(value = "/reset", notes = "修改重置密码")
    @PostMapping(value = "/reset")
    public ResponseBean reset(@RequestBody ResetPasswordBean resetPasswordBean){
        try {
            Integer userId = getCurrentUserId();
            Integer result = userManagementService.resetPassword(userId,resetPasswordBean);
            if (result!=null &&result == ResetPasswordFlagEnum.MODIFY.getCode()){
                return getResponse(ResponseCode.SUCCESS_PROCESSED);
            } else  {
                return getResponse(ResponseCode.SUCCESS_PROCESSED);
            }
        } catch (MembraneException e) {
            return getResponse(e.getCode());
        }catch (Exception e){
            return getResponse(ResponseCode.GENERIC_FAILURE);
        }
    }


    /**
     * 用户状态下拉框数据查询，角色下拉框
     * @return 用户状态下拉框数据
     */
    @ApiOperation(value = "/fetch", notes = "角色下拉框，用户状态下拉框")
    @GetMapping(value = "/fetch")
    public ResponseBean fetch() {
        try {
            List<MultiSelectorOptionVO> roleList = userManagementService.selectRoleList();
            HashMap<Object, Object> map = new HashMap<>();
            map.put("roleList",roleList);
            map.put("statusList",UserStatusEnum.userStatusMapList);
            return getResponse(ResponseCode.SUCCESS_PROCESSED, map);
        } catch (MembraneException e) {
            return getResponse(e.getCode());
        }catch (Exception e){
            return getResponse(ResponseCode.GENERIC_FAILURE);
        }
    }

    /**
     *
     * @param userName 根据用户名发送验证码
     * @param flag 0代表发送邮箱验证码。1代表发送手机验证码
     * @return
     */
    @ApiOperation(value = "/send", notes = "发送验证码")
    @GetMapping(value = "/send")
    public ResponseBean sendVerification(@RequestParam ("userName") String userName,
                                         @RequestParam ("flag") Integer flag){
        try {
            String sendVerification = userManagementService.sendVerification(userName, flag);
            return getResponse(ResponseCode.SUCCESS_PROCESSED, sendVerification);
        } catch (MembraneException e) {
            return getResponse(e.getCode());
        }catch (Exception e){
            return getResponse(ResponseCode.GENERIC_FAILURE);
        }
    }

    /**
     * 忘记密码重置密码
     * @param forgetPassWordBean 忘记密码重置密码实体
     * @return
     */
    @ApiOperation(value = "/forget", notes = "校验验证码成功之后重置密码")
    @PostMapping(value = "/forget")
    public ResponseBean forgetPassWord(@RequestBody ForgetPassWordBean forgetPassWordBean){
        try {
            userManagementService.forgetPassWord(forgetPassWordBean);
            return getResponse(ResponseCode.SUCCESS_PROCESSED);
        } catch (MembraneException e) {
            return getResponse(e.getCode());
        }catch (Exception e){
            return getResponse(ResponseCode.GENERIC_FAILURE);
        }
    }


    /**
     * 编辑用户
     * @param userVO 用户信息
     * @return 操作结果
     */
    @ApiOperation(value = "/update", notes = "编辑用户,修改用户状态")
    @PostMapping(value = "/update")
    public ResponseBean updateUser(@RequestBody UserVO userVO) {
        try {
            Integer userId = getCurrentUserId();
            Integer code = ResponseCode.SUCCESS_PROCESSED;
            if (userVO.getFlag()==0){
                //代表编辑用户
                userManagementService.updateUser(userVO.getId(), userVO.getUserName(), userVO.getTelephone(), userVO.getEmail(),
                        userVO.getFullName(), userVO.getRoles(), userId);
                return getResponse(code);
            }else if (userVO.getFlag() ==1){
                //改变用户状态
                userManagementService.updateUserStatus(userVO.getId(), userVO.getStatus(), userId);
                return getResponse(code);
            }else {
                return getResponse(ResponseCode.GENERIC_FAILURE);
            }
        } catch (MembraneException e) {
            return getResponse(e.getCode());
        }catch (Exception e){
            e.printStackTrace();
            return getResponse(ResponseCode.GENERIC_FAILURE);
        }
    }


    /**
     * 新增用户参数判空方法
     * @param userBean 新增用户实体
     */
    private void userBeanIsEmpty(UserBean userBean){
        if (userBean == null){
            throw new MembraneException(ResponseCode.USER_NAME_IS_NULL);
        }
        if (StringUtils.isEmpty(userBean.getUserName())){
            throw new MembraneException(ResponseCode.USER_NAME_IS_NULL);
        }
        if(StringUtils.isEmpty(userBean.getPassWord())){
            throw new MembraneException(ResponseCode.PASSWORD_IS_NULL);
        }
        if(StringUtils.isEmpty(userBean.getFullName())){
            throw new MembraneException(ResponseCode.USER_FULL_NAME_IS_NULL);
        }
        if(StringUtils.isEmpty(userBean.getEmail())){
            throw new MembraneException(ResponseCode.USER_EMAIL_IS_NULL);
        }
        if(StringUtils.isEmpty(userBean.getTelephone())){
            throw new MembraneException(ResponseCode.USER_CELL_IS_NULL);
        }
    }


}
