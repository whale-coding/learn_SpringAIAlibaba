package com.star.controller;

import com.alibaba.cloud.ai.dashscope.image.DashScopeImageOptions;
import jakarta.annotation.Resource;
import org.springframework.ai.image.ImageModel;
import org.springframework.ai.image.ImagePrompt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 聂建强
 * @date 2026/1/3 21:53
 * @description: 文生图
 */
@RestController
public class Text2ImageController {

    // img model
    public static final String IMAGE_MODEL = "wanx2.1-t2i-turbo";

    @Resource
    private ImageModel imageModel;

    /**
     * 文生图接口
     * @param prompt 描述词
     * @return 图片地址
     */
    // http://localhost:8080/t2i/image?prompt=刺猬
    @GetMapping(value = "/t2i/image")
    public String image(@RequestParam(name = "prompt",defaultValue = "刺猬") String prompt){
        // Prompt
        ImagePrompt imagePrompt = new ImagePrompt(prompt,
                DashScopeImageOptions.builder()
                        .withModel(IMAGE_MODEL)
                        .build());

        return imageModel.call(imagePrompt)
                .getResult()
                .getOutput()
                .getUrl();
    }
}
