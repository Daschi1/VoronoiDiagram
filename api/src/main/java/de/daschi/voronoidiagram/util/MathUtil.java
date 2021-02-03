package de.daschi.voronoidiagram.util;

import javafx.geometry.Point2D;

public class MathUtil {
    public static double scaleToRange(final double value, final double valueMin, final double valueMax, final double targetMin, final double targetMax) {
        return (value - valueMin) / (valueMax - valueMin) * (targetMax - targetMin) + targetMin;
    }

    public static double calculateDistance(final Point2D p, final Point2D q, final DistanceFunction distanceFunction) {
        switch (distanceFunction) {
            case EUCLIDEAN:
                return p.distance(q);
            case MANHATTAN:
                return Math.abs(p.getX() - q.getX()) + Math.abs(p.getY() - q.getY());
            default:
                return Double.NaN;
        }
    }

    public static int clamp(final int x, final int min, final int max) {
        return Math.max(min, Math.min(max, x));
    }

    public static double clamp(final double x, final double min, final double max) {
        return Math.max(min, Math.min(max, x));
    }

    public enum DistanceFunction {
        EUCLIDEAN,
        MANHATTAN,
        DIFFERENCE
    }
}
