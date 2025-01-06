package com.moodbeat.analysis.analysisservice.models;

public record VoiceAnalysis(
        double pitch,
        double volume,
        double speechRate,
        String dominantEmotion,
        double duration
) {
}
