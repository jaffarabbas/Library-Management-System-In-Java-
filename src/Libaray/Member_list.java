package Libaray;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
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

public class Member_list implements Initializable {

    ObservableList<Members> list = FXCollections.observableArrayList();
    public AnchorPane rootPane;
    public javafx.scene.control.TableView<Members> TableView;
    public TableColumn<Members,String> colName;
    public TableColumn<Members,String> colNumber;
    public TableColumn<Members,String> colAddress;
    public TableColumn<Members,String> colCardNumber;
    public TableColumn<Members,String> colDate;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ValueinSertion();
        loadData();
    }


    private void loadData(){
        DbConn connect = new DbConn();
        String SELECT_MEMBER_QUERY = "select * from member_collection";
        //  PreparedStatement preparedStatement = connect.connection.prepareStatement(SELECT_BOOK_QUERY);
        ResultSet resultSet = connect.execQuery(SELECT_MEMBER_QUERY);
        try {
            while (resultSet.next()) {
                String Name = resultSet.getString("name");
                String Number = resultSet.getString("number");
                String Address = resultSet.getString("address");
                String Card_number = resultSet.getString("card_number");
                String Dates =resultSet.getString("insertion_date");
                list.add(new Members(Name,Number,Address,Card_number,Dates));
            }
        }catch (SQLException e){
            Logger.getLogger(BooksCollection.class.getName()).log(Level.SEVERE,null,e);
        }
        TableView.getItems().setAll(list);
    }

    private void ValueinSertion(){
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colNumber.setCellValueFactory(new PropertyValueFactory<>("number"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colCardNumber.setCellValueFactory(new PropertyValueFactory<>("card_number"));
        colDate.setCellValueFactory(new PropertyValueFactory<Members,String>("insertdate"));
    }

     public static class Members{
         private final SimpleStringProperty name;
         private final SimpleStringProperty number;
         private final SimpleStringProperty address;
         private final SimpleStringProperty card_number;
         private final SimpleStringProperty insertdate;

         public Members(String name , String number ,String address , String card_number , String insertion_date){
             this.name = new SimpleStringProperty(name);
             this.number = new SimpleStringProperty(number);
             this.address = new SimpleStringProperty(address);
             this.card_number = new SimpleStringProperty(card_number);
             this.insertdate = new SimpleStringProperty(insertion_date);
         }

         public String getName(){
             return name.get();
         }
         public String getNumber(){
             return number.get();
         }
         public String getAddress(){
             return address.get();
         }
         public String getCard_number(){
             return card_number.get();
         }
         public String getInsertDate(){
             return insertdate.get();
         }

         public SimpleStringProperty insertdateProperty() {
             return insertdate;
         }
     }
}
