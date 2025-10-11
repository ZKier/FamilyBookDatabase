package view;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import model.Person;

public class ParentsChildrenUpdateSceneController {
    @FXML
    private TableView<Person> possibleParentsOrChildren;
    @FXML
    private TableView<Person> currentParentsOrChildren;

    // Populate the first table with whichever (parents or children) have not been added.
    // Populate the second table with whichever (parents or children) already exist in the parents or children list.
    // Handle "Add >>>"
    // Handle "<<< Remove"

    // Handle "New...", I want this to continue to a new scene but remember that there was this scene to go back to as a previous scene
    // Maybe I shouldn't do the "New..." button

    // Considering adding an "Edit..." button
    // Handle "OK"
    // Handle "Cancel"
}
