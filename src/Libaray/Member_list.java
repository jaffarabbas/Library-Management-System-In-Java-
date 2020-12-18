package Libaray;

import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
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
    public RadioButton sortName;
    public RadioButton sortNumber;
    public RadioButton sortAddress;
    public RadioButton sortCard;
    public RadioButton sortDate;
    public RadioButton sortAll;
    public TextField searchEngineMember;
    DbConn connect;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        connect = DbConn.getInstance();
        ValueinSertion();
        loadData();
        Advancesearch();
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

         public Members(String name, String number, String address, String card_number, String insertion_date){
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
         public String getDate(){return  insertdate.get();}
         public SimpleStringProperty insertdateProperty() {
            return insertdate;
         }
     }


    private void loadData(){
        list.clear();
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
        TableView.setItems(list);
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
                    if (search.getDate().toLowerCase().contains(lowerCaseFilter)) {
                        return true; // Filter matches first name.
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
                    }else if (search.getDate().toLowerCase().contains(lowerCaseFilter)) {
                        return true; // Filter matches first name.
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

    //Crud
    public void MemberEdit(ActionEvent actionEvent) {
        Members selectedForEdit = TableView.getSelectionModel().getSelectedItem();
        if(selectedForEdit == null){
            AlertMaker.showError("Failed!","Unable To Delete Please Select The Row");
            return;
        }
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("FXML/Member_Insertion.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            Member controller = (Member) fxmlLoader.getController();
            controller.UpdateInformation(selectedForEdit);
            Stage stage = new Stage();
            stage.setTitle("Edit Book");
            stage.setScene(new Scene(root));
            stage.show();
            stage.setOnCloseRequest((e)->{
                RefreshMember(new ActionEvent());
            });
        }
        catch (IOException e){
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE,null,e);
        }
    }

    public void MemberDelete(ActionEvent actionEvent) {
        Members selectDeleteRow = TableView.getSelectionModel().getSelectedItem();
        if(selectDeleteRow == null){
            AlertMaker.showError("Failed!","Unable To Delete Please Select The Row");
            return;
        }
          boolean check = connect.IsMemberIsAlreadyIssued(selectDeleteRow);
        if(check){
            AlertMaker.showError("Failed!!","Book "+selectDeleteRow.getName()+" Cannot be Deleted\nMember is Already Issued");
        }else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Deleting the Member");
            alert.setContentText("Are you sure you want to Delete " + selectDeleteRow.getName() + " ?");
            Optional<ButtonType> answer = alert.showAndWait();
            if (answer.get().equals(ButtonType.OK)) {
                //Delete Book
                boolean result = connect.DeleteMembers(selectDeleteRow);
                if (result) {
                    AlertMaker.showAlert("Successful!", "Member" + selectDeleteRow.getName() + " Deleted Successfully!!");
                    list.remove(selectDeleteRow);
                } else {
                    AlertMaker.showError("Failed!!", "Member " + selectDeleteRow.getName() + " Cannot be Deleted");
                }
            } else {
                AlertMaker.showAlert("Error In Deleting!!", "Book Cannot be Deleted");
            }
        }
    }


    public void RefreshMember(ActionEvent actionEvent) {
        loadData();
    }
}

