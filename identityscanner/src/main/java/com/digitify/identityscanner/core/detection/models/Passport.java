package com.digitify.identityscanner.core.detection.models;

import org.opencv.core.Mat;
import org.opencv.core.Rect;

public class Passport extends Entity {

    public Passport(Mat detection, Rect boundingBox) {
        super(detection, boundingBox);
    }
}
