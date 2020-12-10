package Libaray;

import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Window;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Date;

public class Books {
    public TextField Sno;
    public TextField Name;
    public TextField isbn;
    public Button BookInterd;
    public Pane bookInsertion_Pane;
    public TextField Author;
    public DatePicker insertionDate;

    public void BookInserted(ActionEvent actionEvent) throws SQLException {
        Window owner = BookInterd.getScene().getWindow();

        if(Sno.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
                    "Please enter your S.NO");
            return;
        }
        if(Name.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
                    "Please enter your Name");
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

        String sno = Sno.getText();
        String name = Name.getText();
        String Isbn = isbn.getText();
        String author = Author.getText();
        LocalDate insertion_date = insertionDate.getValue();

        DbConn connect = new DbConn();

        int flag = connect.insert_Books_query_Executer(sno,name,Isbn,author,insertion_date);
        if(flag == 1){
            infoBox("Book Enter Successful!", null, "Successful");
        }
        else{
            infoBox("Falied!!!", null, "Failed");
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
}
