package com.example.hb.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component; 

@Component
public class RedisConfig {
	
	 @Value("${spring.redis.host}")
	    private String host;

	    @Value("${spring.redis.port}")
	    private int port;

	    
	    @Bean
	    public RedisConnectionFactory redisConnectionFactory() {
	    	LettuceConnectionFactory lettuceConnectionFactory = new LettuceConnectionFactory();
	    	lettuceConnectionFactory.setHostName(host);
	    	lettuceConnectionFactory.setPort(port);
	    	lettuceConnectionFactory.afterPropertiesSet();
	        return lettuceConnectionFactory;
	    }
	    
	    @Bean(name = "redisBlackListTemplate")
	    public RedisTemplate<String, Object> template(RedisConnectionFactory factory) {
	    	RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
	        redisTemplate.setConnectionFactory(redisConnectionFactory());
	        redisTemplate.afterPropertiesSet();
	        return redisTemplate;
	    }
	    
	    /*
    @Bean(name = "redisBlackListTemplate")
    public RedisTemplate<String, Object> template(RedisConnectionFactory factory) {
        // create RedisTemplate<String, Object>
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        // Configuring the connection factory
        template.setConnectionFactory(factory);
        // Define the Jackson2JsonRedisSerializer serialization object
        Jackson2JsonRedisSerializer<Object> jacksonSeial = new Jackson2JsonRedisSerializer<>(Object.class);
        ObjectMapper om = new ObjectMapper();
        // Specify the fields to serialize, field, get and set, and the range of modifiers, ANY is both private and public
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        // Specify the type of serialized input, the class must be non-final modified, final modified class, such as String, Integer, etc. will report an exception
        om.activateDefaultTyping(

                LaissezFaireSubTypeValidator.instance ,
                ObjectMapper.DefaultTyping.NON_FINAL,

                JsonTypeInfo.As.WRAPPER_ARRAY);
        jacksonSeial.setObjectMapper(om);
        StringRedisSerializer stringSerial = new StringRedisSerializer();
        // redis key Serialization method using stringSerial
        template.setKeySerializer(stringSerial);
        // redis value serialization method using jackson
        template.setValueSerializer(jacksonSeial);
        // redis hash key serialized using stringSerial
        template.setHashKeySerializer(stringSerial);
        // redis hash value serialized using jackson
        template.setHashValueSerializer(jacksonSeial);
        template.afterPropertiesSet();
        return template;
        */
}