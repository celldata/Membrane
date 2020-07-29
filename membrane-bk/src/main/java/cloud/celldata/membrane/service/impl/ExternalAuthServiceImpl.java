package cloud.celldata.membrane.service.impl;

import cloud.celldata.membrane.excep.MembraneException;
import cloud.celldata.membrane.mapper.ExternalAuthMapper;
import cloud.celldata.membrane.mapper.UserMapper;
import cloud.celldata.membrane.pojo.ResponseCode;
import cloud.celldata.membrane.pojo.vo.LdapBean;
import cloud.celldata.membrane.pojo.entity.ExternalAuthEntity;
import cloud.celldata.membrane.pojo.entity.ExternalAuthUserEntity;
import cloud.celldata.membrane.pojo.vo.external.*;
import cloud.celldata.membrane.service.ExternalAuthService;
import cloud.celldata.membrane.utils.AttributesExtractUtils;
import cloud.celldata.membrane.utils.BeanMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.naming.Context;
import javax.naming.NameClassPair;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import java.util.*;

/**
 * 外部认证管理业务逻辑层
 *
 * @ProjectName: membrane
 * @Package: cloud.celldata.membrane.service.impl
 * @ClassName: ExternalAuthServiceImpl
 * @Description: 外部认证管理业务逻辑层接口实现
 * @Author: jiwang
 * @CreateDate: 2020/6/22 15:58
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/6/22 15:58
 */
@Service
@Transactional
public class ExternalAuthServiceImpl implements ExternalAuthService {

    private static Logger logger = LoggerFactory.getLogger(ExternalAuthServiceImpl.class);

    @Autowired
    ExternalAuthMapper externalAuthMapper;

    @Autowired
    UserMapper userMapper;

    /**
     * 新增外部认证源
     * @param externalAuthInfo 外部认证源实体
     * @param userId 用户ID
     * @return 新增外部认证源
     */
    @Override
    public ExternalAuthEntity addExternalAuth(ExternalAuthInfo externalAuthInfo, Integer userId) {

        ExternalAuthEntity externalAuth = getExternalAuth(externalAuthInfo);
        Integer countName =externalAuthMapper.countExternalAuthNameByName(null,externalAuth.getName());
        if (countName != 0){
            throw new MembraneException(ResponseCode.EXTERNAL_AUTH_NAME_EXISTS);
        }
        externalAuthMapper.addExternalAuth(externalAuth,userId);


        return externalAuth;
    }

    /**
     * 外部认证源列表查询 多条件查询
     * @param name 认证名称
     * @param type 类型
     * @param id  外部认证Id
     * @return 列表
     */
    @Override
    public List<ExternalAuthInfo> selectAllExternalAuth(String name, Integer type, Integer id) {

        //查出 外部认证源列表
        List<ExternalAuthEntity> externalAuthEntityList =externalAuthMapper.selectAllExternalAuth(id,type,name);

        ArrayList<ExternalAuthInfo> externalAuthInfos = new ArrayList<>();
        for (ExternalAuthEntity entity : externalAuthEntityList) {
            ExternalAuthInfo authInfo = BeanMapper.map(entity, ExternalAuthInfo.class);
            switch (entity.getType()){
                // ldap
                case 1:
                    Map<String, Object> extract = AttributesExtractUtils.extract(entity, "url",
                            "userDn", "pwd", "filter", "base","sycUser","sycSche","sycFixed");
                    authInfo.setParameters(extract);
                    break;
                //后续其他外部认证
            }

            externalAuthInfos.add(authInfo);
        }

        return externalAuthInfos;
    }

    /**
     * 外部认证修改
     * @param externalAuthInfo 修改实体
     * @return 修改后的实体
     */
    @Override
    public ExternalAuthEntity updateExternalAuth(ExternalAuthInfo externalAuthInfo,Integer userId) {

        //序列化
        ExternalAuthEntity externalAuthEntity = getExternalAuth(externalAuthInfo);
        Integer countName =externalAuthMapper.countExternalAuthNameByName(externalAuthEntity.getId(),externalAuthEntity.getName());
        if (countName != 0){
            throw new MembraneException(ResponseCode.EXTERNAL_AUTH_NAME_EXISTS);
        }
        externalAuthMapper.updateExternalAuth(externalAuthEntity,userId);

        return externalAuthEntity;
    }

    /**
     * 外部认证删除
     * @param idList 删除 外部认证
     * @param userId 用户ID
     */
    @Override
    public void removeExternalAuth(List<Integer> idList,Integer userId) {
        //先判断外部认证是否被使用
        Integer appUseExternalAuth = externalAuthMapper.countAppUseExternalAuth(idList);
        if (appUseExternalAuth != 0){
            throw new MembraneException(ResponseCode.EXTERNAL_AUTH_HAVE_ALREADY_USED);
        }

        externalAuthMapper.removeExternalAuth(idList,userId);

    }

