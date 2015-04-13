package com.dain_torson.graphwizard;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;


public class InputInRangeMsgBox extends InputMsgBox {

    private double min;
    private double max;

    public InputInRangeMsgBox(String message, double minVal, double maxVal) {
        super(message);
        this.min = minVal;
        this.max = maxVal;

        getOkButton().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    if (Integer.valueOf(getText()) <= max && Integer.valueOf(getText()) >= min) {
                        input = getText();
                        close();
                    } else {
                        setText("Input out of range");
                    }
                }
                catch (NumberFormatException exception) {
                    setText("Invalid input");
                }
            }
        });
    }

    public double getMin() {
        return min;
    }

    public void setMin(double min) {
        this.min = min;
    }

    public double getMax() {
        return max;
    }

    public void setMax(double max) {
        this.max = max;
    }
}
