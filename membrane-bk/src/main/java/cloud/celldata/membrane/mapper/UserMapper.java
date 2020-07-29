package cloud.celldata.membrane.mapper;



import cloud.celldata.membrane.pojo.entity.ExternalAuthUserEntity;
import cloud.celldata.membrane.pojo.entity.UserEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

/**
 * 用户Mapper
 */
public interface UserMapper {

    /**
     * 根据用户信息查询用户
     * @param userEntity 用户实体
     * @return 用户列表
     */
    List<UserEntity> getUserByUser(UserEntity userEntity);

    /**
     * 根据角色id查询所有用户id
     * @param roleId 角色id
     * @return 用户id列表
     */
    List<Integer> selectUserByRoleId(@Param("roleId") Integer roleId);

    /**
     * 根据应用ID 和用户ID 查询用户对应角色是否有全部权限
     * @param appId 应用主键Id
     * @param userId 用户ID
     * @return 返回是否有 全部权限的List 1代表有全部权限
     */
    List<Integer> selectIsALLApiByUserIdAndAppId(@Param("appId")Integer appId,@Param("userId")Integer userId);

    /**
     * 根据应用ID 和用户ID 查询用户对应部分权限的URI List
     * @param appId 应用主键Id
     * @param userId 用户ID
     * @return URI List
     */
    List<String> selectUriListByAppIdAndUserId(@Param("appId")Integer appId,@Param("userId")Integer userId);


    /**
     * 查询外部认证源用户 userName 集合
     * @param verification 认证方式
     * @param externalAuthId 认证源ID
     * @return 用户名集合
     */
    Set<String> selectExternalAuthUserNameList(@Param("verification")Integer verification,
                                               @Param("externalAuthId")Integer externalAuthId);

    /**
     * 批量插入外部数据源 用户
     * @param userList 外部数据源用户List
     */
    void addExternalAuthUser(List<ExternalAuthUserEntity> userList);

    /**
     * 批量删除外部数据源 用户
     * @param userList 用户名集合
     * @param externalAuthId 外部数据源主键ID
     */
    void removeExternalAuthUser(@Param("userList")List<String> userList,@Param("externalAuthId")Integer externalAuthId);
}
