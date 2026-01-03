package com.star.controller;

import jakarta.annotation.Resource;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.ToolResponseMessage;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.Objects;

/**
 * @author 聂建强
 * @date 2026/1/3 17:10
 * @description:
 */
@RestController
public class ChatClientPromptController {
    @Resource(name = "deepseekChatClient")
    private ChatClient deepseekChatClient;

    @Resource(name = "qwenChatClient")
    private ChatClient qwenChatClient;

    /**
     * 使用 Prompt 进行对话,system提示词和 user提示词
     * @param question
     * @return
     */
    // http://localhost:8080/prompt/chat?question=火锅介绍下
    @GetMapping("/prompt/chat")
    public Flux<String> chat(@RequestParam("question") String question){
        return deepseekChatClient.prompt()
                // system提示词:AI 能力边界
                .system("你是一个法律助手，只回答法律问题，其它问题回复，我只能回答法律相关问题，其它无可奉告")
                // user 提示词:用户输入的问题
                .user(question)
                .stream()
                .content();
    }

    /**
     * 使用 Prompt 进行对话,返回最终的回答内容,AssistantMessage
     * @param question
     * @return
     */
    // http://localhost:8080/prompt/chat4?question=葫芦娃
    @GetMapping("/prompt/chat4")
    public String chat4(@RequestParam("question") String question){
        AssistantMessage assistantMessage = Objects.requireNonNull(deepseekChatClient.prompt()
                        .user(question)
                        .call()
                        .chatResponse())
                .getResult()
                .getOutput();
        return assistantMessage.getText();
    }

    /**
     * 使用 Prompt 进行对话,根据城市名称返回天气信息, Tool提示词
     * @param city
     * @return
     */
    // http://localhost:8080/prompt/chat5?city=北京
    @GetMapping("/prompt/chat5")
    public String chat5(@RequestParam("city") String city){
        // 大模型的回答
        String answer = Objects.requireNonNull(deepseekChatClient.prompt()
                        .user(city + "未来3天天气情况如何?")
                        .call()
                        .chatResponse())
                .getResult()
                .getOutput()
                .getText();
        // 模拟调用天气工具，返回天气信息
        ToolResponseMessage toolResponseMessage = new ToolResponseMessage(
                List.of(new ToolResponseMessage.ToolResponse("1","获得天气",city))
        );
        // 从工具响应消息中获取文本内容，即天气
        String toolResponse = toolResponseMessage.getText();

        // 拼接大模型回答和工具响应，返回给用户
        return answer + toolResponse;
    }
}
