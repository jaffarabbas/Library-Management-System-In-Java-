package Libaray;

import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;

import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.stage.StageStyle;

import javafx.stage.Window;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Login_Controller implements Initializable {

    public TextField username;
    public PasswordField password;
    public Button submitbutton;
    LinkedList<Login_Info> Login = new LinkedList<Login_Info>();

    public AnchorPane Panel;
    public AnchorPane login_page;
    public AnchorPane pannel_check;
    public Button login_button;


    DbConn connect;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        connect = DbConn.getInstance();
    }

    public void LoadDate(){
        String SELECT_BOOK_QUERY = "select * from login";
        //  PreparqedStatement preparedStatement = connect.connection.prepareStatement(SELECT_BOOK_QUERY);
        ResultSet resultSet = connect.execQuery(SELECT_BOOK_QUERY);
        try {
            while (resultSet.next()) {
                String id = resultSet.getString("id");
                String User = resultSet.getString("name");
                String Pass = resultSet.getString("password");
                Login.add(new Login_Info(id,User,Pass));
            }
        }catch (SQLException e){
            Logger.getLogger(BooksCollection.class.getName()).log(Level.SEVERE,null,e);
        }
    }
    public boolean validate(String user, String password) throws SQLException {
        int count_login_index = 0;
        LoadDate();
        while(Login.size()!=count_login_index){
            count_login_index++;
            if(Login.element().getUser().equals(user) && Login.element().getPass().equals(password)){
                return true;
            }else{
                System.out.println("No");
                return false;
            }
        }
        return false;
    }


    public void submitbutton(ActionEvent event) throws SQLException, IOException,Exception {


        Window owner = login_button.getScene().getWindow();

        if(username.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
                    "Please enter your Username");
            return;
        }
        if(password.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
                    "Please enter your Password");
            return;
        }

                String user = username.getText();
                String passwords = password.getText();

                DbConn jdb = new DbConn();
                boolean flag = validate(user, passwords);

                if(!flag) {
                    infoBox("Please enter correct Email and Password", null, "Failed");
                }else {
                    infoBox("Login Successful!", null, "Successful!!!!");
                    closeStage();
                    LoadMain();
                }
            }

            public  void  closeStage(){
                ((Stage)username.getScene().getWindow()).close();
            }

            //load the main pannel
            public void LoadMain(){
                 try{
                     Parent parent = FXMLLoader.load(getClass().getResource("FXML/Pannel.fxml"));
                     Stage stage = new Stage(StageStyle.UNDECORATED);
                     stage.setScene(new Scene(parent));
                     stage.show();
                 }
                 catch (IOException e){
                     e.printStackTrace();
                 }
            }


    //minimize
    public void minimize(ActionEvent actionEvent) {
        Stage stage = (Stage)((Button) actionEvent.getSource()).getScene().getWindow();
        stage.setIconified(true);
    }
    //close app
    public void CloseApp(ActionEvent actionEvent) {
        System.exit(1);
    }

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
