package com.digitify.identityscanner.core.detection.detectors;

import android.content.Context;
import android.graphics.Bitmap;

import com.digitify.identityscanner.R;
import com.digitify.identityscanner.core.detection.models.Eye;
import com.digitify.identityscanner.core.detection.utils.OpenCVUtils;

import org.jetbrains.annotations.NotNull;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Rect;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;

/**
 * Detects eyes in a given image (face) using a pre trained model by OpenCV's CascadeClassifier
 */
public class EyesDetector extends CascadeDetector<Eye> {

    public EyesDetector(Context context) {
        super(context);
    }

    @Override
    protected int getTrainingRawModel() {
        return R.raw.haarcascade_eye_tree_eyeglasses;
    }

    @Nullable
    @Override
    public Eye detect(Bitmap src) {
        List<Eye> eyes = detectAll(src);
        if (eyes.size() > 0) return eyes.get(0);
        return null;
    }

    @Override
    public List<Eye> detectAll(Bitmap src) {
        return detectAll(OpenCVUtils.convertToMat(src));
    }

    @Nullable
    @Override
    public Eye detect(Mat src) {
        List<Eye> eyes = detectAll(src);
        if (eyes.size() > 0) return eyes.get(0);
        return null;
    }

    @Override
    public List<Eye> detectAll(Mat src) {
        List<Eye> eyes = new ArrayList<>();
        List<Rect> rects = findEysRects(src);
        for (Rect rect: rects) {
            Mat output = new Mat();
            Mat croppedFace = OpenCVUtils.crop(src, rect);
            if (croppedFace != null) croppedFace.copyTo(output);
            eyes.add(new Eye(output, rect));
        }

        return eyes;
    }

    @Nullable
    private List<Rect> findEysRects(@NotNull Mat src) {
        MatOfRect eyes = findCascade(src.clone());
        return eyes.toList();
    }
}
