package view;

import java.net.URL;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import model.Person;
import org.json.JSONArray;
import util.DateUtil;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import databaseController.MainApp;

public class PersonEditViewController {
	@FXML
	private TextField firstNameTextField;
    @FXML
    private TextField middleNameTextField;
    @FXML
    private TextField lastNameTextField;
    @FXML
    private TextField parentsTextField;
    @FXML
    private TextField childrenTextField;
    @FXML
    private TextField dateOfBirthTextField;
    @FXML
    private TextArea biographyTextArea;

    // Reference to the main application.
    private MainApp mainApp;
    @FXML
    private ComboBox<String> childrenNameBox = new ComboBox<>();
	
    private Stage dialogStage;
    private Person person;
    private boolean okClicked = false;

/*
    public void setMainApp(@SuppressWarnings("exports") MainApp mainApp) {
        this.mainApp = mainApp;

        // Add observable list data to the table
        personTable.setItems(mainApp.getPersonData());
    }
    // Populate the lists
*/


    /** The constructor.
     The constructor is called before the initialize() method.
     */
    public PersonEditViewController() {
    }
    
    //initializes the controller class
    @FXML
    private void initialize() {
    }
    
    //Sets the stage of this dialogue
    public void setDialogStage(@SuppressWarnings("exports") Stage dialogStage) {
        this.dialogStage = dialogStage;
        // Adds an icon to the stage
        URL imageUrl = getClass().getResource("/resources/images/database_image_freepik2.png");
        this.dialogStage.getIcons().add(new Image(imageUrl.toExternalForm()));
    }
    
    // Sets the textfield's information using the person information
    public void setPerson(@SuppressWarnings("exports") Person person) {
        this.person = person;

        firstNameTextField.setText(person.getFirstName());
        middleNameTextField.setText(person.getMiddleName());
        lastNameTextField.setText(person.getLastName());
        parentsTextField.setText(Integer.toString(person.getParents()));
        childrenTextField.setText(Integer.toString(person.getChildren()));
        dateOfBirthTextField.setText(DateUtil.format(person.getDateOfBirth()));
        dateOfBirthTextField.setPromptText("dd.mm.yyyy");
        biographyTextArea.setText(person.getBiography());
    }

    // Populates the dropdown of children
    public void setChildrenNameBox(ObservableList<Person> personData) {
        // Work through the list
        java.util.Iterator<Person> iterator = personData.iterator();

        while (iterator.hasNext()) {
            Person person = iterator.next();
            // Populates Children's Name dropdown box.
            String firstAndLastName = person.getFirstName() + " " + person.getLastName();
            childrenNameBox.getItems().add(firstAndLastName);
        }
        // Now the issue is making that link between children and parent, then showing the data on the chart. when i click.
    }
    
    /**
     * Returns true if the user clicked OK, false otherwise.
     * @return
     */
    public boolean isOkClicked() {
        return okClicked;
    }
    
    /**
     * Sets the info of the edited person to the edited information.
     */
    @FXML
    private void handleOk() {
    	if (isInputValid()) {
    		person.setFirstName(firstNameTextField.getText());
    		person.setMiddleName(middleNameTextField.getText());
    		person.setLastName(lastNameTextField.getText());

            // If parentsTextField is empty, return 0, else return .getText()
            if (parentsTextField.getText().isEmpty() || childrenTextField.getText() == null) {
                person.setParents(0);
            } else {
                person.setParents(Integer.parseInt(parentsTextField.getText()));
            }

            // If childrenTextField is empty, return 0, else return .getText()
            if (childrenTextField.getText().isEmpty() || childrenTextField.getText() == null) {
                person.setChildren(0);
            } else {
                person.setChildren(Integer.parseInt(childrenTextField.getText()));
            }

            person.setDateOfBirth(DateUtil.parse(dateOfBirthTextField.getText()));
            person.setBiography(biographyTextArea.getText());

            okClicked = true;
            dialogStage.close();
        }
    }
    
    /**
     * Called when the user clicks cancel.
     */
    @FXML
    private void handleCancel() {
        dialogStage.close();
    }

    /**
     * Validates the user input in the text fields.
     * @return true: If the input is valid.
     */
    private boolean isInputValid() {
    	String errorMessage = "";
    	// First name handler
    	if (firstNameTextField.getText() == null || firstNameTextField.getText().length() == 0) {
    		errorMessage += "No valid first name!\n"; 
    	}
    	// Middle name handler
        /*
    	if (middleNameTextField.getText() == null || middleNameTextField.getText().length() == 0) {
    		errorMessage += "No valid middle name!\n"; 
    	}
        */
    	// Last name handler
    	if (lastNameTextField.getText() == null || lastNameTextField.getText().length() == 0) {
    		errorMessage += "No valid last name!\n"; 
    		}
    	// Parents handler
    	if (parentsTextField.getText() == null || parentsTextField.getText().length() == 0) {
    		// Handled elsewhere
    	} else {
    		// Try to parse the text field into an integer.
    		try {
    			Integer.parseInt(parentsTextField.getText());
    		} catch (NumberFormatException e) {
    			errorMessage += "Value for 'Amount of Parents' invalid! (must be an integer)\n"; 
    		}
    	}
    	/** Children handler
         * States that if the field is null or == 0,
         * the error message pops up. I DON'T WANT IT TO SHOW THIS, it's pointless
    	* */
    	if (childrenTextField.getText() == null || childrenTextField.getText().length() == 0) {
            // No need to do anything because this is handled elsewhere
        } else {
        	// Try to parse the text field into an integer
        	try {
        		Integer.parseInt(childrenTextField.getText());
        	} catch (NumberFormatException e) {
        		errorMessage += "Value for 'Amount of Children' invalid! (must be an integer)\n";
        	}
        }
    	// Birthday handler
        if (dateOfBirthTextField.getText() == null || dateOfBirthTextField.getText().length() == 0) {
            errorMessage += "No valid birthday!\n";
        } else {
            if (!DateUtil.validDate(dateOfBirthTextField.getText())) {
                errorMessage += "No valid birthday. Use the format dd/mm/yyyy!\n";
            }
        }

        if (errorMessage.length() == 0) {
            return true;
        } else {
            // Show the error message.
            Alert alert = new Alert(AlertType.ERROR);
            alert.initOwner(dialogStage);
            alert.setTitle("Invalid Fields");
            alert.setHeaderText("Please correct invalid fields");
            alert.setContentText(errorMessage);
            
            alert.showAndWait();
            
            return false;
        }
    }


}
