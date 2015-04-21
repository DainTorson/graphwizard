package com.dain_torson.graphwizard.menus;

import com.dain_torson.graphwizard.drawspace.DrawTab;
import com.dain_torson.graphwizard.drawspace.TabSpace;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;

public class DrawTabContextMenu extends ContextMenu{

    private DrawTab sourceTab;
    private TabSpace sourceSpace;

    public DrawTabContextMenu(DrawTab drawTab, TabSpace tabSpace) {
        this.sourceTab = drawTab;
        this.sourceSpace = tabSpace;

        MenuItem addNewTab = new MenuItem("New Tab");
        MenuItem closeTab = new MenuItem("Close");
        MenuItem closeAllTabs = new MenuItem("Close All");

        addNewTab.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                sourceSpace.addNewTab();
            }
        });

        closeTab.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                sourceSpace.closeTab(sourceTab);
            }
        });

        closeAllTabs.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                sourceSpace.closeAll();
            }
        });

        this.getItems().addAll(addNewTab, closeTab, closeAllTabs);
    }
}
