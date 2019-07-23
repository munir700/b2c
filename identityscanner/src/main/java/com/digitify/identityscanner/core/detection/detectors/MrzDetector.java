package com.digitify.identityscanner.core.detection.detectors;

import com.digitify.identityscanner.core.detection.utils.OpenCVUtils;
import com.digitify.identityscanner.core.detection.models.Mrz;

import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.MatOfPoint2f;
import org.opencv.core.Rect;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;


public class MrzDetector extends EntityDetector<Mrz> {
    @Override
    public Mrz detect(Mat src) {
        Mat output = new Mat();
        Rect rect = findMrz(src, output);
        return new Mrz(output, rect);
    }

    @Nullable
    private Rect findMrz(Mat src, Mat dest) {
        Rect rect = null;
        double downScaleFactor = OpenCVUtils.getScaleFactor(src);

        Mat card = src.clone();
        OpenCVUtils.resize(card, downScaleFactor);
        applyMorphologicalFilters(card);

        card.copyTo(dest);

        MatOfPoint maxContour = findLargestContour(card);
        if (maxContour != null) {
            // scale up the result now
            maxContour = OpenCVUtils.multiply(maxContour, (1 / downScaleFactor));
            rect = adjustPad(src, OpenCVUtils.getContourRect(maxContour));

            Mat mrz = OpenCVUtils.cropAdjusted(src, rect);
            if (mrz != null) mrz.copyTo(dest);
        }

        card.release();
        return rect;
    }

    private Rect adjustPad(Mat src, Rect rect) {
        int pad = 30;
        int x = rect.x - pad;
        int y = rect.y - pad;
        int w = rect.width + (pad * 2);
        int h = rect.height + (pad * 2);
        double[] vals = {x, y, w, h};
        rect.set(vals);
        return OpenCVUtils.adjustRectInImg(src, rect);
    }

    private void applyMorphologicalFilters(Mat tmp) {

        // initialize a rectangular and square structuring kernel for applying morphological operations
        Mat rectKernal = OpenCVUtils.buildStructure(new Size(13, 5));
        Mat sqKernal = OpenCVUtils.buildStructure(new Size(14, 14));

        OpenCVUtils.grayScale(tmp);
        OpenCVUtils.gaussianBlur(tmp);
        OpenCVUtils.morphBlackhat(tmp, rectKernal);
        OpenCVUtils.dilate(tmp);
        // compute the Scharr gradient of the blackhat image and scale the result into the range [0, 255]
        OpenCVUtils.sobel(tmp);
        // TODO: Apply range [0-255] in tmp here

        // apply a closing operation using the rectangular kernel to close gaps in between letters -- then apply Otsu's thresholding method
        OpenCVUtils.morphClose(tmp, rectKernal);
        OpenCVUtils.threshold(tmp, 0, 178);

        // perform another closing operation, this time using the square kernel to close gaps between lines of the MRZ
        OpenCVUtils.morphClose(tmp, sqKernal);
        OpenCVUtils.threshold(tmp, 0, 178);

//        // perform a series of erosions to break apart connected components
//        OpenCVUtils.erode(tmp, OpenCVUtils.buildStructure(new Size(4, 1)));

    }

    @Nullable
    private static MatOfPoint findLargestContour(Mat filteredImg) {
        List<MatOfPoint> contours = new ArrayList<>();
        OpenCVUtils.findContours(filteredImg, contours);
        OpenCVUtils.sortContourByLargestArea(contours);
        return findContourByRatio(filteredImg, contours, 5, 0.75f);
    }

    @Nullable
    private static MatOfPoint findContourByRatio(Mat image, List<MatOfPoint> contours, int minRatio, float minWidthFactor) {
        MatOfPoint rectContour = null;
        for (MatOfPoint cont : contours) {
            Rect bounds = OpenCVUtils.getContourRect(cont);
            float ar = bounds.width / bounds.height;
            double crWidth = (double) bounds.width / image.size().width;
            if (ar >= minRatio && crWidth >= minWidthFactor) {
                rectContour = cont;
            }
        }
        return rectContour;
    }

}
