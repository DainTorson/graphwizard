/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dain_torson.graphwizard;

import java.io.File;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 *
 * @author Ales
 */
public class MenuFile extends Menu {

    Stage newStage = new Stage();

    public MenuFile(String name, Stage stage, Graph graph) {
        this.newStage = stage;
        this.setText(name);
        MenuItem itemNew = new MenuItem("New");
        MenuItem itemOpen = new MenuItem("Open");
        MenuItem itemSave = new MenuItem("Save");
        MenuItem itemExit = new MenuItem("Exit");

        itemNew.setOnAction(new ItemNewEventHandler(graph));
        itemOpen.setOnAction(new ItemOpenEventHandler(newStage));
        itemExit.setOnAction(new ItemExitEventHandler());
        
        this.getItems().addAll(itemNew, itemOpen, itemSave, itemExit);
    }

    private class ItemNewEventHandler implements EventHandler<ActionEvent> {

        Graph graph;
        public ItemNewEventHandler(Graph graph) {
            this.graph = graph;
        }

        @Override
        public void handle(ActionEvent event) {
            graph.clear();
        }
    }

    private class ItemOpenEventHandler implements EventHandler<ActionEvent> {

        FileChooser fileChooser = new FileChooser();
        Stage stage = new Stage();
        ItemOpenEventHandler(Stage stage) {
           this.stage = stage;  
        }
        
        @Override
        public void handle(ActionEvent e) {
            File file = fileChooser.showOpenDialog(stage);
            if (file != null) {
                System.out.print(file.getAbsolutePath());
            }
        }
    }

    private class ItemExitEventHandler implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent e) {
            Platform.exit();
        }

    }

}
