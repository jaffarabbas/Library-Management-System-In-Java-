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
import java.util.LinkedList;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Renew_Books implements Initializable {
    public Pane Pane_Main;
    public TextField SearchIssuedBooks;
    public ListView<String> BooksDetials;
    public Button ReniewissuedBooks;
    public Button SUbmissionOfIssuedBook;
    public boolean SubmissionStatus;
    DbConn connect;
    java.util.LinkedList<Member_list.Members> MemberList = new java.util.LinkedList<>();
    java.util.LinkedList<BooksCollection.Book> BookList = new LinkedList<BooksCollection.Book>();
    LinkedList<Regester_Gerenal.IssuedBooks> IssuedList = new LinkedList<>();
    ObservableList<String> issuedData = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        connect = DbConn.getInstance();
    }

    public void IssuedBookInfo(ActionEvent actionEvent) {
        SubmissionStatus = false;
        String id = SearchIssuedBooks.getText();
        BookDataTaker();
        MemberDataTaker();
        IssuedBookTaker();

        int count_book = 0;
        while(BookList.size()!=count_book) {
            if (IssuedList.element().getBookSno().equals(id)) {
                System.out.println("yes");
                String MemberId = IssuedList.element().getMemberCardNumber();
                int renewCounter = Integer.parseInt(IssuedList.element().getRenew_count());
                issuedData.add("Issue Date and Time : "+IssuedList.element().getSubmissionDate());
                issuedData.add("Renew Count : "+renewCounter);
                issuedData.add("Book Information : ");
                issuedData.add("\tBook Sno : " + BookList.element().getSno());
                issuedData.add("\tBook Name : " + BookList.element().getName());
                issuedData.add("\tBook ISBN : " + BookList.element().getIsbn());
                issuedData.add("\tBook Author : " + BookList.element().getAuthor());
                issuedData.add("\tBook Insertion Date : " + BookList.element().getDate());
                issuedData.add("\tBook Availability : " + BookList.element().getAviliblity());
                issuedData.add("\tMember Information : ");
                issuedData.add("\tMember Name : "+MemberList.element().getName());
                issuedData.add("\tMember Number : "+MemberList.element().getNumber());
                issuedData.add("\tMember Address : "+MemberList.element().getAddress());
                issuedData.add("\tMember Card Number : "+MemberList.element().getCard_number());
                issuedData.add("\tMember Insertion Date : "+MemberList.element().getDate());
                break;
            } else {
                System.out.println("No");
            }
            count_book++;
        }
//        ResultSet resultSet = connect.execQuery(query);
//        try {
//            while (resultSet.next()){
//                String bookId = id;
//                String MemberId = resultSet.getString("memberId");
//                Timestamp issueTime = resultSet.getTimestamp("issueTime");
//                int renewCounter = resultSet.getInt("renew_count");
//
//                issuedData.add("Issue Date and Time : "+issueTime.toGMTString());
//                issuedData.add("Renew Count : "+renewCounter);
//                issuedData.add("Book Information : ");
//                query = "SELECT * FROM book_collection WHERE sno = '"+bookId+"'";
//                ResultSet resultSet1 = connect.execQuery(query);
//                while (resultSet1.next()){
//                    issuedData.add("\tBook Sno : "+resultSet1.getString("sno"));
//                    issuedData.add("\tBook Name : "+resultSet1.getString("name"));
//                    issuedData.add("\tBook ISBN : "+resultSet1.getString("isbn"));
//                    issuedData.add("\tBook Author : "+resultSet1.getString("auther"));
//                    issuedData.add("\tBook Insertion Date : "+resultSet1.getString("insertion_date"));
//                }
//                issuedData.add("Member Information : ");
//                query = "SELECT * FROM member_collection WHERE card_number = '"+MemberId+"'";
//                ResultSet resultSet2 = connect.execQuery(query);
//                while (resultSet2.next()){
//                    issuedData.add("\tMember Name : "+resultSet2.getString("name"));
//                    issuedData.add("\tMember Number : "+resultSet2.getString("number"));
//                    issuedData.add("\tMember Address : "+resultSet2.getString("address"));
//                    issuedData.add("\tMember Card Number : "+resultSet2.getString("card_number"));
//                    issuedData.add("\tMember Insertion Date : "+resultSet2.getString("insertion_date"));
//                }
//                SubmissionStatus = true;
//            }
//        }catch (SQLException exception){
//            DbConn.printSQLException(exception);
//            Logger.getLogger(Renew_Books.class.getName()).log(Level.SEVERE,null,exception);
//        }

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
            String query3 = "DELETE FROM defaulters WHERE bookId = '"+id+"'";
            if(connect.execAction(query) && connect.execAction(query2) && connect.execAction(query3)){
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
    //Data
    private void BookDataTaker(){
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
                BookList.add(new BooksCollection.Book(Sno, Name, Isbn, Auther,Dates, Avail));
            }
        }catch (SQLException e){
            Logger.getLogger(BooksCollection.class.getName()).log(Level.SEVERE,null,e);
        }
    }
    private void MemberDataTaker(){
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
                MemberList.add(new Member_list.Members(Name,Number,Address,Card_number,Dates));
            }
        }catch (SQLException e){
            Logger.getLogger(BooksCollection.class.getName()).log(Level.SEVERE,null,e);
        }
    }
    private void IssuedBookTaker(){
        DbConn connect = new DbConn();
        String ISSUED_ITEMS = "select issued_books.id,issued_books.bookId,book_collection.name,book_collection.isbn,member_collection.name,issued_books.memberId,issued_books.issueTime,issued_books.renew_count FROM ((issued_books INNER JOIN book_collection ON issued_books.bookId = book_collection.sno)INNER JOIN member_collection ON issued_books.memberId = member_collection.card_number) ORDER BY id ASC";
        //  PreparedStatement preparedStatement = connect.connection.prepareStatement(SELECT_BOOK_QUERY);
        ResultSet resultSet = connect.execQuery(ISSUED_ITEMS);
        try {
            while (resultSet.next()) {
                String id = resultSet.getString("id");
                String sno = resultSet.getString("bookId");
                String BookName = resultSet.getString("book_collection.name");
                String BookIsbn = resultSet.getString("book_collection.isbn");
                String MemberName = resultSet.getString("member_collection.name");
                String MemberCard = resultSet.getString("memberId");
                String IssueDate =resultSet.getString("issueTime");
                String renew_count = resultSet.getString("renew_count");

                //Expiry date Creation
                String Days = IssueDate.substring(8,10);
                String Month = IssueDate.substring(5,7);
                String Year = IssueDate.substring(2,4);

                //ye us date ko int me dal dia ham ne
                int day = Integer.parseInt(Days);
                int month = Integer.parseInt(Month);
                int year = Integer.parseInt(Year);

                Preferences preferences = Preferences.getPreferences();
                int ExpiryNo = preferences.getNoOfDaysWithOutFIne();

                int ExpiryDay = day+ExpiryNo;
                if(ExpiryDay >= 32){
                    ExpiryDay = 1;
                    month +=month;
                    if(month >= 12){
                        month  = 1;
                        year +=1;
                    }
                }

                String FinalDate = String.valueOf(ExpiryDay)+"-"+String.valueOf(month)+"-"+String.valueOf(year);
                IssuedList.add(new Regester_Gerenal.IssuedBooks(id,sno,BookName,BookIsbn,MemberName,MemberCard,IssueDate,FinalDate,renew_count));
            }
        }catch (SQLException e){
            Logger.getLogger(BooksCollection.class.getName()).log(Level.SEVERE,null,e);
        }
    }
}

