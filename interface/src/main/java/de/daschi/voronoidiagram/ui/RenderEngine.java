package de.daschi.voronoidiagram.ui;

import de.daschi.voronoidiagram.core.VoronoiEngine;
import de.daschi.voronoidiagram.core.VoronoiNode;
import de.daschi.voronoidiagram.core.VoronoiSeed;
import de.daschi.voronoidiagram.util.ColorUtil;
import de.daschi.voronoidiagram.util.MathUtil;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class RenderEngine {
    private static final int SUBSECTION_WIDTH = 100;
    private static final int SUBSECTION_HEIGHT = 100;
    private static final Duration CALCULATION_TIME = Duration.ZERO;
    private static final Duration CALCULATION_PAUSE_TIME = Duration.millis(15);
    private static final int SUBSECTION_DRAW_WIDTH = 100;
    private static final int SUBSECTION_DRAW_HEIGHT = 100;
    private static final Duration RENDER_DURATION = Duration.ZERO;
    private static final Duration RENDER_PAUSE_DURATION = Duration.millis(1);

    private static void renderVoronoiNode(final Canvas canvas, final VoronoiNode voronoiNode) {
        final VoronoiSeed voronoiSeed = voronoiNode.getVoronoiSeed();
        final Color color = RenderEngine.getColorFromVoronoiSeed(voronoiSeed);
        RenderEngine.drawColor(canvas, (int) voronoiNode.getOrigin().getX(), (int) voronoiNode.getOrigin().getY(), color);
        RenderEngine.renderVoronoiSeed(canvas, voronoiSeed, ColorUtil.invertColor(color).saturate().brighter());
    }

    private static void renderVoronoiSeed(final Canvas canvas, final VoronoiSeed voronoiSeed, final Color color) {
        RenderEngine.drawColor(canvas, (int) voronoiSeed.getPoint2D().getX(), (int) voronoiSeed.getPoint2D().getY(), color);
    }

    private static Color getColorFromVoronoiSeed(final VoronoiSeed voronoiSeed) {
        final Random random = new Random(new Random((long) (voronoiSeed.getPoint2D().getX() + voronoiSeed.getPoint2D().getY())).nextLong() + (long) (voronoiSeed.getPoint2D().getX() + voronoiSeed.getPoint2D().getY()));
        return Color.rgb(random.nextInt(255), random.nextInt(255), random.nextInt(255));
    }

    private static Color getColorForVoronoiSeed(final VoronoiSeed voronoiSeed) {
        return ColorUtil.invertColor(RenderEngine.getColorFromVoronoiSeed(voronoiSeed)).saturate().brighter();
    }

    public static void renderVoronoiDiagram(final Canvas canvas, final MathUtil.DistanceFunction distanceFunction) {
        // Construct color array
        final int xRepeats = (int) Math.ceil(canvas.getWidth() / RenderEngine.SUBSECTION_WIDTH);
        final int yRepeats = (int) Math.ceil(canvas.getHeight() / RenderEngine.SUBSECTION_HEIGHT);
        final AtomicInteger xOffset = new AtomicInteger(0);
        final AtomicInteger yOffset = new AtomicInteger(0);

        final Timeline timeline = new Timeline(new KeyFrame(RenderEngine.CALCULATION_TIME, event -> {
            final Color[][] colors = new Color[RenderEngine.SUBSECTION_WIDTH][RenderEngine.SUBSECTION_HEIGHT];
            for (int x = xOffset.get() * RenderEngine.SUBSECTION_WIDTH; x < xOffset.get() * RenderEngine.SUBSECTION_WIDTH + RenderEngine.SUBSECTION_WIDTH; x++) {
                for (int y = yOffset.get() * RenderEngine.SUBSECTION_HEIGHT; y < yOffset.get() * RenderEngine.SUBSECTION_HEIGHT + RenderEngine.SUBSECTION_HEIGHT; y++) {
                    final Point2D point2D = new Point2D(x, y);
                    Color color;
                    if (!distanceFunction.equals(MathUtil.DistanceFunction.DIFFERENCE)) {
                        color = RenderEngine.getColorFromVoronoiSeed(VoronoiEngine.getVoronoiNode(point2D, distanceFunction).getVoronoiSeed());
                    } else {
                        final Color euclideanColor = RenderEngine.getColorFromVoronoiSeed(VoronoiEngine.getVoronoiNode(point2D, MathUtil.DistanceFunction.EUCLIDEAN).getVoronoiSeed());
                        final Color manhattanColor = RenderEngine.getColorFromVoronoiSeed(VoronoiEngine.getVoronoiNode(point2D, MathUtil.DistanceFunction.MANHATTAN).getVoronoiSeed());
                        color = ColorUtil.blend(euclideanColor, manhattanColor);
                    }
                    final VoronoiSeed voronoiSeed = VoronoiEngine.getVoronoiNode(point2D, MathUtil.DistanceFunction.EUCLIDEAN).getVoronoiSeed();
                    if (point2D.equals(voronoiSeed.getPoint2D())) {
                        color = RenderEngine.getColorForVoronoiSeed(voronoiSeed);
                    }
                    colors[x - xOffset.get() * RenderEngine.SUBSECTION_WIDTH][y - yOffset.get() * RenderEngine.SUBSECTION_HEIGHT] = color;
                }
            }
            // Render color array to canvas
            RenderEngine.drawColorArray(canvas, colors, xOffset.get(), yOffset.get());
            yOffset.incrementAndGet();
            if (yOffset.get() >= yRepeats) {
                yOffset.set(0);
                xOffset.incrementAndGet();
            }
        }), new KeyFrame(RenderEngine.CALCULATION_PAUSE_TIME));
        timeline.setCycleCount(xRepeats * yRepeats);
        timeline.playFromStart();
    }

    private static void drawColorArray(final Canvas canvas, final Color[][] colors, final int xOffset, final int yOffset) {
        final int providedWidth = colors.length;
        final int providedHeight = colors[0].length;
        final int xDrawRepeats = (int) Math.ceil(colors.length / (double) RenderEngine.SUBSECTION_DRAW_WIDTH);
        final int yDrawRepeats = (int) Math.ceil(colors[0].length / (double) RenderEngine.SUBSECTION_DRAW_HEIGHT);
        final AtomicInteger xDrawOffset = new AtomicInteger(0);
        final AtomicInteger yDrawOffset = new AtomicInteger(0);

        final Timeline timeline = new Timeline(new KeyFrame(RenderEngine.RENDER_DURATION, event -> {
            for (int x = xDrawOffset.get() * RenderEngine.SUBSECTION_DRAW_WIDTH; x < xDrawOffset.get() * RenderEngine.SUBSECTION_DRAW_WIDTH + RenderEngine.SUBSECTION_DRAW_WIDTH; x++) {
                for (int y = yDrawOffset.get() * RenderEngine.SUBSECTION_DRAW_HEIGHT; y < yDrawOffset.get() * RenderEngine.SUBSECTION_DRAW_HEIGHT + RenderEngine.SUBSECTION_DRAW_HEIGHT; y++) {
                    Color color = null;
                    if (x < providedWidth && y < providedHeight) {
                        color = colors[x][y];
                    }
                    RenderEngine.drawColor(canvas, x + xOffset * RenderEngine.SUBSECTION_WIDTH, y + yOffset * RenderEngine.SUBSECTION_HEIGHT, color);
                }
            }
            yDrawOffset.incrementAndGet();
            if (yDrawOffset.get() >= yDrawRepeats) {
                yDrawOffset.set(0);
                xDrawOffset.incrementAndGet();
            }
        }), new KeyFrame(RenderEngine.RENDER_PAUSE_DURATION));
        timeline.setCycleCount(xDrawRepeats * yDrawRepeats);
        timeline.playFromStart();
    }

    private static void drawColor(final Canvas canvas, final int x, final int y, final Color color) {
        canvas.getGraphicsContext2D().getPixelWriter().setColor(x, y, color != null ? color : Color.TRANSPARENT);
    }
}
