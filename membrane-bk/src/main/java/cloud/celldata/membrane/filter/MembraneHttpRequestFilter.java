package cloud.celldata.membrane.filter;


import cloud.celldata.membrane.config.MembraneHttpRequestFilterConfig;
import cloud.celldata.membrane.excep.MembraneException;
import cloud.celldata.membrane.service.LoginTokenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * 权限过滤器
 */
@Component
public class MembraneHttpRequestFilter implements Filter {

    private static final Logger log = LoggerFactory.getLogger(MembraneHttpRequestFilter.class);

    @Autowired
    private MembraneHttpRequestFilterConfig membraneHttpRequestFilterConfig;

    @Autowired
    private LoginTokenService loginTokenService;

    @Override
    public void init(FilterConfig arg0) {
        log.info("-------------init-------------");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;

        RequestReaderHttpServletRequestWrapper requestReaderHttpServletRequestWrapper = new RequestReaderHttpServletRequestWrapper(httpServletRequest);
        httpServletResponse.setHeader("Access-Control-Allow-Origin", "*");
        httpServletResponse.setHeader("Access-Control-Allow-Credentials", "true");
        httpServletResponse.setHeader("Access-Control-Allow-Methods", "*");
        httpServletResponse.setHeader("Access-Control-Allow-Headers", "*");
        httpServletResponse.setHeader("Access-Control-Expose-Headers", "*");
        httpServletResponse.setHeader("content-type", "application/json;charset=UTF-8");

        String requestURI = requestReaderHttpServletRequestWrapper.getRequestURI();
        log.info("requestURI:{}", requestURI);

        List<String> getAnonymousURIs = membraneHttpRequestFilterConfig.getAnonymousURIs();

        if(getAnonymousURIs != null &&
           getAnonymousURIs.stream().anyMatch(uri -> uri.equalsIgnoreCase(requestURI))){
            filterChain.doFilter(requestReaderHttpServletRequestWrapper, response);
            return;
        }

        // 过滤vue默认头请求确认
        if (httpServletRequest.getMethod().toUpperCase().equals("OPTIONS")) {
            httpServletResponse.setStatus(HttpServletResponse.SC_OK);
            return;
        }


        //放行swagger
        if (membraneHttpRequestFilterConfig.getSwaggerURIs()!=null || membraneHttpRequestFilterConfig.getSwaggerURIs().size()==0){
            for (String uri : membraneHttpRequestFilterConfig.getSwaggerURIs()) {
                if (requestURI.startsWith(uri)) {
                    filterChain.doFilter(requestReaderHttpServletRequestWrapper, response);
                    return;
                }
            }
        }

        // 获取token
        String token = httpServletRequest.getHeader("Authorization");
        log.info("请求头获取Authorization：{}", token);
        if (!StringUtils.isEmpty(token)) {
            // 验证Token是否有效
            log.info("验证Authorization：{}", token);
            try {
                Boolean result = loginTokenService.validateMembraneToken(token);
                log.info("验证Authorization是否有效：{}", result);
                if (Boolean.TRUE.equals(result)) {
                    filterChain.doFilter(requestReaderHttpServletRequestWrapper, response);
                }
            } catch (MembraneException e) {
                log.error("Exception found during validate membrane Authorization", e);
            }
        }
        httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    }

    @Override
    public void destroy() {
        log.info("-------------destroy-------------");
    }

}
