package com.dain_torson.graphwizard;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class EdgeInputMsgBox extends InputMsgBox {

    private Edge source;

    public EdgeInputMsgBox(Edge edge) {
        super("Enter new weight: ");
        this.source = edge;
        this.setTitle("New weight");

        this.getOkButton().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                source.setValue(Integer.valueOf(getText()));
                close();
            }
        });
    }
}
