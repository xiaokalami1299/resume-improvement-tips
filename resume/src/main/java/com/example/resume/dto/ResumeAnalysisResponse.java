package com.example.resume.dto;

import java.util.List;

public class ResumeAnalysisResponse {

    private boolean success;
    private List<SuggestionDTO> suggestions;
    private String message;

    public ResumeAnalysisResponse() {
    }

    public ResumeAnalysisResponse(boolean success, List<SuggestionDTO> suggestions, String message) {
        this.success = success;
        this.suggestions = suggestions;
        this.message = message;
    }

    public static ResumeAnalysisResponse success(List<SuggestionDTO> suggestions) {
        return new ResumeAnalysisResponse(true, suggestions, "分析成功");
    }

    public static ResumeAnalysisResponse error(String message) {
        return new ResumeAnalysisResponse(false, null, message);
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public List<SuggestionDTO> getSuggestions() {
        return suggestions;
    }

    public void setSuggestions(List<SuggestionDTO> suggestions) {
        this.suggestions = suggestions;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}