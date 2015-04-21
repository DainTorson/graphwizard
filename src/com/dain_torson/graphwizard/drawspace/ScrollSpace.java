package com.dain_torson.graphwizard.drawspace;

import com.dain_torson.graphwizard.graph.Graph;
import javafx.scene.control.ScrollPane;
import javafx.stage.Stage;

public class ScrollSpace extends ScrollPane{

    private DrawSpace drawSpace;

    public ScrollSpace(Stage stage, Graph graph, DrawTab drawTab) {
        drawTab.setContent(this);
        drawSpace = new DrawSpace(stage, this, graph);
    }

    public DrawSpace getDrawSpace() {
        return this.drawSpace;
    }
}
