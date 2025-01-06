package com.moodbeat.analysis.analysisservice.processor;

import be.tarsos.dsp.AudioEvent;
import be.tarsos.dsp.AudioProcessor;
import be.tarsos.dsp.SilenceDetector;

import java.util.concurrent.atomic.AtomicInteger;

public class SpeechRateProcessor implements AudioProcessor {
    private final SilenceDetector silenceDetector;
    private final AtomicInteger syllableCount;
    private double durationInSeconds;

    public SpeechRateProcessor() {
        this.silenceDetector = new SilenceDetector(-70, false);
        this.syllableCount = new AtomicInteger();
    }

    @Override
    public boolean process(AudioEvent audioEvent) {
        if(silenceDetector.currentSPL() > -70) {
            syllableCount.incrementAndGet();
        }
        durationInSeconds = audioEvent.getTimeStamp();

        return true;
    }

    @Override
    public void processingFinished() {}

    public double getSpeechRate() {
        return syllableCount.get() / durationInSeconds;
    }
}
