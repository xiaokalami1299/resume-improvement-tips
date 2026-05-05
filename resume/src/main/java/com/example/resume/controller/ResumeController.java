package com.example.resume.controller;

import com.example.resume.dto.ResumeAnalysisRequest;
import com.example.resume.dto.ResumeAnalysisResponse;
import com.example.resume.service.ResumeAnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/resume")
public class ResumeController {

    private final ResumeAnalysisService resumeAnalysisService;

    @Autowired
    public ResumeController(ResumeAnalysisService resumeAnalysisService) {
        this.resumeAnalysisService = resumeAnalysisService;
    }

    @PostMapping("/analyze")
    public ResponseEntity<ResumeAnalysisResponse> analyzeResume(@RequestBody ResumeAnalysisRequest request) {
        ResumeAnalysisResponse response = resumeAnalysisService.analyzeResume(request);
        return ResponseEntity.ok(response);
    }
}