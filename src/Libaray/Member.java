package Libaray;

import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Window;

import java.sql.SQLException;
import java.time.LocalDate;

public class Member {

    public AnchorPane rootpane;
    public Pane member_pane;
    public TextField name_member;
    public TextField number_member;
    public TextField address_member;
    public TextField card_member;
    public Button member_insertion;
    public DatePicker regester_date;

    public void BookInserted(ActionEvent actionEvent) throws SQLException {
        Window owner = member_insertion.getScene().getWindow();

        if(name_member.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
                    "Please enter your Name");
            return;
        }
        if(number_member.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
                    "Please enter your Number");
            return;
        }
        if(address_member.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
                    "Please enter your Address");
            return;
        }
        if(card_member.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
                    "Please enter your Card number");
            return;
        }
        if(regester_date.getValue().equals("")) {
            showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
                    "Please enter your date");
            return;
        }

        String name = name_member.getText();
        String number = number_member.getText();
        String address = address_member.getText();
        String card_number = card_member.getText();
        LocalDate insertion_date = regester_date.getValue();

        DbConn connect = new DbConn();

        int flag = connect.insert_Member_query_Executer(name,number,address,card_number,insertion_date);
        if(flag == 1){
            infoBox("Login Successful!", null, "Failed");
        }
        else{
            infoBox("Please enter correct Email and Password", null, "Failed");
        }

    }

    private static void showAlert(Alert.AlertType alertType, Window owner, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.initOwner(owner);
        alert.show();
    }
    private static void infoBox(String infoMessage, String headerText, String title) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText(infoMessage);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.showAndWait();
    }
}
