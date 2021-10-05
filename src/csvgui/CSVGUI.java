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
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
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

        tableView.setEditable(true);
        TableColumn columnOne = new TableColumn("First Name");
        columnOne.setMinWidth(100);
        columnOne.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        
        columnOne.setCellFactory(TextFieldTableCell.<Record>forTableColumn());
        columnOne.setOnEditCommit(
                new EventHandler<CellEditEvent<Record, String>>() {
            @Override
            public void handle(CellEditEvent<Record, String> t) {
                ((Record) t.getTableView().getItems().get(
                        t.getTablePosition().getRow())).setFirstName(t.getNewValue());
            }
        }
        );
        // Look online for why this isn't working.
        // it says there's an error but nothing is underlined and the error
        // isn't clear on the problem.
//        columnOne.setOnEditCommit(
//                (CellEditEvent<Record, String> t) -> {
//                    ((Record) t.getTableView().getItems().get(
//                        t.getTablePosition().getRow())
//                        ).setFirstName(t.getNewValue());
//        });

        TableColumn columnTwo = new TableColumn("Last Name");
        columnTwo.setMinWidth(100);
        columnTwo.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        
        columnTwo.setCellFactory(TextFieldTableCell.forTableColumn());
        columnTwo.setOnEditCommit(
            new EventHandler<CellEditEvent<Record, String>>() {
                @Override
                public void handle(CellEditEvent<Record, String> t) {
                    ((Record) t.getTableView().getItems().get(
                        t.getTablePosition().getRow())
                        ).setLastName(t.getNewValue());
                }
            }
        );
        
        
        TableColumn columnThree = new TableColumn("Hobbies");
        columnThree.setMinWidth(150);
        columnThree.setCellValueFactory(new PropertyValueFactory<>("hobby"));

        columnThree.setCellFactory(TextFieldTableCell.forTableColumn());
        columnThree.setOnEditCommit(
            new EventHandler<CellEditEvent<Record, String>>() {
                @Override
                public void handle(CellEditEvent<Record, String> t) {
                    ((Record) t.getTableView().getItems().get(
                        t.getTablePosition().getRow())
                        ).setHobby(t.getNewValue());
                }
            }
        );
        
        tableView.setItems(dataList);
        tableView.getColumns().addAll(columnOne, columnTwo, columnThree);

        Button btn1 = new Button("Open File");
        Button btn2 = new Button("Save File");

        btn1.setPrefWidth(125);
        btn2.setPrefWidth(125);

        final TextField addFirstName = new TextField();
        addFirstName.setPromptText("First Name");
        addFirstName.setMaxWidth(columnOne.getWidth());
        
        final TextField addLastName = new TextField();
        addLastName.setPromptText("Last Name");
        addLastName.setMaxWidth(columnTwo.getWidth());
        
        final TextField addHobby = new TextField();
        addHobby.setPromptText("Hobbies");
        addHobby.setMaxWidth(columnThree.getPrefWidth());
        
        Button btnAdd = new Button("Add");
        
        btnAdd.setOnAction(e -> {
            dataList.add(new Record (
                addFirstName.getText(),
                addLastName.getText(),
                addHobby.getText()));
            addFirstName.clear();
            addLastName.clear();
            addHobby.clear();
        });
        
        
        HBox hBoxAdd = new HBox(addFirstName, addLastName, addHobby, btnAdd);
        hBoxAdd.setSpacing(3);
        
        HBox hBoxIO = new HBox(btn1, btn2);
        hBoxIO.setSpacing(10);
        

        VBox vBox = new VBox();
        vBox.setSpacing(7);
        vBox.setPadding(new Insets(10, 20, 0, 10));
        vBox.getChildren().add(tableView);
        vBox.getChildren().add(hBoxAdd);
        vBox.getChildren().add(hBoxIO);

        root.getChildren().add(vBox);

        stage.setScene(new Scene(root, 372, 500));
        stage.show();

        btn1.setOnAction((event) -> chooseFile());
        btn2.setOnAction((event) -> saveFile());

    }

    public void saveFile() {

        FileChooser fc = new FileChooser();
        fc.setTitle("Select A CSV File");

        // Not sure how this works. I couldn't change the directory no matter what I tried.
        // Look for more examples online and in the docs.
//        fc.setInitialDirectory(new File("CSVGUI"));
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV Files", "*.csv"));

        File selectedFile = fc.showSaveDialog(null);

        if (selectedFile != null) {
            writeCSV(selectedFile.getPath());
        }

    }

    public void writeCSV(String fileName) {

        try (CSVWriter csvWriter = new CSVWriter(new FileWriter(fileName))) {

            for (Record r : dataList) {
                csvWriter.writeNext(r.getArray());
            }

        } catch (FileNotFoundException ex) {
            System.out.println("The file was not found.");
            Logger.getLogger(CSVGUI.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            System.out.println("There was an IO exception.");
            Logger.getLogger(CSVGUI.class.getName()).log(Level.SEVERE, null, ex);
        }

//        System.exit(0);
    }

    public void readCSV(String fileName) {

//        String fileName = chooseFile();
        try {
            // declare instantiate reader.
            // Set a FileReader to read from the file.
            try (CSVReader csvReader = new CSVReader(new FileReader(fileName))) {

                String[] nextLine;

                while ((nextLine = csvReader.readNext()) != null) {

                    Record record = new Record(nextLine[0], nextLine[1], nextLine[2]);
                    dataList.add(record);
                }
            }

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

        if (selectedFile != null) {
            readCSV(selectedFile.getPath());
        }
    }

}
