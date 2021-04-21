package GUI;

import Server.Commands;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

/**
 * Graafisen käyttöliittymän käynnistys.
 * @author Jani Uotinen
 */
public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        //URL url = Main.class.getResource("login.fxml");
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/login.fxml"));
        primaryStage.setTitle("Discord Bot Dashboard");
        Scene login = new Scene(root,300,275);
        login.getStylesheets().add(getClass().getResource("/css/login.css").toExternalForm());
        primaryStage.setScene(login); //new Scene(root, 300, 275)
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        primaryStage.show();
    }


    public static void main(String[] args) throws IOException {
        launch(Main.class);
    }

}