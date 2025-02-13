package view;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import model.Person;
import util.DateUtil;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

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
	
    private Stage dialogStage;
    private Person person;
    private boolean okClicked = false;
    
    //initializes the controller class
    private void initialize() {
    }
    
    //Sets the stage of this dialogue
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }
    
    //Sets the textfield's information using the person information
    public void setPerson(Person person) {
        this.person = person;

        firstNameTextField.setText(person.getFirstName());
        middleNameTextField.setText(person.getMiddleName());
        lastNameTextField.setText(person.getLastName());
        parentsTextField.setText(Integer.toString(person.getParents()));
        childrenTextField.setText(Integer.toString(person.getChildren()));
        dateOfBirthTextField.setText(DateUtil.format(person.getDateOfBirth()));
        dateOfBirthTextField.setPromptText("dd.mm.yyyy");
    }
    
    /**
     * Returns true if the user clicked OK, false otherwise.
     * 
     * @return
     */
    public boolean isOkClicked() {
        return okClicked;
    }
    
    //Sets the info of the edited person to the edited information.
    @FXML
    private void handleOk() {
        if (isInputValid()) {
            person.setFirstName(firstNameField.getText());
            person.setLastName(lastNameField.getText());
            person.setStreet(streetField.getText());
            person.setPostalCode(Integer.parseInt(postalCodeField.getText()));
            person.setCity(cityField.getText());
            person.setBirthday(DateUtil.parse(birthdayField.getText()));

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
     * 
     * @return true if the input is valid
     */
    private boolean isInputValid() {
        String errorMessage = "";

        if (firstNameTextField.getText() == null || firstNameTextField.getText().length() == 0) {
            errorMessage += "No valid first name!\n"; 
        }
        if (middleNameTextField.getText() == null || middleNameTextField.getText().length() == 0) {
            errorMessage += "No valid middle name!\n"; 
        }
        if (lastNameTextField.getText() == null || lastNameTextField.getText().length() == 0) {
            errorMessage += "No valid last name!\n"; 
        }

        if (parentsTextField.getText() == null || parentsTextField.getText().length() == 0) {
            errorMessage += "No valid amount of parents!\n"; 
        } else {
            // try to parse the postal code into an int.
            try {
                Integer.parseInt(parentsTextField.getText());
            } catch (NumberFormatException e) {
                errorMessage += "Value for 'Amount of Parents' invalid! (must be an integer)\n"; 
            }
        }

        if (childrenTextField.getText() == null || childrenTextField.getText().length() == 0) {
            errorMessage += "Value for 'Amount of Children' missing!\n"; 
        } else {
        	try {
        		Integer.parseInt(childrenTextField.getText());
        	} catch (NumberFormatException e) {
        		errorMessage += "Value for 'Amount of Children' invalid! (must be an integer)\n";
        	}
        }

        if (birthdayField.getText() == null || birthdayField.getText().length() == 0) {
            errorMessage += "No valid birthday!\n";
        } else {
            if (!DateUtil.validDate(birthdayField.getText())) {
                errorMessage += "No valid birthday. Use the format dd.mm.yyyy!\n";
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
