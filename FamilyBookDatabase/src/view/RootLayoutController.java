package view;

import javafx.fxml.FXML;
import javafx.stage.FileChooser;

import java.io.File;

import databaseController.MainApp;

public class RootLayoutController {
	
	// Reference to the main application
	private MainApp mainApp;
	
	//
	public void setMainApp(@SuppressWarnings("exports") MainApp mainApp) {
		this.mainApp = mainApp;
	}
	
	/**
	 *  Creates a new family book.
	 */
	@FXML
	public void fileNewHandler() {
		mainApp.getPersonData().clear();
        mainApp.setPersonFilePath(null);
	}
	
	/**
	 *  Opens a pre-existing family book file.
	 */
	@FXML
	public void fileOpenHandler() {
		// Opens a file chooser
		FileChooser fileChooser = new FileChooser();
		// Set an extension filter("Description", "*.ext1")
		FileChooser.ExtensionFilter extensionFilt = new FileChooser.ExtensionFilter("JSON files (*.json)", "*.json");
		fileChooser.getExtensionFilters().add(extensionFilt);
		
		// Show open file dialog
		File file = fileChooser.showOpenDialog(mainApp.getPrimaryStage());
		
		if (file != null) {
			mainApp.loadPersonDataFromFile(file);
		}
	}
	
	/**
	 *  Saves the current instance on the current file or creates a new file to save the data on.
	 */
	@FXML
	public void fileSaveHandler() {
		File personFile = mainApp.getPersonFilePath();
        if (personFile != null) {
            mainApp.savePersonDataToFile(personFile);
        } else {
            fileSaveAsHandler();
        }
	}
	
	/**
	 *  Saves the information as a new file
	 */
	@FXML
	public void fileSaveAsHandler() {
		// Opens a file chooser
		FileChooser fileChooser = new FileChooser();
		// Set an extension filter("Description", "*.ext1")
		FileChooser.ExtensionFilter extensionFilt = new FileChooser.ExtensionFilter("JSON files (*.json)", "*.json");
		fileChooser.getExtensionFilters().add(extensionFilt);
		
		// Show save file dialog
		File file = fileChooser.showSaveDialog(mainApp.getPrimaryStage());
		
		if (file != null) {
			// Make sure it has the correct extension
			if (!file.getPath().endsWith(".json")) {
				file = new File(file.getPath() + ".json");
			}
			mainApp.savePersonDataToFile(file);
		}
	}
	
	/**
	 *  Closes the application
	 */
	@FXML
	public void fileExitHandler() {
		System.exit(0);
		System.out.println("this code ran");
	}
	
	/**
	 * Opens the birthday statistics.
	 */
	@FXML
	private void statisticsBirthdayStatisticshandler() {
	  mainApp.showBirthdayStatistics();
	}
}
