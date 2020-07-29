package cloud.celldata.membrane.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * SSO直接放行url配置列表
 */
@Configuration
@ConfigurationProperties(prefix = "membrane.filter")
public class MembraneHttpRequestFilterConfig {
    // 需放行的url列表
    private List<String> anonymousURIs;

    //swagger 放行地址
    private List<String> swaggerURIs;

    public List<String> getSwaggerURIs() {
        return swaggerURIs;
    }

    public void setSwaggerURIs(List<String> swaggerURIs) {
        this.swaggerURIs = swaggerURIs;
    }

    public List<String> getAnonymousURIs() {
        return anonymousURIs;
    }
    public void setAnonymousURIs(List<String> anonymousURIs) {
        this.anonymousURIs = anonymousURIs;
    }
}
