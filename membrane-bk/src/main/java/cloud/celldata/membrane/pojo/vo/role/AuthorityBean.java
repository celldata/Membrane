package cloud.celldata.membrane.pojo.vo.role;

import cloud.celldata.membrane.pojo.entity.DataScreeEntity;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

/**
 * 权限信息实体
 *
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AuthorityBean {

    // 是否是全部数据权限
    private Boolean allDataAuthority;

    // 数据权限列表
    private List<DataScreeEntity> authority;

    // 有数据权限的id列表
    private List<Integer> haveDataAuthority;

    public Boolean getAllDataAuthority() {
        return allDataAuthority;
    }

    public void setAllDataAuthority(Boolean allDataAuthority) {
        this.allDataAuthority = allDataAuthority;
    }

    public List<DataScreeEntity> getAuthority() {
        return authority;
    }

    public void setAuthority(List<DataScreeEntity> authority) {
        this.authority = authority;
    }

    public List<Integer> getHaveDataAuthority() {
        return haveDataAuthority;
    }

    public void setHaveDataAuthority(List<Integer> haveDataAuthority) {
        this.haveDataAuthority = haveDataAuthority;
    }
}
