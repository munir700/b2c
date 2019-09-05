package com.digitify.identityscanner.utils;

import android.content.Context;
import android.graphics.Rect;
import android.util.DisplayMetrics;

import java.util.Random;

public class NumberUtils {
    public static int generateRandomIntIntRange(int min, int max) {
        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }

    /**
     * This method converts dp unit to equivalent pixels, depending on device density.
     *
     * @param dp      A value in dp (density independent pixels) unit. Which we need to convert into pixels
     * @param context Context to get resources and device specific display metrics
     * @return A float value to represent px equivalent to dp depending on device density
     */
    public static int convertDpToPixel(int dp, Context context) {
        return dp * (context.getResources().getDisplayMetrics().densityDpi / DisplayMetrics.DENSITY_DEFAULT);
    }

    /**
     * This method converts device specific pixels to density independent pixels.
     *
     * @param px      A value in px (pixels) unit. Which we need to convert into db
     * @param context Context to get resources and device specific display metrics
     * @return A float value to represent dp equivalent to px value
     */
    public static int convertPixelsToDp(int px, Context context) {
        return px / (context.getResources().getDisplayMetrics().densityDpi / DisplayMetrics.DENSITY_DEFAULT);
    }

    public static boolean isInsideCardOverlay(Rect r1, Rect r2, int tolerance) {
        boolean isInside = false;
        // inner rect
        Rect dr = new Rect(r2);
        dr.inset(tolerance, tolerance);
        isInside = (r2.contains(r1) && r1.contains(dr));
        if (isInside) {
            return true;
        }

        return isInside;
    }
}
