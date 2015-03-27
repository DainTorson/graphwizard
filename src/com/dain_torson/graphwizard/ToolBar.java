/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dain_torson.graphwizard;

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

    public ToolBar(DrawSpace drawSpace)
    {
        this.getStyleClass().add("toolbar");
        Button cursorButton = new Button("C");
        cursorButton.setPrefSize(buttonSize, buttonSize);
        Button vertexButton = new Button("V");
        vertexButton.setPrefSize(buttonSize, buttonSize);
        Button edgeButton = new Button("E");
        edgeButton.setPrefSize(buttonSize, buttonSize);
        Button orientedButton = new Button("OE");
        orientedButton.setPrefSize(buttonSize, buttonSize);

        cursorButton.setOnAction(new CursorButtonHandler(drawSpace));
        vertexButton.setOnAction(new VertexButtonHandler(drawSpace));
        edgeButton.setOnAction(new EdgeButtonHandler(drawSpace));
        orientedButton.setOnAction(new OrientedButtonHandler(drawSpace));

        this.getChildren().addAll(cursorButton, vertexButton, edgeButton, orientedButton);
    
    }

    private class CursorButtonHandler implements EventHandler<ActionEvent> {

        private DrawSpace drawSpace;

        public CursorButtonHandler(DrawSpace drawSpace) {
            this.drawSpace = drawSpace;
        }

        @Override
        public void handle(ActionEvent event) {
            drawSpace.setOperationType(DrawSpace.OperationType.DEFAULT);

        }
    }

    private class VertexButtonHandler implements EventHandler<ActionEvent> {

        private DrawSpace drawSpace;

        public VertexButtonHandler(DrawSpace drawSpace) {
            this.drawSpace = drawSpace;
        }

        @Override
        public void handle(ActionEvent event) {
            drawSpace.setOperationType(DrawSpace.OperationType.VERTEX);

        }
    }

    private class EdgeButtonHandler implements EventHandler<ActionEvent> {

        private DrawSpace drawSpace;

        public EdgeButtonHandler(DrawSpace drawSpace) {
            this.drawSpace = drawSpace;
        }

        @Override
        public void handle(ActionEvent event) {
            drawSpace.setOperationType(DrawSpace.OperationType.EDGE);

        }
    }

    private class OrientedButtonHandler implements EventHandler<ActionEvent> {

        private DrawSpace drawSpace;

        public OrientedButtonHandler(DrawSpace drawSpace) {
            this.drawSpace = drawSpace;
        }

        @Override
        public void handle(ActionEvent event) {
            drawSpace.setOperationType(DrawSpace.OperationType.ORIENTED_EDGE);

        }
    }
    
}
