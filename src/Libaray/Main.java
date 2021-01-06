package Libaray;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        AnchorPane root = FXMLLoader.load(getClass().getResource("FXML/Login_page.fxml"));
        primaryStage.initStyle(StageStyle.UNDECORATED);
        Screen screen = Screen.getPrimary();
        Scene scene = new  Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
}
