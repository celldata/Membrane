package cloud.celldata.membrane.pojo.bo.token;

import java.io.Serializable;

/**
 * @ProjectName: membrane
 * @Package: cloud.celldata.membrane.pojo.bo.token
 * @ClassName: TokenKeyResponse
 * @Description: 在已经登录权限平台的时候，前端拥有SSOtoken 但是没有业务平台Token 此时想要跳转至业务平台 返回接口实体
 * @Author: jiwang
 * @CreateDate: 2020/6/22 11:11
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/6/22 11:11
 */
public class TokenKeyResponse implements Serializable {

    //业务平台tokenKey
    private String tokenKey;

    //是否有需要跳转的平台权限
    private boolean haveAccess;

    public String getTokenKey() {
        return tokenKey;
    }

    public void setTokenKey(String tokenKey) {
        this.tokenKey = tokenKey;
    }

    public boolean isHaveAccess() {
        return haveAccess;
    }

    public void setHaveAccess(boolean haveAccess) {
        this.haveAccess = haveAccess;
    }
}
