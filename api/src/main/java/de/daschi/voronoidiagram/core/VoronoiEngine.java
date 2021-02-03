package de.daschi.voronoidiagram.core;

import de.daschi.voronoidiagram.util.MathUtil;
import javafx.geometry.Point2D;

import java.util.ArrayList;
import java.util.Random;

public class VoronoiEngine {

    private static final ArrayList<VoronoiSeed> voronoiSeeds = new ArrayList<>();

    public static ArrayList<VoronoiSeed> getVoronoiSeeds() {
        return VoronoiEngine.voronoiSeeds;
    }

    public static void randomizeVoronoiSeeds(final int amount, final int maxX, final int maxY) {
        VoronoiEngine.randomizeVoronoiSeeds(new Random().nextLong(), amount, maxX, maxY);
    }

    public static void randomizeVoronoiSeeds(final long seed, final int amount, final int maxX, final int maxY) {
        VoronoiEngine.voronoiSeeds.clear();
        final Random random = new Random(seed);
        for (int i = 0; i < amount; i++) {
            VoronoiEngine.voronoiSeeds.add(new VoronoiSeed(new Point2D(random.nextInt(maxX), random.nextInt(maxY))));
        }
    }

    public static VoronoiNode getVoronoiNode(final Point2D origin, final MathUtil.DistanceFunction distanceFunction) {
        final VoronoiNode voronoiNode = new VoronoiNode(origin);
        VoronoiEngine.voronoiSeeds.forEach(voronoiSeed -> {
            if (voronoiNode.getVoronoiSeed() == null || MathUtil.calculateDistance(origin, voronoiSeed.getPoint2D(), distanceFunction) <= MathUtil.calculateDistance(origin, voronoiNode.getVoronoiSeed().getPoint2D(), distanceFunction)) {
                voronoiNode.setVoronoiSeed(voronoiSeed);
            }
        });
        return voronoiNode;
    }
}
