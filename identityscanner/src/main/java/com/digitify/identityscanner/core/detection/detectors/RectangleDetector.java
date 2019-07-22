package com.digitify.identityscanner.core.detection.detectors;

import com.digitify.identityscanner.core.detection.models.Rectangle;
import com.digitify.identityscanner.core.detection.utils.OpenCVUtils;

import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.MatOfPoint2f;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;

public abstract class RectangleDetector<T extends Rectangle> extends EntityDetector<T> {

    protected abstract double getMinArea();

    protected final Rect findRect(Mat src, Mat dest) {
        return findRect(src, dest, false);
    }

    /**
     * This function tries to find the largest rectangle in the given image.
     *
     * @param src  original image
     * @param dest Mat to dump the found cropped card
     * @return Rect dimensions of the largest rectangle found
     */

    protected final Rect findRect(Mat src, Mat dest, boolean performPerspectiveTransformation) {
        double downScaleFactor = OpenCVUtils.getScaleFactor(src);
        Rect rect = null;

        Mat card = src.clone();
        // downscale the image for faster process
        OpenCVUtils.resize(card, downScaleFactor);
        applyEdgeDetectionFilters(card);
        MatOfPoint maxContour = findLargestContour(card, getMinArea());

        if (maxContour != null) {
            // scale up the result now
            maxContour = OpenCVUtils.multiply(maxContour, (1 / downScaleFactor));

            // get the bounding box of the contour
            rect = OpenCVUtils.adjustRectInImg(src, OpenCVUtils.getContourRect(maxContour));

            if (performPerspectiveTransformation) {
                // Transform according to perspective to get a bird-eye
                perspectiveTransform(maxContour, src, dest);
            }
        }
        return rect;
    }

    protected void applyEdgeDetectionFilters(Mat card) {
        OpenCVUtils.grayScale(card);
        OpenCVUtils.gaussianBlur(card);
        // OpenCVUtils.threshold(card, 127);
        OpenCVUtils.canny(card);
        OpenCVUtils.dilate(card);
    }

    @Nullable
    protected MatOfPoint findLargestContour(Mat filteredImg, double minRequiredArea) {
        List<MatOfPoint> contours = new ArrayList<>();
        OpenCVUtils.findContoursRelational(filteredImg, contours);
        OpenCVUtils.sortContourByLargestArea(contours);
        return findContourByMaxArea(contours, minRequiredArea);
    }

    @Nullable
    protected MatOfPoint findContourByMaxArea(List<MatOfPoint> contours, double minRequiredArea) {
        MatOfPoint rectContour = null;
        double maxArea = minRequiredArea;
        for (MatOfPoint cont : contours) {
            MatOfPoint2f curve = OpenCVUtils.approxContour(cont.toArray());
            double area = OpenCVUtils.calculateContourArea(cont);
            if (curve.total() == 4 && area >= maxArea) {
                rectContour = cont;
                maxArea = area;
            }
        }
        return rectContour;
    }

    private static void perspectiveTransform(MatOfPoint contour, Mat imgIn, Mat imgOut) {
        Point[] imageCorners = OpenCVUtils.orderCorners(contour);
        Point tl = imageCorners[0];
        Point tr = imageCorners[1];
        Point br = imageCorners[2];
        Point bl = imageCorners[3];

        double w1 = OpenCVUtils.distance(tl, tr);
        double w2 = OpenCVUtils.distance(bl, br);
        double h1 = OpenCVUtils.distance(tl, bl);
        double h2 = OpenCVUtils.distance(tr, br);

        double maxWidth = Math.max(w1, w2);
        double maxHeight = Math.max(h1, h2);

        Point[] sceneCorners = new Point[4];
        sceneCorners[0] = new Point(0, 0);
        sceneCorners[1] = new Point(maxWidth - 1, 0);
        sceneCorners[2] = new Point(maxWidth - 1, maxHeight - 1);
        sceneCorners[3] = new Point(0, maxHeight - 1);

        Mat transformation = Imgproc.getPerspectiveTransform(new MatOfPoint2f(imageCorners), new MatOfPoint2f(sceneCorners));
        Imgproc.warpPerspective(imgIn, imgOut, transformation, new Size(maxWidth, maxHeight));

//        double angle = calcAngleFromCorners(imageCorners, OpenCVUtils.CORNERS_ORDERED);
//        if (angle != 0) {
//            rotateImageLarge(imgOut, imgOut, angle);
//        }

    }
}
