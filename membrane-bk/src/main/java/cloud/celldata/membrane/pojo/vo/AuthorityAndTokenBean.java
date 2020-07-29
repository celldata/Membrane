package cloud.celldata.membrane.pojo.vo;

/**
 * 权限和鉴权信息实体
 */
public class AuthorityAndTokenBean {

    // 业务平台的权限token
    private String authorityToken;

    // 权限
    private Boolean isAllData;

    // 菜单
    private String menu;


    public String getMenu() {
        return menu;
    }

    public void setMenu(String menu) {
        this.menu = menu;
    }

    public String getAuthorityToken() {
        return authorityToken;
    }

    public void setAuthorityToken(String authorityToken) {
        this.authorityToken = authorityToken;
    }

    public Boolean getAllData() {
        return isAllData;
    }

    public void setAllData(Boolean allData) {
        isAllData = allData;
    }
}
