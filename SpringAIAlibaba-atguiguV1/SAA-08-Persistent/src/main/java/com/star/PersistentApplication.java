package com.star;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author 聂建强
 * @date 2026/1/3 20:54
 * @description: 将客户和大模型的对话问答保存进Redis 进行持久化记忆留存
 */
@SpringBootApplication
public class PersistentApplication {
    public static void main(String[] args) {
        SpringApplication.run(PersistentApplication.class,args);
    }
}