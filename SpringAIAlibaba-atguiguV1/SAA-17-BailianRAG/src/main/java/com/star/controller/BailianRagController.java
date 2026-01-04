package com.star.controller;

import com.alibaba.cloud.ai.advisor.DocumentRetrievalAdvisor;
import com.alibaba.cloud.ai.dashscope.api.DashScopeApi;
import com.alibaba.cloud.ai.dashscope.rag.DashScopeDocumentRetriever;
import com.alibaba.cloud.ai.dashscope.rag.DashScopeDocumentRetrieverOptions;
import jakarta.annotation.Resource;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.rag.retrieval.search.DocumentRetriever;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

/**
 * @author 聂建强
 * @date 2026/1/4 11:01
 * @description: RAG 使用云上的向量数据库进行故障解释
 */
@RestController
public class BailianRagController {
    @Resource
    private ChatClient chatClient;

    @Resource
    private DashScopeApi dashScopeApi;

    /**
     * RAG 百炼知识库
     * @param msg 文本
     * @return
     */
    @GetMapping("/bailian/rag/chat")
    // http://localhost:8080/bailian/rag/chat?msg=00000错误信息
    public Flux<String> chat(@RequestParam(name = "msg",defaultValue = "00000错误信息") String msg){
        // 百炼 RAG 构建器
        DocumentRetriever retriever = new DashScopeDocumentRetriever(dashScopeApi,
                DashScopeDocumentRetrieverOptions.builder()
                        .withIndexName("ops") // 知识库名称
                        .build()
        );

        return chatClient.prompt()
                .user(msg)
                .advisors(new DocumentRetrievalAdvisor(retriever))
                .stream()
                .content();
    }
}
