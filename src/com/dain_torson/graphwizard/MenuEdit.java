/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dain_torson.graphwizard;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;

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

        MenuItem calculateItem = new MenuItem("Calculate");
        this.getItems().addAll(calculateItem);

        calculateItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                List<Integer> list = source.findGraphCenterVisualised();

                for(Integer value : list) {
                    System.out.println(value);
                }
            }
        });
    }

}
