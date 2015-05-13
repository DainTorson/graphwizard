/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dain_torson.graphwizard.menus;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import com.dain_torson.graphwizard.drawspace.DrawSpace;
import com.dain_torson.graphwizard.graph.Graph;
import com.dain_torson.graphwizard.graph.elements.Edge;
import com.dain_torson.graphwizard.graph.elements.Vertex;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

public class MenuFile extends Menu {

    private Stage sourceStage;
    private Graph sourceGraph;
    private DrawSpace sourceSpace;
    private MenuItem itemNew;
    private MenuItem itemOpen;
    MenuItem itemSave;

    public MenuFile(Stage stage, Graph graph, DrawSpace drawSpace) {
        this.sourceStage = stage;
        this.sourceGraph = graph;
        this.sourceSpace = drawSpace;
        this.setText("File");
        itemNew = new MenuItem("New");
        itemOpen = new MenuItem("Open");
        itemSave = new MenuItem("Save");
        MenuItem itemExit = new MenuItem("Exit");

        itemNew.setOnAction(new ItemNewEventHandler(sourceGraph));
        itemOpen.setOnAction(new ItemOpenEventHandler(sourceStage, sourceGraph));
        itemSave.setOnAction(new ItemSaveEventHandler(sourceStage, sourceGraph));
        itemExit.setOnAction(new ItemExitEventHandler());
        
        this.getItems().addAll(itemNew, itemOpen, itemSave, itemExit);
    }

    public void update() {
        itemNew.setOnAction(new ItemNewEventHandler(sourceGraph));
        itemOpen.setOnAction(new ItemOpenEventHandler(sourceStage, sourceGraph));
        itemSave.setOnAction(new ItemSaveEventHandler(sourceStage, sourceGraph));
    }

    public Graph getSourceGraph() {
        return sourceGraph;
    }

    public void setSourceGraph(Graph sourceGraph) {
        this.sourceGraph = sourceGraph;
        update();
    }

    public DrawSpace getSourceSpace() {
        return sourceSpace;
    }

    public void setSourceSpace(DrawSpace sourceSpace) {
        this.sourceSpace = sourceSpace;
        update();
    }

    private void saveToBinary(File file, Graph graph) {

        int size = graph.getNumOfVertices();
        int matrix [][] = graph.getAdjacencyMatrix();
        double cooordinates [][] = graph.getCoordinates();
        String values [] = graph.getVerticesValues();

        try {
            DataOutputStream out = new DataOutputStream(new FileOutputStream(file));
            out.writeInt(size);
            for (int row = 0; row < size; ++row) {
                for (int col = 0; col < size; ++col) {
                    out.writeInt(matrix[row][col]);
                }
            }

            for (int vertIdx = 0; vertIdx < size; ++vertIdx) {
                out.writeDouble(cooordinates[vertIdx][0]);
                out.writeDouble(cooordinates[vertIdx][1]);
            }

            for (int vertIdx = 0; vertIdx < size; ++vertIdx) {
                out.writeUTF(values[vertIdx]);
            }

            String [] parts = file.getName().split("\\.");
            graph.setName(parts[0]);
            graph.getDrawSpace().fireEvent(new FileEvent(FileEvent.FILE_SAVED, file));
        } catch (FileNotFoundException exception) {
            System.out.println("Error while saving file");
        } catch (IOException exception) {
            System.out.println("Error while saving file");
        }
    }

    private void openBinary(File file, Graph graph) {

        int size = 0;
        int matrix [][];
        double cooordinates [][];
        String values [];

        try {
            DataInputStream in = new DataInputStream(new FileInputStream(file));
            size = in.readInt();
            matrix = new int[size][size];
            cooordinates = new double[size][2];
            values = new String [size];
            for(int row = 0; row < size; ++row) {
                for(int col = 0; col < size; ++col) {
                    matrix[row][col] = in.readInt();
                }
            }

            for(int vertIdx = 0; vertIdx < size; ++vertIdx) {
                cooordinates[vertIdx][0] = in.readDouble();
                cooordinates[vertIdx][1] = in.readDouble();
            }

            for(int vertIdx = 0; vertIdx < size; ++vertIdx) {
                values[vertIdx] = in.readUTF();
            }

            if(size != 0) {
                graph.reset(matrix, values, size);
                graph.getDrawSpace().reset(graph, cooordinates);
            }

            String [] parts = file.getName().split("\\.");
            graph.setName(parts[0]);
            graph.getDrawSpace().fireEvent(new FileEvent(FileEvent.FILE_OPENED, file));
        }
        catch (FileNotFoundException exception) {
            System.out.println("Error while loading file");
        }
        catch (IOException exception) {
            System.out.println("Error while loading file");
        }
    }

