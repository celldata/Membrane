# Membrane 权限管理系统后端开发说明

## 介绍

​        本文介绍业务平台对接SSO系统的流程与注意事项。

## 参数配置

```
app_id: xxxxxx                                                 // appId
sso_fe_url: http://xxxx.com                                    // 单点前端地址
sso_request_url: http://xxxxx.com/api                          // 单点后台接口前缀
sso_authentication_uri: /oauthServer/getAuthorityData          // SSO鉴权接口URI
```

​        app_id为业务平台唯一标识，app_key为SSO与业务平台间通信的密钥。目前app_id与app_key无固定生成规则，可参考使用UUID生成app_id、使用app_id进行md5编码后生成app_key的规则。

​        sso_fe_url为单点前端访问地址，sso_request_url为单点后台接口访问前缀，具体配置值参考业务平台前后端部署参数。

​        sso_authentication_uri为SSO鉴权接口URI，业务平台接入时配置”/oauthServer/getAuthorityData“（固定参数）即可。

## 过滤器实现

### 1 处理”预检“请求

```
HttpServletRequest httpServletRequest = (HttpServletRequest) request;
HttpServletResponse httpServletResponse = (HttpServletResponse) response;

httpServletResponse.setHeader("Access-Control-Allow-Origin", "*");
httpServletResponse.setHeader("Access-Control-Allow-Credentials", "true");
httpServletResponse.setHeader("Access-Control-Allow-Methods", "*");
httpServletResponse.setHeader("Access-Control-Allow-Headers", "*");
httpServletResponse.setHeader("Access-Control-Expose-Headers", "*");

if (httpServletRequest.getMethod().toUpperCase().equals("OPTIONS")) {
    httpServletResponse.setStatus(HttpServletResponse.SC_OK);
    return;
}
```

### 2 获取token

```
// 获取请求方域名路径
String originHost = httpServletRequest.getHeader("Origin");
// 获取token
String token = null;
if (!StringUtils.isEmpty(httpServletRequest.getHeader("Authorization"))) {
    token = requestReaderHttpServletRequestWrapper.getHeader("Authorization");
} else {
    redirectionLogon(response,originHost);
    return;
}
```

### 3 请求SSO进行接口鉴权

```
// 获取业务接口URI
String requestURI = requestReaderHttpServletRequestWrapper.getRequestURI();
// 请求SSO获得到权限集合
SSORequest ssoRequest = new SSORequest(appId, requestURI, token);
try {
    String path = ssoRequestUrl + ssoAuthenticationUri;
    Map<String, Object> resultMap = HttpClientUtils.postJSON(path, JSONObject.toJSONString(ssoRequest));
    int responseCode = (int)resultMap.get("err_CODE");
    // 鉴权出现错误
    if(responseCode != 0) {
        log.info("************************************鉴权出错："+ JSON.toJSONString(resultMap));
        // Token已经过期，重新登录
        if (responseCode == 1) {
            redirectionLogon(response, originHost);
            return;
        }
        // SSO平台鉴权异常
        else if (responseCode == 1021) {
            // token已过期 需要重新登录 返回重定向地址 和401
            redirectionLogon(response, originHost);
            log.info((String) resultMap.get("err_MESSAGE"));
            return;
        }
        // 其它鉴权问题
        else {
            httpServletResponse.sendError((Integer) resultMap.get("err_CODE"), (String) resultMap.get("err_MESSAGE"));
            return;
        }
    }
    AuthorityResponse authorityResponse = null;
    Object objData = resultMap.get("data");
    if (objData != null) {
        try {
            JSONObject data = (JSONObject) objData;
            authorityResponse = data.toJavaObject(AuthorityResponse.class);
            //执行数据权限mybatis过滤器
        } catch (Exception e) {
            redirectionLogon(response, originHost);
            return;
        }
    } else {
        redirectionLogon(response, originHost);
        return;
    }
    log.info("*********************************鉴权实体："+JSON.toJSONString(authorityResponse));
    if (authorityResponse != null) {
        // 判断是否需要鉴别功能权限
        if (authorityResponse.getExistAPI() != null && authorityResponse.getExistAPI()) {
            // 判断功能权限
            if(((authorityResponse.getAllAPIAuthority() == null) 
            	|| !authorityResponse.getAllAPIAuthority())
            	&& ((authorityResponse.getHaveAPI() == null) 
            	|| !authorityResponse.getHaveAPI())) {
                httpServletResponse.sendError(HttpServletResponse.SC_FORBIDDEN, "权限不足");
                return;
            }
        }
        //判断数据权限
        if(authorityResponse.getRelatedData()){
            checkDataPermissionBySQL(authorityResponse);
        }
    } else {
        redirectionLogon(response, originHost);
        return;
    }
    // 将userName放入request
    httpServletRequest.setAttribute("userName", authorityResponse.getUserName());
    // 放行执行服务函数
    filterChain.doFilter(requestReaderHttpServletRequestWrapper, response);
} catch (Exception e) {
    e.printStackTrace();
    try{
        OutputStream outputStream = response.getOutputStream();
        outputStream.write(e.getMessage().getBytes("UTF-8"));
        outputStream.close();
        httpServletResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
    }
    catch (Exception ex){
        log.error(ex.getMessage());
    }
    log.info(e.getMessage());
    return;
}
//清空缓存
InterceptorContext.removeGranularity();
```

#### 1） 通知前端重定向到登录页

