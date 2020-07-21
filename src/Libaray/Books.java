package Libaray;

import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

public class Books {
    public TextField Sno;
    public TextField Name;
    public TextField isbn;
    public TextField author;
    public Button BookInterd;
    public Pane bookInsertion_Pane;
    public TextField Author;
    public DatePicker insertionDate;

    public void BookInserted(ActionEvent actionEvent) {
        if(Sno.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
                    "Please enter your Question");
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
}