    private Element createVertexNode(Vertex source, Document document) {

        Element vertex = document.createElement("vertex");

        Element value = document.createElement("value");
        value.setTextContent(source.getValue());

        Element coordX = document.createElement("coordX");
        coordX.setTextContent(String.valueOf(source.getView().getX()));

        Element coordY = document.createElement("coordY");
        coordY.setTextContent(String.valueOf(source.getView().getY()));

        vertex.appendChild(value);
        vertex.appendChild(coordX);
        vertex.appendChild(coordY);

        return vertex;
    }

    private Element createEdgeNode(Edge source, Document document) {

        Element edge = document.createElement("edge");

        Element vertex1 = document.createElement("vertex1");
        vertex1.setTextContent(source.getFirstVertex().getValue());

        Element vertex2 = document.createElement("vertex2");
        vertex2.setTextContent(source.getSecondVertex().getValue());

        Element weight = document.createElement("weight");
        weight.setTextContent(String.valueOf(source.getValue()));

        edge.appendChild(vertex1);
        edge.appendChild(vertex2);
        edge.appendChild(weight);

        return edge;
    }

    private void saveToXML(File file, Graph graph) {

        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

            Document document = docBuilder.newDocument();
            Element root = document.createElement("graph");
            document.appendChild(root);
            Element vertices = document.createElement("vertices");
            Element edges = document.createElement("edges");

            for(Vertex vertex : graph.getVertexes()) {
                vertices.appendChild(createVertexNode(vertex, document));
            }

            for(Edge edge : graph.getEdges()) {
                edges.appendChild(createEdgeNode(edge, document));
            }

            root.appendChild(vertices);
            root.appendChild(edges);

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(document);
            StreamResult result = new StreamResult(file);

            transformer.transform(source, result);
        }
        catch (ParserConfigurationException exception) {
            exception.printStackTrace();
        }
        catch (TransformerConfigurationException exception) {
            exception.printStackTrace();
        }
        catch (TransformerException exception) {
            exception.printStackTrace();
        }
    }

    private void openXML(File file, Graph graph) {

        List<Vertex> vertexList = new ArrayList<Vertex>();

    }

    private class ItemNewEventHandler implements EventHandler<ActionEvent> {

        Graph graph;
        public ItemNewEventHandler(Graph graph) {
            this.graph = graph;
        }

        @Override
        public void handle(ActionEvent event) {
            graph.clear();
        }
    }

    private class ItemOpenEventHandler implements EventHandler<ActionEvent> {

        Graph graph;
        Stage stage = new Stage();

        ItemOpenEventHandler(Stage stage,Graph graph) {
            this.stage = stage;
            this.graph = graph;

        }
        
        @Override
        public void handle(ActionEvent e) {

            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Open graph");
            fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("GWG", "*.gwg"),
                    new FileChooser.ExtensionFilter("XML", "*.xml"));
            File file = fileChooser.showOpenDialog(stage);
            if (file != null) {
                openBinary(file, graph);
            }
        }
    }

    private class ItemSaveEventHandler implements EventHandler<ActionEvent> {

        Graph graph;
        Stage stage;
        public ItemSaveEventHandler(Stage stage, Graph graph) {
            this.graph = graph;
            this.stage = stage;
        }

        @Override
        public void handle(ActionEvent event) {

            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save as");
            fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("GWG", "*.gwg"),
                    new FileChooser.ExtensionFilter("XML", "*.xml"));
            fileChooser.setInitialFileName("NewGraph.gwg");
            File file = fileChooser.showSaveDialog(stage);
            if(file != null) {
                if(fileChooser.getSelectedExtensionFilter().getExtensions().get(0).equals("*.gwg")){
                    saveToBinary(file, graph);
                }
                else {
                    saveToXML(file, graph);
                }
            }
        }
    }

    private class ItemExitEventHandler implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent e) {
            Platform.exit();
        }

    }

}
