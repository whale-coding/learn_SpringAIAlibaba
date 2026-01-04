package com.star.controller;

import jakarta.annotation.Resource;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

/**
 * @author 聂建强
 * @date 2026/1/4 16:34
 * @description:
 */
@RestController
public class McpClientCallBaiDuMcpController {
    @Resource
    private ChatClient chatClient; // 添加了MCP调用能力

    @Resource
    private ChatModel chatModel; // 没有添加MCP调用能力

    /**
     * 添加了MCP 调用能力
     * @param msg
     * @return
     */
    // http://localhost:8080/mcp/chat?msg=查询北纬39.9042东经116.4074天气
    // http://localhost:8080/mcp/chat?msg=查询61.149.121.66归属地
    // http://localhost:8080/mcp/chat?msg=查询昌平到天安门路线规划
    @GetMapping("/mcp/chat")
    public Flux<String> chat(@RequestParam(name = "msg") String msg){
        return chatClient.prompt(msg).stream().content();
    }

    /**
     * 没有添加MCP 调用能力
     * @param msg
     * @return
     */
    // http://localhost:8080/mcp/chat2?msg=查询北纬39.9042东经116.4074天气
    @RequestMapping("/mcp/chat2")
    public Flux<String> chat2(@RequestParam(name = "msg") String msg){
        return chatModel.stream(msg);
    }
}
