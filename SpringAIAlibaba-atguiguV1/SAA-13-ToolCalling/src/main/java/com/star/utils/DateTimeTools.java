package com.star.utils;

import org.springframework.ai.tool.annotation.Tool;

import java.time.LocalDateTime;

/**
 * @author 聂建强
 * @date 2026/1/4 15:45
 * @description: Tools
 */
public class DateTimeTools {

    /**
     * 1.定义 function call（tool call）
     * 2. returnDirect (一般使用 false)
     *      true = tool直接返回不走大模型，直接给客户
     *      false = 默认值，拿到tool返回的结果，给大模型，最后由大模型回复
     * @return time
     */
    @Tool(description = "获取当前时间", returnDirect = false)
    public String getCurrentTime(){
        return LocalDateTime.now().toString();
    }
}
