package cloud.celldata.membrane;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageHelper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.Properties;

/**
 * 启动类
 */
@SpringBootApplication
@ComponentScan(basePackages = "cloud.*")
@MapperScan(value = "cloud.celldata.membrane.mapper")
@EnableScheduling
public class MembraneApplication {

	public static void main(String[] args) {SpringApplication.run(MembraneApplication.class, args);}

	/**
	 * 设置 redisTemplate 的序列化设置
	 */
	@Bean
	public RedisTemplate<Object, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
		// 1.创建 redisTemplate 模版
		RedisTemplate<Object, Object> template = new RedisTemplate<>();
		// 2.关联 redisConnectionFactory
		template.setConnectionFactory(redisConnectionFactory);
		// 3.创建 序列化类
		Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
		ObjectMapper om = new ObjectMapper();
		// 4.设置可见度
		om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
		// 5.启动默认的类型
		om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
		// 6.序列化类，对象映射设置
		jackson2JsonRedisSerializer.setObjectMapper(om);
		// 7.设置 value 的转化格式和 key 的转化格式
//        template.setValueSerializer(jackson2JsonRedisSerializer);
		template.setValueSerializer(new GenericJackson2JsonRedisSerializer());
		template.setKeySerializer(new StringRedisSerializer());
		template.afterPropertiesSet();
		return template;
	}


	/**
	 * 配置mybatis的分页插件pageHelper
	 */
	@Bean
	public PageHelper pageHelper() {
		PageHelper pageHelper = new PageHelper();
		Properties properties = new Properties();
		properties.setProperty("offsetAsPageNum", "true");
		properties.setProperty("rowBoundsWithCount", "true");
		properties.setProperty("reasonable", "true");
		// 配置mysql数据库的方言
		properties.setProperty("dialect", "mysql");
		pageHelper.setProperties(properties);
		return pageHelper;
	}
}
