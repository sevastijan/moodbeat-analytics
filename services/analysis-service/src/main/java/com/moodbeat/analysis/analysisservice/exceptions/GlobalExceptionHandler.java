package com.moodbeat.analysis.analysisservice.exceptions;

import com.moodbeat.analysis.analysisservice.models.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UnsupportedAudioFormatException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleUnsupportedAudioFormatException(UnsupportedAudioFormatException exception) {
        return new ErrorResponse("Invalid audio format", exception.getMessage());
    }

    @ExceptionHandler(AudioProcessingException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleAudioProcessingException(AudioProcessingException exception) {
        return new ErrorResponse("Audio processing error", exception.getMessage());
    }
}
