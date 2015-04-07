package com.dain_torson.graphwizard;



import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Graph {

    private List<Vertex> vertexes = new ArrayList<Vertex>();
    private List<Edge> edges = new ArrayList<Edge>();

    public Graph() {

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

    public void reset(int adjacencyMatrix [][], String values [], int size) {

        this.clear();

        for(int vertIdx = 0; vertIdx < size; ++vertIdx) {
            String value = values[vertIdx];
            Vertex temp = new Vertex(value);
            vertexes.add(temp);
        }

        for(int row = 0; row < size; ++row) {
            for(int col = 0; col < size; ++col) {
                if(adjacencyMatrix[row][col] > 0) {
                    boolean isEdgeConnects = false;
                    for(Edge edge : edges) {
                        if(edge.isConnects(vertexes.get(row), vertexes.get(col))) {
                            if(!edge.isExists(vertexes.get(row), vertexes.get(col))) {
                                edge.setDefaultType();
                            }
                            isEdgeConnects = true;
                        }
                    }
                    if(!isEdgeConnects ) {
                        Edge temp = new Edge(vertexes.get(row), vertexes.get(col),
                                adjacencyMatrix[row][col], true);
                        edges.add(temp);
                    }
                }
            }
        }
    }

    public int [][] getAdjacencyMatrix() {
        int size = vertexes.size();
        int matrix [][] = new int [size][size];

        for(int row = 0; row < size; ++row ) {
            for(int col = 0; col < size; ++col) {
                Vertex first = vertexes.get(row);
                Vertex second = vertexes.get(col);

                for(Edge edge : edges) {
                    if(edge.isOriented()) {
                        if (edge.isExists(first, second)) {
                            matrix[row][col] = edge.getValue();
                            break;
                        }
                    }
                    else {
                        if (edge.isConnects(first, second)) {
                            matrix[row][col] = edge.getValue();
                            break;
                        }
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

    public Edge isExists(Vertex start, Vertex end) {
        for (Edge edge : edges) {
            if(edge.isExists(start, end)) {
                return edge;
            }
        }

        return null;
    }

    public Edge isConnects(Vertex start, Vertex end) {
        for (Edge edge : edges) {
            if(edge.isConnects(start, end)) {
                return edge;
            }
        }

        return null;
    }

    public Integer [] findAllDistances(int vertexIdx) {

        Integer distances [] = new Integer[getNumOfVertices()];
        boolean visited [] = new boolean[getNumOfVertices()];
        int matrix [][] = getAdjacencyMatrix();

        for(int idx = 0; idx < getNumOfVertices(); ++idx) {
            distances[idx] = Integer.MAX_VALUE;

            try {
                vertexes.get(idx).getView().setActivity(false);
                vertexes.get(idx).getView().setName("INF");
            }
            catch (NullPointerException exception) {
                System.out.println("No view avalible");
            }
        }

        try {
            vertexes.get(vertexIdx).getView().setExceptional();
            vertexes.get(vertexIdx).getView().setName("0");
        }
        catch (NullPointerException exception) {
            System.out.println("No view avalible");
        }

        distances[vertexIdx] = 0;
        visited[vertexIdx] = true;

        LinkedList<Integer> list = new LinkedList<Integer>();
        list.add(vertexIdx);

        while (list.size() > 0) {
            int vertex = list.poll();
            for(int nextVertex = 0; nextVertex < getNumOfVertices(); ++nextVertex) {
                if(matrix[vertex][nextVertex] > 0) {
                    /*try {
                        Edge current = isConnects(vertexes.get(vertex), vertexes.get(nextVertex));
                        current.getView().setActivity(true);
                        try {
                            Thread.sleep(1000);
                        } catch(InterruptedException exception) {
                            Thread.currentThread().interrupt();
                        }
                        current.getView().setActivity(false);
                    }
                    catch (NullPointerException exception) {
                        System.out.println("No view avalible");
                    }
*/
                    int newDistance = distances[vertex] + matrix[vertex][nextVertex];
                    if (newDistance < distances[nextVertex]) {
                        distances[nextVertex] = newDistance;
                        try {
                            vertexes.get(nextVertex).getView().setName(String.valueOf(newDistance));
                        }
                        catch (NullPointerException exception) {
                            System.out.println("No view avalible");
                        }
                    }
                    if(!visited[nextVertex]) {
                        list.add(nextVertex);
                        visited[nextVertex] = true;
                        try {
                            Vertex temp = vertexes.get(nextVertex);
                            temp.getView().setActivity(true);
                            temp.getView().setActivity(false);
                        }
                        catch (NullPointerException exception) {
                            System.out.println("No view avalible");
                        }
                    }
                }
            }
        }

        return distances;
    }

    public Integer findEccentricity(int vertexIdx) {

        Integer distances [] = findAllDistances(vertexIdx);

        Integer eccentricity = 0;

        for(Integer distance : distances) {
            if(distance > eccentricity) {
                eccentricity = distance;
            }
        }

        return eccentricity;
    }

    public List<Integer> findGraphCenter() {
        Integer eccentricities [] = new Integer[getNumOfVertices()];
        List<Integer> centralVerticesIdxs = new ArrayList<Integer>();
        Integer radius = Integer.MAX_VALUE;

        for(int vertexIdx = 0; vertexIdx < getNumOfVertices(); ++vertexIdx) {
            eccentricities[vertexIdx] = findEccentricity(vertexIdx);
            if(eccentricities[vertexIdx] < radius) {
                radius = eccentricities[vertexIdx];
            }
        }

        for(int vertexIdx = 0; vertexIdx < getNumOfVertices(); ++vertexIdx) {
            if(eccentricities[vertexIdx] == radius) {
                centralVerticesIdxs.add(vertexIdx);
            }
        }

        return centralVerticesIdxs;
    }
}
