package Libaray;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import javafx.stage.Window;

import java.io.IOException;
import java.sql.SQLException;

public class Login_Controller {

    public TextField username;
    public PasswordField password;
    public Button submitbutton;


    public AnchorPane Panel;
    public AnchorPane login_page;


    //minimizw
    public void minimize(ActionEvent actionEvent) {
        Stage stage = (Stage)((Button) actionEvent.getSource()).getScene().getWindow();
        stage.setIconified(true);
    }
    //close app
    public void CloseApp(ActionEvent actionEvent) {
        System.exit(1);
    }


    public void submitbutton(ActionEvent event) throws SQLException, IOException,Exception {


        Window owner = submitbutton.getScene().getWindow();

        if(username.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
                    "Please enter your Username");
            return;
        }
        if(password.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
                    "Please enter your Password");
            return;


                String user = username.getText();
                String passwords = password.getText();

                DbConn jdb = new DbConn();
                boolean flag = jdb.validate(user, password);




                if(!flag) {
                    infoBox("Please enter correct Email and Password", null, "Failed");
                }else {
                    infoBox("Login Successful!", null, "Failed");

                    Student_plane.setOpacity(100);
                    Student_plane.setDisable(false);
                    Login_plane.setOpacity(0);
                    Login_plane.setDisable(true);
                    UCategoryAxis.setText(jdbcDao.getUserId());
                    Chart();


                }
            }


            Panel.setOpacity(100);
            Panel.setDisable(false);
            login_page.setOpacity(0);
            login_page.setDisable(true);
        }
    }

//
//    public void displayDashborad(ActionEvent event) {
//        Panel.setOpacity(100);
//        Panel.setDisable(false);
//        login_page.setOpacity(0);
//        login_page.setDisable(true);
//    }

    private static void infoBox(String infoMessage, String headerText, String title){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText(infoMessage);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.showAndWait();
    }

    private static void showAlert(Alert.AlertType alertType, Window owner, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.initOwner(owner);
        alert.show();
    }

}
