package com.dain_torson.graphwizard.graph.elements.events;

import com.dain_torson.graphwizard.graph.elements.Edge;
import javafx.event.Event;
import javafx.event.EventType;

public class EdgeEvent extends Event {

    private Edge targetEdge;
    public static EventType<EdgeEvent> EDGE_SPRESSED = new EventType("EDGE_SPRESSED");
    public static EventType<EdgeEvent> EDGE_DELETED = new EventType("EDGE_DELETED");

    public EdgeEvent(Edge target, EventType<EdgeEvent> edgeEventType) {
        super(edgeEventType);
        this.targetEdge = target;
    }

    public Edge getTargetEdge() { return targetEdge; }
}
