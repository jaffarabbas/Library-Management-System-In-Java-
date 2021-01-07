package Libaray;

import Libaray.DbConnection.DbConn;
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
import java.util.LinkedList;
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
    LinkedList<Member_list.Members> MemberList = new LinkedList<>();
    LinkedList<BooksCollection.Book> BookList = new LinkedList<BooksCollection.Book>();

    DbConn connect;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        connect = DbConn.getInstance();
    }

    public void LoadBook(ActionEvent actionEvent) {
        BookList.clear();
        clearBookCache();
        String getID = BookIdTaker.getText();
        BookDataTaker(getID);
        int count_book = 0;
        Boolean flag = false;
            while (BookList.size()!=count_book){
                String booksNames = BookList.element().getName();
                String booksAuther = BookList.element().getAuthor();
                Boolean getAviliblity = BookList.element().getAviliblity();
                bookName.setText(booksNames);
                AutherName.setText(booksAuther);
                String available = (getAviliblity)?"Available":"Not Available";
                Availiblity_Of_book.setText(available);
                flag = true;
                count_book++;
            }if(!flag){
                bookName.setText("No Such Book Available");
            }
    }

    public void LoadMember(ActionEvent actionEvent) {
        MemberList.clear();
        clearMemberCache();
        String getID = MemberIdTaker.getText();
        MemberDataTaker(getID);
        int count_member = 0;
        Boolean flag = false;
            while (MemberList.size()!=count_member) {
                String MemberNames = MemberList.element().getName();
                String MemberCard = MemberList.element().getCard_number();
                String Number = MemberList.element().getNumber();
                Member_Name.setText(MemberNames);
                Member_Number.setText(MemberCard);
                Member_Card_Number.setText(Number);
                flag = true;
                count_member++;
            }
            if (!flag) {
                Member_Name.setText("No Such Member in List");
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




    private void BookDataTaker(String id){
        String SELECT_BOOK_QUERY = "select * from book_collection where sno = '"+id+"'";
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
                BookList.add(new BooksCollection.Book(Sno, Name, Isbn, Auther,Dates, Avail));
            }
        }catch (SQLException e){
            Logger.getLogger(BooksCollection.class.getName()).log(Level.SEVERE,null,e);
        }
    }
    private void MemberDataTaker(String id){
        DbConn connect = new DbConn();
        String SELECT_MEMBER_QUERY = "select * from member_collection where card_number = '"+id+"'";
        //  PreparedStatement preparedStatement = connect.connection.prepareStatement(SELECT_BOOK_QUERY);
        ResultSet resultSet = connect.execQuery(SELECT_MEMBER_QUERY);
        try {
            while (resultSet.next()) {
                String Name = resultSet.getString("name");
                String Number = resultSet.getString("number");
                String Address = resultSet.getString("address");
                String Card_number = resultSet.getString("card_number");
                String Dates =resultSet.getString("insertion_date");
                MemberList.add(new Member_list.Members(Name,Number,Address,Card_number,Dates));
            }
        }catch (SQLException e){
            Logger.getLogger(BooksCollection.class.getName()).log(Level.SEVERE,null,e);
        }
    }
}
