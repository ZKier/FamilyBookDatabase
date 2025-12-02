package databaseController;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.prefs.Preferences;

import javafx.collections.ObservableMap;
import javafx.fxml.FXML;
import javafx.scene.control.ButtonType;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.*;
import model.Person;
import view.*;

import static javafx.stage.WindowEvent.WINDOW_CLOSE_REQUEST;

public class MainApp extends Application {

    private Stage primaryStage;
    private BorderPane rootLayout;

	private ObservableList<Person> personDataList = FXCollections.observableArrayList();
    private ObservableMap<Integer, Person> personDataMap = FXCollections.observableHashMap();

	// Should only have 3 usages
	private Person person;
	private boolean isDataModified = false;
    
    public MainApp() {
		/*
    	// Sample data
		personData.add(new Person("Kacey", "Monster"));
		personData.add(new Person("Roddrick", "Keller"));
		personData.add(new Person("Mistriss", "Rains"));
		personData.add(new Person("Famrik", "Kins"));
		personData.add(new Person("Podrey", "Spays"));
		personData.add(new Person("Assata", "Ritz"));
		personData.add(new Person("Promethius", "Kiss"));
		personData.add(new Person("Steeve", "Powers"));
		personData.add(new Person("Mitchell", "Lumphrey"));
		*/
	}
    
    // Lists Persons
    public ObservableList<Person> getPersonData() {
		return personDataList;
	}

	// Lists Persons
	public ObservableMap<Integer, Person> getPersonDataMap() {
		return personDataMap;
	}

	public void setPersonData(ObservableMap<Integer, Person> personData) {
		this.personDataMap = personData;
	}

	@Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Family Book Database");
        
        // Adds an icon the the stage
        URL imageUrl = getClass().getResource("/resources/images/database_image_freepik2.png");
        this.primaryStage.getIcons().add(new Image(imageUrl.toExternalForm()));
        
        initRootLayout();

        showPersonOverview();

