package com.moodbeat.analysis.analysisservice.processor;

import be.tarsos.dsp.AudioEvent;
import be.tarsos.dsp.AudioProcessor;
import be.tarsos.dsp.pitch.PitchDetectionResult;
import be.tarsos.dsp.pitch.PitchProcessor;

public class PitchDetector implements AudioProcessor {
    private float lastPitch;
    private final PitchProcessor pitchProcessor;


    public PitchDetector() {
        this.pitchProcessor = new PitchProcessor(
                PitchProcessor.PitchEstimationAlgorithm.YIN,
                44100,
                1024,
                this::handlePitch
        );
    }

    private void handlePitch(PitchDetectionResult pitchDetectionResult, AudioEvent audioEvent) {
        if(pitchDetectionResult.getPitch() != -1) {
            lastPitch = pitchDetectionResult.getPitch();
        }
    }

    public float getPitch() {
        return lastPitch;
    }

    @Override
    public boolean process(AudioEvent audioEvent) {
        return pitchProcessor.process(audioEvent);
    }

    @Override
    public void processingFinished() {
        pitchProcessor.processingFinished();
    }
}
