package com.dain_torson.graphwizard.msgboxes;

import com.dain_torson.graphwizard.graph.elements.Vertex;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class VertexInputMsgBox extends InputMsgBox {

    private Vertex source;

    public VertexInputMsgBox(Vertex vertex) {
        super("Enter new name: ");
        this.source = vertex;
        this.setTitle("New name");

        this.getOkButton().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                source.setValue(getText());
                close();
            }
        });
    }
}