    /**
     * 外部认证账号密码校验
     * @param externalAuthEntity 外部认证源 实体
     * @param userName 外部认证用户名
     * @param password 外部认证密码
     * @return 是否通过外部数据源认证
     */
    @Override
    public Boolean validateExternalAuthUser(ExternalAuthEntity externalAuthEntity,String userName,String password) throws NamingException {

        if (externalAuthEntity == null){
            return false;
        }
        switch (externalAuthEntity.getType()){
            case 1:
                //ldap数据源
                DirContext ctx = null;
                //获取连接源数据
                String baseUserDn = externalAuthEntity.getBase();
                String url = externalAuthEntity.getUrl();
                String userDn = "uid="+userName+","+baseUserDn;
                Hashtable<String, String> env = new Hashtable<>();
                env.put(javax.naming.Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
                env.put(javax.naming.Context.PROVIDER_URL, url);
                env.put(javax.naming.Context.SECURITY_AUTHENTICATION, "simple");
                env.put(javax.naming.Context.SECURITY_PRINCIPAL, userDn);
                env.put(javax.naming.Context.SECURITY_CREDENTIALS, password);
                try {
                    ctx = new javax.naming.directory.InitialDirContext(env);
                    return true;
                } catch (Exception e) {
                    return false;
                }finally {
                    if (ctx!=null){
                        ctx.close();
                    }
                }
            default:
                return null;
        }
    }

    /**
     * 外部数据源 用户数据同步到SSO平台
     * @param id 外部数据源ID
     */
    @Override
    public void syncExternalAuthUser(Integer id) throws NamingException {
        //查出 外部认证源数据
        List<ExternalAuthEntity> externalAuthEntityList =externalAuthMapper.selectAllExternalAuth(id,null,null);
        if (externalAuthEntityList == null && externalAuthEntityList.size() == 0){
            return;
        }
        switch (externalAuthEntityList.get(0).getType()){
            case 1:
                //ldap数据源
                logger.info("------开始同步ldap数据源------");
                String baseUserDn = externalAuthEntityList.get(0).getBase();
                String url = externalAuthEntityList.get(0).getUrl();
                String pwd = externalAuthEntityList.get(0).getPwd();
                Hashtable<String, Object> env = new Hashtable<String, Object>();
                Boolean isChange = false;
                env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
                env.put(Context.SECURITY_AUTHENTICATION, "simple");
                env.put(Context.SECURITY_CREDENTIALS, pwd);
                env.put(Context.SECURITY_PRINCIPAL, baseUserDn);
                env.put(Context.PROVIDER_URL, url);
                DirContext ctx = null;
                //外部数据源获得的用户Set  uid为ldap用户唯一ID；
                Set<String> externalAuthUserSet = new HashSet<>();
                //业务平台保存用户Set
                Set<String> ssoUserSet = new HashSet<>();
                //两个set 比较的结果
                Set<String> result = new HashSet<String>();
                //新增用户的实体集合
                ArrayList<ExternalAuthUserEntity> externalAuthUserList = new ArrayList<>();
                try {
                    ctx = new InitialDirContext(env);
                    NamingEnumeration<NameClassPair> list = ctx.list(baseUserDn);
                    while (list.hasMore()) {
                        NameClassPair ncp = list.next();
                        String cn = ncp.getName();
                        if (cn.indexOf("=") != -1) {
                            int index = cn.indexOf("=");
                            //cn 为ldap 用户主键 uid
                            cn = cn.substring(index + 1, cn.length());
                            externalAuthUserSet.add(cn);
                        }
                    }
                }  finally {
                    if (ctx != null) {
                            ctx.close();
                    }
                }
                ssoUserSet = userMapper.selectExternalAuthUserNameList(externalAuthEntityList.get(0).getType(), id);
                result.clear();
                result.addAll(externalAuthUserSet);
                //留下的set 是 外部数据源用户set 和 sso数据库用户set的差集
                result.removeAll(ssoUserSet);

                if (result.size() != 0) {
                    //创建实体List
                    for (String userName : result) {
                        ExternalAuthUserEntity externalAuthUserEntity = new ExternalAuthUserEntity();
                        externalAuthUserEntity.setUserName(userName);
                        externalAuthUserEntity.setVerification(externalAuthEntityList.get(0).getType());
                        externalAuthUserEntity.setExternalAuthId(id);
                        externalAuthUserList.add(externalAuthUserEntity);
                    }
                    isChange = true;
                    userMapper.addExternalAuthUser(externalAuthUserList);
                    logger.info("------数据源 {} 用户多于权限平台数据源 增加多余用户数据------",id );
                }

                result.clear();
                result.addAll(ssoUserSet);
                //留下的set 是 sso数据库用户set和 外部数据源用户set的差集
                result.removeAll(externalAuthUserSet);
                if (result.size()!=0){
                    //删除 SSO数据库 比ldap数据库多的部分 （ldap删除的用户）
                    userMapper.removeExternalAuthUser(new ArrayList<>(result),id);
                    isChange = true;
                    logger.info("------数据源 {} 用户少于权限平台数据源 删除权限平台多余用户数据------" ,id);
                }
                if (!isChange){
                    logger.info("------数据源 {} 用户与权限平台数据源 一致 没有任何改变------" ,id);
                }

        }


    }


    /**
     * 序列化外部数据源数据
     * @param externalAuthInfo  外部数据源实体
     * @return 外部数据源
     */
    private ExternalAuthEntity  getExternalAuth(ExternalAuthInfo externalAuthInfo){
        switch (externalAuthInfo.getType()){
            case 1:
                ExternalAuthFactory<LdapBean> ldapBeanexternalAuthFactory = LdapBean::new;
                LdapBean ldapBean = ldapBeanexternalAuthFactory.creat(externalAuthInfo);
                ExternalAuthEntity externalAuthEntity = BeanMapper.map(ldapBean, ExternalAuthEntity.class);
                return  externalAuthEntity;
        }

        return null;
    }
}
