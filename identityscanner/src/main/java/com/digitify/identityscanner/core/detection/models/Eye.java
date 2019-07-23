package com.digitify.identityscanner.core.detection.models;

import org.opencv.core.Mat;
import org.opencv.core.Rect;

public class Eye extends Entity {

    public Eye(Mat detection, Rect boundingBox) {
        super(detection, boundingBox);
    }
}
