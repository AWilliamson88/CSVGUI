package csvgui;

import javafx.beans.property.SimpleStringProperty;

/**
 * Java 3 AT 2.6 
 * Question 6 – 
 * JMC receives output from many different programs in CSV format 
 * you must write a program to display this data.
 * You need to create a program that uses an external 3rd party library 
 * to read and write data to a CSV. 
 * This data must be displayed in a GUI (a table is fine).
 * 
 * @author Andrew Williamson / P113357
 */
public class Record {

    private final SimpleStringProperty firstName;
    private final SimpleStringProperty lastName;
    private final SimpleStringProperty hobby;

    public String getFirstName() {
        return firstName.get();
    }

    public String getLastName() {
        return lastName.get();
    }

    public String getHobby() {
        return hobby.get();
    }
    
    public void setFirstName(String fName) {
        firstName.set(fName);
    }

    public void setLastName(String lName) {
        lastName.set(lName);
    }

    public void setHobby(String newHobby) {
        hobby.set(newHobby);
    }

    public Record(String newOne, String newTwo, String newThree) {
        firstName = new SimpleStringProperty(newOne);
        lastName = new SimpleStringProperty(newTwo);
        hobby = new SimpleStringProperty(newThree);
    }

    public Record(String[] array) {
        firstName = new SimpleStringProperty(array[0]);
        lastName = new SimpleStringProperty(array[1]);
        hobby = new SimpleStringProperty(array[2]);
    }
    
    public String[] getArray() {
        String[] s = new String[]{getFirstName(), getLastName(), getHobby()};
        return s;
    }
}
