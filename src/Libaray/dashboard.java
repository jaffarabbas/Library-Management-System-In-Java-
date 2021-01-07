package Libaray;

import Libaray.DbConnection.DbConn;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;


public class dashboard implements Initializable {
    public TextField SearchBooksField;
    public ListView<String> ViewSearchedBooks;
    public TextField SearchMemberField;
    public ListView<String> ViewSearchedMember;
    LinkedList<Member_list.Members> MemberList = new LinkedList<>();
    LinkedList<BooksCollection.Book> BookList = new LinkedList<BooksCollection.Book>();
    ObservableList<String> issuedData = FXCollections.observableArrayList();
    ObservableList<String> issuedData2 = FXCollections.observableArrayList();
    public AnchorPane rootPane;
    public Text BookCount;
    public Text MemberCount;
    public Text RenewedCount;
    public Text IssuedCount;
    public Text DefaulterCount;
    public Text fine;
    public ImageView settings;
    public Image settingImage;
    DbConn connect;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        connect = DbConn.getInstance();
        BookCounter();
        MemberCounter();
        IssuedCounter();
        RenewedCounter();
        DefaulterCounter();
        FineCalculator();
    }

    public void settings(MouseEvent mouseEvent) {
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("FXML/settings.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Settings");
            stage.setScene(new Scene(root));
            stage.show();
        }
        catch (IOException e){
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE,null,e);
        }
    }
    public void BookCounter(){
        String BOOK_COUNT = "select sno from book_collection";
        ResultSet resultSet = connect.execQuery(BOOK_COUNT);
        int COUNT = 0;
        try {
            while (resultSet.next()) {
                String Count = resultSet.getString("sno");
                COUNT++;
            }
            BookCount.setText(String.valueOf(COUNT));
        }catch (SQLException e){
            Logger.getLogger(BooksCollection.class.getName()).log(Level.SEVERE,null,e);
        }
    }
    public void MemberCounter(){
        String MEMBER_COUNT = "select card_number from member_collection";
        ResultSet resultSet = connect.execQuery(MEMBER_COUNT);
        int COUNT = 0;
        try {
            while (resultSet.next()) {
                String Count = resultSet.getString("card_number");
                COUNT++;
            }
            MemberCount.setText(String.valueOf(COUNT));
        }catch (SQLException e){
            Logger.getLogger(BooksCollection.class.getName()).log(Level.SEVERE,null,e);
        }
    }
    public void IssuedCounter(){
        String ISSUED_COUNT = "select availiblity from book_collection where availiblity = false";
        ResultSet resultSet = connect.execQuery(ISSUED_COUNT);
        int COUNT = 0;
        try {
            while (resultSet.next()) {
                String Count = resultSet.getString("availiblity");
                COUNT++;
            }
            IssuedCount.setText(String.valueOf(COUNT));
        }catch (SQLException e){
            Logger.getLogger(BooksCollection.class.getName()).log(Level.SEVERE,null,e);
        }
    }
    public void RenewedCounter(){
        String DEFAULTER_COUNT = "select renew_count from issued_books where renew_count !=0";
        ResultSet resultSet = connect.execQuery(DEFAULTER_COUNT);
        int COUNT = 0;
        try {
            while (resultSet.next()) {
                int SnoCount = resultSet.getInt("renew_count");
                COUNT++;
            }
            RenewedCount.setText(String.valueOf(COUNT));
        }catch (SQLException e){
            Logger.getLogger(BooksCollection.class.getName()).log(Level.SEVERE,null,e);
        }
    }
    public void DefaulterCounter(){
        String DEFAULTER_COUNT = "select bookId from defaulters";
        ResultSet resultSet = connect.execQuery(DEFAULTER_COUNT);
        int COUNT = 0;
        try {
            while (resultSet.next()) {
                String SnoCount = resultSet.getString("bookId");
                COUNT++;
            }
            DefaulterCount.setText(String.valueOf(COUNT));
        }catch (SQLException e){
            Logger.getLogger(BooksCollection.class.getName()).log(Level.SEVERE,null,e);
        }
    }
    public void FineCalculator(){
        Preferences preferences = Preferences.getPreferences();
        float FineTaker = preferences.getFinePerDay();
        fine.setText(String.valueOf(FineTaker));
    }

    //Search Algorithm uses here

    public void SearchBook(ActionEvent actionEvent) {
        String id = SearchBooksField.getText();
        BookDataTaker(id);
        int count_book = 0;
        while(BookList.size()!=count_book){
            if(BookList.element().getSno().equals(id)){
                System.out.println("yes");
                issuedData.add("Book Sno : "+BookList.element().getSno());
                issuedData.add("Book Name : "+BookList.element().getName());
                issuedData.add("Book ISBN : "+BookList.element().getIsbn());
                issuedData.add("Book Author : "+BookList.element().getAuthor());
                issuedData.add("Book Insertion Date : "+BookList.element().getDate());
                issuedData.add("Book Availability : "+BookList.element().getAviliblity());
                break;
            }else{
                System.out.println("No");
            }
            count_book++;
        }
        ViewSearchedBooks.getItems().setAll(issuedData);
    }

    public void SearchMember(ActionEvent actionEvent) {
        String id = SearchMemberField.getText();
        MemberDataTaker(id);
        int count_member = 0;
        while(MemberList.size()!=count_member){
            if(MemberList.element().getCard_number().equals(id)){
                System.out.println("yes");
                issuedData2.add("Member Name : "+MemberList.element().getName());
                issuedData2.add("Member Number : "+MemberList.element().getNumber());
                issuedData2.add("Member Address : "+MemberList.element().getAddress());
                issuedData2.add("Member Card Number : "+MemberList.element().getCard_number());
                issuedData2.add("Member Insertion Date : "+MemberList.element().getDate());
                break;
            }else{
                System.out.println("No");
            }
            count_member++;
        }
        ViewSearchedMember.getItems().setAll(issuedData2);
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
