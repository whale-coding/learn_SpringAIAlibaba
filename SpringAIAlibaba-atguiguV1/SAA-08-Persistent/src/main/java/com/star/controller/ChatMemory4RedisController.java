package com.star.controller;

import jakarta.annotation.Resource;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.function.Consumer;

import static org.springframework.ai.chat.memory.ChatMemory.CONVERSATION_ID;

/**
 * @author 聂建强
 * @date 2026/1/3 17:10
 * @description: 将客户和大模型的对话问答保存进Redis 进行持久化记忆留存
 */
@RestController
public class ChatMemory4RedisController {

    @Resource(name = "deepseekChatClient")
    private ChatClient deepseekChatClient;

    /**
     * 使用匿名内部类的方式传递参数，实现用户会话持久化
     */
    // http://localhost:8080/chatmemory/chat?msg=2加5等于多少&userId=7
    // http://localhost:8080/chatmemory/chat?msg=再加5等于多少&userId=7
    @GetMapping("/chatmemory/chat")
    public String chat(@RequestParam(name = "msg") String msg, @RequestParam(name = "userId") String userId){
        return deepseekChatClient.prompt(msg)
                .advisors(new Consumer<ChatClient.AdvisorSpec>() {
                    @Override
                    public void accept(ChatClient.AdvisorSpec advisorSpec) {
                        // 设置会话 ID，实现不同用户的会话持久化
                        advisorSpec.param(CONVERSATION_ID, userId);
                    }
                })
                .call()
                .content();
    }

    /**
     * 使用Lambda 表达式简化代码，实现用户会话持久化
     */
    @GetMapping("/chatmemory/chat2")
    public String chat2(@RequestParam(name = "msg") String msg, @RequestParam(name = "userId") String userId){
        return deepseekChatClient.prompt(msg)
                .advisors(advisorSpec -> advisorSpec.param(CONVERSATION_ID, userId))
                .call()
                .content();
    }
}
