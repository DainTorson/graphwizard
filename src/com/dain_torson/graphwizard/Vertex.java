package com.dain_torson.graphwizard;

public class Vertex implements Element{

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
        this.getView().setName(value);
    }

    public VertexView getView() {
        return this.view;
    }

    public void setView(VertexView view) {
        this.view = view;
    }

    public void draw() {
        try {
            view.draw();
        }
        catch (NullPointerException except) {
            System.out.println("Can't draw vertex with value: " + this.value + ". VertexView instance is needed.");
        }
    }

    public void delete() {
        this.view.delete();
    }
}
