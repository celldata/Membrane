package cloud.celldata.membrane.service.impl;

import cloud.celldata.membrane.excep.MembraneException;
import cloud.celldata.membrane.mapper.UserManagementMapper;
import cloud.celldata.membrane.pojo.ResponseCode;
import cloud.celldata.membrane.pojo.vo.ForgetPassWordBean;
import cloud.celldata.membrane.pojo.vo.ResetPasswordBean;
import cloud.celldata.membrane.pojo.bo.PrivilegeRoleDTO;
import cloud.celldata.membrane.pojo.entity.UserEntity;
import cloud.celldata.membrane.pojo.entity.UserRoleEntity;
import cloud.celldata.membrane.pojo.enumeration.ResetPasswordFlagEnum;
import cloud.celldata.membrane.pojo.vo.MultiSelectorOptionVO;
import cloud.celldata.membrane.pojo.vo.SelectedClientRoleVO;
import cloud.celldata.membrane.pojo.vo.UserInfoVO;
import cloud.celldata.membrane.service.AppService;
import cloud.celldata.membrane.service.UserManagementService;
import cloud.celldata.membrane.service.TokenService;
import cloud.celldata.membrane.utils.*;
import com.aliyuncs.exceptions.ClientException;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 用户信息管理业务逻辑层
 *
 * @ProjectName: membrane
 * @Package: cloud.celldata.membrane.service.impl
 * @ClassName: UserManagementServiceImpl
 * @Description: java类作用描述
 * @Author: jiwang
 * @CreateDate: 2020/5/15 16:29
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/5/15 16:29
 */



@Service
@Transactional
public class UserManagementServiceImpl implements UserManagementService {
    private static Logger logger = LoggerFactory.getLogger(UserManagementServiceImpl.class);

    @Autowired
    private UserManagementMapper userManagementMapper;

    @Value("${membrane.secret}")
    private String membraneSecret;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private AppService clientService;

    @Autowired
    JavaMailSender javaMailSender;

    @Value("${ali_msg.new_user_template_code}")
    private String newUserTemplateCode;

    @Value("${EMAIL_TEMPLATE}")
    private String emailTemplate;

    @Value("${spring.mail.username}")
    private String fromEmail;


    @Override
    public PageInfo selectUserInfoList(Integer clientId,Integer roleId, Integer statusCode, String searchKey, Integer pageSize, Integer pageNum) {
        List<UserInfoVO> userInfoList = userManagementMapper.selectUserInfoList(clientId,roleId, statusCode, searchKey);
        return ListPageUtil.getPageInfo(userInfoList, pageNum, pageSize);
    }

    @Override
    public UserEntity addUser(String userName, String fullName, String telephone, String email, String passWord, List<SelectedClientRoleVO> roles, Integer creatorId) {
        if (userManagementMapper.countUserByUserName(userName) > 0) {
            throw new MembraneException(ResponseCode.USER_EXISTS);
        }
        if (userManagementMapper.countUserByTelephone(telephone) > 0) {
            throw new MembraneException(ResponseCode.USER_CELL_EXISTS);
        }
        if (userManagementMapper.countUserByEmail(email) > 0) {
            throw new MembraneException(ResponseCode.USER_EMAIL_EXISTS);
        }

        // 新增用户
        UserEntity userEntity = UserEntity.UserEntityBuilder.anUserEntity()
                .withUserName(userName)
                .withFullName(fullName)
                .withPassword(Sha256.getSHA256(passWord))
                .withTelephone(telephone)
                .withEmail(email)
                .withCreatorId(creatorId)
                .withPrivilege(0)
                .build();

        userManagementMapper.addUser(userEntity);

        // 新增用户角色关系
        List<UserRoleEntity> roleList = transferUserRoleEntity(roles, userEntity.getId(), creatorId);
        if (!CollectionUtils.isEmpty(roleList)) {
            userManagementMapper.addUserRole(roleList);
        }

        return userEntity;
    }

