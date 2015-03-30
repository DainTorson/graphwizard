package com.dain_torson.graphwizard;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;

/**
 * Created by Ales on 29.03.2015.
 */
public class VertexContextMenu extends ContextMenu {

    private Vertex source;
    private Graph graph;

    public VertexContextMenu(Graph sourceGraph) {

        this.graph = sourceGraph;
        MenuItem renameMenuItem = new MenuItem("Rename");
        MenuItem deleteMenuItem = new MenuItem("Delete");

        renameMenuItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (source != null) {
                    VertexInputMsgBox vertexInputMsgBox = new VertexInputMsgBox(source);
                    vertexInputMsgBox.show();
                }
            }
        });
        deleteMenuItem.setOnAction(new DeleteItemHandler());

        this.getItems().addAll(renameMenuItem, deleteMenuItem);
    }

    public Vertex getSource() {
        return source;
    }

    public void setSource(Vertex vertex) {
        source = vertex;
    }

    private class DeleteItemHandler implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent event) {
            if(source != null) {
                graph.deleteVertex(source);
            }
        }
    }

}


