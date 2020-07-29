package cloud.celldata.membrane.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Redis工具类
 *
 * @ProjectName: membrane
 * @Package: cloud.celldata.membrane.utils
 * @ClassName: RedisUtil
 * @Description: java类作用描述
 * @Author: jiwang
 * @CreateDate: 2020/5/15 10:23
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/5/15 10:23
 */
@Component
public class RedisUtil {
    private static Logger logger = LoggerFactory.getLogger(RedisUtil.class);

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 删除对应的value
     * @param key key
     */
    public void remove(final String key) {
        if (exists(key)) {
            redisTemplate.delete(key);
        }
    }

    /**
     * 判断缓存中是否有对应的value
     * @param key key
     * @return 是否存在对应的value
     */
    public boolean exists(final String key) {
        return redisTemplate.hasKey(key);
    }

    /**
     * 读取缓存
     * @param key key
     * @return 对应的value
     */
    public Object get(final String key) {
        ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
        return operations.get(key);
    }

    /**
     * 哈希 添加
     * @param key key
     * @param hashKey hashKey
     * @param value value
     */
    public void hmSet(String key, Object hashKey, Object value){
        HashOperations<String, Object, Object> hash = redisTemplate.opsForHash();
        hash.put(key,hashKey,value);
    }

    /**
     * 哈希获取数据
     * @param key key
     * @param hashKey hashKey
     * @return value
     */
    public Object hmGet(String key, Object hashKey){
        HashOperations<String, Object, Object>  hash = redisTemplate.opsForHash();
        return hash.get(key,hashKey);
    }

    /**
     * hash过期时间设置
     * @param key key
     * @param time time
     * @param minutes 时间单位
     */
    public void setHashTime(String key,Long time, TimeUnit minutes){
        redisTemplate.expire(key,time,minutes);
    }

    /**
     * 从redis取hash
     * @param key key
     * @return value
     */
    public Object getAllObjectForHashKey(String key){
        Map entries = redisTemplate.opsForHash().entries(key);
        return entries ;
    }

    /**
     * 从redis删除hash中的某个特定key值
     * @param hashKey hashKey
     * @param key key
     */
    public void deleteHashKey(String hashKey,String key){
        redisTemplate.opsForHash().delete(hashKey,key);
    }

    /**
     * 普通缓存放入并设置时间
     * @param key 键
     * @param value 值
     * @param time 时间(秒) time要大于0 如果time小于等于0 将设置无限期
     * @return true成功 false 失败
     */
    public boolean set(String key, Object value, long time) {
        try {
            if (time > 0) {
                redisTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS);
            } else {
                set(key, value);
            }
            return true;
        } catch (Exception e) {
            logger.error("存入{}失败",key);
            return false;
        }
    }

    /**
     * 普通缓存放入
     * @param key 键
     * @param value 值
     * @return true成功 false失败
     */
    public boolean set(String key, Object value) {
        try {
            redisTemplate.opsForValue().set(key, value);
            return true;
        } catch (Exception e) {
            logger.error("存入{}失败",key);
            return false;
        }
    }

}
