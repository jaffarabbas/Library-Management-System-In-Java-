package Libaray;

import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;


public class dashboard implements Initializable {
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
//        String DEFAULTER_COUNT = "";
//        ResultSet resultSet = connect.execQuery(DEFAULTER_COUNT);
//        int COUNT = 0;
//        try {
//            while (resultSet.next()) {
//                String SnoCount = resultSet.getString("sno");
//                COUNT++;
//            }
//            BookCount.setText(String.valueOf(COUNT));
//        }catch (SQLException e){
//            Logger.getLogger(BooksCollection.class.getName()).log(Level.SEVERE,null,e);
//        }
    }
    public void FineCalculator(){
        Preferences preferences = Preferences.getPreferences();
        float FineTaker = preferences.getFinePerDay();
        fine.setText(String.valueOf(FineTaker));
    }
}
