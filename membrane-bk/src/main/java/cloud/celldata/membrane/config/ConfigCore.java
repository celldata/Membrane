package cloud.celldata.membrane.config;


import cloud.celldata.membrane.filter.MembraneHttpRequestFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;

import java.util.Locale;

/**
 * 过滤器配置类
 */
@Configuration
public class ConfigCore {

    // 过滤器优先级
    private static final Integer REGISTRATION_ORDER = 1;

    private final String filterName = "MembraneHttpRequestFilter";

    /**
     * 注册自定义过滤器
     * @param membraneHttpRequestFilter 权限过滤器
     * @return 过滤器注册对象
     */
    @Bean
    public FilterRegistrationBean filterRegistrationBean(MembraneHttpRequestFilter membraneHttpRequestFilter) {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        // 添加过滤器
        registration.setFilter(membraneHttpRequestFilter);
        // 设置过滤路径（/*为所有路径）
        registration.addUrlPatterns("/*");
        // 添加默认参数
        registration.addInitParameter("name", "value");
        // 设置优先级
        registration.setName(filterName);
        // 设置优先级
        registration.setOrder(REGISTRATION_ORDER);
        return registration;
    }

    @Bean(name = "localeResolver")
    public LocaleResolver localeResolver() {
        AcceptHeaderLocaleResolver localeResolver = new AcceptHeaderLocaleResolver();
        /** set default locale **/
        localeResolver.setDefaultLocale(Locale.CHINA);
        return localeResolver;
    }


}
