package util;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.shape.Line;
import model.Person;

import javax.swing.text.Position;
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

        // start
        double x = 0;   double y = 0;

        // set the position of the centralNodePerson this could be part of the pos function.
        root.getChildren().addAll(posAndDisplay(centralNodePerson, x, y));

        // I want to start with the root, pop the mothers, then mother's mothers.
        // Then I want to pop the fathers, and father's father's then children

        return root;
    }


    // Method to position nodes by CGPT I barely understand this
    private Node posAndDisplay(Person person, double x, double y) {
        double H_SPACING = 100;
        double V_SPACING = 70;

        Group root = new Group();

        person.setX(x);
        person.setY(y);

        Circle circle = new Circle(x, y, 4, Color.RED);

        Label label = new Label(person.getFirstMiddleInitLastNameOfPerson());
        label.setLayoutX(x - 30);
        label.setLayoutY(y + 8);

        root.getChildren().addAll(circle, label);

        if (visitedNeighbors.contains(person)) return root;
        visitedNeighbors.add(person);

        List<Person> children = person.getChildrenList();
        int count = children.size();

        double startX = x - ((count - 1) * H_SPACING / 2.0);
        double childY = y + V_SPACING;

        for (Person child : children) {
            if (!visitedNeighbors.contains(child)) {
                Node childNode = posAndDisplay(child, startX, childY);
                root.getChildren().add(childNode);

                Line line = new Line(
                        person.getX(),
                        person.getY(),
                        child.getX(),
                        child.getY()
                );

                line.setStroke(Color.WHITE);
                line.setStrokeWidth(1.5);

                root.getChildren().add(0, line);
            }
            startX += H_SPACING;
        }
        return root;
    }

    }
