package cloud.celldata.membrane.pojo.entity;

import org.springframework.beans.factory.annotation.Value;

import java.util.List;
import java.util.UUID;

/**
 * 应用平台token
 */
public class MembraneTokenEntity {
    @Value("${membrane.issuer}")
    private String issuer;

    private Long issue_time;

    private String applicationId;
    private String user_name;
    private String application_name;
    private String userId;
    private List<Object> functions; //功能权限
    private String scopes; //数据权限
    private String tokenId;

    public MembraneTokenEntity(){
        this.issue_time =System.currentTimeMillis();
        this.tokenId = UUID.randomUUID().toString().replaceAll("-","");
    }

    public String getIssuer() {
        return issuer;
    }

    public void setIssuer(String issuer) {
        this.issuer = issuer;
    }

    public Long getIssue_time() {
        return issue_time;
    }

    public void setIssue_time(Long issue_time) {
        this.issue_time = issue_time;
    }

    public String getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(String applicationId) {
        this.applicationId = applicationId;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getApplication_name() {
        return application_name;
    }

    public void setApplication_name(String application_name) {
        this.application_name = application_name;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<Object> getFunctions() {
        return functions;
    }

    public void setFunctions(List<Object> functions) {
        this.functions = functions;
    }

    public String getScopes() {
        return scopes;
    }

    public void setScopes(String scopes) {
        this.scopes = scopes;
    }

    public String getTokenId() {
        return tokenId;
    }

    public void setTokenId(String tokenId) {
        this.tokenId = tokenId;
    }
}
