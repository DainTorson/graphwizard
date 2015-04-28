/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dain_torson.graphwizard;


import com.dain_torson.graphwizard.bars.ToolBar;
import com.dain_torson.graphwizard.bars.TopMenuBar;
import com.dain_torson.graphwizard.drawspace.ScrollSpace;
import com.dain_torson.graphwizard.drawspace.TabSpace;
import com.dain_torson.graphwizard.graph.Graph;
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
    public void start(final Stage primaryStage) {
        
        BorderPane root = new BorderPane();
        TabSpace tabSpace = new TabSpace(primaryStage);
        TopMenuBar menu = new TopMenuBar(primaryStage, tabSpace);
        ToolBar toolBar = new ToolBar(tabSpace);

        root.setTop(menu);
        root.setLeft(toolBar);
        root.setCenter(tabSpace);
        
        Scene scene = new Scene(root, 800, 600);
        
        primaryStage.setTitle("Graph Wizard");
        primaryStage.setScene(scene);
        scene.getStylesheets().add("assets/css/GraphWizard.css");
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
