package com.dain_torson.graphwizard.graph;


import com.dain_torson.graphwizard.drawspace.DrawSpace;
import com.dain_torson.graphwizard.drawspace.DrawSpaceEvent;
import com.dain_torson.graphwizard.graph.elements.Edge;
import com.dain_torson.graphwizard.graph.elements.Element;
import com.dain_torson.graphwizard.graph.elements.Vertex;
import com.dain_torson.graphwizard.msgboxes.OutputMsgBox;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Graph {

    private List<Vertex> vertexes = new ArrayList<Vertex>();
    private List<Edge> edges = new ArrayList<Edge>();
    private LinkedList<AlgorithmStep> steps = new LinkedList<AlgorithmStep>();
    private Timeline timeline = new Timeline();
    private DrawSpace drawSpace;
    private String name = "NewGraph";

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

    public List<Vertex> getVertexes() {
        return vertexes;
    }

    public void setVertexes(List<Vertex> vertexes) {
        this.vertexes = vertexes;
    }

    public void setEdges(List<Edge> edges) {
        this.edges = edges;
    }

    public List<Edge> getEdges() {
        return edges;
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

    public Integer [] findAllDistances(int vertexIdx, boolean stepsRecording) {

        Integer distances [] = new Integer[getNumOfVertices()];
        boolean visited [] = new boolean[getNumOfVertices()];
        int matrix [][] = getAdjacencyMatrix();

        for(int idx = 0; idx < getNumOfVertices(); ++idx) {
            distances[idx] = Integer.MAX_VALUE;
        }

        distances[vertexIdx] = 0;
        visited[vertexIdx] = true;
        if(stepsRecording) {
            steps.add(new AlgorithmStep(AlgorithmCommand.SET_ALL_INF));
            steps.add(new AlgorithmStep(vertexes.get(vertexIdx), String.valueOf(0), AlgorithmCommand.MAKE_SPECIAL));
        }

        LinkedList<Integer> list = new LinkedList<Integer>();
        list.add(vertexIdx);

        while (list.size() > 0) {
            int vertex = list.poll();
            if(stepsRecording) {
                steps.add(new AlgorithmStep(vertexes.get(vertex)));
            }
            for(int nextVertex = 0; nextVertex < getNumOfVertices(); ++nextVertex) {
                if(matrix[vertex][nextVertex] > 0) {
                    int newDistance = distances[vertex] + matrix[vertex][nextVertex];
                    if(stepsRecording) {
                        Edge edge = isConnects(vertexes.get(vertex), vertexes.get(nextVertex));
                        steps.add(new AlgorithmStep(edge));
                    }
                    if (newDistance < distances[nextVertex]) {
                        distances[nextVertex] = newDistance;
                        if(stepsRecording) {
                            steps.add(new AlgorithmStep(vertexes.get(nextVertex), String.valueOf(newDistance),
                                    AlgorithmCommand.DO_NOT_VISIT));
                        }
                    }
                    if(!visited[nextVertex]) {
                        list.add(nextVertex);
                        visited[nextVertex] = true;
                        if(stepsRecording) {
                            steps.add(new AlgorithmStep(vertexes.get(nextVertex), AlgorithmCommand.MARK));
                        }
                    }
                }
            }

            if(stepsRecording) {
                steps.add(new AlgorithmStep(vertexes.get(vertex), AlgorithmCommand.INACTIVATE));
            }
        }

        if(stepsRecording) {
            steps.add(new AlgorithmStep(AlgorithmCommand.RESET_ALL));
        }

        return distances;
    }

    public Integer findEccentricity(int vertexIdx, boolean stepsRecording) {

        Integer distances [] = findAllDistances(vertexIdx, stepsRecording);
        Integer eccentricity = 0;

        for(Integer distance : distances) {
            if(distance > eccentricity) {
                eccentricity = distance;
            }
        }

        return eccentricity;
    }

    public int findRadius(boolean stepsRecording) {

        Integer eccentricities [] = new Integer[getNumOfVertices()];
        Integer radius = Integer.MAX_VALUE;

        for(int vertexIdx = 0; vertexIdx < getNumOfVertices(); ++vertexIdx) {
            eccentricities[vertexIdx] = findEccentricity(vertexIdx, stepsRecording);
            if(eccentricities[vertexIdx] < radius) {
                radius = eccentricities[vertexIdx];
            }
        }

        if(stepsRecording) {
            String outputMsg = "Graph radius is: " + String.valueOf(radius);
            steps.add(new AlgorithmStep(AlgorithmCommand.RESET_DRAWSPACE, outputMsg));
        }

        return radius;
    }

    public int findDiameter(boolean stepsRecording) {

        Integer eccentricities [] = new Integer[getNumOfVertices()];
        Integer diameter = 0;

        for(int vertexIdx = 0; vertexIdx < getNumOfVertices(); ++vertexIdx) {
            eccentricities[vertexIdx] = findEccentricity(vertexIdx, stepsRecording);
            if(eccentricities[vertexIdx] > diameter) {
                diameter = eccentricities[vertexIdx];
            }
        }

        if(stepsRecording) {
            String outputMsg = "Graph diameter is: " + String.valueOf(diameter);
            steps.add(new AlgorithmStep(AlgorithmCommand.RESET_DRAWSPACE, outputMsg));
        }

        return diameter;
    }

    public List<Integer> findGraphCenter(boolean stepsRecording) {

        Integer eccentricities [] = new Integer[getNumOfVertices()];
        List<Integer> centralVerticesIdxs = new ArrayList<Integer>();
        Integer radius = Integer.MAX_VALUE;

        for(int vertexIdx = 0; vertexIdx < getNumOfVertices(); ++vertexIdx) {
            eccentricities[vertexIdx] = findEccentricity(vertexIdx, stepsRecording);
            if(eccentricities[vertexIdx] < radius) {
                radius = eccentricities[vertexIdx];
            }
        }

        for(int vertexIdx = 0; vertexIdx < getNumOfVertices(); ++vertexIdx) {
            if(eccentricities[vertexIdx] == radius) {
                centralVerticesIdxs.add(vertexIdx);
                if(stepsRecording) {
                    steps.add(new AlgorithmStep(vertexes.get(vertexIdx), AlgorithmCommand.MAKE_SPECIAL));
                }
            }
        }

        if(stepsRecording) {
            String outputMsg = "Graph center is: ";
            for (Integer idx : centralVerticesIdxs) {
                outputMsg += String.valueOf(idx) + " ";
            }

            steps.add(new AlgorithmStep(AlgorithmCommand.RESET_DRAWSPACE, outputMsg));
        }

        return centralVerticesIdxs;
    }

    public int findRadiusVisualised(int speed) {

        steps.clear();
        int radius = findRadius(true);
        visualiseAlgorithm(speed);
        return radius;

    }

    public int findDiameterVisualised(int speed) {

        steps.clear();
        int diameter = findDiameter(true);
        visualiseAlgorithm(speed);
        return diameter;

    }

    public List<Integer> findGraphCenterVisualised(int speed) {

        steps.clear();
        List<Integer> center = findGraphCenter(true);
        visualiseAlgorithm(speed);
        return center;

    }

    public void visualiseAlgorithm(int speed) {

        int additionalTime = 100*(11 - speed);
        int delay = additionalTime;

        try {
            drawSpace.setOperationType(DrawSpace.OperationType.IMMUTABLE);
        }catch (NullPointerException exception) {
            System.out.println("Drawspace doesn't exist");
            Platform.exit();
        }

        while(!steps.isEmpty()) {

            AlgorithmStep step = steps.poll();
            timeline.getKeyFrames().add(new KeyFrame(Duration.millis(delay),
                    new AlgorithmPreStepPerformer(step)));

            delay += additionalTime;

            timeline.getKeyFrames().add(new KeyFrame(Duration.millis(delay),
                    new AlgorithmStepPerformer(step)));

            delay += additionalTime;
        }

        timeline.play();

    }

    public DrawSpace getDrawSpace() {
        return drawSpace;
    }

    public void setDrawSpace(DrawSpace drawSpace) {
        this.drawSpace = drawSpace;
        this.drawSpace.addEventFilter(DrawSpaceEvent.ESC_PRESSED, new EventHandler<DrawSpaceEvent>() {
            @Override
            public void handle(DrawSpaceEvent event) {
                timeline.stop();
                timeline.getKeyFrames().clear();
                timeline.getKeyFrames().add(new KeyFrame(Duration.millis(100),
                        new AlgorithmStepPerformer(new AlgorithmStep(AlgorithmCommand.RESET_ALL))));
                timeline.getKeyFrames().add(new KeyFrame(Duration.millis(100),
                        new AlgorithmStepPerformer(new AlgorithmStep(AlgorithmCommand.RESET_DRAWSPACE))));
                timeline.play();
            }
        });
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private class AlgorithmStep {

        private Element source;
        private String value;
        private AlgorithmCommand command = AlgorithmCommand.NONE;

        public AlgorithmStep(Element element) {
            this.source = element;
        }

        public AlgorithmStep(Element element, AlgorithmCommand command) {
            this.source = element;
            this.command = command;
        }

        public AlgorithmStep(Element element, String value) {
            this.source = element;
            this.value = value;
        }

        public AlgorithmStep(Element element, String value, AlgorithmCommand command) {
            this.source = element;
            this.value = value;
            this.command = command;
        }

        public AlgorithmStep(AlgorithmCommand command) {
            this.command = command;
        }
        public AlgorithmStep(AlgorithmCommand command, String message) {
            this.command = command;
            this.value = message;
        }

        public Element getSource() {
            return source;
        }

        public void setSource(Element source) {
            this.source = source;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }


        public AlgorithmCommand getCommand() {
            return command;
        }

        public void setCommand(AlgorithmCommand command) {
            this.command = command;
        }
    }

    private enum AlgorithmCommand {NONE, RESET_ALL, MAKE_SPECIAL,
        DO_NOT_VISIT, SET_ALL_INF, MARK, INACTIVATE, RESET_DRAWSPACE}

    private class AlgorithmPreStepPerformer implements EventHandler<ActionEvent> {

        private AlgorithmStep step;

        public AlgorithmPreStepPerformer(AlgorithmStep step) {
            this.step = step;
        }

        @Override
        public void handle(ActionEvent event) {
            if(step.getCommand() == AlgorithmCommand.NONE){
                step.getSource().getView().setActivity(true);
            }
            else if(step.getCommand() == AlgorithmCommand.MAKE_SPECIAL) {
                step.getSource().getView().setExceptional(true);
            }
        }
    }

    private class AlgorithmStepPerformer implements EventHandler<ActionEvent> {

        private AlgorithmStep step;

        public AlgorithmStepPerformer(AlgorithmStep step) {
            this.step = step;
        }

        @Override
        public void handle(ActionEvent event) {
            if(step.getCommand() == AlgorithmCommand.RESET_ALL) {
                for(Vertex vertex : vertexes) {
                    vertex.getView().resetName();
                    vertex.getView().setExceptional(false);
                    vertex.getView().unmark();
                    vertex.getView().setActivity(false);
                }
            }
            else if(step.getCommand() == AlgorithmCommand.SET_ALL_INF) {
                for(Vertex vertex : vertexes) {
                    vertex.getView().setName("INF");
                }
            }
            else if(step.getCommand() == AlgorithmCommand.RESET_DRAWSPACE) {
                drawSpace.setOperationType(DrawSpace.OperationType.DEFAULT);
                if(step.getValue() != null) {
                    OutputMsgBox msgBox = new OutputMsgBox(step.getValue());
                    msgBox.show();
                }
            }
            else {
                if(step.getValue() != null) {
                    step.getSource().getView().setName(step.getValue());
                }
                if(step.getSource() != null) {
                    if(step.getSource().getClass() == Edge.class) {
                        step.getSource().getView().setActivity(false);
                    }
                    else if(step.getCommand() == AlgorithmCommand.INACTIVATE) {
                        if(step.getSource().getView().isExceptional()) {
                            step.getSource().getView().setExceptional(true);
                        }
                        else {
                            step.getSource().getView().setActivity(false);
                        }
                    }
                    else if(step.getCommand() == AlgorithmCommand.MARK) {
                        step.getSource().getView().mark();
                    }
                }
            }
        }
    }
}
