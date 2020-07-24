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

    public void DASHBORED_METHOD(ActionEvent actionEvent) {
    }

    public void BOOK_INSERTION_METHOD(ActionEvent actionEvent) {
        try{
            Parent parent = FXMLLoader.load(getClass().getResource("FXML/Book_Insertion.fxml"));
            Stage stage = new Stage(StageStyle.UNDECORATED);
            stage.setScene(new Scene(parent));
            stage.show();
        }
        catch (IOException e){
            Logger.getLogger(Library.class.getName()).log(Level.SEVERE,null,e);
        }
    }

    public void REGESTER_METHOD(ActionEvent actionEvent) {
    }

    public void MEMBER_LIST_METHOD(ActionEvent actionEvent) {
    }

    public void BOOK_LIST_METHOD(ActionEvent actionEvent) {
    }

    public void TAKE_AWAY_METHOD(ActionEvent actionEvent) {
    }

    public void NEW_MEMBER_METHOD(ActionEvent actionEvent) {
    }

    public void RESUBMIT_METHOD(ActionEvent actionEvent) {
    }

    public void DEFAULTER_METHOD(ActionEvent actionEvent) {
    }
}
