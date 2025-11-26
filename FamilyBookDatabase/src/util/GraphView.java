package util;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import model.Person;

import java.util.Iterator;
// Assuming you have a Graph and Displayable interface/class for your data

public class GraphView {
    Person centralNodePerson;
    private ObservableList<Person> personData = FXCollections.observableArrayList();

    // Constructor
    public GraphView(Person centralNodePerson, ObservableList<Person> personData) {
        this.centralNodePerson = centralNodePerson;
        this.personData = personData;
    }
    public Node plot() {
        // Create the container
        Group root = new Group(); // This container has parents and children

        // Create the object that will add to the container
        //Circle circle = new Circle(0,0,2, Color.RED);
        //root.getChildren().add(circle);

        // Instead of plotting one thing I want to plot 2 with a line connecting them
        // Depth First Search
        /*
        def DFS(node) {
            mark node as visited
            for each neighbor of node {
                if neighbot is not visited {
                    plot neighbor
                }
            }
        }
         */
        // instead of deleting the name I can add the name to a list and compare to mark a node as visited.
        Iterator<Person> iterator = personData.iterator();
        // I want to start with the root, pop the mothers, then mother's mothers.
        // Then I want to pop the fathers, and father's father's then children

        return root;
    }

    /*
    private Graph<? extends Displayable> graph; // Graph data structure
    private Group group = new Group(); // Container for visual elements

    public GraphView(Graph<? extends Displayable> graph) {
        this.graph = graph;
        this.setCenter(group);
        repaintGraph(); // Initial drawing
    }
    private void repaintGraph() {
        group.getChildren().clear(); // Clear existing elements
        // Draw vertices and their labels
        for (Displayable vertex : graph.getVertices()) { // Iterate through your nodes
            double x = vertex.getX();
            double y = vertex.getY();
            String name = vertex.getName();
            Circle nodeShape = new Circle(x, y, 16); // Example: Circle node
            Text nodeLabel = new Text(x - 8, y - 18, name); // Example: Text label
            group.getChildren().addAll(nodeShape, nodeLabel);
        }

        // Draw edges
        for (int i = 0; i < graph.getSize(); i++) { // Iterate through nodes to find edges
            Displayable sourceVertex = graph.getVertex(i);
            double x1 = sourceVertex.getX();
            double y1 = sourceVertex.getY();
            for (int neighborIndex : graph.getNeighbors(i)) { // Iterate through neighbors
                Displayable targetVertex = graph.getVertex(neighborIndex);
                double x2 = targetVertex.getX();
                double y2 = targetVertex.getY();
                Line edgeLine = new Line(x1, y1, x2, y2); // Example: Line edge
                group.getChildren().add(edgeLine);
            }
        }
    }

     */
}
