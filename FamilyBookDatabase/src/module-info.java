module FamilyBookDatabase {
	requires javafx.controls;
	requires javafx.fxml;
	requires javafx.base;
	
	opens databaseController to javafx.graphics, javafx.fxml;
	
	exports view;
	opens view to javafx.fxml;
}
