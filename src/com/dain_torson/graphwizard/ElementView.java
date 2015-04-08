package com.dain_torson.graphwizard;

public interface ElementView {

    public String getName();
    public void setName(String name);
    public void resetName();
    public void setActivity(boolean value);
    public void setExceptional();
    public void draw();
    public void delete();
}
