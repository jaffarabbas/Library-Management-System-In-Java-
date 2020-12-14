package Libaray;

import javafx.beans.property.SimpleBooleanProperty;
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
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Regester_Gerenal implements Initializable {
    ObservableList<IssuedBooks> list = FXCollections.observableArrayList();
    public AnchorPane rootPane;
    public javafx.scene.control.TableView<IssuedBooks> TableView;
    public TableColumn<IssuedBooks,String> colID;
    public TableColumn<IssuedBooks,String> colBookSno;
    public TableColumn<IssuedBooks,String> colBookName;
    public TableColumn<IssuedBooks,String> colBookISBN;
    public TableColumn<IssuedBooks,String> colMemberName;
    public TableColumn<IssuedBooks,String> colMemberCard;
    public TableColumn<IssuedBooks,String> colSubmissionDate;
    public TableColumn<IssuedBooks,String> colExpiry;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ValueinSertion();
        loadData();
//        Advancesearch();
    }

    private void ValueinSertion(){
        colID.setCellValueFactory(new PropertyValueFactory<>("Id"));
        colBookSno.setCellValueFactory(new PropertyValueFactory<>("BookSno"));
        colBookName.setCellValueFactory(new PropertyValueFactory<>("BookName"));
        colBookISBN.setCellValueFactory(new PropertyValueFactory<>("BookISBN"));
        colMemberName.setCellValueFactory(new PropertyValueFactory<>("MemberName"));
        colMemberCard.setCellValueFactory(new PropertyValueFactory<>("MemberCardNumber"));
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

        public IssuedBooks(String id, String bookSno, String bookName, String bookISBN, String memberName, String memberCardNumber, String submissionDate, String expiryDate) {
            this.Id = new SimpleStringProperty(id);
            this.BookSno = new SimpleStringProperty(bookSno);
            this.BookName = new SimpleStringProperty(bookName);
            this.BookISBN = new SimpleStringProperty(bookISBN);
            this.MemberName = new SimpleStringProperty(memberName);
            this.MemberCardNumber = new SimpleStringProperty(memberCardNumber);
            this.SubmissionDate = new SimpleStringProperty(submissionDate);
            this.ExpiryDate = new SimpleStringProperty(expiryDate);
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
    }

    private void loadData(){
        DbConn connect = new DbConn();
        String ISSUED_ITEMS = "select issued_books.id,issued_books.bookId,book_collection.name,book_collection.isbn,member_collection.name,issued_books.memberId,issued_books.issueTime FROM ((issued_books INNER JOIN book_collection ON issued_books.bookId = book_collection.sno)INNER JOIN member_collection ON issued_books.memberId = member_collection.card_number) ORDER BY id ASC";
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
//                Boolean ExpiryDate = resultSet.getBoolean("");
                list.add(new IssuedBooks(id,sno,BookName,BookIsbn,MemberName,MemberCard,IssueDate,"2-3-2"));
            }
        }catch (SQLException e){
            Logger.getLogger(BooksCollection.class.getName()).log(Level.SEVERE,null,e);
        }
        TableView.getItems().setAll(list);
    }
}
