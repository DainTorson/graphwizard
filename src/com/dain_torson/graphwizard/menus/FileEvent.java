package com.dain_torson.graphwizard.menus;

import com.dain_torson.graphwizard.drawspace.DrawTab;
import javafx.event.Event;
import javafx.event.EventType;

import java.io.File;

public class FileEvent extends Event {

    public static EventType<FileEvent> FILE_SAVED = new EventType("FILE_SAVED");
    public static EventType<FileEvent> FILE_OPENED = new EventType("FILE_OPENED");
    private File source;

    public FileEvent(EventType<FileEvent>  tabSpaceEventType, File file) {
        super(tabSpaceEventType);
        this.source = file;
    }

    @Override
    public File getSource() {
        return source;
    }

    public void setSource(File source) {
        this.source = source;
    }
}
