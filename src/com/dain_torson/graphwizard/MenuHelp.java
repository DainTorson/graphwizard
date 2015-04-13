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

/**
 *
 * @author Ales
 */
public class MenuHelp extends Menu{
    
    public MenuHelp(String name) {

        this.setText(name);
        MenuItem itemAbout = new MenuItem("About");
        itemAbout.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                OutputMsgBox msgBox = new OutputMsgBox("Graph Wizard v0.1");
                msgBox.show();
            }
        });
        this.getItems().addAll(itemAbout);

    }   
}
