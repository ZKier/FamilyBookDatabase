package view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.io.IOException;

import databaseController.MainApp;
import model.Person;
import util.DateUtil;

public class OverviewController {
	@FXML
    private TableView<Person> personTable;
    @FXML
    private TableColumn<Person, String> firstNameColumn;
    @FXML
    private TableColumn<Person, String> lastNameColumn;

    @FXML
    private Label firstNameLabel;
    @FXML
    private Label middleNameLabel;
    @FXML
    private Label lastNameLabel;
    @FXML
    private Label parentsLabel;
    @FXML
    private Label childrenLabel;
    @FXML
    private Label dateOfBirthLabel;

    // Reference to the main application.
    private MainApp mainApp;

/*
     * The constructor.
     * The constructor is called before the initialize() method.
*/
    public OverviewController() {
    }

/*
     * Initializes the controller class. This method is automatically called
     * after the FMXL file has been loaded.
*/
    @FXML
    private void initialize() {
    	// Initialize the person table with the two columns.
        firstNameColumn.setCellValueFactory(cellData -> cellData.getValue().firstNameProperty());
        lastNameColumn.setCellValueFactory(cellData -> cellData.getValue().lastNameProperty());
        //Example for integer columns
        //myIntegerColumn.setCellValueFactory(cellData -> cellData.getValue().myIntegerProperty().asObject());
        
        // Clear person details.
        showPersonDetails(null);

        // Listen for selection changes and show the person details when changed.
        personTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showPersonDetails(newValue));
    }

/*
     * Is called by the main application to give a reference back to itself.
     * @param mainApp
*/
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;

        // Add observable list data to the table
        personTable.setItems(mainApp.getPersonData());
    }

/*
     * Fills all text fields to show details about the person.
     * If the specified person is null, all text fields are cleared.
     * @param person the person or null
*/
    private void showPersonDetails(Person person) {
        if (person != null) {
            // Fill the labels with info from the person object.
            firstNameLabel.setText(person.getFirstName());
            middleNameLabel.setText(person.getMiddleName());
            lastNameLabel.setText(person.getLastName());
            
            parentsLabel.setText(Integer.toString(person.getParents()));
            childrenLabel.setText(Integer.toString(person.getChildren()));
            
            //Convert the birthday into a String! 
            dateOfBirthLabel.setText(DateUtil.format(person.getDateOfBirth()));
            // birthdayLabel.setText(...);
        } else {
            // Person is null, remove all the text.
            firstNameLabel.setText("");
            middleNameLabel.setText("");
            lastNameLabel.setText("");
            
            parentsLabel.setText("");
            childrenLabel.setText("");
            dateOfBirthLabel.setText("");
        }
    }
    
    // Called when the user clicks on the delete button.
    @FXML
    private void handleDeletePerson() {
        int selectedIndex = personTable.getSelectionModel().getSelectedIndex();
        personTable.getItems().remove(selectedIndex);
        if (selectedIndex >= 0) {
            personTable.getItems().remove(selectedIndex);
        } else {
        // Nothing selected.
        Alert alert = new Alert(AlertType.WARNING);
        alert.initOwner(mainApp.getPrimaryStage());
        alert.setTitle("No Selection");
        alert.setHeaderText("No Person Selected");
        alert.setContentText("Please select a person in the table.");

        alert.showAndWait();
    	
        }
	}
    
    // Called when the user clicks the new button. Opens a dialog to edit details for a new person.
    @FXML
    private void handleNewPerson() {
        Person tempPerson = new Person();
        boolean okClicked = mainApp.showPersonEditOverview(tempPerson);
        if (okClicked) {
            mainApp.getPersonData().add(tempPerson);
        }
    }
    
    // Called when the user clicks on the edit button.
    @FXML
    private void handleEditPerson() {
        Person selectedPerson = personTable.getSelectionModel().getSelectedItem();
        if (selectedPerson != null) {
        	System.out.println(selectedPerson.getFirstName()); // Prints the first name so someone is selected
            boolean okClicked = mainApp.showPersonEditOverview(selectedPerson);
            if (okClicked) {
                showPersonDetails(selectedPerson);
            }

        } else {
            // Nothing selected.
            Alert alert = new Alert(AlertType.WARNING);
            alert.initOwner(mainApp.getPrimaryStage());
            alert.setTitle("No Selection");
            alert.setHeaderText("No Person Selected");
            alert.setContentText("Please select a person in the table.");

            alert.showAndWait();
        }
    }

}
