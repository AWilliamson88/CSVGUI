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

    private SimpleStringProperty one;
    private SimpleStringProperty two;
    private SimpleStringProperty three;

    public String getOne() {
        return one.get();
    }

    public String getTwo() {
        return two.get();
    }

    public String getThree() {
        return three.get();
    }

    public Record(String newOne, String newTwo, String newThree) {
        one = new SimpleStringProperty(newOne);
        two = new SimpleStringProperty(newTwo);
        three = new SimpleStringProperty(newThree);
    }

    public Record(String[] array) {
        one = new SimpleStringProperty(array[0]);
        two = new SimpleStringProperty(array[1]);
        three = new SimpleStringProperty(array[2]);
    }
    
    public String[] getArray() {
        String[] s = new String[]{one.get(), two.get(), three.get()};
        return s;
    }
}
