package com.digitify.identityscanner.core.detection.models;

import org.opencv.core.Mat;
import org.opencv.core.Rect;

public class Smile extends Entity {
    public Smile(Mat detection, Rect boundingBox) {
        super(detection, boundingBox);
    }
}
