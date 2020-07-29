package cloud.celldata.membrane.pojo.entity;

/**
 * 外部认证源用户实体
 *
 * @ProjectName: membrane
 * @Package: cloud.celldata.membrane.pojo.entity
 * @ClassName: ExternalAuthUserEntity
 * @Description: 外部数据源 用户实体
 * @Author: jiwang
 * @CreateDate: 2020/6/29 17:04
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/6/29 17:04
 */
public class ExternalAuthUserEntity {

    //外部数据源 用户userName
    private String userName;

    //外部数据源 用户类型
    private Integer verification;

    //外部数据源 ID
    private Integer externalAuthId;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getVerification() {
        return verification;
    }

    public void setVerification(Integer verification) {
        this.verification = verification;
    }

    public Integer getExternalAuthId() {
        return externalAuthId;
    }

    public void setExternalAuthId(Integer externalAuthId) {
        this.externalAuthId = externalAuthId;
    }
}