```
/**
 * 通知前端重定向到登录页
 * @param response 请求响应
 * @param origin 请求源地址
 */
private void redirectionLogon(ServletResponse response, String origin){
    HttpServletResponse httpServletResponse = (HttpServletResponse)response;
    Map params = new HashMap();
    params.put("appId", appId);
    params.put("url", origin);
    params.put("certificationType", certificationType);
    String loginRedirection_Message = "{\"loginUrl\":\""+setUrlParams(ssoFeUrl, params)+"\"}";
    //返回401，由前端重定向到SSO系统
    httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    httpServletResponse.setHeader("content-type", "application/json;charset=UTF-8");
    try{
        OutputStream outputStream = response.getOutputStream();
        outputStream.write(loginRedirection_Message.getBytes("UTF-8"));
        outputStream.close();
    }
    catch (Exception e){
        log.error(e.getMessage());
    }
    finally {
        log.info("no token was provided, return 401 and related logon url");
    }
}
```

#### 2） 将参数和url整合

```
/**
 * 将参数和url整合
 * @param url url
 * @param params 参数
 * @return 带参数的跳转地址
 */
private String setUrlParams(String url, Map<String, String> params) {
    if (params!=null && params.size()>0) {
        url += "?";
        for (Map.Entry param : params.entrySet()) {
            url += param.getKey() + "=" + param.getValue() + "&";
        }
    }
    if (url!=null && url.endsWith("&")) {
        url = url.substring(0, url.length()-1);
    }
    return url;
}
```

#### 3） 通过sql实现数据权限拦截

```
/**
 * 通过sql实现数据权限拦截
 * @param authorityResponse 授权信息
 */
private void checkDataPermissionBySQL(AuthorityResponse authorityResponse) {
    Granularity granularity = new Granularity();
    granularity.setEmptyResult(authorityResponse.getRelatedData());
    granularity.setGranularityList(authorityResponse.getDataList());
    InterceptorContext.setGranularity(granularity);
}
```

### 4 Mybatis拦截器

```
/**
 * 拦截并根据权限信息处理sql
 */
@Override
public Object intercept(Invocation invocation) throws Throwable {
    try {
        Granularity granularity = InterceptorContext.getGranularity();
        if(granularity == null){
            return invocation.proceed();
        }
        StatementHandler statementHandler = (StatementHandler) invocation.getTarget();
        MetaObject metaStatementHandler = MetaObject.forObject(statementHandler, 
        	DEFAULT_OBJECT_FACTORY, DEFAULT_OBJECT_WRAPPER_FACTORY, DEFAULT_REFLECTOR_FACTORY);
        String sql = (String) metaStatementHandler.getValue("delegate.boundSql.sql");
        sql = sql.replace("\n","");
        sql = sql.trim();
        logger.info("拦截到的sql"+sql);
        // 判断是否授权的查询语句
        Boolean emptyResult = granularity.getEmptyResult();
        if(emptyResult){
            List<String> granularityList = granularity.getGranularityList();
            logger.info(" 数据权限"+granularityList.toString());
            sql = DataScope.getSql(sql, granularityList);
            logger.info("过滤之后的sql"+sql);
            metaStatementHandler.setValue("delegate.boundSql.sql", sql);
        }
    } catch (Exception e) {
        e.printStackTrace();
        logger.error("throw exception and will not handle the mybatis sql");
    }
    // process the next query process
    return invocation.proceed();
}
```

```
package cloud.celldata.membranedemo.interceptor.dataScope;

import com.alibaba.fastjson.JSON;
import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.operators.conditional.AndExpression;
import net.sf.jsqlparser.expression.operators.relational.EqualsTo;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class DataScope {
    public static String getSql(String sql, List<String> treeList) throws JSQLParserException {
        net.sf.jsqlparser.statement.Statement targetStatement = CCJSqlParserUtil.parse(sql);
        TableAliasFinder tablesNamesFinder = new TableAliasFinder();
        Map<String,String> x = tablesNamesFinder.getTableAlias(targetStatement);
        //拿出 目标sql所有表名
        Set<String> tabSet = new HashSet<>(tablesNamesFinder.getTableList(targetStatement));
        Select selectStatement = (Select) targetStatement;
        PlainSelect plainSelect = (PlainSelect) selectStatement.getSelectBody();
        if (plainSelect.getWhere() ==null){
            EqualsTo equalsTo = new EqualsTo();
            equalsTo.setLeftExpression(new Column("1"));
            equalsTo.setRightExpression(new Column("1"));
            plainSelect.setWhere(equalsTo);
        }
        for (String tree : treeList) {
            //拿出数据权限表达式所有表名
            DataScopeItem mgr = JSON.parseObject(tree, DataScopeItem.class);
            Set<String> dataSet = getTab(mgr, new HashSet());
            Set<String> result = new HashSet<>();
            //拿出两个表名的交集
            result.clear();
            result.addAll(dataSet);
            result.retainAll(tabSet);
            Expression where = plainSelect.getWhere();
            if (result.size()!= 0){
                if (targetStatement instanceof Select) {
                    Expression convert = DataScopeSQLExpressionConvert.convert(mgr, x, result);
                    if (convert != null) {
                        plainSelect.setWhere(new AndExpression(where, convert));
                    }
                }
            }
        }
        return selectStatement.toString();
    }

    private static Set<String> getTab(DataScopeItem mgr,Set set){
        if (mgr.getRight()!=null || mgr.getLeft()!= null){
            getTab(mgr.getRight(),set);
            getTab(mgr.getLeft(),set);
        }else if (mgr.type.equals(DataScopeItem.nodeType.LEFT_NODE)){
            set.add(String.valueOf(mgr.getValue()).split("\\.")[0]);
        }
        return set;
    }
}
```

