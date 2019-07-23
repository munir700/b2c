package com.digitify.identityscanner.core.detection.models;

import org.opencv.core.Mat;
import org.opencv.core.Rect;

public class Rectangle extends Entity {
    public Rectangle(Mat detection, Rect boundingBox) {
        super(detection, boundingBox);
    }

}
