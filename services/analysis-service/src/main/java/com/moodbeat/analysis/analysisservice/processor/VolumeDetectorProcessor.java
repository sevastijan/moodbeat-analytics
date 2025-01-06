package com.moodbeat.analysis.analysisservice.processor;

import be.tarsos.dsp.AudioEvent;
import be.tarsos.dsp.AudioProcessor;


public class VolumeDetectorProcessor implements AudioProcessor {
    private float currentVolume;

    public VolumeDetectorProcessor() {
    }

    @Override
    public boolean process(AudioEvent audioEvent) {
        float[] audioBuffer = audioEvent.getFloatBuffer();
        float rms = calculateRMS(audioBuffer);

        currentVolume = (float) (20 * Math.log10(rms));

        return true;
    }

    @Override
    public void processingFinished() {

    }

    private float calculateRMS(float[] buffer) {
        float sum = 0;
        for (float sample : buffer) {
            sum += sample * sample;
        }
        return (float) Math.sqrt(sum / buffer.length);
    }

    public float getCurrentVolume() {
        return this.currentVolume;
    }
}
