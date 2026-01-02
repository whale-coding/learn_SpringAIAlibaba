package com.star.controller;

import jakarta.annotation.Resource;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
public class OllamaController {

    @Resource(name = "ollamaChatModel")  // 指定使用 Ollama 的 ChatModel
    private ChatModel chatModel;

    @GetMapping("/ollama/chat")
    public String chat(@RequestParam(name = "msg") String msg){
        return chatModel.call(msg);
    }

    @GetMapping("/ollama/streamchat")
    public Flux<String> streamChat(@RequestParam(name = "msg",defaultValue = "你是谁") String msg){
        return chatModel.stream(msg);
    }
}
