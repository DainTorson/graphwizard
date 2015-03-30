package com.dain_torson.graphwizard;


import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 * Created by Ales on 29.03.2015.
 */
public class InputMsgBox extends Stage{

    private Button okButton;
    private Button cancelButton;
    private TextField textField;
    private Label label;

    public InputMsgBox() {

        GridPane gridPane = new GridPane();

        label = new Label("New value: ");
        textField = new TextField();
        okButton = new Button("Ok");
        cancelButton = new Button("Cancel");

        gridPane.add(label, 0, 0, 2, 1);
        gridPane.add(textField, 0, 1, 2, 1);
        gridPane.add(okButton, 0, 2);
        gridPane.add(cancelButton, 1, 2);
        gridPane.setPadding(new Insets(25, 25, 25, 25));
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setAlignment(Pos.CENTER);

        cancelButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                close();
            }
        });


        Scene scene = new Scene(gridPane, 200, 150);
        this.setTitle("Message Box");
        this.setScene(scene);
    }

    Button getOkButton() {
        return okButton;
    }

    Button getCancelButton() {
        return cancelButton;
    }

    String getText() {
        return textField.getText();
    }
}


