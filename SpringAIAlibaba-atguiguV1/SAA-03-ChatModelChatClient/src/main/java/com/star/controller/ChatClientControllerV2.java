package com.star.controller;

import jakarta.annotation.Resource;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * ChatModel 和 ChatClient 一般都会使用，混合着使用
 */
@RestController
public class ChatClientControllerV2 {

    @Resource
    private ChatModel chatModel;  // 注入 ChatModel

    @Resource
    private ChatClient dashScopechatClient;  // 注入 ChatClient


    @GetMapping("/chatclientv2/dochat")
    public String doChat(@RequestParam(name = "msg",defaultValue = "你是谁") String msg){
        // 先使用 ChatModel 进行对话
        String responseFromModel = chatModel.call(msg);

        // 然后使用 ChatClient 进行对话
        String responseFromClient = dashScopechatClient
                .prompt()
                .user(msg)
                .call()
                .content();

        return "Response from ChatModel: " + responseFromModel + "\nResponse from ChatClient: " + responseFromClient;
    }
}
