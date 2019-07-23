package com.digitify.identityscanner.core.detection.models;

import org.opencv.core.Mat;
import org.opencv.core.Rect;

public class SceneText extends Entity {

    public SceneText(Mat detection, Rect boundingBox) {
        super(detection, boundingBox);
    }
}
