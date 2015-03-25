/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dain_torson.graphwizard;

import javafx.scene.control.MenuBar;
import javafx.stage.Stage;

/**
 *
 * @author Ales
 */
public class TopMenuBar extends MenuBar{
    
    public TopMenuBar(Stage stage, Graph graph)
    {
        this.getStyleClass().add("menubar");
        
        MenuFile menuFile = new MenuFile("File", stage, graph);
        MenuEdit menuEdit = new MenuEdit("Edit");
        MenuHelp menuHelp = new MenuHelp("Help");
        this.getMenus().addAll(menuFile, menuEdit, menuHelp);
        
    }
    
}
