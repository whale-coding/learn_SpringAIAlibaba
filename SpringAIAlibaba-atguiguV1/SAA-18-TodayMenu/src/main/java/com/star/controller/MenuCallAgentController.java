package com.star.controller;

import com.alibaba.cloud.ai.dashscope.agent.DashScopeAgent;
import com.alibaba.cloud.ai.dashscope.agent.DashScopeAgentOptions;
import com.alibaba.cloud.ai.dashscope.api.DashScopeAgentApi;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 聂建强
 * @date 2026/1/4 19:45
 * @description: 今日菜单-百炼工作流/智能体调用示例
 */
@RestController
public class MenuCallAgentController {
    // 百炼平台的 appid
    @Value("${spring.ai.dashscope.agent.options.app-id}")
    private String appId;

    // 百炼云平台的智能体接口对象
    private DashScopeAgent dashScopeAgent;

    public MenuCallAgentController(DashScopeAgentApi dashScopeAgentApi){
        // 初始化智能体代理对象
        this.dashScopeAgent = new DashScopeAgent(dashScopeAgentApi);
    }

    /**
     * 今日菜单-调用智能体
     * @param msg
     * @return
     */
    // http://localhost:8080/eatAgent?msg=今天吃什么
    @GetMapping(value = "/eatAgent")
    public String eatAgent(@RequestParam(name = "msg",defaultValue = "今天吃什么") String msg){
        DashScopeAgentOptions options = DashScopeAgentOptions.builder().withAppId(appId).build();

        Prompt prompt = new Prompt(msg, options);

        return dashScopeAgent.call(prompt).getResult().getOutput().getText();
    }
}
