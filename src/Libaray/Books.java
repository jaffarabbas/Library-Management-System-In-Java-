package Libaray;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Window;

import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.ResourceBundle;

public class Books implements Initializable {
    public TextField Sno;
    public TextField Name;
    public TextField isbn;
    public Button BookInterd;
    public Pane bookInsertion_Pane;
    public TextField Author;
    //Editable
    private Boolean IsEditable = Boolean.FALSE;
    DbConn connect;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        connect = DbConn.getInstance();
    }


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

        //Edit check
        if(IsEditable){
            handleEditMethod();
            return;
        }

        String sno = Sno.getText();
        String name = Name.getText();
        String Isbn = isbn.getText();
        String author = Author.getText();

        int flag = connect.insert_Books_query_Executer(sno,name,Isbn,author);
        if(flag == 1){
            infoBox("Book Enter Successful!", null, "Successful");
        }
        else{
            infoBox("Falied", null, "Failed");
        }
    }

    public void UpdateInformation(BooksCollection.Book books){
        Sno.setText(books.getSno());
        Name.setText(books.getName());
        isbn.setText(books.getIsbn());
        Author.setText(books.getAuthor());
        Sno.setEditable(false);
        IsEditable = Boolean.TRUE;
    }
    public String timestamp(){
        return "";
    }
    //update query fro edit
    private void handleEditMethod() {
        BooksCollection.Book books = new BooksCollection.Book(Sno.getText(),Name.getText(),isbn.getText(),Author.getText(),timestamp(),true);
        if(connect.updateBook(books)){
            AlertMaker.showAlert("Successful!!","Book Updated");
            System.out.println(timestamp());
        }else{
            AlertMaker.showError("Error!","Update Failed");
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
