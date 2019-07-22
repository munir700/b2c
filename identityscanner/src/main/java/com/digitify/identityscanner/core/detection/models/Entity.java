package com.digitify.identityscanner.core.detection.models;

import android.graphics.Bitmap;

import com.digitify.identityscanner.core.detection.interfaces.Detectable;
import com.digitify.identityscanner.core.detection.utils.OpenCVUtils;

import org.opencv.core.Mat;
import org.opencv.core.Rect;

import androidx.annotation.Nullable;

public abstract class Entity implements Detectable {
    private Mat detection;
    private Rect boundingBox;

    public Entity(Mat detection, Rect boundingBox) {
        this.detection = detection;
        this.boundingBox = boundingBox;
    }

    @Override
    public boolean isValid() {
        return !(getDetection() == null
                || getDetection().empty()
                || getBoundingBox() == null
                || getBoundingBox().size().width <= 0
        );
    }

    public Mat getDetection() {
        return detection;
    }

    public void setDetection(Mat detection) {
        this.detection = detection;
    }

    public Rect getBoundingBox() {
        return boundingBox;
    }

    public void setBoundingBox(Rect boundingBox) {
        this.boundingBox = boundingBox;
    }

    @Nullable
    public Bitmap getBitmap() {
        if (isValid()) return OpenCVUtils.convertToBitmap(getDetection());
        return null;
    }
}
