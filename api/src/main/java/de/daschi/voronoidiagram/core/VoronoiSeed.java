package de.daschi.voronoidiagram.core;

import javafx.geometry.Point2D;

public class VoronoiSeed {
    private final Point2D point2D;

    public VoronoiSeed(final Point2D point2D) {
        this.point2D = point2D;
    }

    @Override
    public int hashCode() {
        return this.point2D.hashCode();
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }

        final VoronoiSeed that = (VoronoiSeed) o;

        return this.point2D.equals(that.point2D);
    }

    @Override
    public String toString() {
        return "VoronoiSeed{" +
                "point2D=" + this.point2D +
                '}';
    }

    public Point2D getPoint2D() {
        return this.point2D;
    }
}
