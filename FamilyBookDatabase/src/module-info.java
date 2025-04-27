module FamilyBookDatabase {
	requires javafx.graphics;
	requires javafx.controls;
	requires javafx.media;
	requires javafx.base;
	requires javafx.web;
	requires javafx.swing;
	requires javafx.fxml;
	requires java.prefs;
	requires org.json;
	
	opens databaseController to javafx.graphics, javafx.fxml;
	
	exports view;
	opens view to javafx.fxml;
}
