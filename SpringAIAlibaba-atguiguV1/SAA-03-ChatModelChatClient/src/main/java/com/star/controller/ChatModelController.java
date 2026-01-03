package com.star.controller;

import jakarta.annotation.Resource;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 聂建强
 * @date 2026/1/3 15:21
 * @description: ChatModel 的使用（支持自动注入）
 */
@RestController
public class ChatModelController {
    @Resource // 支持自动注入
    private ChatModel dashScopeChatModel;

    @GetMapping("/chatmodel/dochat")
    public String doChat(@RequestParam(name = "msg",defaultValue = "你是谁") String msg){
        return dashScopeChatModel.call(msg);
    }
}
