package com.digitify.identityscanner.core.detection.interfaces;

public interface IExpressionable extends Analysable {
    float getRightEyeOpenProbability();

    float getLeftEyeOpenProbability();

    float getSmileProbability();
}
