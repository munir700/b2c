package com.digitify.identityscanner.core.detection.utils;

import android.graphics.Bitmap;

import com.digitify.identityscanner.utils.ImageUtils;

import org.jetbrains.annotations.NotNull;
import org.opencv.android.Utils;
import org.opencv.core.Core;
import org.opencv.core.CvException;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfInt;
import org.opencv.core.MatOfPoint;
import org.opencv.core.MatOfPoint2f;
import org.opencv.core.MatOfRect;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.RotatedRect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import androidx.annotation.Nullable;

public class OpenCVUtils extends ImageUtils {

    public static Bitmap convertToBitmap(Mat tmp) {
        Bitmap bmp = Bitmap.createBitmap(tmp.cols(), tmp.rows(), Bitmap.Config.ARGB_8888);
        Utils.matToBitmap(tmp, bmp);
        return bmp;
    }

    public static Mat convertToMat(Bitmap bitmap) {
        Mat tmp = new Mat(bitmap.getHeight(), bitmap.getWidth(), CvType.CV_8UC4, new Scalar(4));
        Utils.bitmapToMat(bitmap, tmp);
        return tmp;
    }

    @Nullable
    public static Mat crop(Mat src, Rect rect) throws CvException {
        return src.submat(rect);
    }

    @Nullable
    public static Mat cropAdjusted(Mat src, Rect rect) throws CvException {
        return crop(src, adjustRectInImg(src, rect));
    }

    public static void resize(Mat src, double imageScaleFactor) {
        Imgproc.resize(src, src, new Size(src.width() * imageScaleFactor, src.height() * imageScaleFactor));
    }

    public static double getScaleFactor(Mat src) {
        double maxHeight = 600;
        double maxWidth = 400;
        double imgWidth = src.width();
        double imgHeight = src.height();
        if (imgHeight > imgWidth) {
            // portrait mode image
            return (maxHeight / imgHeight);
        } else {
            // landscape image
            return (maxWidth / imgWidth);
        }

    }

    public static void sortContourByLargestArea(List<MatOfPoint> contours) {
        Collections.sort(contours, (c1, c2) -> {
            double area1 = Imgproc.contourArea(c1);
            double area2 = Imgproc.contourArea(c2);
            if (area1 > area2) return -1;
            if (area1 < area2) return 1;
            return 0;
        });
    }

    public static Mat dispatchRectangle(Mat src, Rect rect) {
        dispatchRectangle(src, rect, getColor());
        return src;
    }

    public static Mat dispatchRectangle(Mat src, Rect rect, Scalar color) {
        Imgproc.rectangle(src, rect.tl(), rect.br(), color, 4);
        return src;
    }

    public static Scalar getColor() {
        return new Scalar(133, 146, 158, 2);
    }

    public static void grayScale(Mat src) {
        Imgproc.cvtColor(src, src, Imgproc.COLOR_BGR2GRAY);
    }

    public static void medianBlur(Mat src) {
        Imgproc.medianBlur(src, src, 9);
    }

    public static void gaussianBlur(Mat src) {
        gaussianBlur(src, new Size(3, 3));
    }

    public static void gaussianBlur(Mat src, Size size) {
        Imgproc.GaussianBlur(src, src, size, 0);
    }

    public static Mat bilateralFilter(Mat src) {
        Mat blurred = new Mat();
        Imgproc.bilateralFilter(src, blurred, 11, 17, 17);
        return blurred;
    }

    public static void equalizeHist(Mat src) {
        Imgproc.equalizeHist(src, src);
    }

    public static void threshold(Mat src, int low) {
        threshold(src, low, 255);
    }

    public static void threshold(Mat src, int low, int max) {
        Imgproc.threshold(src, src, low, max, Imgproc.THRESH_BINARY | Imgproc.THRESH_OTSU);
    }

    public static void thresholdBinary(Mat src, int low, int max) {
        Imgproc.threshold(src, src, low, max, Imgproc.THRESH_BINARY);
    }

