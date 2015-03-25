/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dain_torson.graphwizard;

import javafx.scene.layout.VBox;

/**
 *
 * @author Ales
 */
public class InfoBar extends VBox{
    
    InfoPanel infoPanel = new InfoPanel();
    
    public InfoBar()
    {
        this.getStyleClass().add("toolbar");
        this.getChildren().addAll(infoPanel);
    }
    
}
