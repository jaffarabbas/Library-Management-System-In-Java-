package Libaray;

import Libaray.DbConnection.DbConn;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Regester_Gerenal implements Initializable {

    LinkedList<IssuedBooks> IssuedList = new LinkedList<>();
    ObservableList<IssuedBooks> list = FXCollections.observableArrayList();
    public AnchorPane rootPane;
    public javafx.scene.control.TableView<IssuedBooks> TableView;
    public TableColumn<IssuedBooks,String> colID;
    public TableColumn<IssuedBooks,String> colBookSno;
    public TableColumn<IssuedBooks,String> colBookName;
    public TableColumn<IssuedBooks,String> colBookISBN;
    public TableColumn<IssuedBooks,String> colMemberName;
    public TableColumn<IssuedBooks,String> colMemberCard;
    public TableColumn<IssuedBooks,String> colRenewCount;
    public TableColumn<IssuedBooks,String> colSubmissionDate;
    public TableColumn<IssuedBooks,String> colExpiry;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ValueinSertion();
        loadData();
    }

    private void ValueinSertion(){
        colID.setCellValueFactory(new PropertyValueFactory<>("Id"));
        colBookSno.setCellValueFactory(new PropertyValueFactory<>("BookSno"));
        colBookName.setCellValueFactory(new PropertyValueFactory<>("BookName"));
        colBookISBN.setCellValueFactory(new PropertyValueFactory<>("BookISBN"));
        colMemberName.setCellValueFactory(new PropertyValueFactory<>("MemberName"));
        colMemberCard.setCellValueFactory(new PropertyValueFactory<>("MemberCardNumber"));
        colRenewCount.setCellValueFactory(new PropertyValueFactory<>("renew_count"));
        colSubmissionDate.setCellValueFactory(new PropertyValueFactory<>("SubmissionDate"));
        colExpiry.setCellValueFactory(new PropertyValueFactory<>("ExpiryDate"));
    }

    public static class IssuedBooks{
        private final SimpleStringProperty Id;
        private final SimpleStringProperty BookSno;
        private final SimpleStringProperty BookName;
        private final SimpleStringProperty BookISBN;
        private final SimpleStringProperty MemberName;
        private final SimpleStringProperty MemberCardNumber;
        private final SimpleStringProperty SubmissionDate;
        private final SimpleStringProperty ExpiryDate;
        private final SimpleStringProperty renew_count;

        public IssuedBooks(String id, String bookSno, String bookName, String bookISBN, String memberName, String memberCardNumber, String submissionDate, String expiryDate, String renew_count) {
            this.Id = new SimpleStringProperty(id);
            this.BookSno = new SimpleStringProperty(bookSno);
            this.BookName = new SimpleStringProperty(bookName);
            this.BookISBN = new SimpleStringProperty(bookISBN);
            this.MemberName = new SimpleStringProperty(memberName);
            this.MemberCardNumber = new SimpleStringProperty(memberCardNumber);
            this.SubmissionDate = new SimpleStringProperty(submissionDate);
            this.ExpiryDate = new SimpleStringProperty(expiryDate);
            this.renew_count = new SimpleStringProperty(renew_count);
        }


        public String getId() {
            return Id.get();
        }
        public String getBookSno() {
            return BookSno.get();
        }
        public String getBookName() {
            return BookName.get();
        }
        public String getBookISBN() {
            return BookISBN.get();
        }
        public String getMemberName() {
            return MemberName.get();
        }
        public String getMemberCardNumber() {
            return MemberCardNumber.get();
        }
        public String getSubmissionDate() {
            return SubmissionDate.get();
        }
        public String isExpiryDate() {
            return ExpiryDate.get();
        }
        public String getRenew_count() {
            return renew_count.get();
        }

        public void setId(String id) {
            this.Id.set(id);
        }
        public void setBookSno(String bookSno) {
            this.BookSno.set(bookSno);
        }
        public void setBookName(String bookName) {
            this.BookName.set(bookName);
        }
        public void setBookISBN(String bookISBN) {
            this.BookISBN.set(bookISBN);
        }
        public void setMemberName(String memberName) {
            this.MemberName.set(memberName);
        }
        public void setMemberCardNumber(String memberCardNumber) {
            this.MemberCardNumber.set(memberCardNumber);
        }
        public void setSubmissionDate(String submissionDate) {
            this.SubmissionDate.set(submissionDate);
        }
        public void setExpiryDate(String expiryDate) {
            this.ExpiryDate.set(expiryDate);
        }
        public void setRenew_count(String renew_count) {
            this.renew_count.set(renew_count);
        }

    }

    private void DataTaker(){
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
                String Renew_Count = resultSet.getString("renew_count");

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

//                Boolean ExpiryDate = resultSet.getBoolean("");
                IssuedList.add(new IssuedBooks(id,sno,BookName,BookIsbn,MemberName,MemberCard,IssueDate,FinalDate,Renew_Count));
            }
        }catch (SQLException e){
            Logger.getLogger(BooksCollection.class.getName()).log(Level.SEVERE,null,e);
        }
        TableView.setItems(list);
    }

    private void loadData() {
        list.clear();
        DataTaker();
        int i = 0;
        while (IssuedList.size() != i) {
            list.add(IssuedList.get(i));
            i++;
        }
        TableView.setItems(list);
    }
}