    public static void thresholdAdaptive(Mat src, int max) {
        Imgproc.adaptiveThreshold(src, src, max, Imgproc.ADAPTIVE_THRESH_GAUSSIAN_C, Imgproc.THRESH_BINARY, 15, 40);
    }

    public static void canny(Mat src) {
        Imgproc.Canny(src, src, 10, 100, 3, true);
    }

    public static void dilate(Mat src) {
        Imgproc.dilate(src, src, new Mat(), new Point(-1, -1), 1); // 1
    }

    public static void morphClose(Mat src, Mat structuringElement) {
        Imgproc.morphologyEx(src, src, Imgproc.MORPH_CLOSE, structuringElement);
    }

    public static void morphBlackhat(Mat src, Mat structuringElement) {
        Imgproc.morphologyEx(src, src, Imgproc.MORPH_BLACKHAT, structuringElement);
    }

    public static Mat buildStructure(Size size) {
        return Imgproc.getStructuringElement(Imgproc.MORPH_RECT, size);
    }

    public static void sobel(Mat src) {
        Imgproc.Sobel(src, src, Imgproc.CV_SCHARR, 1, 0);
    }

    public static void erode(Mat src, Mat structuringElement) {
        Imgproc.erode(src, src, structuringElement, new Point(0, 0), 4);
    }

    public static void findContoursRelational(Mat src, List<MatOfPoint> contours) {
        Imgproc.findContours(src, contours, new Mat(), Imgproc.RETR_TREE, Imgproc.CHAIN_APPROX_SIMPLE);
    }

    public static void findContours(Mat src, List<MatOfPoint> contours) {
        Imgproc.findContours(src, contours, new Mat(), Imgproc.RETR_LIST, Imgproc.CHAIN_APPROX_SIMPLE);
    }

    public static double calculateContourArea(MatOfPoint contour) {
        return Imgproc.contourArea(contour);
    }

    public static MatOfPoint2f approxContour(Point[] contour) {
        MatOfPoint2f c = new MatOfPoint2f(contour);
        double peri = Imgproc.arcLength(c, true);
        MatOfPoint2f approxCurve = new MatOfPoint2f();
        Imgproc.approxPolyDP(c, approxCurve, 0.05 * peri, true);
        return approxCurve;
    }

    public static void drawContours(Mat img, List<MatOfPoint> edgeContours) {
        drawContours(img, edgeContours, getColor());
    }

    public static void drawContours(Mat img, List<MatOfPoint> edgeContours, Scalar color) {
        Imgproc.drawContours(img, edgeContours, -1, color, 2);
    }

    public static void drawContour(Mat image, MatOfPoint contour) {
        List<MatOfPoint> edgeContours = new ArrayList<>();
        edgeContours.add(contour);
        drawContours(image, edgeContours);
    }

    public static void drawContour(Mat image, MatOfPoint contour, Scalar lineColor) {
        List<MatOfPoint> edgeContours = new ArrayList<>();
        edgeContours.add(contour);
        drawContours(image, edgeContours, lineColor);
    }

    public static void drawContourRect(Mat img, MatOfPoint edgeContour, double rectScaleFactor) {
        Rect rect = scaleAndFitRectInImg(img, getContourRect(edgeContour), rectScaleFactor);
        dispatchRectangle(img, rect);
    }

    public static Mat draw(Mat src, MatOfPoint contour, double scaleFactor) {
        if (contour != null && src != null) {
            OpenCVUtils.drawContourRect(src, contour, scaleFactor);
        }
        return src;
    }

    public static Mat crop(Mat src, MatOfPoint contour, double scaleFactor) {
        if (contour != null && src != null) {
            Rect rect = OpenCVUtils.adjustRectInImg(src, OpenCVUtils.getContourRect(contour, scaleFactor));
            return OpenCVUtils.crop(src, rect);
        }
        return src;
    }

    public static Rect getContourRect(MatOfPoint edgeContour) {
        return Imgproc.boundingRect(edgeContour);
    }

    public static Rect getContourRect(MatOfPoint edgeContour, double rectScaleFactor) {
        return scaleRectWithFactor(getContourRect(edgeContour), rectScaleFactor);
    }