		// Handle reminding user to save before closing application.
		this.primaryStage.setOnCloseRequest(this::onCloseWindow);
        
    }

	private void onCloseWindow(WindowEvent event) {
		if (isDataModified) {
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Unsaved Changes");
			alert.setHeaderText("You have unsaved changes.");
			alert.setContentText("Do you want to save your changes before exiting?");

			ButtonType saveButton = new ButtonType("Save");
			ButtonType discardButton = new ButtonType("Discard");
			ButtonType cancelButton = new ButtonType("Cancel");

			alert.getButtonTypes().setAll(saveButton, discardButton, cancelButton);

			Optional<ButtonType> result = alert.showAndWait();


			if (result.isPresent()) {
				if (result.get() == saveButton) {
					// Perform save operation here
					this.fileSaveHandler();
					isDataModified = false; // Data is now saved
				} else if (result.get() == discardButton) {
					// User chose to discard changes, do nothing
					System.out.println("Discarding changes...");
				} else if (result.get() == cancelButton) {
					// Consume the event to prevent closing
					event.consume();
					System.out.println("Close cancelled.");
				}
			}
		}
	}
    
    // Initializes the root layout.
    public void initRootLayout() {
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("/view/RootLayout.fxml"));
            rootLayout = (BorderPane) loader.load();
            
            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            
            // Give the controller access to the main app.
            RootLayoutController controller = loader.getController();
            controller.setMainApp(this);
            
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Shows the person overview inside the root layout.
    public void showPersonOverview() {
        try {
            // Load person overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("/view/Overview.fxml"));
            AnchorPane personOverview = (AnchorPane) loader.load();
            
            // Set person overview into the center of root layout.
            rootLayout.setCenter(personOverview);

            // Give the controller access to the MainApp.
            OverviewController controller = loader.getController();
            //System.out.println(controller); // returns view.OverviewController@70cf0b45
            controller.setMainApp(this);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

	// Opens a new scene for a new edit view (no new tab)
	public void showPersonEditOverviewScene(Person person, String state) {
		try {
			// Load person edit overview scene.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("/view/PersonEditViewScene.fxml"));
			AnchorPane personEditOverviewScene = (AnchorPane) loader.load();

			// Set person overview into the center of root layout.
			rootLayout.setCenter(personEditOverviewScene);

			// Set the person into the controller.
			// Established this controller using SceneBuilder
			PersonEditViewController controller = loader.getController();
			controller.setPerson(person);

			// Keeps the main app consistent.
			controller.setMainApp(this);

			// Shows code for each person but I want their name to show up. :) ##Progressssss
			//controller.setChildrenNameBox(personData);
			if (state.equals("NEW")) { controller.isNew(); }
			// Show the dialog and wait until the user closes it
			//dialogStage.showAndWait();

		} catch (IOException e) {
			e.printStackTrace();
			//return false;
		}
	}

	// Shows the person overview inside the root layout.
	public void showEditParentChildOverview(Person person, String parentOrChild) {
		try {
			// Load person overview.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("/view/ParentsChildrenUpdateScene.fxml"));
			AnchorPane parentsChildrenUpdateScene = (AnchorPane) loader.load();

			// Set person overview into the center of root layout.
			rootLayout.setCenter(parentsChildrenUpdateScene);

			// Give the controller access to the MainApp.
			ParentsChildrenUpdateSceneController controller = loader.getController();
			//System.out.println(controller); // returns view.OverviewController@70cf0b45
			controller.setMainApp(this, person, parentOrChild);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

    // Allows the initialization of the edit view (New Tab) I want to change this
    /*
	public boolean showPersonEditOverview(Person person) {
        try {
            // Load the FXML file and create a new stage for the pop-up dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("/view/PersonEditView.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Edit Person");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Set the person into the controller.
            // Established this controller using SceneBuilder
            PersonEditViewController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setPerson(person);
			// Shows code for each person but I want their name to show up. :) ##Progressssss
			controller.setChildrenNameBox(personData);

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();

            return controller.isOkClicked();
            
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    */

	public void showGraphView() {
		try {
			// Load person overview.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("/view/GraphViewScene.fxml"));
			AnchorPane graphViewScene = (AnchorPane) loader.load();

			// Set person overview into the center of root layout.
			rootLayout.setCenter(graphViewScene);

			// Give the controller access to the MainApp.
			GraphViewController controller = loader.getController();

			controller.setMainApp(this, person);
			//System.out.println("This ran");

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Returns the main stage.
	 * @return
	 */
	public Stage getPrimaryStage() {
		return primaryStage;
	}

	public static void main(String[] args) {
		launch(args);
	}
	
	
	
	
	
	
	/**
	 * Returns the person file preference, i.e. the file that was last opened.
	 * The preference is read from the OS specific registry. If no such
	 * preference can be found, null is returned.
	 * 
	 * @return
	 */
	public File getPersonFilePath() {
	    Preferences prefs = Preferences.userNodeForPackage(MainApp.class);
	    String filePath = prefs.get("filePath", null);
	    if (filePath != null) {
	        return new File(filePath);
	    } else {
	        return null;
	    }
	}
	
	/**
	 * Sets the file path of the currently loaded file. The path is persisted in
	 * the OS specific registry.
	 * 
	 * @param file the file or null to remove the path
	 */
	public void setPersonFilePath(File file) {
	    Preferences prefs = Preferences.userNodeForPackage(MainApp.class);
	    if (file != null) {
	        prefs.put("filePath", file.getPath());

	        // Update the stage title.
	        primaryStage.setTitle("Family Book Database - " + file.getName());
	    } else {
	        prefs.remove("filePath");

	        // Update the stage title.
	        primaryStage.setTitle("Family Book Database");
	    }
	}

	public void fileSaveHandler() {
		File personFile = this.getPersonFilePath();
		if (personFile != null) {
			this.savePersonDataToFile(personFile);
		} else {
			fileSaveAsHandler();
		}
	}

	/**
	 *  Saves the information as a new file
	 */

	public void fileSaveAsHandler() {
		// Opens a file chooser
		FileChooser fileChooser = new FileChooser();
		// Set an extension filter("Description", "*.ext1")
		FileChooser.ExtensionFilter extensionFilt = new FileChooser.ExtensionFilter("JSON files (*.json)", "*.json");
		fileChooser.getExtensionFilters().add(extensionFilt);

		// Show save file dialog
		File file = fileChooser.showSaveDialog(this.getPrimaryStage());

		if (file != null) {
			// Make sure it has the correct extension
			if (!file.getPath().endsWith(".json")) {
				file = new File(file.getPath() + ".json");
			}
			this.savePersonDataToFile(file);
		}
	}
	
	/**
	 *  Saves Person data to a file
	 *  @param file the file 
	 */
	public void savePersonDataToFile(File file) {
		try {
			// Pull the main list from the map to utilize the (key, value) pairing
			JSONArray jsonArray = new JSONArray();
			for (Integer key : this.personDataMap.keySet()) {
				jsonArray.put(personToJSON(this.personDataMap.get(key)));
			}
			
			// Wrap JSONarray so that the person data can be pulled directly
//			JSONObject jsonObject = new JSONObject();
//			jsonObject.put("Persons", jsonArray);
			
			// write and save JSON data to file
			FileWriter writer = new FileWriter(file);
			writer.write(jsonArray.toString(2));
			writer.close();
			
			// Save the file path to the registry
			setPersonFilePath(file);
			setDataNotModified(); // Make sure when exiting it doesn't pull up the modify screen.
			
		} catch (Exception e) {
	        Alert alert = new Alert(AlertType.ERROR);
	        alert.setTitle("Error");
	        alert.setHeaderText("Could not save data");
	        alert.setContentText("Could not save data to file:\n" + file.getPath());

	        alert.showAndWait();
		}
	}
	
	// Helper function
	public JSONObject personToJSON(Person person) {
		//Person(String firstName, String middleName, String lastName, int parents, int children, int month, int day, int year)
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", person.getID());
		jsonObject.put("firstName", person.getFirstName());
		jsonObject.put("middleName", person.getMiddleName());
		jsonObject.put("lastName", person.getLastName());
		jsonObject.put("parents", person.getParentsCount()); // Kinda deprecated
		jsonObject.put("children", person.getChildrenCount()); // Kinda deprecated
		jsonObject.put("month", person.getDateOfBirth().toString().substring(5, 7));
		jsonObject.put("day", person.getDateOfBirth().toString().substring(8, 10));
		jsonObject.put("year", person.getDateOfBirth().toString().substring(0, 4));
		jsonObject.put("biography", person.getBiography());

		// Puts all the parents into the person's information
		JSONArray jsonArrayParents = new JSONArray();
		for (Person p : person.getParentsList()) {
			JSONObject id = new JSONObject();
			id.put("id", p.getID());
			jsonArrayParents.put(id);
		}
		jsonObject.put("parentsList", jsonArrayParents);

		// Puts all the parents into the person's information
		JSONArray jsonArrayChildren = new JSONArray();
		for (Person p : person.getChildrenList()) {
			JSONObject id = new JSONObject();
			id.put("id", p.getID());
			jsonArrayChildren.put(id);
		}
		jsonObject.put("childrenList", jsonArrayChildren);

		return  jsonObject;
	}
	
	/**
	 *  Loads Person data from the specified file. The current person data will be replaced.
	 */
	public void loadPersonDataFromFile(File file) {
		this.personDataMap.clear();
		this.personDataList.clear();
		try {
			FileReader fileReader = new FileReader(file);
			try {
				try {
					// Turn the information into a String
					StringBuilder information = new StringBuilder();
					int data;
					while ((data = fileReader.read()) != -1) {
						information.append((char) data);
					}
					String result = information.toString();

					try {
						fileReader.close();
						//System.out.println(information);
						try {
							// Convert String to JSON array
							JSONArray myFile = new JSONArray(result);

							try {
								// Convert each JSON array object into an individual object to create a person object
								for (Object personObject : myFile) { readPerson(personObject); }
								// Convert the keys of people to parents and children.
								for (Object personObject : myFile) { readParentsAndChildren(personObject); }
							} catch (Exception e) {
								System.out.println("Error reading people, parents, and children.");
							}


							// Save the file path to the registry.
							setPersonFilePath(file);
						} catch (Exception e) {
							System.out.println("Error in file Array, or setting file path");
						}
					} catch (Exception e) {
						System.out.println(e);
						Alert alert = new Alert(AlertType.ERROR);
						alert.setTitle("Error");
						alert.setHeaderText("Could not close file reader");
						alert.setContentText("Could not load data from file:\n" + file.getPath());

						alert.showAndWait();
					}

				} catch (Exception e) {
					System.out.println(e);
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Error");
					alert.setHeaderText("Could not convert file to String");
					alert.setContentText("Could not load data from file:\n" + file.getPath());

					alert.showAndWait();
				}
			} catch (Exception e) {
				System.out.println(e);
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Error");
				alert.setHeaderText("Could not load data within file");
				alert.setContentText("Could not load data from file:\n" + file.getPath());

				alert.showAndWait();
			}
		} catch (Exception e) {
			System.out.println(e);
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText("File Error: Could not load file");
			alert.setContentText("Could not load data from file:\n" + file.getPath());

			alert.showAndWait();
		}
	}

	// Helper method for reading people.
	private void readPerson(Object personObject) {
		JSONObject person = (JSONObject) personObject;

		try {
			String firstName = (String) person.get("firstName");
			//System.out.println(firstName);
			String middleName = (String) person.get("middleName");
			//System.out.println(middleName);
			String lastName = (String) person.get("lastName");
			//System.out.println(lastName);
			int parents = (int) person.get("parents");
			//System.out.println(parents);
			int children = (int) person.get("children");
			//System.out.println(children);
			int month = Integer.parseInt( (String) person.get("month"));
			//System.out.println(month);
			int day = Integer.parseInt( (String) person.get("day"));
			//System.out.println(day);
			int year = Integer.parseInt((String) person.get("year"));
			//System.out.println(year);
			String biography = "";
			//System.out.println("biography pre-update:" + biography);
			try {
				biography = (String) person.get("biography");
				//System.out.println("biography post-update:" + biography);

			} catch (JSONException e) {
				System.out.println("biography missing.");
			}

			// Create the current person object
			Person thisPerson = new Person(firstName, middleName, lastName, parents, children, month, day, year, biography);

			try {
				int id = (int) person.get("id");
				thisPerson.setID(id);
			} catch (JSONException e) {
				System.out.println("ID missing.");
			}

			// Add the loaded data to the screen if the person doesn't exist in the list already. updates the key value
			if (!this.personDataMap.containsValue(thisPerson) && thisPerson.getID() == (null)) {
				Integer maxKey = -1;
				if (!this.personDataMap.isEmpty()) { maxKey = Collections.max(this.personDataMap.keySet()); }
				maxKey += 1;

				// Keeps the ID consistent
				thisPerson.setID(maxKey);
				this.personDataMap.put(maxKey, thisPerson); // Add to Map
				personDataList.add(thisPerson); // Add to list
			} else if (!this.personDataMap.containsValue(thisPerson)) {
				this.personDataMap.put(thisPerson.getID(), thisPerson); // Add to Map
				personDataList.add(thisPerson); // Add to list
			}
		} catch (JSONException e) {
			System.out.println("Unable to retrieve all necessary person information");
		}
	}

	private void readParentsAndChildren(Object personObject) {
		try {
			JSONObject person = (JSONObject) personObject;
			try {
				Integer thisPersonKey = (int) person.get("id");
				Person thisPerson = personDataMap.get(thisPersonKey);
				try {
					// Add Parents
					JSONArray parents = person.getJSONArray("parentsList"); // JSONObject should be converted to a JSONArray
					for (Object parent : parents) {
						// gets the object and converts it back to a JSONObject
						JSONObject parentObject = (JSONObject) parent;
						Integer thisParentID = (int) parentObject.get("id");  // turns it into an Integer
						Person thisParent = personDataMap.get(thisParentID); // retrieves the parent from the map
						// Adds the parent to the list if it's not already there.
						if (!thisPerson.getParentsList().contains(thisParent)) {
							thisPerson.addParent(thisParent);
						}
					}
				} catch (JSONException e) {
					System.out.println("Error loading parents");
				}

				try {
					// Add Children
					JSONArray children = person.getJSONArray("childrenList");
					for (Object child : children) {
						// gets the object
						JSONObject childObject = (JSONObject) child;
						Integer thisChildID = (int) childObject.get("id"); // turns it into an Integer
						Person thisChild = personDataMap.get(thisChildID); // retrieves the child from the map
						// Adds the child to the list if it's not already there.
						if (!thisPerson.getChildrenList().contains(thisChild)) {
							thisPerson.addChild(thisChild);
						}
					}
				} catch (JSONException e) {
					System.out.println("Error loading children");
				}
			} catch (JSONException e) {
				System.out.println("Can't load parents or children, no IDs present");
			}
		} catch (JSONException e) {
			System.out.println("Issue loading personObject for parents and children");
		}
	}
	
	/**
	 * Opens a dialog to show birthday statistics.
	 */
	public void showBirthdayStatistics() {
		try {
			// Load the FXML file and create a new stage for the pop-up.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("/view/BirthdayStatistics.fxml"));
			AnchorPane page = (AnchorPane) loader.load();
			Stage dialogStage = new Stage();
			dialogStage.setTitle("Birthday Statistics");
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(primaryStage);
			Scene scene = new Scene(page);
			dialogStage.setScene(scene);
			
			// Set the persons into the controller.
			BirthdayStatisticsController controller = loader.getController();
			controller.setPersonData(personDataMap);
			
			dialogStage.show();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public void setDataIsModified() {
		this.isDataModified = true;
	}

	public void setDataNotModified() {
		this.isDataModified = false;
	}

}
