package com.dain_torson.graphwizard;


/**
 * Created by asus on 06.03.2015.
 */
public class Edge {

    private Vertex firstVertex;
    private Vertex secondVertex;
    private int value = 1;
    private EdgeView view;
    private boolean oriented = false;

    public Edge(Vertex start, Vertex end, boolean isOriented) {
        this.firstVertex = start;
        this.secondVertex = end;
        this.oriented = isOriented;
    }

    public Edge(Vertex start, Vertex end, int value, boolean isOriented) {
        this.firstVertex = start;
        this.secondVertex = end;
        this.value = value;
        this.oriented = isOriented;
    }

    public Edge(Vertex start, Vertex end, EdgeView view, boolean isOriented) {
        this.firstVertex = start;
        this.secondVertex = end;
        this.view = view;
        this.oriented = isOriented;
    }

    public Edge(Vertex start, Vertex end, EdgeView view, int value, boolean isOriented) {
        this.firstVertex = start;
        this.secondVertex = end;
        this.view = view;
        this.value = value;
        this.oriented = isOriented;
    }

    public int getValue() {
        return this.value;
    }

    public void setValue(int value) {
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

    public boolean isConnects(Vertex start, Vertex end) {
        return ((start == firstVertex && end == secondVertex) ||
                (start == secondVertex && end == firstVertex));
    }

    public boolean isExists(Vertex start, Vertex end) {
        return (start == firstVertex && end == secondVertex);
    }

    public boolean isOriented() {
        return oriented;
    }

    public void setDefaultType() {
        oriented = false;
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
