package com.star.config;

import com.alibaba.cloud.ai.dashscope.api.DashScopeApi;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * SaaL LLM 配置类
 */
@Configuration
public class SaaLLMConfig {
    // 创建 DashScopeApi Bean，配置 API Key
    @Bean
    public DashScopeApi dashScopeApi(){
        return DashScopeApi.builder()
                .apiKey(System.getenv("ALI_API_KEY"))
                .build();
    }

    // 实例化 ChatClient Bean,方便使用注解 @Resource 注入 ChatClient
    // 依赖注入 ChatModel,通过 ChatClient.builder(dashscopeChatModel).build();
    @Bean
    public ChatClient chatClient(ChatModel dashscopeChatModel){
        return ChatClient.builder(dashscopeChatModel)
                .build();
    }
}
