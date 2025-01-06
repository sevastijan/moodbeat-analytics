package com.moodbeat.analysis.analysisservice.services;

import be.tarsos.dsp.AudioDispatcher;
import be.tarsos.dsp.pitch.PitchDetectionResult;
import com.moodbeat.analysis.analysisservice.factories.AudioDispatcherFactory;
import com.moodbeat.analysis.analysisservice.models.VoiceAnalysis;
import com.moodbeat.analysis.analysisservice.processor.PitchDetector;
import com.moodbeat.analysis.analysisservice.processor.VolumeDetector;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class VoiceAnalysisService {

    public VoiceAnalysis analyzeVoice(byte[] audioData) {
        AudioDispatcher dispatcher = AudioDispatcherFactory.fromByteArray(
                audioData,
                44100,
                1024,
                0
        );

        PitchDetector pitchDetector = new PitchDetector();
        VolumeDetector volumeDetector = new VolumeDetector();

        dispatcher.addAudioProcessor(pitchDetector);
        dispatcher.addAudioProcessor(volumeDetector);

        dispatcher.run();

        return new VoiceAnalysis(
                pitchDetector.getPitch(),
                volumeDetector.getCurrentVolume(),
                0.0, //TODO:
                "neutral", //TODO:
                0.0 //TODO:
        );
    }
}