    public static Rect scaleAndFitRectInImg(Mat img, Rect rect, double rectScaleFactor) {
        Rect rect2 = scaleRectWithFactor(rect, rectScaleFactor);
        return adjustRectInImg(img, rect2);
    }

    public static Rect scaleRectWithFactor(Rect rect, double rectScaleFactor) {
        if (rectScaleFactor != 1 && rectScaleFactor != 0) {
            int x = rect.x;
            int y = rect.y;
            int width = rect.width;
            int height = rect.height;

            // scale the dimension back with scaleFactor
            x = (int) ((double) x * rectScaleFactor);
            y = (int) ((double) y * rectScaleFactor);
            height = (int) ((double) height * rectScaleFactor);
            width = (int) ((double) width * rectScaleFactor);
            return new Rect(x, y, width, height);
        }

        return rect;
    }

    public static Rect adjustRectInImg(Mat img, Rect rect) {
        int x = rect.x;
        int y = rect.y;
        int width = rect.width;
        int height = rect.height;

        int x2 = x + width;
        int y2 = y + height;

        // Safety checks
        if (x2 > img.width()) width = img.width() - x;
        if (y2 > img.height()) height = img.height() - y;
        if (x < 0) x = 0;
        if (y < 0) y = 0;
        return new Rect(x, y, width, height);
    }

    public static MatOfRect toMatOfRect(Rect rect) {
        MatOfRect faces = new MatOfRect();
        List<Rect> rectList = new ArrayList<>();
        rectList.add(rect);
        faces.fromList(rectList);
        return faces;
    }


    public static void drawLines(Mat img, MatOfPoint points, boolean isClosed) {
        drawLines(img, points, isClosed, getColor());
    }

    public static void drawLines(Mat img, MatOfPoint points, boolean isClosed, Scalar color) {
        List<MatOfPoint> pointsArray = new ArrayList<MatOfPoint>();
        pointsArray.add(points);
        Imgproc.polylines(img, pointsArray, isClosed, color);
    }

    public static void drawLines(Mat img, Point[] points, boolean isClosed, Scalar color) {
        MatOfPoint matPoint = new MatOfPoint(points);
        drawLines(img, matPoint, isClosed, color);
    }

    public static void drawRect(Mat img, Rect rect, Scalar color) {
        Point[] points = new Point[4];
        RotatedRect rectRotated = new RotatedRect(new Point(rect.x + (rect.width / 2), rect.y + (rect.height / 2)), rect.size(), 0);
        rectRotated.points(points);

        MatOfPoint points2 = new MatOfPoint(points);
        drawLines(img, points2, true, color);
    }

    public static void binaryText(Mat src) {
        OpenCVUtils.grayScale(src);
        // Make all colors white except black and its shades
        OpenCVUtils.thresholdBinary(src, 140, 255);
        // OpenCVUtils.thresholdAdaptive(src, 255);
        OpenCVUtils.equalizeHist(src);
        OpenCVUtils.gaussianBlur(src, new Size(3, 3));
    }

    // Good
//    public static void binaryText(Mat src) {
//        OpenCVUtils.grayScale(src);
//        // Make all colors white except black and its shades
//        OpenCVUtils.thresholdAdaptive(src, 255);
//        OpenCVUtils.thresholdBinary(src, 140, 255);
//        OpenCVUtils.equalizeHist(src);
//        OpenCVUtils.gaussianBlur(src, new Size(3, 3));
//    }

    public static Bitmap binaryText(Bitmap src) {
        Mat tmp = convertToMat(src);
        binaryText(tmp);
        return convertToBitmap(tmp);
    }

    public static double angle(Point p1, Point p2, Point p0) {
        double dx1 = p1.x - p0.x;
        double dy1 = p1.y - p0.y;
        double dx2 = p2.x - p0.x;
        double dy2 = p2.y - p0.y;
        return (dx1 * dx2 + dy1 * dy2)
                / Math.sqrt((dx1 * dx1 + dy1 * dy1) * (dx2 * dx2 + dy2 * dy2)
                + 1e-10);
    }

