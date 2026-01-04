package com.star.controller;

import com.alibaba.cloud.ai.dashscope.embedding.DashScopeEmbeddingOptions;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.document.Document;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.embedding.EmbeddingRequest;
import org.springframework.ai.embedding.EmbeddingResponse;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

/**
 * @author 聂建强
 * @date 2026/1/3 22:19
 * @description: 文本转嵌入向量 & 向量存储与相似度查找
 */
@RestController
@Slf4j
public class Embed2VectorController {
    @Resource
    private EmbeddingModel embeddingModel;

    @Resource
    private VectorStore vectorStore;

    /**
     * 文本转嵌入向量接口
     * @param msg 文本内容
     * @return EmbeddingResponse
     */
    // http://localhost:8080/text2embed?msg=射雕英雄传
    @GetMapping("/text2embed")
    public EmbeddingResponse text2Embed(@RequestParam(name = "msg") String msg){
        // 文本向量化
        // EmbeddingResponse embeddingResponse = embeddingModel.call(new EmbeddingRequest(List.of(msg), null));
        EmbeddingResponse embeddingResponse = embeddingModel.call(new EmbeddingRequest(List.of(msg),
                DashScopeEmbeddingOptions.builder().withModel("text-embedding-v3").build()
        ));
        // 从 EmbeddingResponse 获取向量
        float[] embedding = embeddingResponse.getResult().getOutput();

        System.out.println(Arrays.toString(embedding));
        return embeddingResponse;
    }

    /**
     * 文本向量化 后存入向量数据库RedisStack
     */
    // http://localhost:8080/embed2vector/add
    @GetMapping("/embed2vector/add")
    public void add(){
        List<Document> documents = List.of(
                new Document("i study LLM"),
                new Document("i love java")
        );
        // add()方法底层调用了embeddingModel，会先生成向量，再存入数据库
        vectorStore.add(documents);
    }


    /**
     * 从向量数据库RedisStack查找，进行相似度查找
     * @param msg 文本内容
     * @return
     */
    // http://localhost:8080/embed2vector/get?msg=LLM
    @GetMapping("/embed2vector/get")
    public List<Document> getAll(@RequestParam(name = "msg") String msg){
        SearchRequest searchRequest = SearchRequest.builder()
                    .query(msg)  // 查询内容
                    .topK(2)  // 返回最相似的前 2 个结果
                .build();

        List<Document> list = vectorStore.similaritySearch(searchRequest);

        System.out.println(list);
        return list;
    }
}
