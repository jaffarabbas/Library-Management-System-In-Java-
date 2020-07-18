package Libaray;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;
import java.sql.SQLException;

public class Login_Controller {
    public TextField username;
    public PasswordField password;
    public Button submitbutton;


    //minimizw
    public void minimize(ActionEvent actionEvent) {
        Stage stage = (Stage)((Button) actionEvent.getSource()).getScene().getWindow();
        stage.setIconified(true);
    }
    //close app
    public void CloseApp(ActionEvent actionEvent) {
        System.exit(1);
    }

    public void login(ActionEvent event) throws SQLException, IOException,Exception {


        Window owner = submitbutton.getScene().getWindow();

        if(username.getText().isEmpty()) {
            showAlert(AlertType.ERROR, owner, "Form Error!",
                    "Please enter your email id");
            return;
        }
        if(passwordField.getText().isEmpty()) {
            showAlert(AlertType.ERROR, owner, "Form Error!",
                    "Please enter a password");
            return;
        }

        String emailId = .getText();
        String password = passwordField.getText();

        jdbcDao jdb = new jdbcDao();
        boolean flag = jdb.validate(emailId, password);

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

}
