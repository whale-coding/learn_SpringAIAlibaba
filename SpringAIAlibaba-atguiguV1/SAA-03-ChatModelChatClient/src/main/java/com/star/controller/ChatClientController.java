package com.star.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 聂建强
 * @date 2026/1/3 15:21
 * @description: ChatClient 的使用（不支持自动注入）
 */
@RestController
public class ChatClientController {

    // @Resource
    // private ChatClient chatClient;  // ChatClient 不支持自动注入!!!不像 ChatModel 那样!!!

    /* 方式一：通过构造方法注入 ChatClient */
    private final ChatClient dashScopeChatClient;

    // ChatClient不支持自动输入，依赖ChatModel对象接口，ChatClient.builder(dashScopeChatModel).build();
    public ChatClientController(ChatModel dashScopeChatModel) {
        this.dashScopeChatClient = ChatClient.builder(dashScopeChatModel).build();
    }

    @GetMapping("/chatclient/dochat")
    public String doChat(@RequestParam(name = "msg",defaultValue = "2加9等于几") String msg){
        return dashScopeChatClient
                .prompt()
                .user(msg)
                .call()
                .content();
    }
}
