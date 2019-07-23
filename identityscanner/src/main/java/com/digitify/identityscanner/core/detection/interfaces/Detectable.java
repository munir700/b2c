package com.digitify.identityscanner.core.detection.interfaces;

import android.graphics.Bitmap;

import org.opencv.core.Mat;
import org.opencv.core.Rect;

public interface Detectable extends Validatable {
    Mat getDetection();

    Rect getBoundingBox();

    Bitmap getBitmap();
}
