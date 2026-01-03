package com.star.controller;

import jakarta.annotation.Resource;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.chat.prompt.SystemPromptTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.Map;

/**
 * @author 聂建强
 * @date 2026/1/3 17:10
 * @description:
 */
@RestController
public class PromptTemplateController {
    @Resource(name = "deepseek")
    private ChatModel deepseekChatModel;

    @Resource(name = "qwen")
    private ChatModel qwenChatModel;

    @Resource(name = "deepseekChatClient")
    private ChatClient deepseekChatClient;

    @Resource(name = "qwenChatClient")
    private ChatClient qwenChatClient;

    @Value("classpath:/prompttemplate/atguigu-template.txt")
    private org.springframework.core.io.Resource userTemplate;  // 注入外部提示词模板文件资源

    /**
     * 1、PromptTemplate基本使用，使用占位符设置模版 PromptTemplate
     * @param topic 主题
     * @param output_format 输出格式
     * @param wordCount 字数
     * @return
     */
    // http://localhost:8080/prompttemplate/chat?topic=java&output_format=html&wordCount=200
    @GetMapping("/prompttemplate/chat")
    public Flux<String> chat(@RequestParam("topic") String topic,
                             @RequestParam("output_format") String output_format,
                             @RequestParam("wordCount") String wordCount){
        // 创建 PromptTemplate，设置占位符
        PromptTemplate promptTemplate = new PromptTemplate("讲一个关于{topic}的故事" +
                "并以{output_format}格式输出，" +
                "字数在{wordCount}左右");

        // PromptTemplate -> Prompt, 填充占位符
        Prompt prompt = promptTemplate.create(Map.of(
                "topic", topic,
                "output_format",output_format,
                "wordCount",wordCount));

        return deepseekChatClient.prompt(prompt).stream().content();
    }

    /**
     * 2、PromptTemplate读取外部模板文件，实现模版功能
     * @param topic 主题
     * @param output_format 输出格式
     * @return
     */
    // http://localhost:8080/prompttemplate/chat2?topic=java&output_format=html
    @GetMapping("/prompttemplate/chat2")
    public String chat2(@RequestParam("topic") String topic,
                        @RequestParam("output_format") String output_format){
        // 从文件中读取 PromptTemplate
        PromptTemplate promptTemplate = new PromptTemplate(userTemplate);
        // PromptTemplate -> Prompt, 填充占位符
        Prompt prompt = promptTemplate.create(Map.of("topic", topic, "output_format", output_format));

        return deepseekChatClient.prompt(prompt).call().content();
    }

    /**
     *  3、PromptTemplate多角色设定，ChatClient
     * @param sysTopic 系统角色主题
     * @param userTopic 用户输入主题
     * @return
     * 系统消息(SystemMessage)：设定AI的行为规则和功能边界(xxx助手/什么格式返回/字数控制多少)。
     * 用户消息(UserMessage)：用户的提问/主题
     */
    // http://localhost:8080/prompttemplate/chat3?sysTopic=法律&userTopic=知识产权法
    // http://localhost:8080/prompttemplate/chat3?sysTopic=法律&userTopic=夫妻肺片
    @GetMapping("/prompttemplate/chat3")
    public String chat3(@RequestParam("sysTopic") String sysTopic,
                        @RequestParam("userTopic") String userTopic){
        // 1.SystemPromptTemplate
        SystemPromptTemplate systemPromptTemplate = new SystemPromptTemplate("你是{systemTopic}助手，只回答{systemTopic}其它无可奉告，以HTML格式的结果。");
        Message sysMessage = systemPromptTemplate.createMessage(Map.of("systemTopic", sysTopic));
        // 2.PromptTemplate
        PromptTemplate userPromptTemplate = new PromptTemplate("解释一下{userTopic}");
        Message userMessage = userPromptTemplate.createMessage(Map.of("userTopic", userTopic));
        // 3.组合【关键】 多个 Message -> Prompt
        Prompt prompt = new Prompt(List.of(sysMessage, userMessage));
        // 4.调用 LLM
        return deepseekChatClient.prompt(prompt).call().content();
    }

    /**
     * 3、PromptTemplate多角色设定，ChatModel
     * @param question 用户问题
     * @return
     */
    // http://localhost:8080/prompttemplate/chat4?question=牡丹花
    @GetMapping("/prompttemplate/chat4")
    public String chat4(String question){
        // 1.系统消息
        SystemMessage systemMessage = new SystemMessage("你是一个Java编程助手，拒绝回答非技术问题。");
        // 2.用户消息
        UserMessage userMessage = new UserMessage(question);
        // 3.系统消息+用户消息=完整提示词
        // Prompt prompt = new Prompt(systemMessage, userMessage);
        Prompt prompt = new Prompt(List.of(systemMessage, userMessage));  // 多个消息组成提示词

        // 4.调用LLM
        return deepseekChatModel.call(prompt).getResult().getOutput().getText();
    }

    /**
     * 4、PromptTemplate 人物设定
     * @param question 用户问题
     * @return
     */
    // http://localhost:8080/prompttemplate/chat5?question=火锅
    @GetMapping("/prompttemplate/chat5")
    public Flux<String> chat5(String question){
        return deepseekChatClient.prompt()
                .system("你是一个Java编程助手，拒绝回答非技术问题。")
                .user(question)
                .stream()
                .content();
    }
}
