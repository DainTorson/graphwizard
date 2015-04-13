package com.dain_torson.graphwizard;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class OutputMsgBox extends Stage {

    private Button okButton;
    private Label label;

    public OutputMsgBox(String message) {

        GridPane gridPane = new GridPane();

        label = new Label(message);
        okButton = new Button("Ok");

        okButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                close();
            }
        });

        gridPane.add(label, 0, 0, 2, 1);
        gridPane.add(okButton, 2, 1);
        gridPane.setPadding(new Insets(25, 25, 25, 25));
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setAlignment(Pos.CENTER);

        Scene scene = new Scene(gridPane, 250, 150);
        this.setTitle("Message Box");
        this.setScene(scene);
    }

    Label getLabel() {
        return label;
    }
}
