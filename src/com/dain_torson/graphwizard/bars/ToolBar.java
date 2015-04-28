/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dain_torson.graphwizard.bars;

import com.dain_torson.graphwizard.drawspace.DrawSpace;
import com.dain_torson.graphwizard.drawspace.TabSpace;
import com.dain_torson.graphwizard.drawspace.TabSpaceEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

/**
 *
 * @author Ales
 */
public class ToolBar extends VBox{

    private static int buttonSize = 40;
    private DrawSpace drawSpace;

    public ToolBar(TabSpace tabSpace)
    {
        this.getStyleClass().add("toolbar");
        drawSpace = tabSpace.getCurrentDrawSpace();
        Button cursorButton = new Button();
        cursorButton.setPrefSize(buttonSize, buttonSize);
        cursorButton.getStyleClass().addAll("button", "cursorButton");
        Button vertexButton = new Button();
        vertexButton.setPrefSize(buttonSize, buttonSize);
        vertexButton.getStyleClass().addAll("button", "vertexButton");
        Button edgeButton = new Button();
        edgeButton.setPrefSize(buttonSize, buttonSize);
        edgeButton.getStyleClass().addAll("button", "edgeButton");
        Button orientedButton = new Button();
        orientedButton.setPrefSize(buttonSize, buttonSize);
        orientedButton.getStyleClass().addAll("button", "orEdgeButton");

        cursorButton.setOnAction(new CursorButtonHandler(drawSpace));
        vertexButton.setOnAction(new VertexButtonHandler(drawSpace));
        edgeButton.setOnAction(new EdgeButtonHandler(drawSpace));
        orientedButton.setOnAction(new OrientedButtonHandler(drawSpace));
        tabSpace.addEventHandler(TabSpaceEvent.TAB_SELECTION_CHANGED, new TabSpaceSelectionEventHandler());

        this.getChildren().addAll(cursorButton, vertexButton, edgeButton, orientedButton);
    
    }

    private class CursorButtonHandler implements EventHandler<ActionEvent> {

        private DrawSpace drawSpace;

        public CursorButtonHandler(DrawSpace drawSpace) {
            this.drawSpace = drawSpace;
        }

        @Override
        public void handle(ActionEvent event) {
            if(drawSpace.getOperationType() != DrawSpace.OperationType.IMMUTABLE) {
                drawSpace.setOperationType(DrawSpace.OperationType.DEFAULT);
            }

        }
    }

    private class VertexButtonHandler implements EventHandler<ActionEvent> {

        private DrawSpace drawSpace;

        public VertexButtonHandler(DrawSpace drawSpace) {
            this.drawSpace = drawSpace;
        }

        @Override
        public void handle(ActionEvent event) {
            if(drawSpace.getOperationType() != DrawSpace.OperationType.IMMUTABLE) {
                drawSpace.setOperationType(DrawSpace.OperationType.VERTEX);
            }

        }
    }

    private class EdgeButtonHandler implements EventHandler<ActionEvent> {

        private DrawSpace drawSpace;

        public EdgeButtonHandler(DrawSpace drawSpace) {
            this.drawSpace = drawSpace;
        }

        @Override
        public void handle(ActionEvent event) {
            if(drawSpace.getOperationType() != DrawSpace.OperationType.IMMUTABLE) {
                drawSpace.setOperationType(DrawSpace.OperationType.EDGE);
            }

        }
    }

    private class OrientedButtonHandler implements EventHandler<ActionEvent> {

        private DrawSpace drawSpace;

        public OrientedButtonHandler(DrawSpace drawSpace) {
            this.drawSpace = drawSpace;
        }

        @Override
        public void handle(ActionEvent event) {
            if(drawSpace.getOperationType() != DrawSpace.OperationType.IMMUTABLE) {
                drawSpace.setOperationType(DrawSpace.OperationType.ORIENTED_EDGE);
            }

        }
    }

    private class TabSpaceSelectionEventHandler implements EventHandler<TabSpaceEvent> {

        @Override
        public void handle(TabSpaceEvent event) {
            drawSpace = event.getSource().getDrawSpace();
        }
    }
    
}
