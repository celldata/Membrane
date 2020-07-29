package cloud.celldata.membrane.service;

import cloud.celldata.membrane.pojo.vo.ForgetPassWordBean;
import cloud.celldata.membrane.pojo.vo.ResetPasswordBean;
import cloud.celldata.membrane.pojo.entity.UserEntity;
import cloud.celldata.membrane.pojo.vo.MultiSelectorOptionVO;
import cloud.celldata.membrane.pojo.vo.SelectedClientRoleVO;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * 用户信息管理业务逻辑层接口
 *
 * @ProjectName: membrane
 * @Package: cloud.celldata.membrane.service
 * @ClassName: UserManagementService
 * @Description: 用户信息管理业务逻辑层接口
 * @Author: jiwang
 * @CreateDate: 2020/5/15 16:27
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/5/15 16:27
 */
public interface UserManagementService {


    /**
     * 查询用户列表
     * @param clientId 应用Id
     * @param roleId 角色id
     * @param statusCode 状态code
     * @param searchKey 模糊查询关键字
     * @param pageSize 页大小
     * @param pageNum 当前页
     * @return 用户列表
     */
    PageInfo selectUserInfoList(Integer clientId,Integer roleId, Integer statusCode, String searchKey, Integer pageSize, Integer pageNum);


    /**
     * 新增用户
     * @param userName 用户名称
     * @param fullName 姓名
     * @param telephone 手机号
     * @param roles 角色列表
     * @param creatorId 用户id
     */
    UserEntity addUser(String userName, String fullName, String telephone, String email, String passWord, List<SelectedClientRoleVO> roles, Integer creatorId);


    /**
     * 修改重置密码方法
     * @param resetPasswordBean 修改重置密码参数实体
     * @param operatorId     当前操作者的用户id
     * @return
     */
    Integer resetPassword(Integer operatorId,ResetPasswordBean resetPasswordBean);

    /**
     * 用户名是否存在
     * @param userName
     * @return 是否存在
     */
    void userNameIsExist(String userName);

    /**
     * 发送验证码
     * @param userName 用户名
     * @param flag  0代表 发送邮箱验证码  ，1代表发送手机验证码
     */
    String sendVerification(String userName, Integer flag);

    /**
     * 忘记密码重置密码
     * @param forgetPassWordBean
     */
    void forgetPassWord(ForgetPassWordBean forgetPassWordBean);

    /**
     * 更新用户
     * @param userId 用户id
     * @param userName 用户名
     * @param telephone 手机号
     * @param fullName 姓名
     * @param roles 角色列表
     * @param updaterId 更新者id
     */
    void updateUser(Integer userId, String userName, String telephone, String email, String fullName, List<SelectedClientRoleVO> roles,
                    Integer updaterId);

    /**
     * 更新用户状态
     * @param userId 用户id
     * @param status 状态
     * @param updaterId 更新者id
     * @return 更新用户信息记录条数
     */
    int updateUserStatus(Integer userId, Integer status, Integer updaterId);

    /**
     * 查询角色列表
     * @return 角色列表
     */
    List<MultiSelectorOptionVO> selectRoleList();

}
