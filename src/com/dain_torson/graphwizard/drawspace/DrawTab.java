package com.dain_torson.graphwizard.drawspace;

import com.dain_torson.graphwizard.graph.Graph;
import com.dain_torson.graphwizard.menus.DrawTabContextMenu;
import com.dain_torson.graphwizard.menus.FileEvent;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.Tab;
import javafx.stage.Stage;

public class DrawTab extends Tab{

    private ScrollSpace scrollSpace;
    private Graph graph;
    private TabSpace tabSpace;

    public DrawTab(Stage stage, Graph graph, TabSpace tabSpace) {
        this.graph = graph;
        this.tabSpace = tabSpace;
        setText(this.graph.getName());

        scrollSpace = new ScrollSpace(stage, graph, this);
        scrollSpace.getDrawSpace().addEventHandler(FileEvent.FILE_OPENED, new FileOpenedHandler());
        scrollSpace.getDrawSpace().addEventHandler(FileEvent.FILE_SAVED, new FileSavedHandler());
        scrollSpace.getDrawSpace().addEventHandler(DrawSpaceEvent.DRAWSPACE_CHANGED, new DrawSpaceMutatedHandler());
        this.setContextMenu(new DrawTabContextMenu(this, this.tabSpace));
        this.setOnClosed(new DrawTabOnCloseHandler(this));
    }

    public DrawSpace getDrawSpace() {
        return scrollSpace.getDrawSpace();
    }

    public Graph getGraph() {
        return graph;
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

    private class FileOpenedHandler implements EventHandler<FileEvent> {

        @Override
        public void handle(FileEvent event) {
            setText(graph.getName());
        }
    }

    private class FileSavedHandler implements EventHandler<FileEvent> {

        @Override
        public void handle(FileEvent event) {
            setText(graph.getName());
        }
    }

    private class DrawSpaceMutatedHandler implements EventHandler<DrawSpaceEvent> {

        @Override
        public void handle(DrawSpaceEvent event) {
            setText(graph.getName() + "*");
        }
    }
}
