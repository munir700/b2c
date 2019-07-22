package com.digitify.identityscanner.core.recognition.models;

import com.digitify.identityscanner.core.recognition.interfaces.IRecognitionResult;

public class FaceRecognitionResult implements IRecognitionResult {
    @Override
    public String getName() {
        return null;
    }

    @Override
    public double getConfidence() {
        return 0;
    }
}
