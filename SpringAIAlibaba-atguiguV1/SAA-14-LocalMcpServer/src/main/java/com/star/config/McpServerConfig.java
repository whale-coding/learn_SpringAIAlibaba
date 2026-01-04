package com.star.config;

import com.star.service.WeatherService;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.ai.tool.method.MethodToolCallbackProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author 聂建强
 * @date 2026/1/4 16:14
 * @description: ToolCallbackProvider 配置类
 */
@Configuration
public class McpServerConfig {
    /**
     * 将工具方法暴露给外部 mcp client 调用
     * @param weatherService 天气服务工具类
     * @return
     */
    @Bean
    public ToolCallbackProvider weatherTools(WeatherService weatherService){
        return MethodToolCallbackProvider.builder()
                .toolObjects(weatherService)
                .build();
    }
}
