package view;

import databaseController.MainApp;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import model.Person;
import util.DateUtil;
import util.GraphView;

public class GraphViewController {
    @FXML
    private TableView<Person> personTable;
    @FXML
    private TableColumn<Person, String> firstNameColumn;
    @FXML
    private TableColumn<Person, String> lastNameColumn;

    @FXML
    private TextField firstNameTextField;
    @FXML
    private TextField middleNameTextField;
    @FXML
    private TextField lastNameTextField;
    @FXML
    private TextField parentsTextField;
    @FXML
    private Label parentsLabel;
    @FXML
    private Label parentsNamesLabel;
    @FXML
    private Label updateParentsLabel;
    @FXML
    private TextField childrenTextField;
    @FXML
    private Label childrenLabel;
    @FXML
    private Label childrenNamesLabel;
    @FXML
    private Label updateChildrenLabel;
    @FXML
    private TextField dateOfBirthTextField;
    @FXML
    private TextArea biographyTextArea;
    @FXML
    private BorderPane graphView;

    // Reference to the main application.
    private MainApp mainApp;

    private Person person;
    private Person centralNodePerson;


    /** The constructor.
     The constructor is called before the initialize() method.
     */
    public GraphViewController() {
    }

    @FXML
    private void initialize() {
        // Initialize the person table with the two columns.
        firstNameColumn.setCellValueFactory(cellData -> cellData.getValue().firstNameProperty());
        lastNameColumn.setCellValueFactory(cellData -> cellData.getValue().lastNameProperty());
        // Listen for selection changes and show the person details when changed.
        personTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> setPerson(newValue));

    }

    public void setMainApp(@SuppressWarnings("exports") MainApp mainApp, Person person) {
        this.mainApp = mainApp;
        this.centralNodePerson = person;

        // Add observable list data to the table
        personTable.setItems(mainApp.getPersonData());
        plotGraph(centralNodePerson);
    }

    public void setPerson(@SuppressWarnings("exports") Person person) {
        this.person = person;

        firstNameTextField.setText(person.getFirstName());
        middleNameTextField.setText(person.getMiddleName());
        lastNameTextField.setText(person.getLastName());
        //parentsTextField.setText(Integer.toString(person.getParentsCount()));
        //childrenTextField.setText(Integer.toString(person.getChildrenCount()));
        parentsLabel.setText("Parents: " + Integer.toString(person.getParentsCount()));
        childrenLabel.setText("Children: " + Integer.toString(person.getChildrenCount()));

        if (person.getParentsCount() == 0) {
            parentsNamesLabel.setText("No available parents.");
        } else {
            parentsNamesLabel.setText(person.getFirstMiddleLastNameOfParents());
        }

        if (person.getChildrenCount() == 0) {
            childrenNamesLabel.setText("No available children.");
        } else {
            childrenNamesLabel.setText(person.getFirstMiddleLastNameOfChildren());
        }

        dateOfBirthTextField.setText(DateUtil.format(person.getDateOfBirth()));
        dateOfBirthTextField.setPromptText("dd.mm.yyyy");
        biographyTextArea.setText(person.getBiography());
    }

    /**
     * Called when the user clicks cancel. Returns the user to the previous screen.
     */
    @FXML
    private void handleCancel() {
        mainApp.showPersonOverview();
    }

    private void plotGraph(Person centralNodePerson) {
        // Find the location that you want the graph plotted.
        BorderPane location = this.graphView;
        // then give the instructions on plotting the graph.
        GraphView graph = new GraphView(centralNodePerson, mainApp.getPersonData());
        location.setCenter(graph.plot());
    }
}
