package com.example.resume.dto;

public class SuggestionDTO {

    private String id;
    private String category;
    private String content;
    private String originalText;
    private String suggestedText;
    private String priority;

    public SuggestionDTO() {
    }

    public SuggestionDTO(String id, String category, String content, String originalText, String suggestedText, String priority) {
        this.id = id;
        this.category = category;
        this.content = content;
        this.originalText = originalText;
        this.suggestedText = suggestedText;
        this.priority = priority;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getOriginalText() {
        return originalText;
    }

    public void setOriginalText(String originalText) {
        this.originalText = originalText;
    }

    public String getSuggestedText() {
        return suggestedText;
    }

    public void setSuggestedText(String suggestedText) {
        this.suggestedText = suggestedText;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }
}