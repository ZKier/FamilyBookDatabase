package view;

import databaseController.MainApp;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import model.Person;
import databaseController.MainApp;


public class ParentsChildrenUpdateSceneController {
    @FXML
    private TableView<Person> possibleParentsOrChildrenTable;
    @FXML
    private TableView<Person> currentParentsOrChildrenTable;
    @FXML
    private TableColumn<Person, String> possibleFirstNameColumn;
    @FXML
    private TableColumn<Person, String> possibleLastNameColumn;
    @FXML
    private TableColumn<Person, String> currentFirstNameColumn;
    @FXML
    private TableColumn<Person, String> currentLastNameColumn;

    // Reference to the main application.
    private MainApp mainApp;

    /** The constructor.
     The constructor is called before the initialize() method.
     */
    public ParentsChildrenUpdateSceneController() {
    }

    /** initializes the controller class
     *
     */
    @FXML
    private void initialize() {
        // Initialize the possible people table with the two columns.
        possibleFirstNameColumn.setCellValueFactory(cellData -> cellData.getValue().firstNameProperty());
        possibleLastNameColumn.setCellValueFactory(cellData -> cellData.getValue().lastNameProperty());

        // Initialize the possible people table with the two columns.
        currentFirstNameColumn.setCellValueFactory(cellData -> cellData.getValue().firstNameProperty());
        currentLastNameColumn.setCellValueFactory(cellData -> cellData.getValue().lastNameProperty());

        // Listen for selection changes and show the person details when changed.
        //personTable.getSelectionModel().selectedItemProperty().addListener(
        //        (observable, oldValue, newValue) -> showPersonDetails(newValue));
    }

    // Populate the first table with whichever (parents or children) have not been added.
    // Populate the second table with whichever (parents or children) already exist in the parents or children list.
    // Handle "Add >>>"
    // Handle "<<< Remove"

    // Handle "New...", I want this to continue to a new scene but remember that there was this scene to go back to as a previous scene
    // Maybe I shouldn't do the "New..." button

    // Considering adding an "Edit..." button
    // Handle "OK"
    // Handle "Cancel"
    private void handleCancel() {

    }

    // Allows the scene to grab the main application.
    public void setMainApp(@SuppressWarnings("exports") MainApp mainApp) {
        this.mainApp = mainApp;

        // Add observable list data to the table
        possibleParentsOrChildrenTable.setItems(mainApp.getPersonData());
    }

    // I might want a helper method to see if there were any changes that needed to be saved
}
