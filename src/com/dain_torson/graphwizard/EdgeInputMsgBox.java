package com.dain_torson.graphwizard;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

/**
 * Created by Ales on 30.03.2015.
 */
public class EdgeInputMsgBox extends InputMsgBox {

    private Edge source;

    public EdgeInputMsgBox(Edge edge) {
        this.source = edge;
        this.setTitle("New weight: ");

        this.getOkButton().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                source.setValue(Integer.valueOf(getText()));
                close();
            }
        });
    }
}
