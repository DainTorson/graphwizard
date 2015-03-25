package com.dain_torson.graphwizard;

import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ales on 16.03.2015.
 */
public class DrawSpace extends Pane {

    private OperationType operationType = OperationType.DEFAULT;
    private Graph graph;
    private GhostEdge ghostEdge;
    private EdgeDrawContext edgeDrawContext = new EdgeDrawContext();
    private List<VertexView> vertexViews = new ArrayList<VertexView>();
    private List<EdgeView> edgeViews = new ArrayList<EdgeView>();

    public DrawSpace(Stage primaryStage, Graph graph) {

        this.graph = graph;
        this.ghostEdge = new GhostEdge(this);
        this.addEventFilter(MouseEvent.MOUSE_PRESSED, new VertexOperationHandler(this));
        this.addEventFilter(MouseEvent.MOUSE_PRESSED, new DefaultOperationHandler());
        this.addEventFilter(MouseEvent.MOUSE_MOVED, new EdgeDrawHandler());
        primaryStage.addEventFilter(KeyEvent.KEY_PRESSED, new DeleteKeyHandler());

    }

    public OperationType getOperationType() {

        return this.operationType;
    }

    public void setOperationType(OperationType operationType) {

        this.operationType = operationType;
    }

    public void reset(Graph newGraph, double coordinates [][]) {
        if(graph != newGraph) {
            graph.clear();
            graph = newGraph;
        }

        for(int vertIdx = 0; vertIdx < graph.getNumOfVertices(); ++vertIdx) {
            Vertex temp = graph.getVertex(vertIdx);
            VertexView tempView = new VertexView(coordinates[vertIdx][0], coordinates[vertIdx][1],
                    this, temp);
            temp.setView(tempView);
            temp.draw();
            tempView.getCircle().addEventFilter(VertexEvent.VERTEX_PRESSED, new EdgeOperationHandler(this));
            tempView.getCircle().addEventFilter(VertexEvent.VERTEX_PRESSED, new VertexActivityHandler());
            tempView.getCircle().addEventFilter(VertexEvent.VERTEX_DELETED, new VertexDeleteHandler());
            vertexViews.add(tempView);
        }

        for(int edgeIdx = 0; edgeIdx < graph.getNumOfEdges(); ++edgeIdx) {
            Edge temp = graph.getEdge(edgeIdx);
            EdgeView tempView = new EdgeView(temp.getFirstVertex().getView(),
                    temp.getSecondVertex().getView(), this, temp);
            temp.setView(tempView);
            temp.draw();
            tempView.getLine().addEventFilter(EdgeEvent.EDGE_DELETED, new EdgeDeleteHandler());
            edgeViews.add(tempView);
        }
    }

    private class VertexOperationHandler implements EventHandler<MouseEvent> {

        private DrawSpace drawSpace;

        public VertexOperationHandler(DrawSpace drawSpace) {
            this.drawSpace = drawSpace;
        }

        @Override
        public void handle(MouseEvent event) {
            if(operationType == OperationType.VERTEX) {
                if(edgeDrawContext.isDrawing) {
                    edgeDrawContext.isDrawing = false;
                    ghostEdge.hide();
                }
                Vertex temp = new Vertex(String.valueOf(graph.getNumOfVertices()));
                VertexView tempView = new VertexView(event.getX(), event.getY(), drawSpace, temp);
                temp.setView(tempView);
                temp.draw();
                tempView.getCircle().addEventFilter(VertexEvent.VERTEX_PRESSED, new EdgeOperationHandler(drawSpace));
                tempView.getCircle().addEventFilter(VertexEvent.VERTEX_PRESSED, new VertexActivityHandler());
                tempView.getCircle().addEventFilter(VertexEvent.VERTEX_DELETED, new VertexDeleteHandler());
                vertexViews.add(tempView);
                graph.addVertex(temp);
            }
        }
    }

    private class EdgeOperationHandler implements EventHandler<VertexEvent> {

        private DrawSpace drawSpace;

        public EdgeOperationHandler(DrawSpace drawSpace) {
            this.drawSpace = drawSpace;
        }

        @Override
        public void handle(VertexEvent event) {
            if(operationType == OperationType.EDGE) {
                if (edgeDrawContext.isDrawing) {
                    Edge temp = new Edge(edgeDrawContext.startVx, event.getTargetVx());
                    EdgeView tempView = new EdgeView(edgeDrawContext.startVx.getView(),
                            event.getTargetVx().getView(), drawSpace, temp);
                    temp.setView(tempView);
                    temp.draw();
                    tempView.getLine().addEventFilter(EdgeEvent.EDGE_DELETED, new EdgeDeleteHandler());
                    edgeViews.add(tempView);
                    graph.addEdge(temp);
                    edgeDrawContext.isDrawing = false;
                    ghostEdge.hide();
                } else {
                    edgeDrawContext.startVx = event.getTargetVx();
                    edgeDrawContext.isDrawing = true;
                }
            }
        }
    }

    private class DefaultOperationHandler implements EventHandler<MouseEvent> {

        @Override
        public void handle(MouseEvent event) {

            for(VertexView vertexView : vertexViews) {
                vertexView.setActivity(false);
            }

            for(EdgeView edgeView : edgeViews) {
                edgeView.setActivity(false);
            }
        }
    }

    private class EdgeDrawHandler implements EventHandler<MouseEvent> {

        @Override
        public void handle(MouseEvent event) {
            if(edgeDrawContext.isDrawing) {
                ghostEdge.draw();
                ghostEdge.setStartPoint(edgeDrawContext.startVx.getView().getX(),
                        edgeDrawContext.startVx.getView().getY());
                ghostEdge.setEndPoint(event.getX(), event.getY());
            }
        }
    }

    private class VertexActivityHandler implements EventHandler<VertexEvent> {

        @Override
        public void handle(VertexEvent event) {
            if(operationType == OperationType.DEFAULT) {
                event.getTargetVx().getView().setActivity(true);
            }
        }
    }


    private class DeleteKeyHandler implements EventHandler<KeyEvent> {

        @Override
        public void handle(KeyEvent event) {
            if(event.getCode() == KeyCode.DELETE) {
                List<Integer> activeIdx = new ArrayList<Integer>();
                for(VertexView vertexView : vertexViews) {
                    if(vertexView.getActivity()) {
                        activeIdx.add(vertexViews.indexOf(vertexView));
                    }
                }

                for(Integer idx : activeIdx) {
                    graph.deleteVertex(vertexViews.get(idx).getVertex());
                }

                activeIdx.clear();
                for(EdgeView edgeView : edgeViews) {
                    if(edgeView.getActivity()) {
                        activeIdx.add(edgeViews.indexOf(edgeView));
                    }
                }

                for(Integer idx : activeIdx) {
                    graph.deleteEdge(edgeViews.get(idx).getEdge());
                }
            }
        }
    }

    public class VertexDeleteHandler implements EventHandler<VertexEvent> {

        @Override
        public void handle(VertexEvent event) {
            vertexViews.remove(event.getTargetVx().getView());
        }
    }

    public class EdgeDeleteHandler implements EventHandler<EdgeEvent> {

        @Override
        public void handle(EdgeEvent event) {
            edgeViews.remove(event.getTargetEdge().getView());
            System.out.println("EdgeViewes " + String.valueOf(edgeViews.size()));
        }
    }

    public enum OperationType {DEFAULT, VERTEX, EDGE}

    private class EdgeDrawContext {

        public Vertex startVx;
        public boolean isDrawing = false;
    }
}
