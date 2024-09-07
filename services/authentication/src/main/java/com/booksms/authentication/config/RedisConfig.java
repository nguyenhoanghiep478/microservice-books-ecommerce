package com.booksms.authentication.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericToStringSerializer;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;

@Configuration
@Slf4j
public class RedisConfig {
    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration();
        configuration.setHostName("localhost");
        configuration.setPort(6379);
        configuration.setDatabase(4);
        return new LettuceConnectionFactory(configuration);
    }

    @Bean
    public RedisTemplate<Integer,Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<Integer,Object> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);
        log.info("redis connection factory: {}", redisConnectionFactory);
        template.setKeySerializer(new GenericToStringSerializer<>(Integer.class));
        template.setValueSerializer(new Jackson2JsonRedisSerializer<>(Object.class));
        log.info("redis template: {}", template);
        return template;
    }
}
