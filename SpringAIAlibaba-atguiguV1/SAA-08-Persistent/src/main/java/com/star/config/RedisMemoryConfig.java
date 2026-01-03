package com.star.config;

import com.alibaba.cloud.ai.memory.redis.RedisChatMemoryRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author 聂建强
 * @date 2026/1/3 20:59
 * @description:
 */
@Configuration
public class RedisMemoryConfig {
    @Value("${spring.data.redis.host}")
    private String host;

    @Value("${spring.data.redis.port}")
    private int port;

    // 配置 RedisChatMemoryRepository Bean
    @Bean
    public RedisChatMemoryRepository redisChatMemoryRepository(){
        return RedisChatMemoryRepository.builder()
                    .host(host)
                    .port(port)
                .build();
    }
}
