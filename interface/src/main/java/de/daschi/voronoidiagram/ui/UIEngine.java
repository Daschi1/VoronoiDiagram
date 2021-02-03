package de.daschi.voronoidiagram.ui;

import de.daschi.voronoidiagram.application.VoronoiDiagram;
import de.daschi.voronoidiagram.core.VoronoiEngine;
import de.daschi.voronoidiagram.core.VoronoiSeed;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

public class UIEngine {
    public static void addVoronoiSeedLabel(final Pane parent, final VoronoiSeed voronoiSeed) {
        final Text text = new Text("X: " + voronoiSeed.getPoint2D().getX() + ", Y: " + voronoiSeed.getPoint2D().getY());
        final Button button = new Button("Remove");
        final HBox hBox = new HBox(text, button);
        button.setOnMouseClicked(event -> {
            VoronoiEngine.getVoronoiSeeds().remove(voronoiSeed);
            VoronoiDiagram.rerenderCanvases();
            parent.getChildren().remove(hBox);
        });
        parent.getChildren().add(hBox);
    }
}
