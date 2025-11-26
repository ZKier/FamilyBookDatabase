package model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Person implements Comparable<Person>, util.Displayable {
	private final StringProperty firstName;
	private final StringProperty middleName;
	private final StringProperty lastName;
	private final IntegerProperty parents;
	private final IntegerProperty children;
	private final ObjectProperty<LocalDate> dateOfBirth;
	private final StringProperty biography;

	private List<Person> parentsList = new ArrayList<>();
	private List<Person> childrenList = new ArrayList<>();

	private double x;
	private double y;
	
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

	public Person(String firstName, String middleName, String lastName, int month, int day, int year, String biography) {
		this.firstName = new SimpleStringProperty(firstName);
		this.middleName = new SimpleStringProperty(middleName);
		this.lastName = new SimpleStringProperty(lastName);
		this.parents = new SimpleIntegerProperty(0);
		this.children = new SimpleIntegerProperty(0);
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
	
	public int getParentsCount() { return parentsList.size(); }

	public List<Person> getParentsList() { return parentsList; }
	
	public int getChildrenCount() {
		return childrenList.size();
	}

	public List<Person> getChildrenList() { return childrenList; }

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

	public String getFirstMiddleLastNameOfParents() {
		Iterator<Person> iterator = parentsList.iterator();
		StringBuilder firstMiddleLastNameList = new StringBuilder();
		while(iterator.hasNext()) {
			String firstMiddleLastName = "";
			Person person = iterator.next();
			String firstName1 = person.getFirstName();
			String middleName1 = person.getMiddleName();
			String middleNameInitials1 = getInitals(middleName1);
			String lastName1 = person.getLastName();

			firstMiddleLastName = firstName1 + " " + middleNameInitials1 + lastName1;
			firstMiddleLastNameList.append(firstMiddleLastName);
			if (iterator.hasNext()) {firstMiddleLastNameList.append(", ");}
		}
		return firstMiddleLastNameList.toString();
	}

	public String getFirstMiddleLastNameOfChildren() {
		Iterator<Person> iterator = childrenList.iterator();
		StringBuilder firstMiddleLastNameList = new StringBuilder();
		while(iterator.hasNext()) {
			String firstMiddleLastName = "";
			Person person = iterator.next();
			String firstName1 = person.getFirstName();
			String middleName1 = person.getMiddleName();
			String middleNameInitials1 = getInitals(middleName1);
			String lastName1 = person.getLastName();

			firstMiddleLastName = firstName1 + " " + middleNameInitials1 + lastName1;
			firstMiddleLastNameList.append(firstMiddleLastName);
			if (iterator.hasNext()) {firstMiddleLastNameList.append(", ");}
		}
		return firstMiddleLastNameList.toString();
	}
	private String getInitals(String fullName) {
		String[] names = fullName.trim().split("\\s+"); // split by one or more spaces
		StringBuilder initals = new StringBuilder();

		for (String name : names) {
			if (!name.isEmpty()) {
				initals.append(name.charAt(0));
				initals.append(". ");
			}
		}
		return initals.toString();
	}

	public double getX() {
		return this.x;
	}

	public double getY() {
		return this.y;
	}
	
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

	// Might have an error (I don't think this auto updates the children list which it probably should.)
	public void setParentsList(List<Person> parentsList) {
		this.parentsList.clear();
		this.parentsList.addAll(parentsList);
	}
	
	public void setChildren(int children) {
		this.children.set(children);
	}

	public void setChildrenList(List<Person> childrenList) {
		this.childrenList.clear();
		this.childrenList.addAll(childrenList);
	}

	public void setDateOfBirth(LocalDate dateOfBirth) {
		this.dateOfBirth.set(dateOfBirth);
	}

	public void setBiography(String biography) { this.biography.set(biography); }

	public void setX(double x) {
		this.x = x;
	}

	public void setY(double y) {
		this.y = y;
	}


	public void addParent(Person parent) {
		if (!parentsList.contains(parent)) {
			parentsList.add(parent);
			parent.addChild(this); // keep relationship consistent
		}
	}

	public void addChild(Person child) {
		if (!childrenList.contains(child)) {
			childrenList.add(child);
			child.addParent(this); // keep relationship consistent
		}
	}

	public void removeParent(Person parent) {
		if (parentsList.contains(parent)) {
			parentsList.remove(parent);
			parent.removeChild(this); // keep relationship consistent
		}
	}

	public void removeChild(Person child) {
		if (childrenList.contains(child)) {
			childrenList.remove(child);
			child.removeParent(this); // keep relationship consistent
		}
	}

	@Override
	public int compareTo(Person other) {
		int cmp = this.getLastName().compareTo(other.getLastName());
		if (cmp != 0) return cmp;

		cmp = this.getFirstName().compareTo(other.getFirstName());
		if (cmp != 0) return cmp;

		return this.getMiddleName().compareTo(other.getMiddleName());
	}

	@Override
	public boolean equals(Object object) {
		// Attempt to cast the object to a person object
		Person person = (Person) object;

		// Compare all the properties.
		if ( person != null &&
				this.getFirstName().equals(person.getFirstName()) &&
						this.getMiddleName().equals(person.getMiddleName()) &&
						this.getLastName().equals(person.getLastName()) &&
						this.getDateOfBirth().equals(person.getDateOfBirth()) &&
						this.getBiography().equals(person.getBiography()) &&
						this.getChildrenList().equals(person.getChildrenList()) &&
						this.getParentsList().equals(person.getParentsList())
		) {
			return true;
		} else {
			return false;
		}

		//this.middleName.equals(person.middleName);
		//this.lastName.equals(person.lastName);
		//this.parents.equals(person.parents);
		//this.children.equals(person.children);
		//this.dateOfBirth.equals(person.dateOfBirth);
		//this.biography.equals(person.biography);

		//this.parentsList.equals(person.parentsList);
		//this.childrenList.equals(person.childrenList);
	}
	//toString?
}
