package cloud.celldata.membrane.service;



import cloud.celldata.membrane.pojo.vo.LoginBean;
import cloud.celldata.membrane.pojo.entity.UserEntity;
import cloud.celldata.membrane.pojo.entity.UserLoginTokenEntity;

import java.util.List;

/**
 * 用户管理业务逻辑层接口
 */
public interface UserService {

     /**
     * 根据用户信息查询用户
     * @param userEntity 查询条件
     * @return 用户信息列表
     */
    List<UserEntity> getUserByUser(UserEntity userEntity);

    /**
     * 登陆
     * @param toLoginBean 登陆参数
     * @return token
     */
    UserLoginTokenEntity login(LoginBean toLoginBean);

}