    @Override
    public Integer resetPassword(Integer operatorId, ResetPasswordBean resetPasswordBean) {

        ResetPasswordBean resetPasswordBeanResponse = new ResetPasswordBean();
        switch (resetPasswordBean.getFlag().getCode()){
            case 1:
                //修改密码
                if (StringUtils.isEmpty(resetPasswordBean.getNewPassword())) {
                    throw new MembraneException(ResponseCode.NEW_PASSWORD_IS_NULL);
                }
                if (StringUtils.isEmpty(resetPasswordBean.getOldPassword())){
                    throw new MembraneException(ResponseCode.ORIGIN_PASSWORD_IS_NULL);
                }
                String dataOldPassword = userManagementMapper.selectPasswordByUserId(resetPasswordBean.getUserId());
                if (dataOldPassword.equals(Sha256.getSHA256(resetPasswordBean.getOldPassword()))){
                    if (dataOldPassword.equals(Sha256.getSHA256(resetPasswordBean.getNewPassword()))){
                        throw new MembraneException(ResponseCode.PASSWORD_NOT_CHANGE);
                    }
                }else{
                    //resetPasswordBeanResponse.setOldPassword(ResponseCode.ORIGIN_PASSWORD_ERROR_MSG);
                    throw new MembraneException(ResponseCode.ORIGIN_PASSWORD_INCORRECT);
                };

                    userManagementMapper.updatePassword(resetPasswordBean.getUserId(), Sha256.getSHA256(resetPasswordBean.getNewPassword()),operatorId);
                    // userId 退出登录(删除redis中的登录token)
                    tokenService.removeUserToken(resetPasswordBean.getUserId());
                    return ResetPasswordFlagEnum.MODIFY.getCode();

            case 0 :
                //重置密码
                // 更新用户密码
                userManagementMapper.updatePassword(resetPasswordBean.getUserId(), Sha256.getSHA256(Md5Utils.calc(ResponseCode.RESET_PASSWORD)), operatorId);

                // userId 退出登录(删除redis中的登录token)
                tokenService.removeUserToken(resetPasswordBean.getUserId());
                return ResetPasswordFlagEnum.RESET.getCode();

            default:
                throw new MembraneException(ResponseCode.PARAMETER_INVALID);

        }
    }

    @Override
    public void userNameIsExist(String userName) {
        int userNameCount = userManagementMapper.userNameIsExist(userName);
        if (userNameCount==0){
            //用户名不存在
            throw new MembraneException(ResponseCode.USER_NAME_NOT_EXIST);
        }
    }

    @Override
    public String sendVerification(String userName, Integer flag) {
        UserEntity userEntity = userManagementMapper.selectUserByUserName(userName);
        if (userEntity!=null){
            switch (flag){
                case 0:
                    //发送邮箱验证码
                    //邮箱验证码 没有才发送 防止多次发送
                    if (Boolean.FALSE.equals(tokenService.verificationValid(userEntity.getId(),flag))){
                        String emailVerification = VerificationUtil.getEmailVerification();
                        SimpleMailMessage message = new SimpleMailMessage();
                        message.setSubject("重置密码验证码");
                        message.setFrom(fromEmail);
                        message.setTo(userEntity.getEmail());
                        message.setSentDate(new Date());
                        message.setText(emailVerification+","+emailTemplate);
                        javaMailSender.send(message);
                        tokenService.setVerification(emailVerification,userEntity.getId(),flag);
                        return VerificationUtil.getHiddenEmail(userEntity.getEmail());
                    }else {
                        throw new MembraneException(ResponseCode.EMAIL_SEND_ERROR);
                    }
                case 1:
                    //发送手机验证码
                    //验证码 没有才发送 防止多次发送
                    if (!tokenService.verificationValid(userEntity.getId(), flag)) {
                        String phoneVerification = VerificationUtil.getPhoneVerification();
                        try {
                            AliMessageUtil.sendMessage(newUserTemplateCode, userEntity.getTelephone(), phoneVerification);
                        } catch (ClientException e) {
                            logger.error("手机验证码发送失败---手机号为",userEntity.getTelephone());
                        }
                        tokenService.setVerification(phoneVerification,userEntity.getId(),flag);
                        return VerificationUtil.getHiddenPhone(userEntity.getTelephone());
                    }else {
                        throw new MembraneException(ResponseCode.SMS_SEND_ERROR);
                    }
                default:
                    throw new MembraneException(ResponseCode.GENERIC_FAILURE);
            }

        }else {
                //用户名不存在
            throw new MembraneException(ResponseCode.USER_NAME_NOT_EXIST);
        }

    }

    @Override
    public void forgetPassWord(ForgetPassWordBean forgetPassWordBean) {
        UserEntity userEntity = userManagementMapper.selectUserByUserName(forgetPassWordBean.getUserName());
        if (userEntity!=null){
            String reidsVerification = tokenService.getVerification(userEntity.getId(), forgetPassWordBean.getFlag().getCode());
            if (!StringUtils.isEmpty(reidsVerification)){
                if (reidsVerification.equals(forgetPassWordBean.getVerification())){
                    userManagementMapper.updatePassword(userEntity.getId(), Sha256.getSHA256(forgetPassWordBean.getPassWord()), 1);
                    tokenService.deleteVerification(userEntity.getId(),forgetPassWordBean.getFlag().getCode());
                }else {
                    //验证码不正确
                    throw new MembraneException(ResponseCode.VERIFICATION_IS_INVALID);
                }
            }else {
                //验证码已过期
                throw new MembraneException(ResponseCode.VERIFICATION_IS_EXPIRED);
            }
        }else {
            //用户名不存在
            throw new MembraneException(ResponseCode.USER_NAME_NOT_EXIST);
        }
    }

