/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dain_torson.graphwizard;


import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 *
 * @author Ales
 */
public class GraphWizard extends Application {
    
    
    @Override
    public void start(Stage primaryStage) {
        
        BorderPane root = new BorderPane();
        Graph graph = new Graph();
        ScrollSpace scrollSpace = new ScrollSpace(primaryStage, graph);
        //DrawSpace drawSpace = new DrawSpace(primaryStage, graph);
        TopMenuBar menu = new TopMenuBar(primaryStage, graph, scrollSpace.getDrawSpace());
        ToolBar toolBar = new ToolBar(scrollSpace.getDrawSpace());
        InfoBar infobar = new InfoBar();

        root.setTop(menu);
        root.setLeft(toolBar);
        root.setRight(infobar);
        root.setCenter(scrollSpace);
        
        Scene scene = new Scene(root, 800, 600);
        
        primaryStage.setTitle("Graph Wizard - " + "New Project");
        primaryStage.setScene(scene);
        scene.getStylesheets().add
            (GraphWizard.class.getResource("GraphWizard.css").toExternalForm());
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