    public enum Rotation {
        ROTATE_90_CLOCKWISE, //Rotate 90 degrees clockwise
        ROTATE_180, //Rotate 180 degrees clockwise
        ROTATE_90_COUNTERCLOCKWISE; //Rotate 270 degrees clockwise

        int getCoreRotation() {
            int angle = Core.ROTATE_90_CLOCKWISE;
            switch (this) {
                case ROTATE_180:
                    angle = Core.ROTATE_180;
                    break;
                case ROTATE_90_CLOCKWISE:
                    angle = Core.ROTATE_90_CLOCKWISE;
                    break;
                case ROTATE_90_COUNTERCLOCKWISE:
                    angle = Core.ROTATE_90_COUNTERCLOCKWISE;
                    break;

            }
            return angle;
        }
    }

    ;


    /**
     * rotates image by rotation degree
     *
     * @param image
     * @return
     */
    public static void rotateImage(@NotNull Mat image, Rotation rotation) {
        Core.rotate(image, image, rotation.getCoreRotation());
    }

    /**
     * rotates image 90 degree
     *
     * @param image
     * @return
     */
    public static Mat rotateImage(@NotNull Mat image) {
        int height = image.cols();
        int width = image.rows();

        // rotate
        Mat result = new Mat(width, height, CvType.CV_8UC4);
        Core.transpose(image, result);
        flip(result, Rotation.ROTATE_180);
        // Core.flip(result, result, Core.ROTATE_180);
        return result;
    }

    /**
     * rotates image 90 degree
     *
     * @param image
     * @return
     */
    public static void flip(@NotNull Mat image, Rotation rotation) {
        Core.flip(image, image, rotation.getCoreRotation());
    }

    public static double distance(double x1, double y1, double x2, double y2) {
        return Math.sqrt(Math.pow((x2 - x1), 2) + Math.pow((y2 - y1), 2));
    }

    public static double distance(Point p1, Point p2) {
        return Math.sqrt(Math.pow((p2.x - p1.x), 2) + Math.pow((p2.y - p1.y), 2));
    }

    public static Point[] rectToPoints(Rect roi) {
        if (roi == null || roi.width == 0) return null;
        Point[] points = new Point[4];
        points[0] = new Point(roi.x, roi.y);
        points[1] = new Point(roi.x + roi.width, roi.y);
        points[2] = new Point(roi.x + roi.width, roi.y + roi.height);
        points[3] = new Point(roi.x, roi.y + roi.height);
        return points;
    }

    public static MatOfPoint multiply(MatOfPoint contour, double scaleFactor) {
        Point[] cps = contour.toArray();
        for (Point p : cps) {
            p.x = p.x * scaleFactor;
            p.y = p.y * scaleFactor;
        }
        return new MatOfPoint(cps);
    }

    public static Point[] orderCorners(MatOfPoint2f corners) {
        return orderCorners(corners.toArray());
    }

    public static Point[] orderCorners(MatOfPoint corners) {
        return orderCorners(corners.toArray());
    }

    /**
     * Given 4 points (corners). Arrange to points in order: top-left,
     * top-right, bottom-right, bottom-left
     *
     * @param corners
     * @return 4 ordered points
     */
    public static Point[] orderCorners(Point[] corners) {
        Point[] ordered = new Point[4];
        List<Point> points = Arrays.asList(corners);
        // Sort in asc using sums
        Collections.sort(points, (p1, p2) -> {
            double ps1 = p1.x + p1.y;
            double ps2 = p2.x + p2.y;
            return Double.compare(ps1, ps2);
        });

        ordered[0] = points.get(0); // Top-Left
        ordered[2] = points.get(points.size() - 1); // Bottom-Right

        // Sort in asc using subtraction
        Collections.sort(points, (p1, p2) -> {
            double ps1 = p1.x - p1.y;
            double ps2 = p2.x - p2.y;
            return Double.compare(ps1, ps2);
        });

        ordered[3] = points.get(0); // top-right
        ordered[1] = points.get(points.size() - 1); // bottom-left
        return ordered;
    }

