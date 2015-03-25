package com.dain_torson.graphwizard;

import javafx.event.Event;
import javafx.event.EventType;

/**
 * Created by asus on 12.03.2015.
 */
public class MyEvent extends Event {

    private double xCoord;
    private double yCoord;
    public static EventType<MyEvent> COORD_CHANGED = new EventType("COORD_CHANGED");

    public MyEvent(double x, double y) {
        super(COORD_CHANGED);
        this.xCoord = x;
        this.yCoord = y;
    }

    public double getxCoord() {
        return  xCoord;
    }

    public double getyCoord() {
        return yCoord;
    }
}
