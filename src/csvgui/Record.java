package csvgui;


import javafx.beans.property.SimpleStringProperty;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author P113357
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
