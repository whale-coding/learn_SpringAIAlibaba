package com.star.config;

import com.alibaba.cloud.ai.dashscope.api.DashScopeApi;
import com.alibaba.cloud.ai.dashscope.chat.DashScopeChatModel;
import com.alibaba.cloud.ai.dashscope.chat.DashScopeChatOptions;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * SaaL LLM 配置类
 * ChatModel+ChatClient+多模型共存
 * 说明：自定义模型，yml中就不需要配置模型名称了！
 */
@Configuration
public class SaaLLMConfig {

    // 模型名称常量定义，一套系统多模型共存
    private final String DEEPSEEK_MODEL = "deepseek-v3";
    private final String QWEN_MODEL = "qwen-plus";

    // ChatModel Bean 实例化，不同模型实例化不同的 ChatModel Bean
    @Bean(name = "deepseek")
    public ChatModel deepSeek(){
        return DashScopeChatModel.builder()
                .dashScopeApi(DashScopeApi.builder().apiKey(System.getenv("ALI_API_KEY")).build())
                .defaultOptions(DashScopeChatOptions.builder().withModel(DEEPSEEK_MODEL).build())
                .build();
    }

    // ChatModel Bean 实例化，不同模型实例化不同的 ChatModel Bean
    @Bean(name = "qwen")
    public ChatModel qwen(){
        return DashScopeChatModel.builder()
                .dashScopeApi(DashScopeApi.builder().apiKey(System.getenv("ALI_API_KEY")).build())
                .defaultOptions(DashScopeChatOptions.builder().withModel(QWEN_MODEL).build())
                .build();
    }

    // ChatClient Bean 实例化，不同模型实例化不同的 ChatClient Bean
    @Bean(name = "deepseekChatClient")
    public ChatClient deepseekChatClient(@Qualifier("deepseek") ChatModel deepseek){
        return ChatClient.builder(deepseek)
                .defaultOptions(ChatOptions.builder().model(DEEPSEEK_MODEL).build())  // 可选，因为 ChatModel 已经指定模型
                .build();
    }

    // ChatClient Bean 实例化，不同模型实例化不同的 ChatClient Bean
    @Bean(name = "qwenChatClient")
    public ChatClient qwenChatClient(@Qualifier("qwen") ChatModel qwen){
        return ChatClient.builder(qwen)
                // .defaultOptions(ChatOptions.builder().model(QWEN_MODEL).build())  // 可选，因为 ChatModel 已经指定模型
                .build();
    }
}
