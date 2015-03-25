package com.dain_torson.graphwizard;

import javafx.event.Event;
import javafx.event.EventType;

/**
 * Created by Ales on 24.03.2015.
 */
public class EdgeEvent extends Event {

    private Edge targetEdge;
    public static EventType<EdgeEvent> EDGE_PRESSED = new EventType("EDGE_PRESSED");
    public static EventType<EdgeEvent> EDGE_DELETED = new EventType("EDGE_DELETED");

    public EdgeEvent(Edge target, EventType<EdgeEvent> edgeEventType) {
        super(edgeEventType);
        this.targetEdge = target;
    }

    public Edge getTargetEdge() { return targetEdge; }
}
