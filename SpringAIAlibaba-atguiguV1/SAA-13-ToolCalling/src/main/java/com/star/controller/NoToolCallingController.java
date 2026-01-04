package com.star.controller;

import jakarta.annotation.Resource;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

/**
 * @author 聂建强
 * @date 2026/1/4 15:47
 * @description:
 */
@RestController
public class NoToolCallingController {
    @Resource
    private ChatModel chatModel;

    // http://localhost:8080/notoolcall/chat?msg=你是谁现在几点
    @GetMapping("/notoolcall/chat")
    public Flux<String> chat(@RequestParam(name = "msg",defaultValue = "你是谁现在几点") String msg){
        return chatModel.stream(msg);
    }
}
