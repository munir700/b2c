package com.digitify.identityscanner.core.detection.models;

import org.opencv.core.Mat;
import org.opencv.core.Rect;

public class Mrz extends Entity {

    public Mrz(Mat detection, Rect boundingBox) {
        super(detection, boundingBox);
    }

    @Override
    public boolean isValid() {
//        return !(getDetection() == null
//                || getDetection().empty()
//        ); == null
//                || getDetection().empty()
//        );

        return super.isValid();
    }
}
