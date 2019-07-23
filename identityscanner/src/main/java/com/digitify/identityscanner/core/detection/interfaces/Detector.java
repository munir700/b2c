package com.digitify.identityscanner.core.detection.interfaces;

import android.graphics.Bitmap;

import org.opencv.core.Mat;

import java.util.List;

public interface Detector<D extends Detectable> extends Validator<D> {
    D detect(Mat src);
    D detect(Bitmap src);
    List<D> detectAll(Mat src);
    List<D> detectAll(Bitmap src);
}
