package view;

import databaseController.MainApp;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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

    Person person;

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
    @FXML
    private void handleCancel() {
        mainApp.showPersonEditOverviewScene(person);
    }

    // Allows the scene to grab the main application.
    public void setMainApp(@SuppressWarnings("exports") MainApp mainApp, Person person) {
        this.mainApp = mainApp;
        setPerson(person);
        // Add observable list data for parents to the table
        possibleParentsOrChildrenTable.setItems(getNonParentsData());
        currentParentsOrChildrenTable.setItems(FXCollections.observableArrayList(person.getParentsList()));

        // Add observable list data for children to the table
        //possibleParentsOrChildrenTable.setItems(getNonChildrenData());
        //currentParentsOrChildrenTable.setItems(FXCollections.observableArrayList(person.getChildrenList()));
    }

    // I might want a helper method to see if there were any changes that needed to be saved
    // Helper method to return a list of non-parents.
    public ObservableList<Person> getNonParentsData() {

        ObservableList<Person> allPeople = FXCollections.observableArrayList();
        allPeople.addAll(mainApp.getPersonData());
        ObservableList<Person> parentsList = FXCollections.observableArrayList(person.getParentsList());
        allPeople.removeAll(parentsList);
        allPeople.remove(this.person);
        return allPeople;
    }
    // Helper method to determine who isn't already a child.
    public ObservableList<Person> getNonChildrenData() {
        ObservableList<Person> allPeople = FXCollections.observableArrayList();
        allPeople.addAll(mainApp.getPersonData());
        ObservableList<Person> childrenList = FXCollections.observableArrayList(person.getChildrenList());
        allPeople.removeAll(childrenList);
        allPeople.remove(this.person);
        return allPeople;
    }

    // Set Person to determine who is the person being edited.
    public void setPerson(Person person) {
        this.person = person;
    }
}
