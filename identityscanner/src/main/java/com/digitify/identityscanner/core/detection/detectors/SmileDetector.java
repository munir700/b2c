package com.digitify.identityscanner.core.detection.detectors;

import android.content.Context;
import android.graphics.Bitmap;

import com.digitify.identityscanner.R;
import com.digitify.identityscanner.core.detection.models.Smile;
import com.digitify.identityscanner.core.detection.utils.OpenCVUtils;

import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Rect;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;

public class SmileDetector extends CascadeDetector<Smile> {

    public SmileDetector(Context context) {
        super(context);
    }

    @Override
    protected int getTrainingRawModel() {
        return R.raw.haarcascade_smile;
    }

    @Nullable
    @Override
    public Smile detect(Bitmap src) {
        List<Smile> eyes = detectAll(src);
        if (eyes.size() > 0) return eyes.get(0);
        return null;
    }

    @Override
    public List<Smile> detectAll(Bitmap src) {
        return detectAll(OpenCVUtils.convertToMat(src));
    }

    @Nullable
    @Override
    public Smile detect(Mat src) {
        List<Smile> eyes = detectAll(src);
        if (eyes.size() > 0) return eyes.get(0);
        return null;
    }

    @Override
    public List<Smile> detectAll(Mat src) {
        List<Smile> smiles = new ArrayList<>();
        MatOfRect mrects = findCascade(src.clone());
        List<Rect> rects = mrects.toList();
        for (Rect rect: rects) {
            Mat output = new Mat();
            Mat croppedFace = OpenCVUtils.crop(src, rect);
            if (croppedFace != null) croppedFace.copyTo(output);
            smiles.add(new Smile(output, rect));
        }

        return smiles;
    }

}
