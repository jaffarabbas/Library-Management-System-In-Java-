package Libaray;

import javafx.beans.property.Property;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import java.util.LinkedList;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BooksCollection implements Initializable{
//   LinkedList BookList = new LinkedList();
   //Datastructers
   LinkedList<Book> BookList = new LinkedList<Book>();
   ObservableList<Book> list = FXCollections.observableArrayList();
   public AnchorPane rootPane;
   public javafx.scene.control.TableView<Book>TableView;
   public TableColumn<Book,String>colSno;
   public TableColumn<Book,String> colName;
   public TableColumn<Book,String> colISBN;
   public TableColumn<Book,String>colAuther;
   public TableColumn<Book,String>colDate;
   public TableColumn<Book,Boolean> colAvailiblity;

//radio button
    public TextField searchEngine;
    public RadioButton sortSno;
    public RadioButton sortAuther;
    public RadioButton sortISBN;
    public RadioButton sortName;
    public RadioButton sortDate;
    public RadioButton sortAvail;
    public RadioButton sortAll;

    //Data base
    DbConn connect;
   @Override
   public void initialize(URL url, ResourceBundle resourceBundle) {
       connect = DbConn.getInstance();
       ValueInsertion();
       loadData();
   }

   private void ValueInsertion(){
       colSno.setCellValueFactory(new PropertyValueFactory<>("sno"));
       colName.setCellValueFactory(new PropertyValueFactory<>("name"));
       colISBN.setCellValueFactory(new PropertyValueFactory<>("isbn"));
       colAuther.setCellValueFactory(new PropertyValueFactory<>("author"));
       colDate.setCellValueFactory(new PropertyValueFactory<Book,String>("indate"));
       colAvailiblity.setCellValueFactory(new PropertyValueFactory<>("availiblity"));
   }

    public static class Book{
        private final SimpleStringProperty sno;
        private final SimpleStringProperty name;
        private final SimpleStringProperty isbn;
        private final SimpleStringProperty auther;
        private final SimpleStringProperty insertdate;
        private final SimpleBooleanProperty availiblity;

        public Book(String sno , String name , String isbn , String auther ,  String insertedDate , Boolean Availiblity ){
            this.sno = new SimpleStringProperty(sno);
            this.name = new SimpleStringProperty(name);
            this.isbn = new SimpleStringProperty(isbn);
            this.auther = new SimpleStringProperty(auther);
            this.insertdate = new SimpleStringProperty(insertedDate);
            this.availiblity = new SimpleBooleanProperty(Availiblity);
        }

         public String getSno(){
           return sno.get();
         }
         public String getName(){
           return name.get();
         }
         public String getIsbn(){
           return isbn.get();
         }
         public String getAuthor(){
           return auther.get();
         }
         public String getDate(){
          return insertdate.getValue();
         }
         public Boolean getAviliblity(){
           return availiblity.get();
         }

         public void setSno(String sno){
            this.sno.set(sno);
         }
         public void setName(String name){
            this.name.set(name);
         }
         public void seIsbn(String isbn){
            this.isbn.set(isbn);
         }
         public void setAuther(String auther){
            this.auther.set(auther);
         }
         public void setInsertionDate(String insertDate){
            this.insertdate.set(insertDate);
         }
         public void setAvailiblity(Boolean Availiblity){
            this.availiblity.set(Availiblity);
         }
         public SimpleStringProperty indateProperty() {
           return insertdate;
         }
         public SimpleBooleanProperty availiblityProperty() {
           return availiblity;
        }
}

    private void DataTaker(){
        String SELECT_BOOK_QUERY = "select * from book_collection";
        //  PreparqedStatement preparedStatement = connect.connection.prepareStatement(SELECT_BOOK_QUERY);
        ResultSet resultSet = connect.execQuery(SELECT_BOOK_QUERY);
        try {
            while (resultSet.next()) {
                String Sno = resultSet.getString("sno");
                String Name = resultSet.getString("name");
                String Isbn = resultSet.getString("isbn");
                String Auther = resultSet.getString("auther");
                String Dates =resultSet.getString("insertion_date");
                Boolean Avail = resultSet.getBoolean("availiblity");
                BookList.add(new Book(Sno, Name, Isbn, Auther,Dates, Avail));
            }
        }catch (SQLException e){
            Logger.getLogger(BooksCollection.class.getName()).log(Level.SEVERE,null,e);
        }
    }

    private void loadData(){
        list.clear();
        DataTaker();
        int i = 0;
        while (BookList.size() != i) {
            list.add(BookList.get(i));
            i++;
        }
        TableView.setItems(list);
        //Advance Sorting of table items
        AdvanceSearch();
    }

    private void AdvanceSearch(){
        // Wrap the ObservableList in a FilteredList (initially display all data).
        FilteredList<Book> filteredData = new FilteredList<>(list, b -> true);

        // 2. Set the filter Predicate whenever the filter changes.
        searchEngine.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(search -> {

                // Compare first name and last name of every person with filter text.
                String lowerCaseFilter = newValue.toLowerCase();

                if(sortSno.isSelected()){
                    if(search.getSno().toLowerCase().contains(lowerCaseFilter)){
                        return true;
                    }
                }else if(sortISBN.isSelected()){
                    if(search.getIsbn().toLowerCase().contains(lowerCaseFilter)){
                        return true;
                    }
                }else if(sortName.isSelected()){
                    if (search.getName().toLowerCase().contains(lowerCaseFilter)) {
                        return true; // Filter matches first name.
                    }
                }else if(sortAuther.isSelected()){
                    if (search.getAuthor().contains(lowerCaseFilter)){
                        return true;
                    }
                }else if(sortDate.isSelected()){
                    if(search.getDate().contains(lowerCaseFilter)){
                        return true;
                    }
                }else if(sortAvail.isSelected()){
                    if(search.getAviliblity().toString().toLowerCase().contains(lowerCaseFilter)){
                        return true;
                    }
                }else if(sortAll.isSelected()){
                    if(search.getSno().toLowerCase().contains(lowerCaseFilter)){
                        return true;
                    }else if(search.getIsbn().toLowerCase().contains(lowerCaseFilter)){
                        return true;
                    }else if (search.getName().toLowerCase().contains(lowerCaseFilter)) {
                        return true; // Filter matches first name.
                    }else if (search.getAuthor().contains(lowerCaseFilter)){
                        return true;
                    }else if(search.getDate().contains(lowerCaseFilter)){
                        return true;
                    }else if(search.getAviliblity().toString().toLowerCase().contains(lowerCaseFilter)){
                        return true;
                    }
                }
                // If filter text is empty, display all persons.
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                else
                    return false; // Does not match.
            });
        });

        // 3. Wrap the FilteredList in a SortedList.
        SortedList<Book> sortedData = new SortedList<>(filteredData);

        // 4. Bind the SortedList comparator to the TableView comparator.
        // 	  Otherwise, sorting the TableView would have no effect.
        sortedData.comparatorProperty().bind(TableView.comparatorProperty());

        // 5. Add sorted (and filtered) data to the table.
        TableView.setItems(sortedData);
    }
    //Crud
    public void BookDelete(ActionEvent actionEvent) {
       Book selectDeleteRow = TableView.getSelectionModel().getSelectedItem();
       if(selectDeleteRow == null){
           AlertMaker.showError("Failed!","Unable To Delete Please Select The Row");
           return;
       }
       boolean check = connect.IsBookAlreadyIssued(selectDeleteRow);
       if(check){
           AlertMaker.showError("Failed!!","Book "+selectDeleteRow.getName()+" Cannot be Deleted\nBook is Already Issued");
       }else {
           Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
           alert.setTitle("Deleting the Book");
           alert.setContentText("Are you sure you want to Delete " + selectDeleteRow.getName() + " ?");
           Optional<ButtonType> answer = alert.showAndWait();
           if (answer.get().equals(ButtonType.OK)) {
               //Delete Book
               boolean result = connect.DeleteBooks(selectDeleteRow);
               if (result) {
                   AlertMaker.showAlert("Successful!", "Book" + selectDeleteRow.getName() + " Deleted Successfully!!");
                   list.remove(selectDeleteRow);
               } else {
                   AlertMaker.showError("Failed!!", "Book " + selectDeleteRow.getName() + " Cannot be Deleted");
               }
           } else {
               AlertMaker.showAlert("Error In Deleting!!", "Book Cannot be Deleted");
           }
       }
    }
    //Edit
    public void BookEdit(ActionEvent actionEvent) {
        Book selectedForEdit = TableView.getSelectionModel().getSelectedItem();
        if(selectedForEdit == null){
            AlertMaker.showError("Failed!","Unable To Delete Please Select The Row");
            return;
        }
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("FXML/Book_Insertion.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            Books controller = (Books) fxmlLoader.getController();
            controller.UpdateInformation(selectedForEdit);
            Stage stage = new Stage();
            stage.setTitle("Edit Book");
            stage.setScene(new Scene(root));
            stage.show();
            stage.setOnCloseRequest((e)->{
                Refresh(new ActionEvent());
            });
        }
        catch (IOException e){
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE,null,e);
        }
    }

    public void Refresh(ActionEvent actionEvent) {
       loadData();
    }
}