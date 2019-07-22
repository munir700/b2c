package com.digitify.identityscanner.core.detection.detectors;

import android.graphics.Bitmap;

import com.digitify.identityscanner.core.detection.interfaces.Detector;
import com.digitify.identityscanner.core.detection.models.Entity;
import com.digitify.identityscanner.core.detection.utils.OpenCVUtils;

import org.opencv.core.Mat;

import java.util.ArrayList;
import java.util.List;

public abstract class EntityDetector<T extends Entity> implements Detector<T> {

    @Override
    public T detect(Bitmap src) {
        return detect(OpenCVUtils.convertToMat(src));
    }

    @Override
    public List<T> detectAll(Mat src) {
        ArrayList<T> list = new ArrayList<>();
        list.add(detect(src));
        return list;
    }

    @Override
    public List<T> detectAll(Bitmap src) {
        ArrayList<T> list = new ArrayList<>();
        list.add(detect(src));
        return list;
    }

    @Override
    public boolean validate(T object) {
        return (object != null && object.isValid());
    }
}
