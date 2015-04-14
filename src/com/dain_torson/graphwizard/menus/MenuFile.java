/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dain_torson.graphwizard.menus;

import java.io.*;

import com.dain_torson.graphwizard.drawspace.DrawSpace;
import com.dain_torson.graphwizard.graph.Graph;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 *
 * @author Ales
 */
public class MenuFile extends Menu {

    Stage newStage = new Stage();

    public MenuFile(Stage stage, Graph graph, DrawSpace drawSpace) {
        this.newStage = stage;
        this.setText("File");
        MenuItem itemNew = new MenuItem("New");
        MenuItem itemOpen = new MenuItem("Open");
        MenuItem itemSave = new MenuItem("Save");
        MenuItem itemExit = new MenuItem("Exit");

        itemNew.setOnAction(new ItemNewEventHandler(graph));
        itemOpen.setOnAction(new ItemOpenEventHandler(newStage, graph, drawSpace));
        itemSave.setOnAction(new ItemSaveEventHandler(newStage, graph));
        itemExit.setOnAction(new ItemExitEventHandler());
        
        this.getItems().addAll(itemNew, itemOpen, itemSave, itemExit);
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
        DrawSpace drawSpace;
        ItemOpenEventHandler(Stage stage,Graph graph, DrawSpace drawSpace) {
            this.stage = stage;
            this.graph = graph;
            this.drawSpace = drawSpace;
        }
        
        @Override
        public void handle(ActionEvent e) {
            int size = 0;
            int matrix [][];
            double cooordinates [][];
            String values [];

            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Open graph");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("GWG", "*.gwg"));
            File file = fileChooser.showOpenDialog(stage);
            if (file != null) {
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
                        drawSpace.reset(graph, cooordinates);
                    }
                }
                catch (FileNotFoundException exception) {
                    System.out.println("Error while loading file");
                }
                catch (IOException exception) {
                    System.out.println("Error while loading file");
                }
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
            int size = graph.getNumOfVertices();
            int matrix [][] = graph.getAdjacencyMatrix();
            double cooordinates [][] = graph.getCoordinates();
            String values [] = graph.getVerticesValues();

            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save as");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("GWG", "*.gwg"));
            fileChooser.setInitialFileName("NewGraph.gwg");
            File file = fileChooser.showSaveDialog(stage);
            if(file != null) {
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
                } catch (FileNotFoundException exception) {
                    System.out.println("Error while saving file");
                } catch (IOException exception) {
                    System.out.println("Error while saving file");
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
