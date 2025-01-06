package com.moodbeat.analysis.analysisservice.controllers;

import com.moodbeat.analysis.analysisservice.models.VoiceAnalysis;
import com.moodbeat.analysis.analysisservice.services.VoiceAnalysisService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/voice")
public class VoiceAnalysisController {
    private final VoiceAnalysisService voiceAnalysisService;

    public VoiceAnalysisController(VoiceAnalysisService voiceAnalysisService) {
        this.voiceAnalysisService = voiceAnalysisService;
    }

    @PostMapping("/analyze")
    public VoiceAnalysis analyzeVoice(@RequestParam MultipartFile audioFile) throws IOException {
        return voiceAnalysisService.analyzeVoice(audioFile.getBytes());
    }
}
