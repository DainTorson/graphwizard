package com.dain_torson.graphwizard.msgboxes;


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
import javafx.stage.WindowEvent;


public class InputMsgBox extends Stage{

    private Button okButton;
    private Button cancelButton;
    private TextField textField;
    private Label label;
    protected String input;

    public InputMsgBox(String message) {

        GridPane gridPane = new GridPane();

        label = new Label(message);
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

        okButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                input = getText();
                close();
            }
        });
        cancelButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                input = "canceled";
                close();
            }
        });


        Scene scene = new Scene(gridPane, 200, 150);
        this.setTitle("Message Box");
        this.setScene(scene);
    }

    public Button getOkButton() {
        return okButton;
    }

    public void setMessage(String message) {
        label.setText(message);
    }

    public void setText(String text) {
        textField.setText(text);
    }

    public Button getCancelButton() {
        return cancelButton;
    }

    protected String getText() {
        return textField.getText();
    }

    public String getInput() {
        return input;
    }

    @Override
    public void close(){
        fireEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSE_REQUEST));
        super.close();
    }
}


