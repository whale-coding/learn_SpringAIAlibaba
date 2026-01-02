package com.star.config;

import com.alibaba.cloud.ai.dashscope.api.DashScopeApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * SaaL LLM 配置类
 */
@Configuration
public class SaaLLMConfig {
    @Value("${spring.ai.dashscope.api-key}")
    private String apiKey;

    // 创建 DashScopeApi Bean，配置 API Key
    @Bean
    public DashScopeApi dashScopeApi(){
        return DashScopeApi.builder()
                .apiKey(apiKey)
                .build();
    }

    /**
     * 方式2:System.getenv("环境变量")
     * @return
     */
    // @Bean
    // public DashScopeApi dashScopeApi(){
    //     return DashScopeApi.builder()
    //             .apiKey(System.getenv("ALI_API_KEY"))
    //             .build();
    // }
}
