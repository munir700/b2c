package com.digitify.identityscanner.core.detection.enums;

import org.jetbrains.annotations.Contract;

/**
 * For Expressions probability limit based on 68 Landmarks of Face
 */
public enum FaceExpression {
    SMILE(0.35, 0.37),
    LAUGH(0.44, 0.99),
    EYE_OPEN(0.23, 0.99),
    EYE_CLOSE(0, 0.17),
    EYE_HALF_OPEN(0.18, 0.22);

    double lowerLimit;
    double upperLimit;

    FaceExpression(double lowerLimit, double upperLimit) {
        this.lowerLimit = lowerLimit;
        this.upperLimit = upperLimit;
    }

    @Contract(pure = true)
    public double getLowerLimit() {
        return lowerLimit;
    }

    @Contract(pure = true)
    public double getUpperLimit() {
        return upperLimit;
    }

    public boolean qualify(double prob) {
        return (prob >= getLowerLimit() && prob <= getUpperLimit());
    }

}
