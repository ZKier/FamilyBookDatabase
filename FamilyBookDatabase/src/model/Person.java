package model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Person {
	private final StringProperty firstName;
	private final StringProperty middleName;
	private final StringProperty lastName;
	private final IntegerProperty parents;
	private final IntegerProperty children;
	private final ObjectProperty<LocalDate> dateOfBirth;
	private final StringProperty biography;
	
	//Constructor
	public Person() {
		this(null, null);
	}
	
	public Person(String firstName, String lastName) {
		this.firstName = new SimpleStringProperty(firstName);
		this.middleName = new SimpleStringProperty("");
		this.lastName = new SimpleStringProperty(lastName);
		
		// Some initial dummy data, just for convenient testing.
		this.parents = new SimpleIntegerProperty(0);
		this.children = new SimpleIntegerProperty(0);
		this.dateOfBirth = new SimpleObjectProperty<LocalDate>(LocalDate.of(1999, 2, 21));
		this.biography = new SimpleStringProperty("");
	}
	
	public Person(String firstName, String lastName, int month, int day, int year) {
		this.firstName = new SimpleStringProperty(firstName);
		this.middleName = new SimpleStringProperty("");
		this.lastName = new SimpleStringProperty(lastName);
		
		// Some initial dummy data, just for convenient testing.
		this.parents = new SimpleIntegerProperty(0);
		this.children = new SimpleIntegerProperty(0);
		this.dateOfBirth = new SimpleObjectProperty<LocalDate>(LocalDate.of(year, month, day));
		this.biography = new SimpleStringProperty("");
	}
	
	public Person(String firstName, String middleName, String lastName, int parents, int children, int month, int day, int year) {
		this.firstName = new SimpleStringProperty(firstName);
		this.middleName = new SimpleStringProperty(middleName);
		this.lastName = new SimpleStringProperty(lastName);
		this.parents = new SimpleIntegerProperty(parents);
		this.children = new SimpleIntegerProperty(children);
		this.dateOfBirth = new SimpleObjectProperty<LocalDate>(LocalDate.of(year, month, day));
		this.biography = new SimpleStringProperty("");
	}

	public Person(String firstName, String middleName, String lastName, int parents, int children, int month, int day, int year, String biography) {
		this.firstName = new SimpleStringProperty(firstName);
		this.middleName = new SimpleStringProperty(middleName);
		this.lastName = new SimpleStringProperty(lastName);
		this.parents = new SimpleIntegerProperty(parents);
		this.children = new SimpleIntegerProperty(children);
		this.dateOfBirth = new SimpleObjectProperty<LocalDate>(LocalDate.of(year, month, day));
		this.biography = new SimpleStringProperty(biography);
	}
	
	// Get Methods
	public String getFirstName() {
		return firstName.get();
	}
	
	public String getMiddleName() {
		return middleName.get();
	}
	
	public String getLastName() {
		return lastName.get();
	}
	
	public StringProperty firstNameProperty() {
		return firstName;
	}
	
	public StringProperty middleNameProperty() {
		return middleName;
	}
	
	public StringProperty lastNameProperty() {
		return lastName;
	}
	
	public int getParents() {
		return parents.get();
	}
	
	public int getChildren() {
		return children.get();
	}
	
	// I want this to have several optional parameters for return formatting | might worry about this later
	public LocalDate getDateOfBirth() {
		return dateOfBirth.get();
	}
	
	public LocalDate getFormattedDateOfBirth() {
		// create a formatter
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM uuuu");
        LocalDate ld = LocalDate.parse((CharSequence)this.getDateOfBirth().toString(), formatter);
        return ld;
	}

	public String getBiography() { return biography.get(); }

	public StringProperty biographyProperty() { return biography; }

	
	//Set Methods
	public void setFirstName(String firstName) {
		this.firstName.set(firstName);
	}
	
	public void setMiddleName(String middleName) {
		this.middleName.set(middleName);
	}
	
	public void setLastName(String lastName) {
		this.lastName.set(lastName);
	}
	
	public void setParents(int parents) {
		this.parents.set(parents);
	}
	
	public void setChildren(int children) {
		this.children.set(children);
	}
	
	public void setDateOfBirth(LocalDate dateOfBirth) {
		this.dateOfBirth.set(dateOfBirth);
	}

	public void setBiography(String biography) { this.biography.set(biography); }
	
	//toString?
}
