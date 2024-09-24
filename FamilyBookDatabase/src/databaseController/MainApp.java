package databaseController;

import java.io.IOException;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.Person;
import view.OverviewController;

public class MainApp extends Application {

    private Stage primaryStage;
    private BorderPane rootLayout;
    
    private ObservableList<Person> personData = FXCollections.observableArrayList();
    
    public MainApp() {
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
	}
    
    //Lists Persons
    public ObservableList<Person> getPersonData() {
		return personData;
	}

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Family Book Database");

        initRootLayout();

        showPersonOverview();
    }
    
    //Initializes the root layout.
    public void initRootLayout() {
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("/view/RootLayout.fxml"));
            rootLayout = (BorderPane) loader.load();
            
            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Shows the person overview inside the root layout.
    public void showPersonOverview() {
        try {
            // Load person overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("/view/Overview.fxml"));
            AnchorPane personOverview = (AnchorPane) loader.load();
            
            // Set person overview into the center of root layout.
            rootLayout.setCenter(personOverview);

            // Give the controller access to the main app.
            OverviewController controller = loader.getController();
            controller.setMainApp(this);

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
}
