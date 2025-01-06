package com.moodbeat.analysis.analysisservice.processor;

import be.tarsos.dsp.AudioEvent;
import be.tarsos.dsp.AudioProcessor;
import be.tarsos.dsp.onsets.ComplexOnsetDetector;
import be.tarsos.dsp.pitch.FastYin;
import be.tarsos.dsp.pitch.PitchDetectionResult;
import be.tarsos.dsp.pitch.PitchProcessor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class PitchDetectorProcessor implements AudioProcessor {
    private float lastPitch = -1;
    private final List<Float> pitchBuffer = new ArrayList<>();
    private static final int BUFFER_SIZE = 5;


    public PitchDetectorProcessor() {
    }


    public float getPitch() {
        return lastPitch;
    }

    @Override
    public boolean process(AudioEvent audioEvent) {
        float[] buffer = audioEvent.getFloatBuffer();
        float sampleRate = audioEvent.getSampleRate();

        int windowSize = 1024;
        float[] window = new float[windowSize];
        System.arraycopy(buffer, 0, window, 0, Math.min(buffer.length, windowSize));

        float maxCorrelation = 0;
        int maxLag = 0;
        for (int lag = 20; lag < windowSize/2; lag++) {
            float correlation = 0;
            for (int i = 0; i < windowSize-lag; i++) {
                correlation += window[i] * window[i+lag];
            }
            if (correlation > maxCorrelation) {
                maxCorrelation = correlation;
                maxLag = lag;
            }
        }

        if (maxLag > 0) {
            float currentPitch = sampleRate / maxLag;
            if (currentPitch > 50 && currentPitch < 500) {
                pitchBuffer.add(currentPitch);
                if (pitchBuffer.size() > BUFFER_SIZE) {
                    pitchBuffer.remove(0);
                }
                lastPitch = getMedianPitch();
            }
        }
        return true;
    }

    private float getMedianPitch() {
        List<Float> sorted = new ArrayList<>(pitchBuffer);
        Collections.sort(sorted);
        return sorted.get(sorted.size() / 2);
    }

    @Override
    public void processingFinished() {
    }

}
