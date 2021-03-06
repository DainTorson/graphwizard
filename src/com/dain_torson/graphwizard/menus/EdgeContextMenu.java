package com.dain_torson.graphwizard.menus;

import com.dain_torson.graphwizard.graph.elements.Edge;
import com.dain_torson.graphwizard.msgboxes.EdgeInputMsgBox;
import com.dain_torson.graphwizard.graph.Graph;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;

public class EdgeContextMenu extends ContextMenu {

    private Edge source;
    private Graph graph;

    public EdgeContextMenu(Graph sourceGraph) {

        this.graph = sourceGraph;
        MenuItem renameMenuItem = new MenuItem("Set weight");
        MenuItem deleteMenuItem = new MenuItem("Delete");

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

        this.getItems().addAll(renameMenuItem, deleteMenuItem);
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
