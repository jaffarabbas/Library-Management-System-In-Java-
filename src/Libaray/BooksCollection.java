package Libaray;

import javafx.beans.property.Property;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BooksCollection implements Initializable {

    ObservableList<Book> list = FXCollections.observableArrayList();
    public AnchorPane rootPane;
    public javafx.scene.control.TableView<Book>TableView;
    public TableColumn<Book,String>colSno;
    public TableColumn<Book,String> colName;
    public TableColumn<Book,String> colISBN;
    public TableColumn<Book,String>colAuther;
    public TableColumn<Book,String>colDate;
    public TableColumn<Book,boolean> colAvailiblity;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initCoil();
        loadData();
    }

    private void loadData(){
        DbConn connect = new DbConn();
        String SELECT_BOOK_QUERY = "select * from book_collection";
      //  PreparedStatement preparedStatement = connect.connection.prepareStatement(SELECT_BOOK_QUERY);
        ResultSet resultSet = connect.execQuery(SELECT_BOOK_QUERY);
        String check = "insertiondate";
       try {
           while (resultSet.next()) {
               String Sno = resultSet.getString("sno");
               String Name = resultSet.getString("name");
               String Isbn = resultSet.getString("isbn");
               String Auther = resultSet.getString("auther");
               String date =resultSet.getString(check);
               Boolean Avail = resultSet.getBoolean("availiblity");
               list.add(new Book(Sno, Name, Isbn, Auther, date, Avail));
           }
       }catch (SQLException e){
           Logger.getLogger(BooksCollection.class.getName()).log(Level.SEVERE,null,e);
       }
        TableView.getItems().setAll(list);
    }

    private void initCoil() {
        String check = "insertiondate";
        colSno.setCellValueFactory(new PropertyValueFactory<>("sno"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colISBN.setCellValueFactory(new PropertyValueFactory<>("isbn"));
        colAuther.setCellValueFactory(new PropertyValueFactory<>("author"));
        colDate.getCellValueFactory(new PropertyValueFactory<>("insertiondate"));
        colAvailiblity.setCellValueFactory(new PropertyValueFactory<>("availiblity"));
    }


   public static class Book{
        private final SimpleStringProperty sno;
        private final SimpleStringProperty name;
        private final SimpleStringProperty isbn;
        private final SimpleStringProperty auther;
        private final SimpleStringProperty indate;
        private final SimpleBooleanProperty availiblity;

        public Book(String sno , String name , String isbn , String auther ,  String insertedDate , Boolean Availiblity ){
            this.sno = new SimpleStringProperty(sno);
            this.name = new SimpleStringProperty(name);
            this.isbn = new SimpleStringProperty(isbn);
            this.auther = new SimpleStringProperty(auther);
            this.indate = new SimpleStringProperty(insertedDate);
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
           return indate.get();
       }
        public Boolean getAviliblity(){
            return availiblity.get();
        }


    }

}
