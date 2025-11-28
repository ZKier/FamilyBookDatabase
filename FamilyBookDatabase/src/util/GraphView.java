package util;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import model.Person;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
// Assuming you have a Graph and Displayable interface/class for your data

public class GraphView {
    Person centralNodePerson;
    private ObservableList<Person> personData = FXCollections.observableArrayList();

    // Create a list of visited neighbors
    List<Person> visitedNeighbors = new ArrayList<>();

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
                if neighbor is not visited {
                    plot neighbor
                }
            }
        }
         */
        // start
        double x = 0;   double y = 0;

        // set the position of the centralNodePerson this could be part of the pos function.
        root.getChildren().addAll(posAndDisplay(centralNodePerson, x, y));

        //Iterator<Person> iterator = personData.iterator();
        // I want to start with the root, pop the mothers, then mother's mothers.
        // Then I want to pop the fathers, and father's father's then children

        return root;
    }

    // Method to position nodes assisted by CGPT
    private Node posAndDisplay(Person person, double x, double y) {
        double H_SPACING = 100;   // horizontal spacing between siblings
        double V_SPACING = 70;    // vertical spacing between generations

        // Create the container
        Group root = new Group();
        person.setX(x);
        person.setY(y);

        // Render the circle + label
        Circle circle = new Circle(4, Color.RED);
        Label label = new Label(person.getFirstMiddleInitLastNameOfPerson());

        VBox vBox = new VBox(5, circle, label);
        vBox.setAlignment(javafx.geometry.Pos.CENTER);

        // Position the vBox itself
        vBox.setLayoutX(x);
        vBox.setLayoutY(y);

        root.getChildren().add(vBox);

        // Mark node visited
        if (visitedNeighbors.contains(person)) return root;
        visitedNeighbors.add(person);

        // Spread children horizontally around the parent
        List<Person> children = person.getChildrenList();
        int count = children.size();

        // Center children around parent's x-coordinate
        double startX = x - ( (count - 1) * H_SPACING / 2.0 );

        double childY = y + V_SPACING;

        for (Person child : children) {
            if (!visitedNeighbors.contains(child)) {
                Node childNode = posAndDisplay(child, startX, childY);
                root.getChildren().add(childNode);
            }
            startX += H_SPACING;
        }

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
