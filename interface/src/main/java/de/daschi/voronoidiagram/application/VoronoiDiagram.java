package de.daschi.voronoidiagram.application;

import de.daschi.voronoidiagram.core.VoronoiEngine;
import de.daschi.voronoidiagram.core.VoronoiSeed;
import de.daschi.voronoidiagram.ui.RenderEngine;
import de.daschi.voronoidiagram.ui.UIEngine;
import de.daschi.voronoidiagram.util.MathUtil;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class VoronoiDiagram extends Application {

    private static final double WIDTH = 400, HEIGHT = 400;
    private static Canvas euclideanCanvas, differenceCanvas, manhattanCanvas;
    private static VBox vBox;
    private static final EventHandler<? super MouseEvent> canvasMouseClickHandler = event -> {
        VoronoiSeed voronoiSeed = new VoronoiSeed(new Point2D(event.getX(), event.getY()));
        VoronoiEngine.getVoronoiSeeds().add(voronoiSeed);
        UIEngine.addVoronoiSeedLabel(VoronoiDiagram.vBox, voronoiSeed);
        VoronoiDiagram.rerenderCanvases();
    };

    public static void main(final String[] args) {
        Application.launch(args);
    }

    public static void rerenderCanvases() {
        RenderEngine.renderVoronoiDiagram(VoronoiDiagram.euclideanCanvas, MathUtil.DistanceFunction.EUCLIDEAN);
        RenderEngine.renderVoronoiDiagram(VoronoiDiagram.differenceCanvas, MathUtil.DistanceFunction.DIFFERENCE);
        RenderEngine.renderVoronoiDiagram(VoronoiDiagram.manhattanCanvas, MathUtil.DistanceFunction.MANHATTAN);
    }

    @Override
    public void start(final Stage primaryStage) {
        final BorderPane borderPane = new BorderPane();
        final Canvas euclideanCanvas = new Canvas(VoronoiDiagram.WIDTH, VoronoiDiagram.HEIGHT);
        euclideanCanvas.setOnMouseClicked(VoronoiDiagram.canvasMouseClickHandler);
        VoronoiDiagram.euclideanCanvas = euclideanCanvas;
        final Canvas centerCanvas = new Canvas(VoronoiDiagram.WIDTH, VoronoiDiagram.HEIGHT);
        centerCanvas.setOnMouseClicked(VoronoiDiagram.canvasMouseClickHandler);
        VoronoiDiagram.differenceCanvas = centerCanvas;
        final Canvas rightCanvas = new Canvas(VoronoiDiagram.WIDTH, VoronoiDiagram.HEIGHT);
        rightCanvas.setOnMouseClicked(VoronoiDiagram.canvasMouseClickHandler);
        VoronoiDiagram.manhattanCanvas = rightCanvas;
        final TilePane tilePane = new TilePane(Orientation.HORIZONTAL, 10, 10, euclideanCanvas, centerCanvas, rightCanvas);
        tilePane.setTileAlignment(Pos.TOP_CENTER);
        tilePane.setAlignment(Pos.TOP_CENTER);
        borderPane.setTop(tilePane);

        final VBox vBox = new VBox();
        borderPane.setBottom(vBox);
        VoronoiDiagram.vBox = vBox;

        primaryStage.setWidth(VoronoiDiagram.WIDTH * 3 + 10 * 4);
        primaryStage.setHeight(VoronoiDiagram.HEIGHT * 2);
        primaryStage.setScene(new Scene(borderPane));
        primaryStage.setResizable(true);
        primaryStage.setTitle("VoronoiDiagram");
        primaryStage.show();

        //VoronoiEngine.randomizeVoronoiSeeds(5192003, 40, VoronoiDiagram.WIDTH, VoronoiDiagram.HEIGHT);
    }
}
