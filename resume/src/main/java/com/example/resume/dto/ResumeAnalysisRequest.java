package com.example.resume.dto;

public class ResumeAnalysisRequest {

    private String resumeText;

    public ResumeAnalysisRequest() {
    }

    public ResumeAnalysisRequest(String resumeText) {
        this.resumeText = resumeText;
    }

    public String getResumeText() {
        return resumeText;
    }

    public void setResumeText(String resumeText) {
        this.resumeText = resumeText;
    }
}