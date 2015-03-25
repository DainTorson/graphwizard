package com.dain_torson.graphwizard;

/**
 * Created by Ales on 22.03.2015.
 */
public class Vertex {

    private String value = "";
    private VertexView view;

    public Vertex() {

    }

    public Vertex(String value) {
        this.value = value;
    }

    public Vertex(VertexView vertexView) {
        this.view = vertexView;
    }

    public Vertex(VertexView vertexView, String value) {
        this.view = vertexView;
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public VertexView getView() {
        return this.view;
    }

    public void setView(VertexView view) {
        this.view = view;
    }

    public void draw() {
        this.view.draw();
    }

    public void delete() {
        this.view.delete();
        //this.view = null;
    }
}
