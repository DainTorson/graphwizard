package com.dain_torson.graphwizard.drawspace;

import javafx.event.Event;
import javafx.event.EventType;

public class DrawSpaceEvent extends Event{

    public static EventType<DrawSpaceEvent> ESC_PRESSED = new EventType("ESC_PRESSED");

    public DrawSpaceEvent(EventType<DrawSpaceEvent>  drawSpaceEventType) {
        super(drawSpaceEventType);
    }
}
