package com.example.resume.service.impl;

import com.example.resume.client.AIClient;
import com.example.resume.dto.ResumeAnalysisRequest;
import com.example.resume.dto.ResumeAnalysisResponse;
import com.example.resume.dto.SuggestionDTO;
import com.example.resume.service.ResumeAnalysisService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

@Service
public class ResumeAnalysisServiceImpl implements ResumeAnalysisService {

    private final AIClient aiClient;
    private final ObjectMapper objectMapper;
    private final String promptTemplate;

    @Autowired
    public ResumeAnalysisServiceImpl(AIClient aiClient, ObjectMapper objectMapper) throws IOException {
        this.aiClient = aiClient;
        this.objectMapper = objectMapper;
        this.promptTemplate = loadPromptTemplate();
    }

    private String loadPromptTemplate() throws IOException {
        ClassPathResource resource = new ClassPathResource("prompt-template.txt");
        try (InputStream inputStream = resource.getInputStream()) {
            return new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
        }
    }

    @Override
    public ResumeAnalysisResponse analyzeResume(ResumeAnalysisRequest request) {
        try {
            // 替换模板中的占位符
            String prompt = promptTemplate.replace("{{resumeText}}", request.getResumeText());

            // 调用AI客户端
            String aiResponse = aiClient.callAI(prompt);

            // 解析JSON响应
            JsonNode rootNode = objectMapper.readTree(aiResponse);
            JsonNode suggestionsNode = rootNode.get("suggestions");

            List<SuggestionDTO> suggestions = objectMapper.convertValue(
                    suggestionsNode,
                    new TypeReference<List<SuggestionDTO>>() {}
            );

            return ResumeAnalysisResponse.success(suggestions);

        } catch (Exception e) {
            e.printStackTrace();
            return ResumeAnalysisResponse.error("分析失败：" + e.getMessage());
        }
    }
}
