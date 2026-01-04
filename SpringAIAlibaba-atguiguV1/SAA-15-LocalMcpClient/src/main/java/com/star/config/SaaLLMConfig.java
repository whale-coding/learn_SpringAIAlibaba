package com.star.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author 聂建强
 * @date 2026/1/4 16:25
 * @description:
 */
@Configuration
public class SaaLLMConfig {
    @Bean
    public ChatClient chatClient(ChatModel chatModel, ToolCallbackProvider tools){
        return ChatClient.builder(chatModel)
                .defaultToolCallbacks(tools.getToolCallbacks())  // mcp协议，配置见yml文件
                .build();
    }
}
