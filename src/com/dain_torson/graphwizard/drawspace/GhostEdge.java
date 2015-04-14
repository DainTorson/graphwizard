package com.dain_torson.graphwizard.drawspace;

import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;

public class GhostEdge {

    private Line line;
    private Pane drawSpace;
    public GhostEdge(Pane pNode) {
        this.drawSpace = pNode;
        this.line = new Line(0, 0, 0, 0);
        drawSpace.getChildren().add(this.line);
        this.line.setVisible(false);
        this.line.getStyleClass().add("ghostEdge");
        this.line.getStrokeDashArray().addAll(10d, 5d, 2d, 5d);
    }

    public void setStartPoint(double x, double y) {
        line.setStartX(x);
        line.setStartY(y);
    }
    public void setEndPoint(double x, double y) {
        line.setEndX(x);
        line.setEndY(y);
    }

    public void draw() {
        this.line.setVisible(true);
    }

    public void hide() {
        this.line.setVisible(false);
    }
}
