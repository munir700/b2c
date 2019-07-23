package com.digitify.identityscanner.core.detection.detectors;

import com.digitify.identityscanner.core.detection.models.Card;

import org.opencv.core.Mat;
import org.opencv.core.Rect;

public class CardDetector extends RectangleDetector<Card> {
    public final double CARD_AREA_MIN = 10000;

    @Override
    public Card detect(Mat src) {
        Mat output = new Mat();
        Rect rect = findRect(src, output, true);
        return new Card(output, rect);
    }

    @Override
    protected double getMinArea() {
        return CARD_AREA_MIN;
    }
}
