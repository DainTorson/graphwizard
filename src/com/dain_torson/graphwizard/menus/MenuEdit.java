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

    private Graph source;
    private MenuItem calculateCenterItem;
    private MenuItem calculateRadiusItem;
    private MenuItem calculateDiameterItem;

    public MenuEdit(Graph graph) {
        this.source = graph;
        this.setText("Edit");
        Menu menu = new Menu("Calculate");

        calculateCenterItem = new MenuItem("Graph center");
        calculateRadiusItem = new MenuItem("Radius");
        calculateDiameterItem = new MenuItem("Diameter");
        menu.getItems().addAll(calculateRadiusItem, calculateDiameterItem, calculateCenterItem);
        this.getItems().add(menu);

        update();
    }

    private void update() {

        calculateRadiusItem.setOnAction(new CalculateHandler(SearchTarget.RADIUS));
        calculateDiameterItem.setOnAction(new CalculateHandler(SearchTarget.DIAMETER));
        calculateCenterItem.setOnAction(new CalculateHandler(SearchTarget.CENTER));
    }

    public void setSource(Graph source) {
        this.source = source;
        update();
    }

    public Graph getSource() {
        return source;
    }

    private enum SearchTarget {CENTER, RADIUS, DIAMETER}

    private class CalculateHandler implements EventHandler<ActionEvent> {

        private SearchTarget target;

        public CalculateHandler(SearchTarget target) {
            this.target = target;
        }

        @Override
        public void handle(ActionEvent event) {

            YesNoMsgBox msgBox = new YesNoMsgBox("Do you want to visualise algorithm?");
            msgBox.showAndWait();

            if(msgBox.getValue()) {

                InputInRangeMsgBox inRangeMsgBox =
                        new InputInRangeMsgBox("Visualisation speed(1 - 10): ", 1, 10);
                inRangeMsgBox.setText(String.valueOf(5));
                inRangeMsgBox.showAndWait();

                int speed;
                try {
                    speed = Integer.valueOf(inRangeMsgBox.getInput());
                }
                catch (NumberFormatException exception) {
                    return;
                }

                String message;
                if(target == SearchTarget.RADIUS) {
                    source.findRadiusVisualised(speed);
                }
                else if(target == SearchTarget.DIAMETER) {
                    source.findDiameterVisualised(speed);
                }
                else {
                    source.findGraphCenterVisualised(speed);
                }
            }
            else {

                String message;
                if(target == SearchTarget.RADIUS) {
                    message = "Graph radius is: " + String.valueOf(source.findRadius(false));
                }
                else if(target == SearchTarget.DIAMETER) {
                    message = "Graph diameter is: " + String.valueOf(source.findDiameter(false));
                }
                else {
                    List<Integer> list = source.findGraphCenter(false);
                    message = "Graph center is: ";
                    for (Integer value : list) {
                        message += String.valueOf(value) + " ";
                    }
                }

                OutputMsgBox outputMsgBox = new OutputMsgBox(message);
                outputMsgBox.show();
            }
        }
    }
}
