package cloud.celldata.membrane.service;

import cloud.celldata.membrane.pojo.entity.ExternalAuthEntity;
import cloud.celldata.membrane.pojo.vo.external.ExternalAuthInfo;

import javax.naming.NamingException;
import java.util.List;

/**
 * 外部认证管理业务逻辑层接口
 *
 * @ProjectName: membrane
 * @Package: cloud.celldata.membrane.service
 * @ClassName: ExternalAuthService
 * @Description: 外部认证管理业务逻辑层接口
 * @Author: jiwang
 * @CreateDate: 2020/6/22 15:57
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/6/22 15:57
 */
public interface ExternalAuthService {

    /**
     * 新增外部认证源
     * @param externalAuthInfo 外部认证源实体
     * @param userId 用户ID
     * @return 新增外部认证源
     */
    ExternalAuthEntity addExternalAuth(ExternalAuthInfo externalAuthInfo, Integer userId);

    /**
     * 外部认证源列表查询 多条件查询
     * @param name 认证名称
     * @param type 类型
     * @param id  外部认证Id
     * @return 列表
     */
    List<ExternalAuthInfo> selectAllExternalAuth(String name, Integer type, Integer id);

    /**
     * 外部认证修改
     * @param externalAuthInfo 修改实体
     * @param userId 用户ID
     * @return 修改后的实体
     */
    ExternalAuthEntity updateExternalAuth(ExternalAuthInfo externalAuthInfo,Integer userId);

    /**
     * 外部认证删除
     * @param idList 删除 外部认证
     * @param userId 用户ID
     */
    void removeExternalAuth(List<Integer> idList,Integer userId);


    /**
     * 外部认证账号密码校验
     * @param externalAuthEntity 外部认证源 实体
     * @param userName 外部认证用户名
     * @param password 外部认证密码
     * @return 是否通过外部数据源认证
     */
    Boolean validateExternalAuthUser(ExternalAuthEntity externalAuthEntity,String userName,String password) throws NamingException;


    /**
     * 外部数据源 用户数据同步到SSO平台
     * @param id 外部数据源ID
     */
    void syncExternalAuthUser(Integer id) throws NamingException;
}
