package com.dain_torson.graphwizard;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by asus on 17.03.2015.
 */
public class Graph {

    public List<Vertex> vertexes = new ArrayList<Vertex>();
    private List<Edge> edges = new ArrayList<Edge>();

    public Graph() {

    }

    public Graph(int adjacencyMatrix [][], String values [], int size) {

        for(int vertIdx = 0; vertIdx < size; ++vertIdx) {
            String value = values[vertIdx];
            Vertex temp = new Vertex(value);
            vertexes.add(temp);
        }

        for(int row = 0; row < size; ++row) {
            for(int col = 0; col < size; ++col) {
                if(adjacencyMatrix[row][col] > 0) {
                    boolean isEdgeExists = false;
                    for(Edge edge : edges) {
                        if(edge.isConnects(vertexes.get(row), vertexes.get(col))) {
                            isEdgeExists = true;
                        }
                    }
                    if(!isEdgeExists) {
                        Edge temp = new Edge(vertexes.get(row), vertexes.get(col),
                                adjacencyMatrix[row][col]);
                        edges.add(temp);
                    }
                }
            }
        }

        System.out.print(vertexes.size());
        System.out.print(edges.size());
    }

    public void addVertex(Vertex vertex) {
        vertexes.add(vertex);
    }

    public void deleteVertex(Vertex vertex) {
        if(vertexes.contains(vertex)) {
            List<Edge> edgesToDelete = new ArrayList<Edge>();
            for(Edge edge : edges) {
                if(edge.getFirstVertex() == vertex || edge.getSecondVertex() == vertex) {
                    edgesToDelete.add(edge);
                }
            }

            vertex.delete();
            vertexes.remove(vertex);

            for(Edge edge : edgesToDelete) {
                deleteEdge(edge);
            }

            System.out.println("Edges " + String.valueOf(edges.size()));


        }
    }

    public void deleteEdge(Edge edge) {
        if(edges.contains(edge)) {
            edge.delete();
            edges.remove(edges.indexOf(edge));
        }

    }

    public void addEdge(Edge edge) {
        edges.add(edge);
    }

    public int getNumOfVertices() {
        return vertexes.size();
    }

    public int getNumOfEdges() {
        return edges.size();
    }

    public Vertex getVertex(int index) {
        return vertexes.get(index);
    }

    public Edge getEdge(int index) {
        return edges.get(index);
    }

    public void clear() {
        for(Vertex vertex : vertexes) {
            vertex.delete();
        }

        for(Edge edge : edges) {
            edge.delete();
        }

        vertexes.clear();
        edges.clear();
    }

    public int [][] getAdjacencyMatrix() {
        int size = vertexes.size();
        int matrix [][] = new int [size][size];

        for(int row = 0; row < size; ++row ) {
            for(int col = 0; col < size; ++col) {
                Vertex first = vertexes.get(row);
                Vertex second = vertexes.get(col);

                for(Edge edge : edges) {
                    if((edge.getFirstVertex() == first && edge.getSecondVertex() == second) ||
                            (edge.getFirstVertex() == second && edge.getSecondVertex() == first)) {
                        matrix[row][col] = edge.getValue();
                        break;
                    }
                }
            }
        }
        return matrix;
    }

    public double [][] getCoordinates() {
        int size = vertexes.size();
        double coordinates [][] = new double[size][2];

        for(int vertIdx = 0; vertIdx < size; ++ vertIdx) {
            coordinates[vertIdx][0] = vertexes.get(vertIdx).getView().getX();
            coordinates[vertIdx][1] = vertexes.get(vertIdx).getView().getY();
        }
        return coordinates;
    }

    public String [] getVerticesValues() {
        int size = vertexes.size();
        String values [] = new String[size];

        for(int vertIdx = 0; vertIdx < size; ++ vertIdx) {
            values[vertIdx] = vertexes.get(vertIdx).getValue();
        }
        return values;
    }
}
