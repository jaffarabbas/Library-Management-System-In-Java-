package Libaray;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.mysql.cj.conf.PropertyKey.logger;

public class Renew_Books implements Initializable {
    public Pane Pane_Main;
    public TextField SearchIssuedBooks;
    public ListView<String> BooksDetials;
    public Button ReniewPssuedBooks;
    public Pane Pane_Main1;

    DbConn connect;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        connect = DbConn.getInstance();
    }

    public void IssuedBookInfo(ActionEvent actionEvent) {
        ObservableList<String> issuedData = FXCollections.observableArrayList();
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
                    issuedData.add("Book Sno : "+resultSet1.getString("sno"));
                    issuedData.add("Book Name : "+resultSet1.getString("name"));
                    issuedData.add("Book ISBN : "+resultSet1.getString("isbn"));
                    issuedData.add("Book Author : "+resultSet1.getString("auther"));
                    issuedData.add("Book Insertion Date : "+resultSet1.getString("insertion_date"));
                }
                issuedData.add("Member Information : ");
                query = "SELECT * FROM member_collection WHERE card_number = '"+MemberId+"'";
                ResultSet resultSet2 = connect.execQuery(query);
                while (resultSet2.next()){
                    issuedData.add("Member Name : "+resultSet2.getString("name"));
                    issuedData.add("Member Number : "+resultSet2.getString("number"));
                    issuedData.add("Member Address : "+resultSet2.getString("address"));
                    issuedData.add("Member Card Number : "+resultSet2.getString("card_number"));
                    issuedData.add("Member Insertion Date : "+resultSet2.getString("insertion_date"));
                }
            }
        }catch (SQLException exception){
            DbConn.printSQLException(exception);
            Logger.getLogger(Renew_Books.class.getName()).log(Level.SEVERE,null,exception);
        }

        BooksDetials.getItems().setAll(issuedData);
    }

    public void Renew_Issued_Books(ActionEvent actionEvent) {
    }
}
