package Libaray;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Defaulter implements Initializable {
    public AnchorPane rootPane;
    ObservableList<Defaulters> list = FXCollections.observableArrayList();
    public javafx.scene.control.TableView<Defaulters> TableView;
    public TableColumn<String, Defaulters> colID;
    public TableColumn<String, Defaulters> colBookSno;
    public TableColumn<String, Defaulters> colBookName;
    public TableColumn<String, Defaulters> colBookISBN;
    public TableColumn<String, Defaulters> colMemberName;
    public TableColumn<String, Defaulters> colMemberCard;
    public TableColumn<String, Defaulters> colSubmissionDate;
    public TableColumn<String, Defaulters> colExpiry;
    public TableColumn<String, Defaulters> colFine;
    public boolean flag=false;

    DbConn connect;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        connect = DbConn.getInstance();
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
        colSubmissionDate.setCellValueFactory(new PropertyValueFactory<>("SubmissionDate"));
        colExpiry.setCellValueFactory(new PropertyValueFactory<>("ExpiryDate"));
        colFine.setCellValueFactory(new PropertyValueFactory<>("Fine"));
    }

    public static class Defaulters{
        private final SimpleStringProperty Id;
        private final SimpleStringProperty BookSno;
        private final SimpleStringProperty BookName;
        private final SimpleStringProperty BookISBN;
        private final SimpleStringProperty MemberName;
        private final SimpleStringProperty MemberCardNumber;
        private final SimpleStringProperty SubmissionDate;
        private final SimpleStringProperty ExpiryDate;
        private final SimpleStringProperty Fine;

        public Defaulters(String id, String bookSno, String bookName, String bookISBN, String memberName, String memberCardNumber, String submissionDate, String expiryDate, String fine) {
            this.Id = new SimpleStringProperty(id);
            this.BookSno = new SimpleStringProperty(bookSno);
            this.BookName = new SimpleStringProperty(bookName);
            this.BookISBN = new SimpleStringProperty(bookISBN);
            this.MemberName = new SimpleStringProperty(memberName);
            this.MemberCardNumber = new SimpleStringProperty(memberCardNumber);
            this.SubmissionDate = new SimpleStringProperty(submissionDate);
            this.ExpiryDate = new SimpleStringProperty(expiryDate);
            this.Fine = new SimpleStringProperty(fine);
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
        public String getFine() {
            return Fine.get();
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
        public void setFine(String fine) {
            this.Fine.set(fine);
        }
    }

    private void loadData(){
        GenerateDefaulter();
        String DEFAULTER = "select defaulters.id,defaulters.bookId,book_collection.name,book_collection.isbn,member_collection.name,defaulters.memberId,defaulters.issueTime,defaulters.expiry_date,defaulters.fine FROM ((defaulters INNER JOIN book_collection ON defaulters.bookId = book_collection.sno)INNER JOIN member_collection ON defaulters.memberId = member_collection.card_number)";
        //  PreparedStatement preparedStatement = connect.connection.prepareStatement(SELECT_BOOK_QUERY);
        ResultSet resultSet = connect.execQuery(DEFAULTER);
        try {
            while (resultSet.next()) {
                String id = resultSet.getString("id");
                String sno = resultSet.getString("bookId");
                String BookName = resultSet.getString("book_collection.name");
                String BookIsbn = resultSet.getString("book_collection.isbn");
                String MemberName = resultSet.getString("member_collection.name");
                String MemberCard = resultSet.getString("memberId");
                String IssueDate =resultSet.getString("issueTime");
                String expiryDate = resultSet.getString("expiry_date");
                String Fine = resultSet.getString("fine");
//                Boolean ExpiryDate = resultSet.getBoolean("");
                list.add(new Defaulters(id,sno,BookName,BookIsbn,MemberName,MemberCard,IssueDate,expiryDate,Fine));
            }
        }catch (SQLException e){
            Logger.getLogger(BooksCollection.class.getName()).log(Level.SEVERE,null,e);
        }
        TableView.setItems(list);
    }

    public void GenerateDefaulter(){
        String query = "select bookId,memberId,renew_count,issueTime from Issued_books";
        ResultSet resultSet = connect.execQuery(query);
        try {
            while (resultSet.next()) {
                // database se dat le kr aya hai
                String Date = resultSet.getString("issueTime");
                String BookId = resultSet.getString("bookId");
                String MemberId = resultSet.getString("memberId");
                String RenewCount = resultSet.getString("renew_count");

                String Days = Date.substring(8,10);
                String Month = Date.substring(5,7);
                String Year = Date.substring(2,4);

                //ye us date ko int me dal dia ham ne
                int day = Integer.parseInt(Days);
                int month = Integer.parseInt(Month);
                int year = Integer.parseInt(Year);
                //sql ki date
                System.out.println("Current Day : "+day);
                System.out.println("Current Month : "+month);
                System.out.println("Current Year : "+year);
                //yha se expiry date kai din ajay gai
                Preferences preferences = Preferences.getPreferences();
                int ExpiryNo = preferences.getNoOfDaysWithOutFIne();
                System.out.println("Expiry : "+ExpiryNo);
                //ye expiry date kai din ko sql kai date kai dino se plus krha hai
                int ExpiryDay = day+ExpiryNo;
                System.out.println("Expiry Day : "+ExpiryDay);
                //ye us din ki date ajay gi
                DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Calendar calobj = Calendar.getInstance();
                String newDateTaker = df.format(calobj.getTime());
                //us din ki date string me tor se
                String CheckDayInString = newDateTaker.substring(8,10);
                String CheckMonthInString = newDateTaker.substring(5,7);
                String CheckYearInString = newDateTaker.substring(2,4);
                //us din ki date int me dal de taky us ko change krsky
                int CheckDay = Integer.parseInt(CheckDayInString);
                int CheckMonth = Integer.parseInt(CheckMonthInString);
                int CheckYear = Integer.parseInt(CheckYearInString);

                System.out.println(CheckDay+" "+CheckMonth+" "+CheckYear);

                //yaha se ham fine calculate kry gai
                int calculatingFine = CheckDay - ExpiryDay;
                float new_fine = preferences.getFinePerDay() * calculatingFine;
                String FineInData = String.valueOf(new_fine);
                //Issue book bookId,memberId
                if(ExpiryDay < CheckDay){
                    String IS_BOOK_IS_IN_DEFAULTER = "SELECT COUNT(*) FROM defaulters WHERE bookId = ?";
                    try {
                        PreparedStatement statement = connect.connection.prepareStatement(IS_BOOK_IS_IN_DEFAULTER);
                        statement.setString(1,BookId);
                        ResultSet resultSet2 = statement.executeQuery();
                        if(resultSet2.next()){
                            int count = resultSet2.getInt(1);
                            if(count > 0){
                                System.out.println(count);
                                System.out.println("Already");
                                String DEFAULTER_UPDATE = "UPDATE defaulters SET fine = ? WHERE bookId= ?";
                                try{
                                    PreparedStatement statement2 = connect.connection.prepareStatement(DEFAULTER_UPDATE);
                                    statement2.setString(1, FineInData);
                                    statement2.setString(2, BookId);
                                    int result2 = statement2.executeUpdate();
                                    if(result2>0){
                                        System.out.println("Inserted");
                                    }else{
                                        System.out.println("Not Inserted");
                                    }
                                }catch (SQLException e){
                                    e.printStackTrace();
                                }
                            }else{
                                String DEFAULTER = "INSERT INTO defaulters (bookId,memberId,issueTime,renew_count,expiry_date,fine) VALUES ('"+BookId+"','"+MemberId+"','"+Date+"','"+RenewCount+"','"+newDateTaker+"','"+preferences.getFinePerDay()+"')";
                                if(connect.execAction(DEFAULTER)){
                                    System.out.println("Done");
                                }else{
                                    System.out.println("Not Done");
                                }
                            }
                        }
                    }catch (SQLException e) {
                        e.printStackTrace();
                    }
                }else{
                    System.out.println("Yes He have Time");
                }
            }
        }catch (SQLException e){
            Logger.getLogger(BooksCollection.class.getName()).log(Level.SEVERE,null,e);
        }

    }

}
