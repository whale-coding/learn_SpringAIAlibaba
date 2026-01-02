package com.star.controller;

import jakarta.annotation.Resource;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
public class ChatHelloController {

    @Resource
    private ChatModel chatModel;  // 对话模型，调用阿里云百炼平台

    /**
     * 普通调用
     * @param msg
     * @return
     */
    @GetMapping(value = "/hello/dochat")
    public String doChat(@RequestParam(name = "msg",defaultValue="你是谁") String msg){
        return chatModel.call(msg);
    }

    /**
     * 流式调用
     * @param msg
     * @return
     */
    @GetMapping(value = "/hello/streamchat")
    public Flux<String> stream(@RequestParam(name = "msg",defaultValue="你是谁") String msg){
        return chatModel.stream(msg);
    }
}
