/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dain_torson.graphwizard.menus;

import com.dain_torson.graphwizard.graph.Graph;
import com.dain_torson.graphwizard.msgboxes.InputInRangeMsgBox;
import com.dain_torson.graphwizard.msgboxes.OutputMsgBox;
import com.dain_torson.graphwizard.msgboxes.YesNoMsgBox;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.stage.WindowEvent;

import java.util.List;

/**
 *
 * @author Ales
 */
public class MenuEdit extends Menu {

    Graph source;

    public MenuEdit(Graph graph) {
        this.source = graph;
        this.setText("Edit");

        MenuItem calculateItem = new MenuItem("Graph center");
        Menu menu = new Menu("Calculate");
        menu.getItems().add(calculateItem);
        this.getItems().add(menu);

        calculateItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                final YesNoMsgBox msgBox = new YesNoMsgBox("Do you want to visualise algorithm?");
                msgBox.show();
                msgBox.setOnCloseRequest(new EventHandler<WindowEvent>() {
                    @Override
                    public void handle(WindowEvent event) {
                        if (msgBox.getValue()) {

                            final InputInRangeMsgBox inRangeMsgBox =
                                    new InputInRangeMsgBox("Visualisation speed(1 - 10): ", 1, 10);
                            inRangeMsgBox.setText(String.valueOf(5));
                            inRangeMsgBox.show();
                            inRangeMsgBox.setOnCloseRequest(new EventHandler<WindowEvent>() {
                                @Override
                                public void handle(WindowEvent event) {
                                    int speed;
                                    try {
                                        speed = Integer.valueOf(inRangeMsgBox.getInput());
                                        System.out.println(speed);
                                    }
                                    catch (NumberFormatException exception) {
                                        return;
                                    }
                                    speed = 11 - speed;
                                    System.out.println(speed);
                                    source.findGraphCenterVisualised(speed);

                            }
                            });
                        }
                        else {
                            List<Integer> list = source.findGraphCenter(false);

                            String message = "Graph center is: ";
                            for (Integer value : list) {
                                message += String.valueOf(value) + " ";
                            }
                            OutputMsgBox outputMsgBox = new OutputMsgBox(message);
                            outputMsgBox.show();
                        }
                    }
                });
            }
        });
    }

}
