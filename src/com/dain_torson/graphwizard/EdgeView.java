package com.dain_torson.graphwizard;

import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Polygon;

public class EdgeView implements ElementView{

    private static double width = 8;

    private VertexView firstVertex;
    private VertexView secondVertex;
    private Polygon polygon = new Polygon();
    private Label label;
    private String name;
    private DrawSpace parentNode;
    private Edge edge;
    private boolean isActive = false;
    private boolean isExceptional = false;
    private boolean isMarked = false;

    public EdgeView(VertexView start, VertexView end, DrawSpace pNode, final Edge edge) {
        this.firstVertex = start;
        this.secondVertex = end;
        this.edge = edge;
        this.parentNode = pNode;
        this.name = String.valueOf(edge.getValue());
        this.label = new Label(name);
        firstVertex.getCircle().addEventFilter(VertexEvent.VERTEX_RELOCATED, new CircleRelocHandler());
        secondVertex.getCircle().addEventFilter(VertexEvent.VERTEX_RELOCATED, new CircleRelocHandler());

        relocator(null);
        this.polygon.getStyleClass().add("edgeInactive");
        polygon.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                setActivity(true);
                if(event.getButton() == MouseButton.SECONDARY) {
                    polygon.fireEvent(new EdgeEvent(edge, EdgeEvent.EDGE_SPRESSED));
                }
            }
        });

    }

    public void draw() {
        parentNode.getChildren().addAll(polygon, label);
    }

    private double getHorisMargin(double x1, double x2, double y1, double y2) {

        double sin = (x2 - x1)/(Math.sqrt(Math.pow((x2 - x1), 2) + Math.pow((y2 - y1), 2)));
        return 1.5 * VertexView.getRadius() * sin;
    }

    private double getVertMargin(double x1, double x2, double y1, double y2) {

        double cos = (y2 - y1)/(Math.sqrt(Math.pow((x2 - x1), 2) + Math.pow((y2 - y1), 2)));
        return 1.5 * VertexView.getRadius() * cos;
    }

    private double getSin(double x1, double x2, double y1, double y2) {
        return (x2 - x1)/(Math.sqrt(Math.pow((x2 - x1), 2) + Math.pow((y2 - y1), 2)));
    }

    private double getCos(double x1, double x2, double y1, double y2) {
        return (y2 - y1)/(Math.sqrt(Math.pow((x2 - x1), 2) + Math.pow((y2 - y1), 2)));

    }

    private void relocator(VertexEvent event) {

        double x1 = firstVertex.getX();
        double x2 = secondVertex.getX();
        double y1 = firstVertex.getY();
        double y2 = secondVertex.getY();

        double newx1, newx2, newy1, newy2;
        double point1x, point2x, point3x, point4x, point5x;
        double point1y, point2y, point3y, point4y, point5y;

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

        point1x = newx1 + (width / 2) * getCos(x1, x2, y1, y2);
        point1y = newy1 - (width / 2) * getSin(x1, x2, y1, y2);
        point2x = newx1 - (width / 2) * getCos(x1, x2, y1, y2);
        point2y = newy1 + (width / 2) * getSin(x1, x2, y1, y2);
        point3x = newx2 + (width / 2) * getCos(x2, x1, y2, y1);
        point3y = newy2 - (width / 2) * getSin(x2, x1, y2, y1);
        point4x = newx2;
        point4y = newy2;
        point5x = newx2 - (width / 2) * getCos(x2, x1, y2, y1);
        point5y = newy2 + (width / 2) * getSin(x2, x1, y2, y1);

        if(edge.isOriented()) {
            point3x += 1.5 * width * getSin(x2, x1, y2, y1);
            point3y += 1.5 * width * getCos(x2, x1, y2, y1);
            point5x += 1.5 * width * getSin(x2, x1, y2, y1);
            point5y += 1.5 * width * getCos(x2, x1, y2, y1);
        }

        polygon.getPoints().clear();
        polygon.getPoints().addAll(new Double [] {
                point1x, point1y,
                point2x, point2y,
                point3x, point3y,
                point4x, point4y,
                point5x, point5y});

        double labelX = newx1 + (newx2 - newx1) / 2;
        double labelY = newy1 + (newy2 - newy1) / 2;

        label.setLayoutX(labelX);
        label.setLayoutY(labelY);

    }

    public Edge getEdge() {
        return edge;
    }

    public Polygon getPolygon() {
        return polygon;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
        label.setText(name);
    }

    public void resetName() {
        name = String.valueOf(edge.getValue());
        label.setText(name);
    }

    public boolean getActivity() {
        return  isActive;
    }

    public void  setActivity(boolean value) {
        this.isActive = value;
        if(this.isActive) {
            this.polygon.getStyleClass().clear();
            this.polygon.getStyleClass().add("edgeActive");
        } else {
            this.polygon.getStyleClass().clear();
            if(isMarked) {
                this.polygon.getStyleClass().add("edgeMarked");
            }
            else {
                this.polygon.getStyleClass().add("edgeInactive");
            }
        }
    }

    public void setExceptional(boolean value) {
        isExceptional = value;
        if(value) {
            this.polygon.getStyleClass().clear();
            this.polygon.getStyleClass().add("edgeExceptional");
        }
        else {
            setActivity(false);
        }
    }

    public void mark(){
        isMarked = true;
        this.polygon.getStyleClass().clear();
        this.polygon.getStyleClass().add("edgeMarked");

    }

    public void unmark(){
        isMarked = false;
        this.polygon.getStyleClass().clear();
        this.polygon.getStyleClass().add("edgeInactive");
    }

    public boolean isExceptional() {
        return isExceptional;
    }

    public void delete() {
        polygon.fireEvent(new EdgeEvent(this.getEdge(), EdgeEvent.EDGE_DELETED));
        this.parentNode.getChildren().removeAll(polygon, label);
    }

    public void update(){
        relocator(null);
        this.label.setText(String.valueOf(edge.getValue()));
    }

    private class CircleRelocHandler implements EventHandler<VertexEvent> {

        @Override
        public void handle(VertexEvent event) {
           relocator(event);
        }
    }
}
