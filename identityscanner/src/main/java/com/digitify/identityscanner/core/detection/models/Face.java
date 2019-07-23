package com.digitify.identityscanner.core.detection.models;


import android.graphics.Bitmap;

import com.digitify.identityscanner.core.detection.enums.FaceLandmark;
import com.digitify.identityscanner.core.detection.interfaces.IExpressionable;
import com.digitify.identityscanner.core.detection.utils.OpenCVUtils;
import com.digitify.identityscanner.core.recognition.interfaces.Matchable;
import com.digitify.identityscanner.core.recognition.interfaces.Recognisable;

import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint2f;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class Face extends Entity implements IExpressionable, Recognisable, Matchable, Cloneable {
    private MatOfPoint2f landmarks;

    public Face(Mat detection, Rect boundingBox) {
        super(detection, boundingBox);
    }

    @Nullable
    public MatOfPoint2f getAllLandmarks() {
        return landmarks;
    }

    public void setLandmarks(@NonNull MatOfPoint2f landmarks) {
        this.landmarks = landmarks;
    }

    @Override
    public float getRightEyeOpenProbability() {
        return (float) calculateEAR(FaceLandmark.RIGHT_EYE);
    }

    @Override
    public float getLeftEyeOpenProbability() {
        return (float) calculateEAR(FaceLandmark.LEFT_EYE);
    }

    @Override
    public float getSmileProbability() {
        return (float) calculateMAR();
    }

    /**
     * Calculates the mouth aspect ratio (MAR) using MOUTH FaceLandmark points
     * https://medium.freecodecamp.org/smilfie-auto-capture-selfies-by-detecting-a-smile-using-opencv-and-python-8c5cfb6ec197
     */
    private double calculateMAR() {
        List<Point> mouthPoints = getLandmarks(FaceLandmark.MOUTH);
        if (mouthPoints.size() > 0) {
            double dA = OpenCVUtils.distance(mouthPoints.get(10), mouthPoints.get(2)); // |59 - 51|;
            double dB = OpenCVUtils.distance(mouthPoints.get(9), mouthPoints.get(3)); // |58 - 52|;
            double dC = OpenCVUtils.distance(mouthPoints.get(8), mouthPoints.get(4)); // |57 - 53|;
            double D = OpenCVUtils.distance(mouthPoints.get(6), mouthPoints.get(0)); // |57 - 53|;  smile < 98
            double L = (dA + dB + dC) / 3; //
            return (L / D);
        }
         return 0;
    }

    /**
     * Calculates the eyes aspect ratio (EAR) using *_EYE FaceLandmark points
     * https://medium.freecodecamp.org/smilfie-auto-capture-selfies-by-detecting-a-smile-using-opencv-and-python-8c5cfb6ec197
     */
    private double calculateEAR(FaceLandmark eye) {
        if (eye != FaceLandmark.RIGHT_EYE && FaceLandmark.LEFT_EYE != eye) return -1;
        List<Point> mouthPoints = getLandmarks(eye);
        if (mouthPoints.size() > 0) {
            double dA = OpenCVUtils.distance(mouthPoints.get(5), mouthPoints.get(1)); // |48 - 44|;
            double dB = OpenCVUtils.distance(mouthPoints.get(4), mouthPoints.get(2)); // |47 - 45|;
            double L = (dA + dB) / 2;
            double D = OpenCVUtils.distance(mouthPoints.get(3), mouthPoints.get(0)); // |40 - 37|;
            return (L / D);
        }

        return 0;
    }

    /**
     * Draws all landmarks..
     * Only For debugging purpose
     *
     * @return
     */
    public Bitmap getContourBitmap() {
        Mat landed = getDetection().clone();
        drawLandmark(landed, FaceLandmark.LEFT_EYE);
        drawLandmark(landed, FaceLandmark.RIGHT_EYE);
        drawLandmark(landed, FaceLandmark.LEFT_EYE_BROW);
        drawLandmark(landed, FaceLandmark.RIGHT_EYE_BROW);
        drawLandmark(landed, FaceLandmark.MOUTH);
        drawLandmark(landed, FaceLandmark.NOSE);
        drawLandmark(landed, FaceLandmark.JAW);
        return OpenCVUtils.convertToBitmap(landed);
    }

    private Bitmap drawLandmark(Mat tmp, FaceLandmark mark) {
        List<Point> points = getLandmarks(mark);
        if (points.size() > 0) {
            Point[] pArray = new Point[points.size()];
            points.toArray(pArray);
            OpenCVUtils.drawLines(tmp, pArray, false, new Scalar(0, 255, 0));
        }
        return OpenCVUtils.convertToBitmap(tmp);
    }


    /**
     * This function finds the landmark points range for the given FaceLandmark.
     *
     * @param mark
     * @return List<Point>
     */
    private List<Point> getLandmarks(FaceLandmark mark) {
        List<Point> points = new ArrayList<>();
        if (getAllLandmarks() != null) {
            int size = mark.getEnd();
            for (int j = mark.getStart() - 1; j < size; j++) {
                double[] dp = getAllLandmarks().get(j, 0);
                Point p = new Point(dp[0], dp[1]);
                points.add(p);
            }
        }
        return points;
    }

    public Face clone() {
        return new Face(getDetection(), getBoundingBox());
    }
}
