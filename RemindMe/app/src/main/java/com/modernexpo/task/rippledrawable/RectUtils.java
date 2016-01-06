package com.modernexpo.task.rippledrawable;
import android.graphics.Rect;
/**
 * Created by oleksandr.pachkovsky on 06.01.2016.
 */


public final class RectUtils {
    // Square
    public static Rect of(int centerX, int centerY, int radius) {
        return new Rect(
                centerX - radius,
                centerY - radius,
                centerX + radius,
                centerY + radius);
    }

    public static int getRadius(Rect square) {
        return square.centerX() - square.left;
    }

    public static Rect adjustCenter(Rect rect, int x, int y) {
        return of(x, y, getRadius(rect));
    }

    public static Rect adjustRadius(Rect rect, int radius) {
        return of(rect.centerX(), rect.centerY(), radius);
    }

    // Prevent instantiation
    private RectUtils() {}
}