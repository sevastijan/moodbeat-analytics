package com.moodbeat.analysis.analysisservice.processor;

import be.tarsos.dsp.AudioEvent;
import be.tarsos.dsp.AudioProcessor;

public class VolumeDetector implements AudioProcessor {
    private float currentVolume;
    private final AmplitudeProcessor amplitudeProcessor;

    public VolumeDetector() {
        this.amplitudeProcessor = new AmplitudeProcessor();
    }

    @Override
    public boolean process(AudioEvent audioEvent) {
        float[] audioBuffer = audioEvent.getFloatBuffer();
        float amplitude = amplitudeProcessor.process(audioBuffer);

        currentVolume = (float) (20 * Math.log10(amplitude));

        return true;
    }

    @Override
    public void processingFinished() {

    }

    public float getCurrentVolume() {
        return currentVolume;
    }
}
