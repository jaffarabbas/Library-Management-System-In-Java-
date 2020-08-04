package Libaray;

import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Window;

public class Member {

    public AnchorPane rootpane;
    public Pane member_pane;
    public TextField name_member;
    public TextField number_member;
    public TextField address_member;
    public TextField card_member;
    public Button member_insertion;
    public DatePicker regester_date;

    public void BookInserted(ActionEvent actionEvent) {
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
        if(isbn.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
                    "Please enter your ISBN");
            return;
        }
        if(Author.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
                    "Please enter your Author");
            return;
        }
        if(insertionDate.getValue().equals("")) {
            showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
                    "Please enter your date");
            return;
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
    private static void infoBox(String infoMessage, String headerText, String title){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText(infoMessage);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.showAndWait();
}
