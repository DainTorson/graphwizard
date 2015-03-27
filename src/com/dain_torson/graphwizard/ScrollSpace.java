package com.dain_torson.graphwizard;

import javafx.scene.control.ScrollPane;
import javafx.stage.Stage;

/**
 * Created by Ales on 27.03.2015.
 */
public class ScrollSpace extends ScrollPane{

    private DrawSpace drawSpace;

    public ScrollSpace(Stage stage, Graph graph) {
        drawSpace = new DrawSpace(stage, this, graph);
    }

    public DrawSpace getDrawSpace() {
        return this.drawSpace;
    }
}
