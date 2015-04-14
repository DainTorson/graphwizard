package com.dain_torson.graphwizard.graph.elements.events;

import com.dain_torson.graphwizard.graph.elements.Vertex;
import javafx.event.Event;
import javafx.event.EventType;


public class VertexEvent extends Event {

    private Vertex targetVx;
    public static EventType<VertexEvent> VERTEX_RELOCATED = new EventType("VERTEX_RELOCATED");
    public static EventType<VertexEvent> VERTEX_PRESSED = new EventType("VERTEX_PRESSED");
    public static EventType<VertexEvent> VERTEX_SPRESSED = new EventType("VERTEX_SPRESSED");
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
