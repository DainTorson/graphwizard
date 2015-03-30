package com.dain_torson.graphwizard;

import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;

/**
 * Created by asus on 11.03.2015.
 */
public class VertexView {

    static private final int margin = 30;
    static private final int radius = 10;

    private DrawSpace parentNode;
    private Circle circle;
    private Label label;
    private String name;
    private DragContext circleContext = new DragContext();
    private DragContext labelContext = new DragContext();
    private Vertex vertex;
    private boolean isActive = false;

    public VertexView(double x, double y, DrawSpace pNode, Vertex vertex) {

        this.parentNode = pNode;
        this.vertex = vertex;
        this.name = this.vertex.getValue();
        this.label = new Label(this.name);
        this.label.relocate(x, y - margin);
        this.circle = new Circle(x, y, radius);

        this.circle.getStyleClass().add("vertexInactive");

        this.circle.addEventFilter(MouseEvent.MOUSE_PRESSED, new CirclePressedEventHandler(this));
        this.circle.addEventFilter(MouseEvent.MOUSE_DRAGGED, new CircleDraggedEventHandler(this));
        this.circle.addEventFilter(MouseEvent.MOUSE_PRESSED, new CircleSecondaryPressedHandler(this));

        this.label.addEventFilter(MouseEvent.MOUSE_PRESSED, new LabelPressedEventHandler());
        this.label.addEventFilter(MouseEvent.MOUSE_DRAGGED, new LabelDraggedEventHandler());


    }

    public void draw() {
        parentNode.getChildren().addAll(label, circle);
    }

    public double getX() {
        return circle.getCenterX();
    }

    public double getY() {
        return circle.getCenterY();
    }

    public Circle getCircle() {
        return circle;
    }

    public Vertex getVertex() {
        return vertex;
    }

    public static int getRadius() {
        return radius;
    }

    public boolean getActivity() {
        return  isActive;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
        this.label.setText(name);
    }

    public void delete() {
        parentNode.getChildren().removeAll(circle, label);
        circle.fireEvent(new VertexEvent(this.getVertex(), VertexEvent.VERTEX_DELETED));
    }

    public void setActivity(boolean value) {
        this.isActive = value;
        if(this.isActive) {
            this.circle.getStyleClass().clear();
            this.circle.getStyleClass().add("vertexActive");
        }
        else {
            this.circle.getStyleClass().clear();
            this.circle.getStyleClass().add("vertexInactive");
        }
    }

    private class CirclePressedEventHandler implements EventHandler<MouseEvent>  {

        VertexView parentVertex;

        public CirclePressedEventHandler(VertexView pVertex) {
            this.parentVertex = pVertex;

        }

        @Override
        public void handle(MouseEvent event) {
            circleContext.prevMouseX = event.getX();
            circleContext.prevMouseY = event.getY();
            circleContext.prevX = circle.getCenterX();
            circleContext.prevY = circle.getCenterY();
            labelContext.prevX = label.getLayoutX();
            labelContext.prevY = label.getLayoutY();
            circle.fireEvent(new VertexEvent(parentVertex.getVertex(), VertexEvent.VERTEX_PRESSED));

        }
    }

    private class CircleDraggedEventHandler implements EventHandler<MouseEvent> {

        VertexView parentVertex;

        public CircleDraggedEventHandler(VertexView pVertex) {
            this.parentVertex = pVertex;

        }

        @Override
        public void handle(MouseEvent event) {
            if(parentNode.getOperationType() == DrawSpace.OperationType.DEFAULT) {
                double newX = circleContext.prevX - circleContext.prevMouseX + event.getX();
                double newY = circleContext.prevY - circleContext.prevMouseY + event.getY();
                double newLabelX = labelContext.prevX - circleContext.prevMouseX + event.getX();
                double newLabelY = labelContext.prevY - circleContext.prevMouseY + event.getY();
                if (newX + circle.getLayoutX() - radius > 0 && newY + circle.getLayoutY() - radius > 0) {
                        if(newX + circle.getLayoutX() + radius > parentNode.getPrefWidth()) {
                            parentNode.setPrefWidth(parentNode.getPrefWidth() + 100);
                        }
                        if(newY + circle.getLayoutY() + radius > parentNode.getPrefHeight()) {
                            parentNode.setPrefHeight(parentNode.getPrefHeight() + 100);
                        }

                    circle.setCenterX(newX);
                    circle.setCenterY(newY);
                    label.setLayoutX(newLabelX);
                    label.setLayoutY(newLabelY);
                    circle.fireEvent(new VertexEvent(parentVertex.getVertex(), VertexEvent.VERTEX_RELOCATED));
                }
            }
        }
    }

    public class CircleSecondaryPressedHandler implements EventHandler<MouseEvent> {

        VertexView parentVertex;

        public CircleSecondaryPressedHandler(VertexView pVertex) {
            this.parentVertex = pVertex;
        }

        @Override
        public void handle(MouseEvent event) {
            if(event.getButton() == MouseButton.SECONDARY) {
                circle.fireEvent(new VertexEvent(parentVertex.getVertex(), VertexEvent.VERTEX_SPRESSED));
            }
        }

    }

    private class LabelPressedEventHandler implements EventHandler<MouseEvent> {

        @Override
        public void handle(MouseEvent event) {
            labelContext.prevMouseX = event.getX();
            labelContext.prevMouseY = event.getY();
            labelContext.prevX = label.getTranslateX();
            labelContext.prevY = label.getTranslateY();

        }
    }

    private class LabelDraggedEventHandler implements EventHandler<MouseEvent> {

        @Override
        public void handle(MouseEvent event) {
            double newX = labelContext.prevX - labelContext.prevMouseX + event.getX();
            double newY = labelContext.prevY - labelContext.prevMouseY + event.getY();

            if (newX + label.getLayoutX() > 0 && newY + label.getLayoutY() > 0
                    && newX + label.getLayoutX() < parentNode.getWidth()
                    && newY + label.getLayoutY() < parentNode.getHeight()) {

                label.setTranslateX(newX);
                label.setTranslateY(newY);

            }
        }
    }

    private final class DragContext {

        public double prevX;
        public double prevY;
        public double prevMouseX;
        public double prevMouseY;
    }

}
