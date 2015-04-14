package com.dain_torson.graphwizard.graph.elements;

import com.dain_torson.graphwizard.graph.elements.views.ElementView;

public interface Element {

    public void draw();
    public void delete();
    ElementView getView();

}
