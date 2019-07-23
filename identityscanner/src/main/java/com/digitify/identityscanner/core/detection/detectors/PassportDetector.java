package com.digitify.identityscanner.core.detection.detectors;

import com.digitify.identityscanner.components.TransparentCardView;
import com.digitify.identityscanner.core.detection.models.Mrz;
import com.digitify.identityscanner.core.detection.models.Passport;
import com.digitify.identityscanner.core.detection.utils.OpenCVUtils;

import org.opencv.core.Mat;
import org.opencv.core.Rect;

public class PassportDetector extends EntityDetector<Passport> {

    private MrzDetector mrzDetector;

    @Override
    public Passport detect(Mat src) {
        Mat output = new Mat();
        Rect rect = findPassport(src, output);
        return new Passport(output, rect);
    }

    private Rect findPassport(Mat src, Mat dest) {
        Rect passRect = null;
        Mrz mrz = getMrzDetector().detect(src);
        if (getMrzDetector().validate(mrz)) {
            Rect rect = mrz.getBoundingBox();
            // calculate would be height
            int b = rect.y + rect.height;
            int expectedHeight = Math.round(rect.width / TransparentCardView.PASSPORT_RATIO);
            int expectedTop = b - expectedHeight;

            passRect = new Rect(rect.x, expectedTop, rect.width, expectedHeight);
            Mat passport = OpenCVUtils.cropAdjusted(src, passRect);
            if (passport != null) passport.copyTo(dest);
        }

        return passRect;
    }

    public MrzDetector getMrzDetector() {
        if (mrzDetector == null) mrzDetector = new MrzDetector();
        return mrzDetector;
    }
}
