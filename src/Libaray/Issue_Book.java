package Libaray;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Issue_Book implements Initializable {
    public Pane Pane_Main;
    public Text bookName;
    public Text AutherName;
    public Text Availiblity_Of_book;
    public Text Member_Name;
    public Text Member_Number;
    public Text Member_Card_Number;
    public Button IssueBook_id;
    public TextField BookIdTaker;
    public TextField MemberIdTaker;

    DbConn connect;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        connect = DbConn.getInstance();
    }

    public void LoadBook(ActionEvent actionEvent) {
        clearBookCache();
        String getID = BookIdTaker.getText();
        String query = "SELECT * FROM book_collection WHERE sno = '" + getID + "'";
        ResultSet resultSet = connect.execQuery(query);
        Boolean flag = false;
        try{
            while (resultSet.next()){
                String booksNames = resultSet.getString("name");
                String booksAuther = resultSet.getString("auther");
                Boolean getAviliblity = resultSet.getBoolean("availiblity");
                bookName.setText(booksNames);
                AutherName.setText(booksAuther);
                String available = (getAviliblity)?"Available":"Not Available";
                Availiblity_Of_book.setText(available);
                flag = true;
            }if(!flag){
                bookName.setText("No Such Book Available");
            }
        }
        catch (SQLException e){
            Logger.getLogger(Issue_Book.class.getName()).log(Level.SEVERE,null,e);
        }
    }

    public void LoadMember(ActionEvent actionEvent) {
        clearMemberCache();
        String getID = MemberIdTaker.getText();
        String query = "SELECT * FROM member_collection WHERE card_number = '" + getID + "'";
        ResultSet resultSet = connect.execQuery(query);
        Boolean flag = false;
        try{
            while (resultSet.next()){
                String MemberNames = resultSet.getString("name");
                String MemberCard = resultSet.getString("card_number");
                String Number = resultSet.getString("number");
                Member_Name.setText(MemberNames);
                Member_Number.setText(MemberCard);
                Member_Card_Number.setText(Number);
                flag = true;
            }if(!flag){
                Member_Name.setText("No Such Member in List");
            }
        }
        catch (SQLException e){
            Logger.getLogger(Issue_Book.class.getName()).log(Level.SEVERE,null,e);
        }
    }

    public void clearBookCache(){
        bookName.setText("");
        AutherName.setText("");
        Availiblity_Of_book.setText("");
    }

    public void clearMemberCache(){
        Member_Name.setText("");
        Member_Number.setText("");
        Member_Card_Number.setText("");
    }

    public void IssueBook(ActionEvent actionEvent) {
        //taking id from fields
        String BookId = BookIdTaker.getText();
        String MemberId = MemberIdTaker.getText();
        //Alert
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("Confirm Issue Books");
        alert.setTitle("CONFIRMATION");
        alert.setHeaderText("Are you Sure you want to issue book "+bookName.getText()+"\n to "+Member_Name.getText()+" ?");
        Optional<ButtonType> response = alert.showAndWait();
        if(response.get().equals(ButtonType.OK)){
            String query = "INSERT INTO issued_books (bookId,memberId) VALUES ('"+BookId+"','"+MemberId+"')";
            String query2 = "UPDATE book_collection SET availiblity = false WHERE sno = '"+BookId+"'";
            if(connect.execAction(query) && connect.execAction(query2)){
                Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                alert1.setContentText("Successful!!!");
                alert1.setTitle(null);
                alert1.setHeaderText("Book Issue Completed!!!");
                alert1.showAndWait();
            }else{
                Alert alert1 = new Alert(Alert.AlertType.ERROR);
                alert1.setContentText("Failed!!!");
                alert1.setTitle(null);
                alert1.setHeaderText("Book Issue Failed!!!");
                alert1.showAndWait();
            }
        }else{
            Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
            alert1.setContentText("Cancelled!!!");
            alert1.setTitle(null);
            alert1.setHeaderText("Book Issue Cancelled!!!");
            alert1.showAndWait();
        }
    }
}
