/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dain_torson.graphwizard.bars;

import com.dain_torson.graphwizard.drawspace.DrawSpace;
import com.dain_torson.graphwizard.graph.Graph;
import com.dain_torson.graphwizard.menus.MenuEdit;
import com.dain_torson.graphwizard.menus.MenuFile;
import com.dain_torson.graphwizard.menus.MenuHelp;
import javafx.scene.control.MenuBar;
import javafx.stage.Stage;

/**
 *
 * @author Ales
 */
public class TopMenuBar extends MenuBar{
    
    public TopMenuBar(Stage stage, Graph graph, DrawSpace drawSpace)
    {
        this.getStyleClass().add("menubar");
        
        MenuFile menuFile = new MenuFile(stage, graph, drawSpace);
        MenuEdit menuEdit = new MenuEdit(graph);
        MenuHelp menuHelp = new MenuHelp("Help");
        this.getMenus().addAll(menuFile, menuEdit, menuHelp);
        
    }
    
}
