package Libaray;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.mysql.cj.conf.PropertyKey.logger;

public class Renew_Books implements Initializable {
    public Pane Pane_Main;
    public TextField SearchIssuedBooks;
    public ListView<String> BooksDetials;
    public Button ReniewissuedBooks;
    public Button SUbmissionOfIssuedBook;
    public boolean SubmissionStatus;
    DbConn connect;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        connect = DbConn.getInstance();
    }

    public void IssuedBookInfo(ActionEvent actionEvent) {
        ObservableList<String> issuedData = FXCollections.observableArrayList();
        SubmissionStatus = false;
        String id = SearchIssuedBooks.getText();
        String query = "SELECT * FROM issued_books WHERE bookId = '"+id+"'";
        ResultSet resultSet = connect.execQuery(query);
        try {
            while (resultSet.next()){
                String bookId = id;
                String MemberId = resultSet.getString("memberId");
                Timestamp issueTime = resultSet.getTimestamp("issueTime");
                int renewCounter = resultSet.getInt("renew_count");

                issuedData.add("Issue Date and Time : "+issueTime.toGMTString());
                issuedData.add("Renew Count : "+renewCounter);
                issuedData.add("Book Information : ");
                query = "SELECT * FROM book_collection WHERE sno = '"+bookId+"'";
                ResultSet resultSet1 = connect.execQuery(query);
                while (resultSet1.next()){
                    issuedData.add("\tBook Sno : "+resultSet1.getString("sno"));
                    issuedData.add("\tBook Name : "+resultSet1.getString("name"));
                    issuedData.add("\tBook ISBN : "+resultSet1.getString("isbn"));
                    issuedData.add("\tBook Author : "+resultSet1.getString("auther"));
                    issuedData.add("\tBook Insertion Date : "+resultSet1.getString("insertion_date"));
                }
                issuedData.add("Member Information : ");
                query = "SELECT * FROM member_collection WHERE card_number = '"+MemberId+"'";
                ResultSet resultSet2 = connect.execQuery(query);
                while (resultSet2.next()){
                    issuedData.add("\tMember Name : "+resultSet2.getString("name"));
                    issuedData.add("\tMember Number : "+resultSet2.getString("number"));
                    issuedData.add("\tMember Address : "+resultSet2.getString("address"));
                    issuedData.add("\tMember Card Number : "+resultSet2.getString("card_number"));
                    issuedData.add("\tMember Insertion Date : "+resultSet2.getString("insertion_date"));
                }
                SubmissionStatus = true;
            }
        }catch (SQLException exception){
            DbConn.printSQLException(exception);
            Logger.getLogger(Renew_Books.class.getName()).log(Level.SEVERE,null,exception);
        }

        BooksDetials.getItems().setAll(issuedData);
    }
    //Submit book
    public void Submission_Of_Issued_Books(ActionEvent actionEvent) {
        if(!SubmissionStatus){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Failed");
            alert.setTitle(null);
            alert.setHeaderText("Please Select A Book to Submit");
            alert.showAndWait();
            return;
        }
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("Confirmation Submission");
        alert.setTitle(null);
        alert.setHeaderText("Are You Sure You Want to Return the Book");

        Optional<ButtonType> response = alert.showAndWait();
        if(response.get().equals(ButtonType.OK)){
            String id = SearchIssuedBooks.getText();
            String query = "DELETE FROM issued_books WHERE bookId = '"+id+"'";
            String query2 = "UPDATE book_collection SET availiblity = true WHERE sno = '"+id+"'";
            if(connect.execAction(query) && connect.execAction(query2)){
                Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                alert1.setContentText("Success!!");
                alert1.setTitle(null);
                alert1.setHeaderText("Book Has Been Submitted");
                alert1.showAndWait();
            }else{
                Alert alert1 = new Alert(Alert.AlertType.ERROR);
                alert1.setContentText("Failed!!");
                alert1.setTitle(null);
                alert1.setHeaderText("Submission Failed!");
                alert1.showAndWait();
            }
        }else{
            Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
            alert1.setContentText("Cancelled!!!");
            alert1.setTitle(null);
            alert1.setHeaderText("Submission Cancelled!!!");
            alert1.showAndWait();
        }

    }

    public void Renew_Issued_Books(ActionEvent actionEvent) {
        if(!SubmissionStatus){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Failed");
            alert.setTitle(null);
            alert.setHeaderText("Please Select A Book to Renew");
            alert.showAndWait();
            return;
        }
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("Confirmation Renew!!");
        alert.setTitle(null);
        alert.setHeaderText("Are You Sure You Want to Renew the Book");

        Optional<ButtonType> response = alert.showAndWait();
        if(response.get().equals(ButtonType.OK)){
            String id = SearchIssuedBooks.getText();
            String query = "UPDATE issued_books SET issueTime = CURRENT_TIMESTAMP,renew_count = renew_count+1 WHERE bookId = '"+id+"'";
            if(connect.execAction(query)){
                Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                alert1.setContentText("Success!!");
                alert1.setTitle(null);
                alert1.setHeaderText("Book Has Been Renewed!");
                alert1.showAndWait();
            }else{
                Alert alert1 = new Alert(Alert.AlertType.ERROR);
                alert1.setContentText("Failed!!");
                alert1.setTitle(null);
                alert1.setHeaderText("Renewed Failed!");
                alert1.showAndWait();
            }
        }else{
            Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
            alert1.setContentText("Cancelled!!!");
            alert1.setTitle(null);
            alert1.setHeaderText("Renewed Cancelled!!!");
            alert1.showAndWait();
        }
    }


}
