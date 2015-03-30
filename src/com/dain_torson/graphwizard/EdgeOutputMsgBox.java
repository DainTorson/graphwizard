package com.dain_torson.graphwizard;

/**
 * Created by Ales on 30.03.2015.
 */
public class EdgeOutputMsgBox extends OutputMsgBox {

    private Edge source;

    public EdgeOutputMsgBox(Edge edge) {
        this.source = edge;
        this.getLabel().setText("Edge weight is: " + String.valueOf(source.getValue()));
    }
}
