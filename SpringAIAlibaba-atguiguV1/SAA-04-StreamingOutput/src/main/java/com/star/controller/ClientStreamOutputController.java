package com.star.controller;

import jakarta.annotation.Resource;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

/**
 * @author 聂建强
 * @date 2026/1/3 15:32
 * @description: 通过ChatClient 实现stream实现流式输出
 */
@RestController
public class ClientStreamOutputController {
    @Resource(name = "deepseekChatClient")
    private ChatClient deepseekChatClient;

    @Resource(name = "qwenChatClient")
    private ChatClient qwenChatClient;

    @GetMapping(value = "/stream/chatflux3")
    public Flux<String> chatflux3(@RequestParam(name = "question",defaultValue = "你是谁") String question){
        // 使用 deepseek 模型进行流式输出
        return deepseekChatClient.prompt(question).stream().content();
    }

    @GetMapping(value = "/stream/chatflux4")
    public Flux<String> chatflux4(@RequestParam(name = "question",defaultValue = "你是谁") String question){
        // 使用 qwen 模型进行流式输出
        return qwenChatClient.prompt(question).stream().content();
    }
}
