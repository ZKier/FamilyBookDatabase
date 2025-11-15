package model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.StringProperty;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


// Intended to be a holding class for updating people.
public class PersonData {
    private StringProperty firstName;
    private StringProperty middleName;
    private StringProperty lastName;
    private IntegerProperty parents;
    private IntegerProperty children;
    private ObjectProperty<LocalDate> dateOfBirth;
    private StringProperty biography;

    private final List<Person> parentsList = new ArrayList<>();
    private final List<Person> childrenList = new ArrayList<>();

    //Constructor
    public PersonData() {}

    public PersonData(String firstName) {

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

    public String getBiography() { return biography.get(); }

    public int getParentsCount() { return parentsList.size(); }

    public List<Person> getParentsList() { return parentsList; }

    public int getChildrenCount() {
        return childrenList.size();
    }

    public List<Person> getChildrenList() { return childrenList; }



    // Set Methods
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


    public void addParent(Person parent) {
        if (!parentsList.contains(parent)) {
            parentsList.add(parent);
        }
    }

    public void addChild(Person child) {
        if (!childrenList.contains(child)) {
            childrenList.add(child);
        }
    }

    public void removeParent(Person parent) {
            parentsList.remove(parent);
    }

    public void removeChild(Person child) {
            childrenList.remove(child);
    }

    public void pushParentsToPerson(Person person) {
        if (!this.getParentsList().isEmpty()) {

        }
    }
    public void pushToPerson(Person person) {
        if (!this.getFirstName().isEmpty()) { person.setFirstName(this.getFirstName()); }
        if (!this.getMiddleName().isEmpty()) { person.setFirstName(this.getFirstName()); }
    }
}
