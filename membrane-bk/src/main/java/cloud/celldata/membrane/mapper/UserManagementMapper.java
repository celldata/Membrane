package cloud.celldata.membrane.mapper;

import cloud.celldata.membrane.pojo.bo.PrivilegeRoleDTO;
import cloud.celldata.membrane.pojo.entity.UserEntity;
import cloud.celldata.membrane.pojo.entity.UserRoleEntity;
import cloud.celldata.membrane.pojo.vo.MultiSelectorOptionVO;
import cloud.celldata.membrane.pojo.vo.UserInfoVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 用户管理Mapper
 * @ProjectName: membrane
 * @Package: cloud.celldata.membrane.mapper
 * @ClassName: UserManagementMapper
 * @Description: java类作用描述
 * @Author: jiwang
 * @CreateDate: 2020/5/14 17:24
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/5/14 17:24
 */
public interface UserManagementMapper {

    /**
     * 查询用户信息
     * @param userId 用户id
     * @return 用户实体
     */
    UserEntity selectUserById(Integer userId);

    /**
     * 查询用户信息
     * @param userName 用户名
     * @return 用户实体
     */
    UserEntity selectUserByUserName(String userName);



    /**
     * 查询用户列表
     * @param roleId 角色id
     * @param statusCode 状态码
     * @param searchKey 模糊查询关键字
     * @return 用户列表
     */
    List<UserInfoVO> selectUserInfoList(@Param("clientId") Integer clientId,
                                        @Param("roleId") Integer roleId,
                                        @Param("statusCode") Integer statusCode,
                                        @Param("searchKey") String searchKey);


    /**
     * 查询同名用户个数
     * @param userName 用户名
     * @return 同名用户个数
     */
    int countUserByUserName(String userName);

    /**
     * 查询手机号相同的用户个数
     * @param telephone 手机号
     * @return 手机号相同的用户个数
     */
    int countUserByTelephone(String telephone);

    /**
     * 查询邮箱地址相同的用户个数
     * @param email  邮箱地址
     * @return
     */
    int countUserByEmail(String email);


    /**
     * 新增用户
     * @param user 用户实体
     * @return 新增用户记录条数
     */
    int addUser(UserEntity user);


    /**
     * 查询管理员角色
     * @return 管理员角色信息
     */
    PrivilegeRoleDTO selectPrivilegeRole();


    /**
     * 查询用户密码
     * @param userId 用户id
     * @return 用户密码
     */
    String selectPasswordByUserId(@Param("userId") Integer userId);

    /**
     * 修改密码
     * @param userId 用户id
     * @param password 密码
     * @param updaterId 操作用户id
     * @return 修改密码记录条数
     */
    int updatePassword(@Param("userId") Integer userId,
                       @Param("password") String password,
                       @Param("updaterId") Integer updaterId);

    /**
     * 判断有户名是否存在
     * @param userName 用户名
     * @return 0代表不存在 非0代表存在
     */
    int userNameIsExist(String userName);

    /**
     * 查询同名用户个数
     * @param userName 用户名
     * @param userId 用户id
     * @return 同名用户个数
     */
    int countUserByUserNameWithOutCurrent(@Param("userName") String userName, @Param("userId") Integer userId);

    /**
     * 查询手机号相同的用户个数
     * @param telephone 手机号
     * @param userId 用户id
     * @return 手机号相同的用户个数
     */
    int countUserByTelephoneWithOutCurrent(@Param("telephone") String telephone, @Param("userId") Integer userId);

    /**
     * 查询邮箱相同的用户个数
     * @param telephone 手机号
     * @param userId 用户id
     * @return 手机号相同的用户个数
     */
    int countUserByEmailWithOutCurrent(@Param("email") String telephone, @Param("userId") Integer userId);

    /**
     * 更新用户
     * @param userEntity 用户实体
     * @param updaterId 操作用户id
     * @return 更新用户记录条数
     */
    int updateUser(@Param("userEntity") UserEntity userEntity, @Param("updaterId") Integer updaterId);


    /**
     * 更新用户状态
     * @param userId 用户id
     * @param status 状态
     * @param updaterId 操作用户id
     * @return 更新用户记录条数
     */
    int updateUserStatus(@Param("userId") Integer userId,
                         @Param("status") Integer status,
                         @Param("updaterId") Integer updaterId);


    /**
     * 查询角色列表
     * @return 角色列表
     */
    List<MultiSelectorOptionVO> selectRoleList();


    /**
     * 新增用户角色
     *
     * @param userRoleList 用户角色列表
     * @return 新增用户角色记录条数
     */
    int addUserRole(List<UserRoleEntity> userRoleList);

    /**
     * 查询用户角色id列表
     *
     * @param userId 用户id
     * @return 用户角色id列表
     */
    List<Integer> selectRoleIdsByUserId(@Param("userId") Integer userId);

    /**
     * 根据用户ID和AppId查询用户在此应用下有哪些角色
     * @param userId 用户ID
     * @param appId appId(注意此APPid并非应用表中的主键ID)
     * @return 用户该应用下的角色列表
     */
    List<Integer> selectRoleIdsByUserIdAndAppId(@Param("userId") Integer userId,@Param("appId") String appId);

    /**
     * 删除用户角色
     *
     * @param userId    用户id
     * @param updaterId 操作用户id
     * @return 删除用户角色记录条数
     */
    int deleteUserRole(@Param("userId") Integer userId, @Param("updaterId") Integer updaterId);
}
