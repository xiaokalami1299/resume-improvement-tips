package com.example.resume.service;

import com.example.resume.dto.ResumeAnalysisRequest;
import com.example.resume.dto.ResumeAnalysisResponse;

public interface ResumeAnalysisService {

    ResumeAnalysisResponse analyzeResume(ResumeAnalysisRequest request);
}