    @Override
    public void updateUser(Integer userId, String userName, String telephone, String email, String fullName, List<SelectedClientRoleVO> roles, Integer updaterId) {
        if (userManagementMapper.countUserByUserNameWithOutCurrent(userName, userId) > 0) {
            throw new MembraneException(ResponseCode.USER_EXISTS);
        }
        if (userManagementMapper.countUserByTelephoneWithOutCurrent(telephone, userId) > 0) {
            throw new MembraneException(ResponseCode.USER_CELL_EXISTS);
        }
        if (userManagementMapper.countUserByEmailWithOutCurrent(email, userId) > 0) {
            throw new MembraneException(ResponseCode.USER_EMAIL_EXISTS);
        }
        // 更新用户信息
        UserEntity userEntity = userManagementMapper.selectUserById(userId);
        userEntity.setUserName(userName);
        userEntity.setTelephone(telephone);
        userEntity.setEmail(email);
        userEntity.setFullName(fullName);
        userManagementMapper.updateUser(userEntity, updaterId);

        // 判断用户角色是否有修改
        Boolean isRoleUpdated = checkRoleUpdateStatus(userId, roles);
        if (!isRoleUpdated) {
            // 删除用户原有角色
            userManagementMapper.deleteUserRole(userId, updaterId);
            // 重新插入用户角色
            List<UserRoleEntity> roleList = transferUserRoleEntity(roles, userEntity.getId(), updaterId);
            if (!CollectionUtils.isEmpty(roleList)) {
                userManagementMapper.addUserRole(roleList);
            }
            // 变更valid
            tokenService.removeUserToken(userId);
        }
    }

    @Override
    public int updateUserStatus(Integer userId, Integer status, Integer updaterId) {
        int rows = userManagementMapper.updateUserStatus(userId, status, updaterId);
        if (status == 2) {
            // 变更valid
            tokenService.removeUserToken(userId);
        }
        return rows;
    }

    /**
     * 查询角色列表
     *
     * @return 角色列表
     */
    @Override
    public List<MultiSelectorOptionVO> selectRoleList() {
        return userManagementMapper.selectRoleList();
    }


    /**
     * 是否有超级管理员权限
     *
     * @param roles 角色列表
     * @return 1-超级管理员；0-非超级管理员
     */
    private int isPrivilegeUser(List<SelectedClientRoleVO> roles) {
        PrivilegeRoleDTO privilegeRole = userManagementMapper.selectPrivilegeRole();
        if (privilegeRole != null) {
            for (SelectedClientRoleVO client : roles) {
                Integer clientId = client.getClientId();
                if (clientId != null && clientId.equals(privilegeRole.getClientId())) {
                    for (Integer roleId : client.getRoleIds()) {
                        if (roleId != null && roleId.equals(privilegeRole.getRoleId())) {
                            return 1;
                        }
                    }
                }
            }
        }
        return 0;
    }

    /**
     * 转换用户角色实体格式
     *
     * @param roles     角色列表
     * @param userId    用户唯一标识
     * @param creatorId 创建者id
     * @return 用户角色实体列表
     */
    private List<UserRoleEntity> transferUserRoleEntity(List<SelectedClientRoleVO> roles, Integer userId, Integer creatorId) {
        List<UserRoleEntity> userRoleEntities = new ArrayList<>();
        for (SelectedClientRoleVO client : roles) {
            for (Integer roleId : client.getRoleIds()) {
                userRoleEntities.add(UserRoleEntity.UserRoleEntityBuilder.anUserRoleEntity()
                        .withUserId(userId)
                        .withRoleId(roleId)
                        .withCreatorId(creatorId)
                        .build());
            }
        }
        return userRoleEntities;
    }

    /**
     * 校验角色更新状态
     *
     * @param userId 用户唯一标识
     * @param roles  角色列表
     * @return 角色是否更新
     */
    private Boolean checkRoleUpdateStatus(Integer userId, List<SelectedClientRoleVO> roles) {
        List<Integer> roleIds = userManagementMapper.selectRoleIdsByUserId(userId);
        if ((roles == null || roles.size() == 0) && (roleIds == null || roleIds.size() == 0)) {
            return true;
        }
        if ((roles == null || roles.size() == 0) || (roleIds == null || roleIds.size() == 0)) {
            return false;
        }
        List<Integer> selectedRoleIds = new ArrayList<>();
        for (SelectedClientRoleVO client : roles) {
            if (client != null && client.getRoleIds() != null) {
                selectedRoleIds.addAll(client.getRoleIds());
            }
        }
        if (selectedRoleIds.size() != roleIds.size()) {
            return false;
        }
        for (Integer roleId : selectedRoleIds) {
            if (!roleIds.contains(roleId)) {
                return false;
            }
        }
        return true;
    }
}


