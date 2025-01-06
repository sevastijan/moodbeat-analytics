package com.moodbeat.analysis.analysisservice.processor;

public class AmplitudeProcessor {
    public float process (float[] audioBuffer) {
        float sum = 0;
        for (float sample : audioBuffer) {
            sum += Math.abs(sample);
        }
        return sum / audioBuffer.length;
    }
}
