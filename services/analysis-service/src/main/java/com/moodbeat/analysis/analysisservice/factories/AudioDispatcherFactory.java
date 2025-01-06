package com.moodbeat.analysis.analysisservice.factories;

import be.tarsos.dsp.AudioDispatcher;
import be.tarsos.dsp.io.TarsosDSPAudioFormat;
import be.tarsos.dsp.io.UniversalAudioInputStream;

import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.ByteArrayInputStream;
import java.io.IOException;

public class AudioDispatcherFactory {

    public static AudioDispatcher fromByteArray(
            byte[] audioData,
            float sampleRate,
            int bufferSize,
            int overlap
    ) {
        ByteArrayInputStream bais = new ByteArrayInputStream(audioData);
        TarsosDSPAudioFormat format = new TarsosDSPAudioFormat(
                sampleRate,
                16,
                1,
                true,
                true
        );

        return new AudioDispatcher(
                new UniversalAudioInputStream(bais, format),
                bufferSize,
                overlap
        );
    }
}
