package cloud.celldata.membrane.scheduled;

import cloud.celldata.membrane.mapper.ExternalAuthMapper;
import cloud.celldata.membrane.pojo.entity.ExternalAuthEntity;
import cloud.celldata.membrane.service.ExternalAuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.naming.NamingException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 定时任务每小时同步一次（同步数据源用户数据到权限平台）
 *
 * @ProjectName: membrane
 * @Package: cloud.celldata.membrane.scheduled
 * @ClassName: ScheduledMethod
 * @Description: 定时任务类
 * @Author: jiwang
 * @CreateDate: 2020/7/1 14:22
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/7/1 14:22
 */
@Component
public class ScheduledMethod {

    private static Logger logger = LoggerFactory.getLogger(ScheduledMethod.class);

    @Autowired
    ExternalAuthMapper externalAuthMapper;

    @Autowired
    ExternalAuthService externalAuthService;
    /**
     * 定时任务每小时同步一次  同步数据源用户数据 到 权限平台
     */
    @Scheduled(cron ="0 0 * * * ?" )
    public void syncExternalAuthUser(){

        //分布式情况下 最好加上分布式锁
        List<ExternalAuthEntity> externalAuthEntityList = externalAuthMapper.selectAllExternalAuth(null, null, null);
        logger.info("------同步外部数据源用户数据定时任务启动------");

        //过滤掉不需要同步外部数据源数据源的部分
        List<ExternalAuthEntity> externalAuthEntities = externalAuthEntityList.stream()
                .filter(it->null!=it.getSycUser())
                .filter(it->null!=it.getSycSche())
                .filter(it -> 1==it.getSycUser())
                .filter(it -> 1==it.getSycSche())
                .collect(Collectors.toList());

        if (externalAuthEntities.size()!=0) {
            for (ExternalAuthEntity externalAuthEntity : externalAuthEntities) {
                switch (externalAuthEntity.getType()){
                    case 1:
                        try {
                        Date createTime = externalAuthEntity.getCreateTime();
                        //应用创建时间
                        long create = createTime.getTime();
                        //目前时间和 数据源创建时间的小时差如果是外部数据源设定间隔时间的整数倍 说明 到了间隔时间
                        if ((System.currentTimeMillis()-create)/1000/60/60%externalAuthEntity.getSycFixed()==0){
                            externalAuthService.syncExternalAuthUser(externalAuthEntity.getId());
                            logger.info("------外部ldap数据源 {} 同步用户成功------",externalAuthEntity.getId());
                         }
                        } catch (NamingException e) {
                            logger.error("------外部ldap数据源连接出错 {} 同步用户失败------",externalAuthEntity.getId());
                            e.printStackTrace();
                        }catch (Exception e) {
                            logger.error("------外部ldap数据源 {} 同步用户失败------",externalAuthEntity.getId());
                            e.printStackTrace();
                        }
                        break;
                }
            }
        }
    }

}
