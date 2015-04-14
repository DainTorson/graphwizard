package com.dain_torson.graphwizard.graph.elements.views;

public interface ElementView {

    public String getName();
    public void setName(String name);
    public void resetName();
    public void setActivity(boolean value);
    public void setExceptional(boolean value);
    public void draw();
    public void delete();
    public void mark();
    public void unmark();
    boolean isExceptional();
}
