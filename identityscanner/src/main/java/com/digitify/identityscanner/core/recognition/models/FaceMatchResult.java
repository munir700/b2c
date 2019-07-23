package com.digitify.identityscanner.core.recognition.models;

import com.digitify.identityscanner.core.detection.models.Face;
import com.digitify.identityscanner.core.recognition.interfaces.IMatchResult;

public class FaceMatchResult implements IMatchResult {

    private boolean matched = false;
    private double confidence = 0;
    private Face source;
    private Face target;

    public FaceMatchResult() {
    }

    public FaceMatchResult(boolean isMatched, double confidence) {
        this.matched = isMatched;
        this.confidence = confidence;
    }

    @Override
    public boolean isMatched() {
        return matched;
    }

    @Override
    public double getConfidence() {
        return confidence;
    }

    public Face getSource() {
        return source;
    }

    public void setMatched(boolean matched) {
        this.matched = matched;
    }

    public void setConfidence(double confidence) {
        this.confidence = confidence;
    }

    public void setSource(Face source) {
        this.source = source;
    }

    public Face getTarget() {
        return target;
    }

    public void setTarget(Face target) {
        this.target = target;
    }

    @Override
    public String toString() {
        return "FaceMatchResult{" +
                "matched=" + matched +
                ", confidence=" + confidence +
                '}';
    }
}
