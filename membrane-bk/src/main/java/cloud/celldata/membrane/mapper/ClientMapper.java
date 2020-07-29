package cloud.celldata.membrane.mapper;

import cloud.celldata.membrane.pojo.vo.CertificationBean;
import cloud.celldata.membrane.pojo.vo.ClientBean;
import cloud.celldata.membrane.pojo.vo.ClientListBean;
import cloud.celldata.membrane.pojo.entity.ClientEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 平台信息管理Mapper
 *
 * @ProjectName: membrane
 * @Package: cloud.celldata.membrane.mapper
 * @ClassName: ClientMapper
 * @Description: 平台信息管理Mapper
 * @Author: jiwang
 * @CreateDate: 2020/5/19 14:23
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/5/19 14:23
 */
public interface ClientMapper {

    /**
     * 根据查询条件查询某个平台
     * @param clientEntity 查询条件
     * @return 平台实体
     */
    ClientEntity getClient(ClientEntity clientEntity);

    /**
     * 条件查询所有平台
     * @param authentication  认证状态 0: "未认证", 1: "已认证"
     * @param verification 认证方式    0: "CAS" ,1: "OAUTH" , 2 :"SAML"
     * @param tokenCheckType Token 验证方式 0:安全优先, 1:性能优先
     * @param clientId 应用ID
     * @return
     */
    List<ClientListBean> selectAllClient(@Param("authentication") Integer authentication,
                                         @Param("verification") Integer verification,
                                         @Param("tokenCheckType") Integer tokenCheckType,
                                         @Param("clientId") Integer clientId);

    /**
     * 查询应用平台用户名个数
     * @param clientName 应用名个数
     * @return
     */
    Integer countClientByclientName(@Param("clientName") String clientName, @Param("clientId") Integer clientId);

    /**
     * 查询应用平台appId个数
     * @param appId appid
     * @return  appid个数
     */
    Integer countClientByAppId(@Param("appId") String appId, @Param("clientId") Integer clientId);

    /**
     * 新增应用
     * @param clientBean 应用实体
     * @param userId    新增用户ID
     */
    void addClient(@Param("client")CertificationBean clientBean, @Param("userId") Integer userId);

    /**
     * 编辑应用
     * @param clientBean 应用实体
     * @param userId    编辑用户ID
     */
    void updateApp(@Param("client")ClientBean clientBean, @Param("userId")Integer userId);



}
