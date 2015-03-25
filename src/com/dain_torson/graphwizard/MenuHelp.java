/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dain_torson.graphwizard;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;

/**
 *
 * @author Ales
 */
public class MenuHelp extends Menu{
    
    public MenuHelp(String name)
    {
      this.setText(name);
      MenuItem itemAbout = new MenuItem("About");
      this.getItems().addAll(itemAbout);
        
    }   
}
