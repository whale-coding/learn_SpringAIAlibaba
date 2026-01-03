package com.star.controller;

import jakarta.annotation.Resource;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

/**
 * @author 聂建强
 * @date 2026/1/3 15:28
 * @description: 通过ChatModel 实现stream实现流式输出
 */
@RestController
public class ChatModelStreamOutputController {
    @Resource(name = "deepseek")
    private ChatModel deepseekChatModel;

    @Resource(name = "qwen")
    private ChatModel qwenChatModel;

    @GetMapping(value = "/stream/chatflux1")
    public Flux<String> chatflux(@RequestParam(name = "question",defaultValue = "你是谁") String question){
        // 使用 deepseek 模型进行流式输出
        return deepseekChatModel.stream(question);
    }

    @GetMapping(value = "/stream/chatflux2")
    public Flux<String> chatflux2(@RequestParam(name = "question",defaultValue = "你是谁") String question){
        // 使用 qwen 模型进行流式输出
        return qwenChatModel.stream(question);
    }
}
