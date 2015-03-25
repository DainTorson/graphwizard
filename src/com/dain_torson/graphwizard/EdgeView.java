package com.dain_torson.graphwizard;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;

/**
 * Created by Ales on 22.03.2015.
 */
public class EdgeView {

    private VertexView firstVertex;
    private VertexView secondVertex;
    private Line line;
    private DrawSpace parentNode;
    private Edge edge;
    private boolean isActive = false;

    public EdgeView(VertexView start, VertexView end, DrawSpace pNode, Edge edge) {
        this.firstVertex = start;
        this.secondVertex = end;
        this.edge = edge;
        this.parentNode = pNode;
        firstVertex.getCircle().addEventFilter(VertexEvent.VERTEX_RELOCATED, new CircleRelocHandler());
        secondVertex.getCircle().addEventFilter(VertexEvent.VERTEX_RELOCATED, new CircleRelocHandler());

        line = new Line(0, 0, 0, 0);
        relocator(null);
        line.getStyleClass().add("edgeInactive");

        line.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                setActivity(true);
            }
        });
    }

    public void draw() {
        parentNode.getChildren().add(line);
    }

    private double getHorisMargin(double x1, double x2, double y1, double y2) {

        double sin = (x2 - x1)/(Math.sqrt(Math.pow((x2 - x1), 2) + Math.pow((y2 - y1), 2)));
        return 1.5 * VertexView.getRadius() * sin;
    }

    private double getVertMargin(double x1, double x2, double y1, double y2) {

        double cos = (y2 - y1)/(Math.sqrt(Math.pow((x2 - x1), 2) + Math.pow((y2 - y1), 2)));
        return 1.5 * VertexView.getRadius() * cos;
    }

    private void relocator(VertexEvent event) {

        double x1 = firstVertex.getX();
        double x2 = secondVertex.getX();
        double y1 = firstVertex.getY();
        double y2 = secondVertex.getY();

        double newx1, newx2, newy1, newy2;

        if(event != null) {

            if (event.getTargetVx() == firstVertex.getVertex()) {
                x1 = event.getXCoord();
                y1 = event.getYCoord();
            }
            else if(event.getTargetVx() == secondVertex.getVertex()) {
                x2 = event.getXCoord();
                y2 = event.getYCoord();
            }
        }

        newx1 = x1 + getHorisMargin(x1, x2, y1, y2);
        newy1 = y1 + getVertMargin(x1, x2, y1, y2);
        newx2 = x2 + getHorisMargin(x2, x1, y2, y1);
        newy2 = y2 + getVertMargin(x2, x1, y2, y1);

        line.setStartX(newx1);
        line.setStartY(newy1);
        line.setEndX(newx2);
        line.setEndY(newy2);

    }

    public Edge getEdge() {
        return edge;
    }

    public Line getLine() {
        return line;
    }

    public boolean getActivity() {
        return  isActive;
    }

    public void  setActivity(boolean value) {
        this.isActive = value;
        if(this.isActive) {
            this.line.getStyleClass().clear();
            this.line.getStyleClass().add("edgeActive");
        }
        else {
            this.line.getStyleClass().clear();
            this.line.getStyleClass().add("edgeInactive");
        }
    }

    public void delete() {
        line.fireEvent(new EdgeEvent(this.getEdge(), EdgeEvent.EDGE_DELETED));
        this.parentNode.getChildren().remove(line);
    }

    private class CircleRelocHandler implements EventHandler<VertexEvent> {

        @Override
        public void handle(VertexEvent event) {
           relocator(event);
        }
    }
}
