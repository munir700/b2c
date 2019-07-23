package com.digitify.identityscanner.core.detection.enums;

public enum FaceLandmark {
    NOSE(28, 36),
    RIGHT_EYE(43, 48),
    LEFT_EYE(37, 42),
    MOUTH(49, 68),
    RIGHT_EYE_BROW(23, 27),
    LEFT_EYE_BROW(18, 22),
    JAW(1, 17),
    ALL(1, 68);

    private int start, end;

    FaceLandmark(int start, int end) {
        this.start = start;
        this.end = end;
    }

    public int getStart() {
        return start;
    }

    public int getEnd() {
        return end;
    }
}
