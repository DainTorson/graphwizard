/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dain_torson.graphwizard;

import javafx.scene.control.Accordion;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;

/**
 *
 * @author Ales
 */
public class InfoPanel extends Accordion{

    TitledPane tpName = new TitledPane();
    TitledPane tpVertices = new TitledPane();
    TitledPane tpEdges = new TitledPane();
    
    public InfoPanel()
    {
        this.tpName.setText("Progect Name");
        this.tpName.setContent(new Label("New Project"));
        this.tpVertices.setText("Vertices");
        this.tpVertices.setContent(new Label(String.valueOf(0)));
        this.tpEdges.setText("Edges");
        this.tpEdges.setContent(new Label(String.valueOf(0)));
        this.getPanes().addAll(tpName, tpVertices, tpEdges);
        
    }
    
}
