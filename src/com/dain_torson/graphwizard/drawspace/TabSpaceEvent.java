package com.dain_torson.graphwizard.drawspace;

import javafx.event.Event;
import javafx.event.EventType;

/**
 * Created by Ares on 22.04.2015.
 */
public class TabSpaceEvent extends Event {

    public static EventType<TabSpaceEvent> TAB_SELECTION_CHANGED = new EventType("TAB_SELECTION_CHANGED");
    private DrawTab source;

    public TabSpaceEvent(EventType<TabSpaceEvent>  tabSpaceEventType, DrawTab drawTab) {
        super(tabSpaceEventType);
        this.source = drawTab;
    }

    @Override
    public DrawTab getSource() {
        return source;
    }

    public void setSource(DrawTab source) {
        this.source = source;
    }
}
