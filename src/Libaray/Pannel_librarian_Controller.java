package Libaray;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Pannel_librarian_Controller {

    public Button dashbored;
    public Button book_insertion;
    public Button regester;
    public Button Member_list;
    public Button Book_list;
    public Button take_away;
    public Button newMember;
    public Button Resubmit;
    public Button Desfaulter;
    public AnchorPane pannel_check;
    public AnchorPane rootpane;

    public void DASHBORED_METHOD(ActionEvent actionEvent) {
        try{
            AnchorPane pane = FXMLLoader.load(getClass().getResource("FXML/dashboard.fxml"));
            rootpane.getChildren().setAll(pane);
        }
        catch (IOException e){
            Logger.getLogger(Library.class.getName()).log(Level.SEVERE,null,e);
        }
    }

    public void BOOK_INSERTION_METHOD(ActionEvent actionEvent) {
            try{
                AnchorPane pane = FXMLLoader.load(getClass().getResource("FXML/Book_Insertion.fxml"));
                rootpane.getChildren().setAll(pane);
            }
            catch (IOException e){
                Logger.getLogger(Library.class.getName()).log(Level.SEVERE,null,e);
            }
    }

    public void REGESTER_METHOD(ActionEvent actionEvent) {
        try{
            AnchorPane pane = FXMLLoader.load(getClass().getResource("FXML/Regester.fxml"));
            rootpane.getChildren().setAll(pane);
        }
        catch (IOException e){
            Logger.getLogger(Library.class.getName()).log(Level.SEVERE,null,e);
        }
    }

    public void MEMBER_LIST_METHOD(ActionEvent actionEvent) {
        try{
            AnchorPane pane = FXMLLoader.load(getClass().getResource("FXML/Member_list.fxml"));
            rootpane.getChildren().setAll(pane);
        }
        catch (IOException e){
            Logger.getLogger(Library.class.getName()).log(Level.SEVERE,null,e);
        }
    }

    public void BOOK_LIST_METHOD(ActionEvent actionEvent) {
        try{
            AnchorPane pane = FXMLLoader.load(getClass().getResource("FXML/Book_collection.fxml"));
            rootpane.getChildren().setAll(pane);
        }
        catch (IOException e){
            Logger.getLogger(Library.class.getName()).log(Level.SEVERE,null,e);
        }
    }

    public void TAKE_AWAY_METHOD(ActionEvent actionEvent) {
        try{
            AnchorPane pane = FXMLLoader.load(getClass().getResource("FXML/Issue_Book.fxml"));
            rootpane.getChildren().setAll(pane);
        }
        catch (IOException e){
            Logger.getLogger(Library.class.getName()).log(Level.SEVERE,null,e);
        }
    }

    public void NEW_MEMBER_METHOD(ActionEvent actionEvent) {
        try{
            AnchorPane pane = FXMLLoader.load(getClass().getResource("FXML/Member_Insertion.fxml"));
            rootpane.getChildren().setAll(pane);
        }
        catch (IOException e){
            Logger.getLogger(Library.class.getName()).log(Level.SEVERE,null,e);
        }
    }

    public void RESUBMIT_METHOD(ActionEvent actionEvent) {
        try{
            AnchorPane pane = FXMLLoader.load(getClass().getResource("FXML/Reniew_book.fxml"));
            rootpane.getChildren().setAll(pane);
        }
        catch (IOException e){
            Logger.getLogger(Library.class.getName()).log(Level.SEVERE,null,e);
        }
    }

    public void DEFAULTER_METHOD(ActionEvent actionEvent) {
    }

    public void minimize(ActionEvent actionEvent) {
        Stage stage = (Stage)((Button) actionEvent.getSource()).getScene().getWindow();
        stage.setIconified(true);
    }

    public void CloseApp(ActionEvent actionEvent) {
        System.exit(1);
    }
}
