package com.star.controller;

import com.star.records.StudentRecord;
import jakarta.annotation.Resource;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.function.Consumer;

/**
 * @author 聂建强
 * @date 2026/1/3 17:10
 * @description:
 */
@RestController
public class StructuredOutputController {

    @Resource(name = "deepseekChatClient")
    private ChatClient deepseekChatClient;

    /**
     * 结构化输出-生成学生记录-完整版
     * @param sname 学生姓名
     * @param email 学生邮箱
     * @return
     */
    // http://localhost:8080/structuredoutput/chat?sname=李四&email=zzyybs@126.com
    @GetMapping("/structuredoutput/chat")
    public StudentRecord chat(@RequestParam(name = "sname") String sname,
                              @RequestParam(name = "email") String email) {
        return deepseekChatClient.prompt()
                .user(new Consumer<ChatClient.PromptUserSpec>() {
                    @Override
                    public void accept(ChatClient.PromptUserSpec promptUserSpec) {
                        promptUserSpec.text("学号1001，我叫{sname},大学专业计算机科学与技术,邮箱{email}")
                                .param("sname",sname)
                                .param("email",email);
                    }
                })
                .call()
                .entity(StudentRecord.class);
    }

    /**
     * 结构化输出-生成学生记录-简化版(lambda表达式)
     * @param sname 学生姓名
     * @param email 学生邮箱
     * @return
     */
    // http://localhost:8080/structuredoutput/chat2?sname=孙伟&email=zzyybs@126.com
    @GetMapping("/structuredoutput/chat2")
    public StudentRecord chat2(@RequestParam(name = "sname") String sname,
                               @RequestParam(name = "email") String email){
        String stringTemplate = """
               学号1002，我叫{sname},大学专业软件工程,邮箱{email}
               """;
        return deepseekChatClient.prompt()
                .user(promptUserSpec -> promptUserSpec.text(stringTemplate)
                        .param("sname",sname)
                        .param("email",email))
                .call()
                .entity(StudentRecord.class);
    }
}
