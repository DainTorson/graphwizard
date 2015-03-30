package com.dain_torson.graphwizard;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;

/**
 * Created by Ales on 29.03.2015.
 */
public class EdgeContextMenu extends ContextMenu {

    private Edge source;
    private Graph graph;

    public EdgeContextMenu(Graph sourceGraph) {

        this.graph = sourceGraph;
        MenuItem viewMenuItem = new MenuItem("View weight");
        MenuItem renameMenuItem = new MenuItem("Set weight");
        MenuItem deleteMenuItem = new MenuItem("Delete");

        viewMenuItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(source != null) {
                    EdgeOutputMsgBox edgeOutputMsgBox = new EdgeOutputMsgBox(source);
                    edgeOutputMsgBox.show();
                }
            }
        });
        renameMenuItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (source != null) {
                    EdgeInputMsgBox edgeInputMsgBox = new EdgeInputMsgBox(source);
                    edgeInputMsgBox.show();
                }
            }
        });
        deleteMenuItem.setOnAction(new DeleteItemHandler());

        this.getItems().addAll(viewMenuItem, renameMenuItem, deleteMenuItem);
    }

    public Edge getSource() {
        return source;
    }

    public void setSource(Edge edge) {
        source = edge;
    }

    private class DeleteItemHandler implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent event) {
            if(source != null) {
                graph.deleteEdge(source);
            }
        }
    }


}
