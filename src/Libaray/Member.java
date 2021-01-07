package Libaray;

import Libaray.DbConnection.DbConn;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Window;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class Member implements Initializable {

    public AnchorPane rootpane;
    public Pane member_pane;
    public TextField name_member;
    public TextField number_member;
    public TextField address_member;
    public TextField card_member;
    public Button member_insertion;
    //Editable
    private Boolean IsEditable = Boolean.FALSE;
    DbConn connect;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        connect = DbConn.getInstance();
    }

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

        //Edit check
        if(IsEditable){
            handleEditMethod();
            return;
        }

        String name = name_member.getText();
        String number = number_member.getText();
        String address = address_member.getText();
        String card_number = card_member.getText();

        int flag = connect.insert_Member_query_Executer(name,number,address,card_number);
        if(flag == 1){
            infoBox("Member add Successful!", null, "Successful!!");
        }
        else{
            infoBox("Failed!!", null, "Failed");
        }

    }

    public void UpdateInformation(Member_list.Members members){
        card_member.setText(members.getCard_number());
        number_member.setText(members.getNumber());
        name_member.setText(members.getName());
        address_member.setText(members.getAddress());
        card_member.setEditable(false);
        IsEditable = Boolean.TRUE;
    }
    //update query fro edit
    private void handleEditMethod() {
        Member_list.Members member = new Member_list.Members(name_member.getText(),number_member.getText(),address_member.getText(),card_member.getText(),timestamp());
        if(connect.updateMember(member)){
            AlertMaker.showAlert("Successful!!","Member Updated");
        }else{
            AlertMaker.showError("Error!","Update Failed");
        }
    }

    private String timestamp() {
        return "";
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
