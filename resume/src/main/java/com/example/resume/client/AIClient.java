package com.example.resume.client;

import org.springframework.stereotype.Component;

@Component
public class AIClient {

    /**
     * 调用AI服务获取简历优化建议（Mock实现）
     * @param prompt 提示词
     * @return AI返回的JSON字符串
     */
    public String callAI(String prompt) {
        // Mock返回一段硬编码的JSON，包含5条不同类别的建议
        return """
            {
              "suggestions": [
                {
                  "id": "1",
                  "category": "项目经验",
                  "content": "建议使用量化指标展示项目成果，增强说服力",
                  "originalText": "负责完成了公司的电商平台开发项目",
                  "suggestedText": "主导电商平台全栈开发项目，优化页面加载速度40%，提升用户转化率15%",
                  "priority": "high"
                },
                {
                  "id": "2",
                  "category": "技能描述",
                  "content": "技能描述过于笼统，建议具体说明掌握程度和使用经验",
                  "originalText": "熟悉Java开发",
                  "suggestedText": "精通Java 17/Spring Boot 3.x，有5年企业级后端开发经验",
                  "priority": "medium"
                },
                {
                  "id": "3",
                  "category": "工作经历",
                  "content": "建议使用行动动词开头，突出主动性和成果",
                  "originalText": "参与了多个项目的开发工作",
                  "suggestedText": "主导3个核心项目的架构设计与开发，累计交付功能模块20+",
                  "priority": "high"
                },
                {
                  "id": "4",
                  "category": "教育背景",
                  "content": "建议补充相关课程或学术成果，增加竞争力",
                  "originalText": "本科毕业于XX大学计算机专业",
                  "suggestedText": "本科毕业于XX大学计算机科学与技术专业，GPA 3.8/4.0，获得校级奖学金",
                  "priority": "low"
                },
                {
                  "id": "5",
                  "category": "关键词优化",
                  "content": "建议增加与目标岗位相关的关键词，提高ATS通过率",
                  "originalText": "做过一些后端开发工作",
                  "suggestedText": "具备微服务架构设计、分布式系统开发、云原生部署经验",
                  "priority": "medium"
                }
              ]
            }
            """;
    }
}