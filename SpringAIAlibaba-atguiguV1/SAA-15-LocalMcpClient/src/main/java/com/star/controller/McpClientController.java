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
 * @date 2026/1/4 16:26
 * @description:
 */
@RestController
public class McpClientController {

    @Resource
    private ChatClient chatClient; // 使用mcp 支持

    @Resource
    private ChatModel chatModel; // 没有纳入tool支持，普通调用

    // http://localhost:8081/mcpclient/chat?msg=上海
    @GetMapping("/mcpclient/chat")
    public Flux<String> chat(@RequestParam(name = "msg",defaultValue = "北京") String msg){

        return chatClient.prompt(msg).stream().content();
    }

    // http://localhost:8081/mcpclient/chat2?msg=上海
    @RequestMapping("/mcpclient/chat2")
    public Flux<String> chat2(@RequestParam(name = "msg",defaultValue = "北京") String msg){

        return chatModel.stream(msg);
    }
}
