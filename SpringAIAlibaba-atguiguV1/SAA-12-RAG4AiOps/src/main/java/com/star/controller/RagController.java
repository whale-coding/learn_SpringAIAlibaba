package com.star.controller;

import jakarta.annotation.Resource;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.rag.advisor.RetrievalAugmentationAdvisor;
import org.springframework.ai.rag.retrieval.search.VectorStoreDocumentRetriever;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

/**
 * @author 聂建强
 * @date 2026/1/4 11:01
 * @description: RAG example: 根据运维手册进行故障解释
 */
@RestController
public class RagController {
    @Resource(name = "qwenChatClient")
    private ChatClient chatClient;

    @Resource
    private VectorStore vectorStore;

    /**
     * RAG
     * @param msg 文本
     * @return
     */
    // http://localhost:8080/rag4aiops?msg=00000
    // http://localhost:8080/rag4aiops?msg=C2222
    @GetMapping("/rag4aiops")
    public Flux<String> rag(@RequestParam(name = "msg") String msg){
        String systemInfo = """
                你是一个运维工程师,按照给出的编码给出对应故障解释,否则回复找不到信息。
                """;

        // RAG 核心代码
        RetrievalAugmentationAdvisor advisor = RetrievalAugmentationAdvisor.builder()
                .documentRetriever(VectorStoreDocumentRetriever.builder().vectorStore(vectorStore).build())
                .build();

        return chatClient
                .prompt()
                .system(systemInfo)
                .user(msg)
                .advisors(advisor)
                .stream()
                .content();
    }
}
