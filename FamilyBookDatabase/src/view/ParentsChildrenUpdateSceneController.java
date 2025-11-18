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
import java.util.Iterator;


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

    private String parentOrChild;

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
        if (parentOrChild.equals("PARENT")) {
            // Handles parent add
            handleParentsAdd();
        } else if (parentOrChild.equals("CHILD")) {
            // Handles children add
            handleChildrenAdd();
        } else {
            System.out.println("ERROR DETERMINING PARENT OR CHILD");
        }

    }
    // Handle "<<< Remove"
    @FXML
    private void handleRemove() {
        if (parentOrChild.equals("PARENT")) {
            // Handles parents remove
            handleParentsRemove();
        } else if (parentOrChild.equals("CHILD")) {
            // Handles children remove
            handleChildrenRemove();
        } else {
            System.out.println("ERROR DETERMINING PARENT OR CHILD");
        }
    }

    // Handle "New...", I want this to continue to a new scene but remember that there was this scene to go back to as a previous scene
    // Maybe I shouldn't do the "New..." button

    // Considering adding an "Edit..." button
    // Handle "OK"
    @FXML
    private void handleOK() {
        if (parentOrChild.equals("PARENT")) {
            // Handles parents OK
            handleParentsOK();
        } else if (parentOrChild.equals("CHILD")) {
            // Handles children OK
            handleChildrenOK();
        } else {
            System.out.println("ERROR DETERMINING PARENT OR CHILD");
        }
    }
    // Handle "Cancel"
    @FXML
    private void handleCancel() {
        // Doesn't finalize any changes, just returns to the previous screen.
        mainApp.showPersonEditOverviewScene(person, "CANCEL");
    }

    // Add method for parents
    private void handleParentsAdd() {
        if (possibleParentsOrChildrenTable.getSelectionModel().getSelectedItem() != null) {
            Person newPerson = possibleParentsOrChildrenTable.getSelectionModel().getSelectedItem();

            // Keeps the Remove Data and Add Data Lists Consistent
            if (personRemoveData.getParentsList().contains(newPerson)) {
                personRemoveData.removeParent(newPerson);
            } else if (!person.getParentsList().contains(newPerson)) {
                personAddData.addParent(newPerson);
            } else {
                System.out.println("Error: Parent Not Found");
            }

            // Removes the temporary people in the personCopy list
            ObservableList<Person> nonParentsDataList = FXCollections.observableArrayList(getNonParentsData());
            ObservableList<Person> tempParentsList = FXCollections.observableArrayList(personAddData.getParentsList());

            //tempParentsList.addAll(person.getParentsList());
            tempParentsList.addAll(person.getParentsList());
            tempParentsList.removeAll(personRemoveData.getParentsList());

            nonParentsDataList.removeAll(tempParentsList);
            nonParentsDataList.addAll(personRemoveData.getParentsList());

            possibleParentsOrChildrenTable.setItems(nonParentsDataList);
            // Adds the current parents to the 'current' table (Including the temporarily stored ones.)
            currentParentsOrChildrenTable.setItems(tempParentsList);
        }
    }

    // Add method for children
    private void handleChildrenAdd() {
        if (possibleParentsOrChildrenTable.getSelectionModel().getSelectedItem() != null) {
            Person newPerson = possibleParentsOrChildrenTable.getSelectionModel().getSelectedItem();

            // Keeps the Remove Data and Add Data Lists Consistent
            if (personRemoveData.getChildrenList().contains(newPerson)) {
                personRemoveData.removeChild(newPerson);
            } else if (!person.getChildrenList().contains(newPerson)) {
                personAddData.addChild(newPerson);
            } else {
                System.out.println("Error: Child Not Found");
            }

            // Removes the temporary people in the personCopy list
            ObservableList<Person> nonChildrenDataList = FXCollections.observableArrayList(getNonChildrenData());
            ObservableList<Person> tempChildrenList = FXCollections.observableArrayList(personAddData.getChildrenList());

            //tempParentsList.addAll(person.getParentsList());
            tempChildrenList.addAll(person.getChildrenList());
            tempChildrenList.removeAll(personRemoveData.getChildrenList());

            nonChildrenDataList.removeAll(tempChildrenList);
            nonChildrenDataList.addAll(personRemoveData.getChildrenList());

            possibleParentsOrChildrenTable.setItems(nonChildrenDataList);
            // Adds the current parents to the 'current' table (Including the temporarily stored ones.)
            currentParentsOrChildrenTable.setItems(tempChildrenList);
        }
    }

    // Remove method for parents
    private void handleParentsRemove() {
        if (currentParentsOrChildrenTable.getSelectionModel().getSelectedItem() != null) {
            Person newPerson = currentParentsOrChildrenTable.getSelectionModel().getSelectedItem();
            // I want to remove it from the current temp list or add it to a removal list
            // Keeps the Remove Data and Add Data Lists Consistent
            if (personAddData.getParentsList().contains(newPerson)) {
                personAddData.removeParent(newPerson);
                //System.out.println("Person removed from add data");
            } else if (person.getParentsList().contains(newPerson)) {
                personRemoveData.addParent(newPerson);
            } else {
                System.out.println("Error: Parent Not Found");
            }

            // Removes the temporary people in the personCopy list
            ObservableList<Person> tempParentsList = FXCollections.observableArrayList(personAddData.getParentsList());
            // This is for previously saved parents
            tempParentsList.addAll(person.getParentsList());
            tempParentsList.removeAll(personRemoveData.getParentsList());

            ObservableList<Person> nonParentsDataList = FXCollections.observableArrayList(getNonParentsData());
            nonParentsDataList.removeAll(tempParentsList);
            nonParentsDataList.addAll(personRemoveData.getParentsList());

            // Updates Each Table
            possibleParentsOrChildrenTable.setItems(nonParentsDataList);
            currentParentsOrChildrenTable.setItems(tempParentsList);
        }
    }

    // Remove method for children
    private void handleChildrenRemove() {
        if (currentParentsOrChildrenTable.getSelectionModel().getSelectedItem() != null) {
            Person newPerson = currentParentsOrChildrenTable.getSelectionModel().getSelectedItem();
            // I want to remove it from the current temp list or add it to a removal list
            // Keeps the Remove Data and Add Data Lists Consistent
            if (personAddData.getChildrenList().contains(newPerson)) {
                personAddData.removeChild(newPerson);
                //System.out.println("Person removed from add data");
            } else if (person.getChildrenList().contains(newPerson)) {
                personRemoveData.addChild(newPerson);
            } else {
                System.out.println("Error: Child Not Found");
            }

            // Removes the temporary people in the personCopy list
            ObservableList<Person> tempChildrenList = FXCollections.observableArrayList(personAddData.getChildrenList());
            // This is for previously saved parents
            tempChildrenList.addAll(person.getChildrenList());
            tempChildrenList.removeAll(personRemoveData.getChildrenList());

            ObservableList<Person> nonChildrenDataList = FXCollections.observableArrayList(getNonChildrenData());
            nonChildrenDataList.removeAll(tempChildrenList);
            nonChildrenDataList.addAll(personRemoveData.getChildrenList());

            // Updates Each Table
            possibleParentsOrChildrenTable.setItems(nonChildrenDataList);
            currentParentsOrChildrenTable.setItems(tempChildrenList);
        }
    }

    // OK method for parents
    private void handleParentsOK() {
        // On OK I want all the added parents to be added and all the removed parents to be removed
        ObservableList<Person> addData = FXCollections.observableArrayList(personAddData.getParentsList());
        ObservableList<Person> removeData = FXCollections.observableArrayList(personRemoveData.getParentsList());

        Iterator<Person> addIterator = addData.iterator();
        while (addIterator.hasNext()) {
            person.addParent(addIterator.next());
        }

        Iterator<Person> removeIterator = removeData.iterator();
        while (removeIterator.hasNext()) {
            person.removeParent(removeIterator.next());
        }
        mainApp.showPersonEditOverviewScene(person, "EMPTY");
    }

    // OK method for children
    private void handleChildrenOK() {
        // On OK I want all the added children to be added and all the removed children to be removed
        ObservableList<Person> addData = FXCollections.observableArrayList(personAddData.getChildrenList());
        ObservableList<Person> removeData = FXCollections.observableArrayList(personRemoveData.getChildrenList());

        Iterator<Person> addIterator = addData.iterator();
        while (addIterator.hasNext()) {
            person.addChild(addIterator.next());
        }

        Iterator<Person> removeIterator = removeData.iterator();
        while (removeIterator.hasNext()) {
            person.removeChild(removeIterator.next());
        }
        mainApp.showPersonEditOverviewScene(person, "EMPTY");
    }

    // Allows the scene to grab the main application.
    public void setMainApp(@SuppressWarnings("exports") MainApp mainApp, Person person, String parentOrChild) {
        this.mainApp = mainApp;
        setPerson(person);
        this.parentOrChild = parentOrChild;
        // Supposed to run and create a new database so that the cancel can return to this upon usage.
        setTempPersonData();
        // Add observable list data for parents to the table
        if (parentOrChild.equals("PARENT")) {
            possibleParentsOrChildrenTable.setItems(getNonParentsData());
            currentParentsOrChildrenTable.setItems(FXCollections.observableArrayList(person.getParentsList()));
        } else if (parentOrChild.equals("CHILD")) {
            possibleParentsOrChildrenTable.setItems(getNonChildrenData());
            currentParentsOrChildrenTable.setItems(FXCollections.observableArrayList(person.getChildrenList()));
        } else {
            System.out.println("Error: Current Selection of Parent or Child Undefined.");
        }


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
