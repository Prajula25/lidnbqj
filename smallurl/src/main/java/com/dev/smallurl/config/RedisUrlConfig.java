package com.dev.smallurl.config;

import com.dev.smallurl.model.URL;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisUrlConfig {


  @Autowired
  ObjectMapper mapper;

  @Autowired
  RedisConnectionFactory connectionFactory;

  @Bean
  RedisTemplate<String, URL> redisTemplate() {
    final RedisTemplate<String, URL> redisTemplate = new RedisTemplate<>();
    Jackson2JsonRedisSerializer valueSerializer = new Jackson2JsonRedisSerializer(URL.class);
    valueSerializer.setObjectMapper(mapper);
    redisTemplate.setConnectionFactory(connectionFactory);
    redisTemplate.setKeySerializer(new StringRedisSerializer());
    redisTemplate.setValueSerializer(valueSerializer);
    return redisTemplate;
  }
}