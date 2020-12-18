package Libaray;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class settings implements Initializable {
    public AnchorPane rootPane;
    public TextField ExpiryDay;
    public TextField Fine;
    public Button SettingsUpdate;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initDefaultValues();
    }
    public void SettingsUpdate(ActionEvent actionEvent) {
        float ChangeFine = Float.parseFloat(Fine.getText());
        int ExpiryDayChange = Integer.parseInt(ExpiryDay.getText());

        //set value to json
        Preferences preferences = Preferences.getPreferences();
        preferences.setFinePerDay(ChangeFine);
        preferences.setNoOfDaysWithOutFIne(ExpiryDayChange);

        Preferences.WritePreferenceToFile(preferences);
    }
    //default value from config.txt file
    public void initDefaultValues(){
        Preferences preferences = Preferences.getPreferences();
        Fine.setText(String.valueOf(preferences.getFinePerDay()));
        ExpiryDay.setText(String.valueOf(preferences.getNoOfDaysWithOutFIne()));
    }
}
