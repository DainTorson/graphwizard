package com.dain_torson.graphwizard.drawspace;

import com.dain_torson.graphwizard.graph.Graph;
import com.dain_torson.graphwizard.menus.DrawTabContextMenu;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.Tab;
import javafx.stage.Stage;

public class DrawTab extends Tab{

    private ScrollSpace scrollSpace;
    private Graph graph;
    private TabSpace tabSpace;
    private String name = "New";

    public DrawTab(Stage stage, Graph graph, TabSpace tabSpace) {
        this.graph = graph;
        this.tabSpace = tabSpace;

        scrollSpace = new ScrollSpace(stage, graph, this);
        this.setText(name);
        this.setContextMenu(new DrawTabContextMenu(this, this.tabSpace));
        this.setOnClosed(new DrawTabOnCloseHandler(this));
    }

    public DrawSpace getDrawSpace() {
        return scrollSpace.getDrawSpace();
    }

    public Graph getGraph() {
        return graph;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private class DrawTabOnCloseHandler implements EventHandler<Event> {

        private DrawTab target;

        public DrawTabOnCloseHandler(DrawTab target) {
            this.target = target;
        }

        @Override
        public void handle(Event event) {
            tabSpace.closeTab(target);
        }
    }
}
