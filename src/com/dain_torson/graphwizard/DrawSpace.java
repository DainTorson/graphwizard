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

    public DrawSpace(Stage primaryStage, ScrollSpace scrollSpace, Graph graph) {

        this.graph = graph;
        this.ghostEdge = new GhostEdge(this);
        scrollSpace.setContent(this);
        this.setPrefSize(800, 600);
        this.addEventFilter(MouseEvent.MOUSE_PRESSED, new VertexOperationHandler(this));
        this.addEventFilter(MouseEvent.MOUSE_PRESSED, new DefaultOperationHandler(this));
        this.addEventFilter(MouseEvent.MOUSE_MOVED, new EdgeDrawHandler());
        primaryStage.addEventFilter(KeyEvent.KEY_PRESSED, new DeleteKeyHandler());
        primaryStage.addEventFilter(KeyEvent.KEY_PRESSED, new EscapeKeyHandler());

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
            Vertex newVertex = graph.getVertex(vertIdx);
            VertexView newVertexView = new VertexView(coordinates[vertIdx][0], coordinates[vertIdx][1],
                    this, newVertex);
            newVertex.setView(newVertexView);
            newVertex.draw();
            newVertexView.getCircle().addEventFilter(VertexEvent.VERTEX_PRESSED, new EdgeOperationHandler());
            newVertexView.getCircle().addEventFilter(VertexEvent.VERTEX_PRESSED, new VertexActivityHandler());
            newVertexView.getCircle().addEventFilter(VertexEvent.VERTEX_DELETED, new VertexDeleteHandler());
            vertexViews.add(newVertexView);
        }

        for(int edgeIdx = 0; edgeIdx < graph.getNumOfEdges(); ++edgeIdx) {
            Edge newEdge = graph.getEdge(edgeIdx);
            EdgeView newEdgeView = new EdgeView(newEdge.getFirstVertex().getView(),
                    newEdge.getSecondVertex().getView(), this, newEdge);
            newEdge.setView(newEdgeView);
            newEdge.draw();
            newEdgeView.getPolygon().addEventFilter(EdgeEvent.EDGE_DELETED, new EdgeDeleteHandler());
            edgeViews.add(newEdgeView);
        }
    }

    private void createEdge(Vertex firstVertex, Vertex secondVertex, boolean isOriented) {

        Edge newEdge = new Edge(firstVertex, secondVertex, isOriented);
        EdgeView newEdgeView = new EdgeView(firstVertex.getView(), secondVertex.getView(), this, newEdge);
        newEdge.setView(newEdgeView);
        newEdge.draw();
        newEdgeView.getPolygon().addEventFilter(EdgeEvent.EDGE_DELETED, new EdgeDeleteHandler());
        edgeViews.add(newEdgeView);
        graph.addEdge(newEdge);
    }

    public void deselectAll() {
        for(VertexView vertexView : vertexViews) {
            vertexView.setActivity(false);
        }

        for(EdgeView edgeView : edgeViews) {
            edgeView.setActivity(false);
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
                Vertex newVertex = new Vertex(String.valueOf(graph.getNumOfVertices()));
                VertexView newVertexView = new VertexView(event.getX(), event.getY(), drawSpace, newVertex );
                newVertex.setView(newVertexView);
                newVertex.draw();
                newVertexView.getCircle().addEventFilter(VertexEvent.VERTEX_PRESSED, new EdgeOperationHandler());
                newVertexView.getCircle().addEventFilter(VertexEvent.VERTEX_PRESSED, new VertexActivityHandler());
                newVertexView.getCircle().addEventFilter(VertexEvent.VERTEX_DELETED, new VertexDeleteHandler());
                vertexViews.add(newVertexView);
                graph.addVertex(newVertex);
            }
        }
    }

    private class EdgeOperationHandler implements EventHandler<VertexEvent> {

        @Override
        public void handle(VertexEvent event) {
            if(operationType == OperationType.EDGE || operationType == OperationType.ORIENTED_EDGE) {
                if (edgeDrawContext.isDrawing) {
                    Edge temp = graph.isConnects(edgeDrawContext.startVx, event.getTargetVx());
                    if(operationType == OperationType.EDGE) {
                        if(temp != null) return;
                        createEdge(edgeDrawContext.startVx, event.getTargetVx(), false);
                    }
                    else {
                        if(temp != null) {
                            if (graph.isExists(edgeDrawContext.startVx, event.getTargetVx()) == null) {
                                temp.setDefaultType();
                                temp.getView().update();
                            }
                        }
                        else {
                            createEdge(edgeDrawContext.startVx, event.getTargetVx(), true);
                        }
                    }
                    edgeDrawContext.isDrawing = false;
                    ghostEdge.hide();
                }
                else {
                    edgeDrawContext.startVx = event.getTargetVx();
                    edgeDrawContext.isDrawing = true;
                }
            }
        }
    }

    private class DefaultOperationHandler implements EventHandler<MouseEvent> {

        DrawSpace drawSpace;
        public DefaultOperationHandler(DrawSpace drawSpace) {
            this.drawSpace = drawSpace;
        }
        @Override
        public void handle(MouseEvent event) {
            deselectAll();
            System.out.println(event.getSceneX());
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

    private class EscapeKeyHandler implements EventHandler<KeyEvent> {

        @Override
        public void handle(KeyEvent event) {
            if(event.getCode() == KeyCode.ESCAPE) {
                if (edgeDrawContext.isDrawing) {
                    edgeDrawContext.isDrawing = false;
                    ghostEdge.hide();
                }
                deselectAll();
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

    public enum OperationType {DEFAULT, VERTEX, EDGE, ORIENTED_EDGE}

    private class EdgeDrawContext {

        public Vertex startVx;
        public boolean isDrawing = false;
    }
}
