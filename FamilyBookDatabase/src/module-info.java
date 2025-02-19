module FamilyBookDatabase {
	requires javafx.controls;
	requires javafx.fxml;
	requires javafx.base;
	requires javafx.graphics;
	
	opens databaseController to javafx.graphics, javafx.fxml;
	
	exports view;
	opens view to javafx.fxml;
}
