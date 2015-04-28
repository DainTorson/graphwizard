package com.dain_torson.graphwizard.drawspace;

import com.dain_torson.graphwizard.graph.Graph;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class TabSpace extends TabPane{

    private List<DrawTab> tabList = new ArrayList<DrawTab>();
    private Stage stage;

    public TabSpace(Stage stage) {

        this.stage = stage;
        Graph first = new Graph();
        DrawTab drawTab = new DrawTab(stage, first, this);
        first.setDrawSpace(drawTab.getDrawSpace());
        this.tabList.add(drawTab);
        this.getTabs().add(drawTab);
    }

    public void addNewTab(){
        Graph tempGraph = new Graph();
        DrawTab tempTab = new DrawTab(stage, tempGraph, this);
        tempGraph.setDrawSpace(tempTab.getDrawSpace());
        tabList.add(tempTab);
        getTabs().add(tempTab);
    }

    public void closeTab(DrawTab tab) {
        tabList.remove(tab);
        getTabs().remove(tab);

        if(tabList.isEmpty()) {
            addNewTab();
        }
    }

    public void closeAll() {
        tabList.clear();
        getTabs().clear();
        addNewTab();
    }

    public Graph getCurrentGraph() {
        for(DrawTab tab : tabList) {
            if(tab.isSelected()) {
                return tab.getGraph();

            }
        }
        return null;
    }

    public DrawSpace getCurrentDrawSpace() {
        for(DrawTab tab : tabList) {
            if(tab.isSelected()) {
                return tab.getDrawSpace();
            }
        }
        return null;
    }

    private class TabSelectionChangedHandler implements EventHandler<Event> {

        @Override
        public void handle(Event event) {

        }
    }
}
