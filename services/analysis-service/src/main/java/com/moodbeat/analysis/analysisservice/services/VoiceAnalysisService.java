package com.moodbeat.analysis.analysisservice.services;

import be.tarsos.dsp.AudioDispatcher;
import be.tarsos.dsp.SilenceDetector;
import com.moodbeat.analysis.analysisservice.factories.AudioDispatcherFactory;
import com.moodbeat.analysis.analysisservice.models.VoiceAnalysis;
import com.moodbeat.analysis.analysisservice.processor.PitchDetectorProcessor;
import com.moodbeat.analysis.analysisservice.processor.SpeechRateProcessor;
import com.moodbeat.analysis.analysisservice.processor.VolumeDetectorProcessor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicInteger;

@Service
@Slf4j
public class VoiceAnalysisService {
    /**
     Next factor to implements steps:

     Analysis and interpretation of the words spoken
     Variability of voice pitch (variability) - indicates emotionality
     Pauses in speaking - may indicate uncertainty
     Signal energy in different frequency bands
     Jitter (micro fluctuations in pitch)
     Shimmer (micro fluctuations in amplitude)

     */

    public VoiceAnalysis analyzeVoice(byte[] audioData) {
        AudioDispatcher dispatcher = AudioDispatcherFactory.fromByteArray(
                audioData,
                44100,
                1024,
                0
        );

        PitchDetectorProcessor pitchDetector = new PitchDetectorProcessor();
        VolumeDetectorProcessor volumeDetector = new VolumeDetectorProcessor();

        dispatcher.addAudioProcessor(pitchDetector);
        dispatcher.addAudioProcessor(volumeDetector);

        dispatcher.run();

        return new VoiceAnalysis(
                pitchDetector.getPitch(),
                volumeDetector.getCurrentVolume(),
                calculateSpeechRate(audioData),
                determineEmotion(pitchDetector.getPitch(), volumeDetector.getCurrentVolume(), calculateSpeechRate(audioData)),
                calculateDuration(audioData)
        );
    }

    private String determineEmotion(double pitch, double volume, double speechRate) {
        if (pitch > 230 && volume > -60 && speechRate > 40) return "VERY_EXCITED";
        if (pitch > 200 && volume > -62 && speechRate > 35) return "NERVOUS";
        if (pitch > 180 && volume < -64 && speechRate > 40) return "SAD";
        if (pitch > 180 && volume > -63 && speechRate > 30) return "HAPPY";
        if (pitch < 180 && volume < -63 && speechRate < 30) return "SAD";
        return "NEUTRAL";
    }

    private double calculateSpeechRate(byte[] audioData) {
        AudioDispatcher dispatcher = AudioDispatcherFactory.fromByteArray(
                audioData,
                44100,
                1024,
                0
        );

        SilenceDetector silenceDetector = new SilenceDetector(-70, false);
        SpeechRateProcessor speechRateProcessor = new SpeechRateProcessor();

        dispatcher.addAudioProcessor(silenceDetector);
        dispatcher.addAudioProcessor(speechRateProcessor);

        dispatcher.run();

        return speechRateProcessor.getSpeechRate();
    }

    private double calculateDuration(byte[] audioData) {
        return (double) audioData.length / 44100;
    }
}
