package com.digitify.identityscanner.core.detection.models;

import org.opencv.core.Mat;
import org.opencv.core.Rect;

public class Card extends Rectangle {

    public Card(Mat detection, Rect boundingBox) {
        super(detection, boundingBox);
    }
}
