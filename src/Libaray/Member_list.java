package Libaray;

import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
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
    public TableColumn<Members,String> colID;
    public TableColumn<Members,String> colName;
    public TableColumn<Members,String> colNumber;
    public TableColumn<Members,String> colAddress;
    public TableColumn<Members,String> colCardNumber;
    public TableColumn<Members,String> colDate;
    public RadioButton sortName;
    public RadioButton sortNumber;
    public RadioButton sortAddress;
    public RadioButton sortCard;
    public RadioButton sortDate;
    public RadioButton sortAll;
    public TextField searchEngineMember;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ValueinSertion();
        loadData();
        Advancesearch();
    }

    private void ValueinSertion(){
        colID.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colNumber.setCellValueFactory(new PropertyValueFactory<>("number"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colCardNumber.setCellValueFactory(new PropertyValueFactory<>("card_number"));
        colDate.setCellValueFactory(new PropertyValueFactory<Members,String>("insertdate"));
    }

     public static class Members{
         private final SimpleStringProperty id;
         private final SimpleStringProperty name;
         private final SimpleStringProperty number;
         private final SimpleStringProperty address;
         private final SimpleStringProperty card_number;
         private final SimpleStringProperty insertdate;

         public Members(String id ,String name , String number ,String address , String card_number , String insertion_date){
             this.id = new SimpleStringProperty(id);
             this.name = new SimpleStringProperty(name);
             this.number = new SimpleStringProperty(number);
             this.address = new SimpleStringProperty(address);
             this.card_number = new SimpleStringProperty(card_number);
             this.insertdate = new SimpleStringProperty(insertion_date);
         }

         public String getId() {
             return id.get();
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

    private void loadData(){
        DbConn connect = new DbConn();
        String SELECT_MEMBER_QUERY = "select * from member_collection";
        //  PreparedStatement preparedStatement = connect.connection.prepareStatement(SELECT_BOOK_QUERY);
        ResultSet resultSet = connect.execQuery(SELECT_MEMBER_QUERY);
        try {
            while (resultSet.next()) {
                String Id = resultSet.getString("id");
                String Name = resultSet.getString("name");
                String Number = resultSet.getString("number");
                String Address = resultSet.getString("address");
                String Card_number = resultSet.getString("card_number");
                String Dates =resultSet.getString("insertion_date");
                list.add(new Members(Id,Name,Number,Address,Card_number,Dates));
            }
        }catch (SQLException e){
            Logger.getLogger(BooksCollection.class.getName()).log(Level.SEVERE,null,e);
        }
        TableView.getItems().setAll(list);
    }

    //Advance Search
    private void Advancesearch(){
        // Wrap the ObservableList in a FilteredList (initially display all data).
        FilteredList<Members> filteredData = new FilteredList<>(list, b -> true);

        // 2. Set the filter Predicate whenever the filter changes.
        searchEngineMember.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(search -> {

                // Compare first name and last name of every person with filter text.
                String lowerCaseFilter = newValue.toLowerCase();

                if(sortName.isSelected()){
                    if(search.getName().toLowerCase().contains(lowerCaseFilter)){
                        return true;
                    }
                }else if(sortNumber.isSelected()){
                    if(search.getNumber().toLowerCase().contains(lowerCaseFilter)){
                        return true;
                    }
                }else if(sortAddress.isSelected()){
                    if (search.getAddress().toLowerCase().contains(lowerCaseFilter)) {
                        return true; // Filter matches first name.
                    }
                }else if(sortCard.isSelected()){
                    if (search.getCard_number().toLowerCase().contains(lowerCaseFilter)) {
                        return true; // Filter matches first name.
                    }
                }else if(sortDate.isSelected()){
                    if (search.getInsertDate().contains(lowerCaseFilter)){
                        return true;
                    }
                }else if(sortAll.isSelected()) {
                    if(search.getName().toLowerCase().contains(lowerCaseFilter)){
                        return true;
                    }else if(search.getNumber().toLowerCase().contains(lowerCaseFilter)){
                        return true;
                    }else if (search.getName().toLowerCase().contains(lowerCaseFilter)) {
                        return true; // Filter matches first name.
                    }else if (search.getAddress().toLowerCase().contains(lowerCaseFilter)){
                        return true;
                    }else if(search.getCard_number().toLowerCase().contains(lowerCaseFilter)){
                        return true;
                    }else if(search.getInsertDate().toLowerCase().contains(lowerCaseFilter)){
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
        SortedList<Members> sortedData = new SortedList<>(filteredData);

        // 4. Bind the SortedList comparator to the TableView comparator.
        // 	  Otherwise, sorting the TableView would have no effect.
        sortedData.comparatorProperty().bind(TableView.comparatorProperty());

        // 5. Add sorted (and filtered) data to the table.
        TableView.setItems(sortedData);
    }


}