    /**
     * Given 4 points (corners). Arrange to points in order: top-left,
     * top-right, bottom-right, bottom-left
     *
     * @param cornersUnordered
     * @return 4 ordered points
     */
    public static Point[] arrangeCorners(Point[] cornersUnordered) {
        Point[] cornerPoints = new Point[4];
        Point p1, p2, p3, p4;
        Point topLeft = null, topRight = null, botRight = null, botLeft = null;
        List<Point> corners = Arrays.asList(cornersUnordered);

        /* Top set of points */
        // find p1
        p1 = corners.get(0);
        for (Point point : corners) {
            if (point.y < p1.y) {
                p1 = point;
            }
        }
        corners.remove(p1);

        // find p2
        p2 = corners.get(0);
        for (Point point : corners) {
            if (distance(p1, point) < distance(p1, p2)) {
                p2 = point;
            }
        }
        corners.remove(p2);

        /* Identify top left and top right */
        /*
         * Note that the logic is safe if the points have equal x values. Safe
         * in the sense that different points will get assigned to topLeft and
         * topRight
         */
        topLeft = p1.x < p2.x ? p1 : p2;
        topRight = p2.x > p1.x ? p2 : p1;

        /* Bottom set of points */
        // corners only contains 2 points, the bottom ones
        p3 = corners.get(0);
        p4 = corners.get(1);
        botRight = p3.x > p4.x ? p3 : p4;
        botLeft = p4.x < p3.x ? p4 : p3;

        cornerPoints[0] = topLeft;
        cornerPoints[1] = topRight;
        cornerPoints[2] = botRight;
        cornerPoints[3] = botLeft;

        return cornerPoints;
    }

    public static double slope(Point p1, Point p2) {
        return (p2.y - p1.y) / (p2.x - p1.x);
    }

    public static boolean riseIsUp(Point p1, Point p2) {
        if (p2.y < p1.y) {
            return true;
        }
        return false;
    }

    public static boolean riseIsDown(Point p1, Point p2) {
        return !(riseIsUp(p1, p2));
    }

    /**
     * Rotates an image. The new image size is max(height,width) x
     * max(height,width) of the input image.
     *
     * @param imgIn
     * @param imgOut
     * @param angle
     */
    public static void rotateImageLarge(Mat imgIn, Mat imgOut, double angle) {
        Mat rotationMatrix = Imgproc.getRotationMatrix2D(new
                Point(imgIn.width() / 2, imgIn.height() / 2), angle, 1.0);
        Size imgSize = imgIn.size();
        int maxSize = (int)
                Math.max((double) imgSize.height, (double)
                        imgSize.width);
        Size finalSize = new Size(maxSize, maxSize);

        Imgproc.warpAffine(imgIn, imgOut, rotationMatrix,
                finalSize);
    }

    /**
     * Angle needed to rotate the rectangle to be at a 90.
     *
     * @param cornerPoints
     * @return
     */
    // TODO: Raise an error if cornerPoints is not of length 4
    public static final int CORNERS_ORDERED = 1;
    public static final int CORNERS_UNORDERED = 2;

    public static double calcAngleFromCorners(Point[] cornerPoints, int ordering) {
        Point[] orderedCorners = null;
        if (ordering == CORNERS_UNORDERED) {
            orderedCorners = orderCorners(cornerPoints);
        } else {
            orderedCorners = cornerPoints;
        }

        double topSlope = Math.abs(slope(orderedCorners[0], orderedCorners[1]));
        if (Double.isInfinite(topSlope) || Double.isInfinite(-topSlope)) {
            topSlope = 0;
        } else if (riseIsUp(orderedCorners[0], orderedCorners[1]) == true) {
            topSlope *= -1;
        }
        double angle = Math.atan(topSlope) * 180 / Math.PI;
        return angle;
    }

    public static boolean areAllPointsInImage(Mat img, Point[] points) {
        for (int i = 0; i < points.length; ++i) {
            if (points[i].x < 0 || points[i].x > img.width() || points[i].y < 0
                    || points[i].y > img.height()) {
                return false;
            }
        }
        return true;
    }
}
