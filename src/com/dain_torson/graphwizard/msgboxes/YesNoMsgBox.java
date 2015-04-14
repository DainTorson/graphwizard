package com.dain_torson.graphwizard.msgboxes;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class YesNoMsgBox extends Stage {

    private Button yesButton;
    private Button noButton;
    private Label label;
    private boolean value;

    public YesNoMsgBox(String message) {

        GridPane gridPane = new GridPane();
        yesButton = new Button("Yes");
        noButton = new Button("No");
        label = new Label(message);

        gridPane.add(label, 0, 0, 4, 1);
        gridPane.add(yesButton, 1, 1);
        gridPane.add(noButton, 2, 1);
        gridPane.setPadding(new Insets(25, 25, 25, 25));
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setAlignment(Pos.CENTER);

        Scene scene = new Scene(gridPane, 300, 150);
        this.setTitle("Message Box");
        this.setScene(scene);

        yesButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                value = true;

                close();
            }
        });

        noButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                value = false;
                close();
            }
        });

    }

    public void setMessage(String message) {
        label.setText(message);
    }

    public boolean getValue() {
        return value;
    }

    @Override
    public void close(){
        fireEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSE_REQUEST));
        super.close();
    }
}
