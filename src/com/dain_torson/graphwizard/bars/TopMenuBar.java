/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dain_torson.graphwizard.bars;

import com.dain_torson.graphwizard.drawspace.DrawSpace;
import com.dain_torson.graphwizard.drawspace.TabSpace;
import com.dain_torson.graphwizard.drawspace.TabSpaceEvent;
import com.dain_torson.graphwizard.graph.Graph;
import com.dain_torson.graphwizard.menus.MenuEdit;
import com.dain_torson.graphwizard.menus.MenuFile;
import com.dain_torson.graphwizard.menus.MenuHelp;
import javafx.event.EventHandler;
import javafx.scene.control.MenuBar;
import javafx.scene.control.Tab;
import javafx.stage.Stage;

/**
 *
 * @author Ales
 */
public class TopMenuBar extends MenuBar{

    private MenuFile menuFile;
    private MenuEdit menuEdit;
    private MenuHelp menuHelp;

    public TopMenuBar(Stage stage, TabSpace tabSpace)
    {
        this.getStyleClass().add("menubar");
        
        menuFile = new MenuFile(stage, tabSpace.getCurrentGraph(), tabSpace.getCurrentDrawSpace());
        menuEdit = new MenuEdit(tabSpace.getCurrentGraph());
        menuHelp = new MenuHelp("Help");
        this.getMenus().addAll(menuFile, menuEdit, menuHelp);
        tabSpace.addEventHandler(TabSpaceEvent.TAB_SELECTION_CHANGED, new TabSpaceSelectionEventHandler());
        
    }

    private class TabSpaceSelectionEventHandler implements EventHandler<TabSpaceEvent> {

        @Override
        public void handle(TabSpaceEvent event) {
            DrawSpace drawSpace = event.getSource().getDrawSpace();
            Graph graph = event.getSource().getGraph();
            menuFile.setSourceGraph(graph);
            menuFile.setSourceSpace(drawSpace);
            menuEdit.setSource(graph);
        }
    }
}
