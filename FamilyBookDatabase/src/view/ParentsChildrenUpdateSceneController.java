package view;

import databaseController.MainApp;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import model.Person;
import databaseController.MainApp;
import model.PersonData;

import java.util.ArrayList;


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
    // To update the main person, 'person'. I think I have to update every person in this app.
    Person tempPerson;
    PersonData personAddData =  new PersonData();
    PersonData personRemoveData =  new PersonData();
    // Reference to the main application.
    private MainApp mainApp;
    private ObservableList<Person> tempPersonData = FXCollections.observableArrayList();

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

        // Initialize the current people table with the two columns.
        currentFirstNameColumn.setCellValueFactory(cellData -> cellData.getValue().firstNameProperty());
        currentLastNameColumn.setCellValueFactory(cellData -> cellData.getValue().lastNameProperty());

        // Listen for selection changes and show the person details when changed.
        //personTable.getSelectionModel().selectedItemProperty().addListener(
        //        (observable, oldValue, newValue) -> showPersonDetails(newValue));
    }

    // Populate the first table with whichever (parents or children) have not been added.
    // Populate the second table with whichever (parents or children) already exist in the parents or children list.
    // Handle "Add >>>"
    @FXML
    private void handleAdd() {
        Person newPerson = possibleParentsOrChildrenTable.getSelectionModel().getSelectedItem();

        // Keeps the Remove Data and Add Data Lists Consistent
        if (personRemoveData.getParentsList().contains(newPerson)) {
            personRemoveData.removeParent(newPerson);
        } else if (!person.getParentsList().contains(newPerson)) {
            personAddData.addParent(newPerson);
        } else {
            System.out.println("Error: Parent Not Found");
        }

        // Adds the selected person to the list of Parents to add.
        personAddData.addParent(newPerson);
        // Should update the 'parents' data.
        ObservableList<Person> tempParentsList = FXCollections.observableArrayList(personAddData.getParentsList());
        // Removes the temporary people in the personCopy list
        ObservableList<Person> nonParentsDataList = FXCollections.observableArrayList(getNonParentsData());
        nonParentsDataList.removeAll(tempParentsList);
        nonParentsDataList.addAll(personRemoveData.getParentsList());

        possibleParentsOrChildrenTable.setItems(nonParentsDataList);

        // Adds the current parents to the 'current' table (Including the temporarily stored ones.)
        tempParentsList.addAll(person.getParentsList());
        currentParentsOrChildrenTable.setItems(tempParentsList);
    }
    // Handle "<<< Remove"
    @FXML
    private void handleRemove() {
        Person newPerson = currentParentsOrChildrenTable.getSelectionModel().getSelectedItem();
        // I want to remove it from the current temp list or add it to a removal list
        // Keeps the Remove Data and Add Data Lists Consistent
        if (personAddData.getParentsList().contains(newPerson)) {
            personAddData.removeParent(newPerson);
        } else if (person.getParentsList().contains(newPerson)) { // Side note: I have to make it so that if i do a removal and want to later add i can handle that as well.
            personRemoveData.addParent(newPerson);
        } else {
            System.out.println("Error: Parent Not Found");
        }

        ObservableList<Person> tempParentsList = FXCollections.observableArrayList(personAddData.getParentsList());
        // Removes the temporary people in the personCopy list
        ObservableList<Person> nonParentsDataList = FXCollections.observableArrayList(getNonParentsData());
        nonParentsDataList.addAll(personRemoveData.getParentsList());

        possibleParentsOrChildrenTable.setItems(nonParentsDataList);
        currentParentsOrChildrenTable.setItems(tempParentsList);
    }

    // Handle "New...", I want this to continue to a new scene but remember that there was this scene to go back to as a previous scene
    // Maybe I shouldn't do the "New..." button

    // Considering adding an "Edit..." button
    // Handle "OK"
    // Handle "Cancel"
    @FXML
    private void handleCancel() {
        // Doesn't finalize any changes, just returns to the previous screen.
        mainApp.showPersonEditOverviewScene(person);

    }

    // Allows the scene to grab the main application.
    public void setMainApp(@SuppressWarnings("exports") MainApp mainApp, Person person) {
        this.mainApp = mainApp;
        setPerson(person);
        // Supposed to run and create a new database so that the cancel can return to this upon usage.
        setTempPersonData();
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

    // I need to set the tempPerson on Init as well.
    public void setTempPerson() {
        Person thisPerson = this.person;
        String firstName = thisPerson.getFirstName();
        String middleName = thisPerson.getMiddleName();
        String lastName = thisPerson.getLastName();
        int month = thisPerson.getDateOfBirth().getMonthValue();
        int date = thisPerson.getDateOfBirth().getDayOfMonth();
        int year = thisPerson.getDateOfBirth().getYear();
        String biography = thisPerson.getBiography();
        this.tempPerson = new Person(firstName, middleName, lastName, month, date, year, biography);
        this.tempPerson.setChildrenList(thisPerson.getChildrenList());
        this.tempPerson.setParentsList(thisPerson.getParentsList());
    }

    private void setTempPersonData() {
        this.tempPersonData = FXCollections.observableArrayList(mainApp.getPersonData());
    }

    private ObservableMap<Integer, Person> createPersonDataMap(ObservableList<Person> personList) {
        // Create the map object
        ObservableMap<Integer, Person> myPersonDataObservableMap = FXCollections.observableHashMap();
        // pull the person data
        ArrayList<Person> personData = new ArrayList<>(personList);
        // map it
        int num = 0;
        for (Person person : personData) {
            Integer key = num;
            myPersonDataObservableMap.put(key, person);
            num++;
        }
        // I need to find the current person selected.
        // return the map
        return myPersonDataObservableMap;
    }
}
