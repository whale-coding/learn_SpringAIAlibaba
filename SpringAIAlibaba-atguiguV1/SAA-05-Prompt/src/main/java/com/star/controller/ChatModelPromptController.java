package com.star.controller;

import jakarta.annotation.Resource;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

/**
 * @author 聂建强
 * @date 2026/1/3 17:08
 * @description:
 */
@RestController
public class ChatModelPromptController {
    @Resource(name = "deepseek")
    private ChatModel deepseekChatModel;

    @Resource(name = "qwen")
    private ChatModel qwenChatModel;

    /**
     * 使用 Prompt 进行对话,返回完整响应信息
     * @param question
     * @return
     */
    // http://localhost:8080/prompt/chat2?question=葫芦娃
    @GetMapping("/prompt/chat2")
    public Flux<ChatResponse> chat2(@RequestParam("question") String question){
        // 系统消息
        SystemMessage systemMessage = new SystemMessage("你是一个讲故事的助手,每个故事控制在300字以内");
        // 用户消息
        UserMessage userMessage = new UserMessage(question);
        // 将用户消息和系统消息封装到 Prompt 中，然后传递给 ChatModel
        Prompt prompt = new Prompt(userMessage, systemMessage);
        // 原始返回的是 Flux<ChatResponse>，包含完整的响应信息
        return deepseekChatModel.stream(prompt);
    }

    /**
     * 使用 Prompt 进行对话,从完整响应信息中获取文本内容，也就是最终的回答
     * @param question
     * @return
     */
    // http://localhost:8080/prompt/chat2?question=葫芦娃
    @GetMapping("/prompt/chat3")
    public Flux<String> chat3(@RequestParam("question") String question){
        // 系统消息
        SystemMessage systemMessage = new SystemMessage("你是一个讲故事的助手,每个故事控制在600字以内且以HTML格式返回");
        // 用户消息
        UserMessage userMessage = new UserMessage(question);
        // 将用户消息和系统消息封装到 Prompt 中，然后传递给 ChatModel
        Prompt prompt = new Prompt(userMessage, systemMessage);
        // 从完整响应信息中获取文本内容，也就是最终的回答
        return deepseekChatModel.stream(prompt)
                .map(response -> response.getResults().get(0).getOutput().getText());
    }
}
