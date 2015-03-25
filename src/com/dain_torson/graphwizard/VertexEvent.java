package com.dain_torson.graphwizard;

import javafx.event.Event;
import javafx.event.EventType;

/**
 * Created by asus on 12.03.2015.
 */
public class VertexEvent extends Event {

    private Vertex targetVx;
    public static EventType<VertexEvent> VERTEX_RELOCATED = new EventType("VERTEX_RELOCATED");
    public static EventType<VertexEvent> VERTEX_PRESSED = new EventType("VERTEX_PRESSED");
    public static EventType<VertexEvent> VERTEX_DELETED = new EventType("VERTEX_DELETED");

    public VertexEvent(Vertex target, EventType<VertexEvent> vertexEventType) {
        super(vertexEventType);
        this.targetVx = target;
    }

    public double getXCoord() {
        return targetVx.getView().getX();
    }

    public double getYCoord() { return targetVx.getView().getY(); }

    public Vertex getTargetVx() { return targetVx; }
}
