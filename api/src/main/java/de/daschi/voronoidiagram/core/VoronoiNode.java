package de.daschi.voronoidiagram.core;

import javafx.geometry.Point2D;

public class VoronoiNode {

    private final Point2D origin;
    private VoronoiSeed voronoiSeed;

    public VoronoiNode(final Point2D origin) {
        this(origin, null);
    }

    public VoronoiNode(final Point2D origin, final VoronoiSeed voronoiSeed) {
        this.origin = origin;
        this.voronoiSeed = voronoiSeed;
    }

    public Point2D getOrigin() {
        return this.origin;
    }

    public VoronoiSeed getVoronoiSeed() {
        return this.voronoiSeed;
    }

    void setVoronoiSeed(final VoronoiSeed voronoiSeed) {
        this.voronoiSeed = voronoiSeed;
    }
}
