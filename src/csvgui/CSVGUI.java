package csvgui;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvValidationException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 *
 * @author P113357
 */
public class CSVGUI extends Application {

    final TableView<Record> tableView = new TableView<>();

    final ObservableList<Record> dataList
            = FXCollections.observableArrayList();

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        launch(args);
    }

    /**
     *
     * @param stage
     * @throws Exception
     */
    @Override
    public void start(Stage stage) throws Exception {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.

        stage.setTitle("CSV Table");

        Group root = new Group();

        TableColumn columnOne = new TableColumn("First Name");
        columnOne.setCellValueFactory(new PropertyValueFactory<>("one"));

        TableColumn columnTwo = new TableColumn("Last Name");
        columnTwo.setCellValueFactory(new PropertyValueFactory<>("two"));

        TableColumn columnThree = new TableColumn("Hobbies");
        columnThree.setCellValueFactory(new PropertyValueFactory<>("three"));

        tableView.setItems(dataList);
        tableView.getColumns().addAll(columnOne, columnTwo, columnThree);
        tableView.setEditable(true);
        
        Button btn1 = new Button("Open File");
        Button btn2 = new Button("Save File");
        
        btn1.setPrefWidth(125);
        btn2.setPrefWidth(125);
        
        
        HBox hBox = new HBox(btn1, btn2);
        hBox.setSpacing(10);
        
        VBox vBox = new VBox();
        vBox.setSpacing(7);
        vBox.setPadding(new Insets(10, 0, 0, 10));
        vBox.getChildren().add(tableView);
        vBox.getChildren().add(hBox);

        root.getChildren().add(vBox);

        stage.setScene(new Scene(root, 280, 450));
        stage.show();
        
        
        btn1.setOnAction((event) -> chooseFile());
        btn2.setOnAction((event) -> saveFile());
        

    }
    
    public void saveFile() {
        
        FileChooser fc = new FileChooser();
        fc.setTitle("Select A CSV File");
        
        fc.setInitialDirectory(new File("CSVGUI"));
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV Files", "*.csv"));
        
        File selectedFile = fc.showSaveDialog(null);
        
        if(selectedFile != null) {
            writeCSV(selectedFile.getPath());
        }
        
    }

    public void writeCSV(String fileName)  {
        
        try (CSVWriter csvWriter = new CSVWriter(new FileWriter(fileName))) {
            
            
            
            for(Record r : dataList) {
                csvWriter.writeNext(r.getArray());
            }
            
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(CSVGUI.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(CSVGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        System.exit(0);
    }
    

    public void readCSV(String fileName) {

//        String fileName = chooseFile();
        
        try {
            System.out.println("into the try block.");
            
                // declare instantiate reader.
                // Set a FileReader to read from the file.
            try (CSVReader csvReader = new CSVReader(new FileReader(fileName))) {

//            CSVReader csvReader = new CSVReader(new FileReader("users.csv"));
                    
                String[] nextLine;
                
                System.out.println("into the while loop.");
                while ((nextLine = csvReader.readNext()) != null) {
                    
                    System.out.println("creating the record");
                    Record record = new Record(nextLine[0], nextLine[1], nextLine[2]);
                    System.out.println("adding the record.");
                    dataList.add(record);
                    
//                for (String token : nextLine) {
//                    System.out.println(token);
//                }
//                System.out.println("*****************");
                }
            }
            System.out.println("closed.");

        } catch (FileNotFoundException e) {
            System.out.println("The File was not found.");
            Logger.getLogger(CSVGUI.class.getName()).log(Level.SEVERE, null, e);
        } catch (IOException e) {
            System.out.println("There was an error reading the file.");
            Logger.getLogger(CSVGUI.class.getName()).log(Level.SEVERE, null, e);
        } catch (CsvValidationException ex) {
            Logger.getLogger(CSVGUI.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception e) {
            System.out.println("Program failed at somewhere.");
            System.out.println(e.getMessage());
//            Logger.getLogger(CSVTable.class.getName()).log(Level.SEVERE, null, e);
        }

    }
    
    public void chooseFile() {
        
        FileChooser fc = new FileChooser();
        fc.setTitle("Select A CSV File");
        
        File selectedFile = fc.showOpenDialog(null);
        
        if(selectedFile != null) {
            readCSV(selectedFile.getPath());
        }
        
        
    }

}
