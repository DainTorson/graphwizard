package com.dain_torson.graphwizard;


/**
 * Created by asus on 06.03.2015.
 */
public class Edge {

    private Vertex firstVertex;
    private Vertex secondVertex;
    private String value = "";
    private EdgeView view;

    public Edge(Vertex start, Vertex end) {
        this.firstVertex = start;
        this.secondVertex = end;
    }

    public Edge(Vertex start, Vertex end, String value) {
        this.firstVertex = start;
        this.secondVertex = end;
        this.value = value;
    }

    public Edge(Vertex start, Vertex end, EdgeView view) {
        this.firstVertex = start;
        this.secondVertex = end;
        this.view = view;
    }

    public Edge(Vertex start, Vertex end, EdgeView view, String value) {
        this.firstVertex = start;
        this.secondVertex = end;
        this.view = view;
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public EdgeView getView() {
        return this.view;
    }

    public void setView(EdgeView view) {
        this.view = view;
    }

    public Vertex getFirstVertex() {
        return firstVertex;
    }

    public Vertex getSecondVertex() {
        return secondVertex;
    }

    public void delete() {
        this.view.delete();
    }

    public void draw() {
        try {
            this.view.draw();
        }
        catch (NullPointerException except) {
            System.out.println("Can't draw edge with value: " + this.value + ". EdgeView instance is needed.");
        }
    }
}
