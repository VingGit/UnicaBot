package GUI;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Graafisen käyttöliittymän käynnistys.
 * @author Jani Uotinen
 */
public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        //URL url = Main.class.getResource("scene1.fxml");
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/scene1.fxml"));
        primaryStage.setTitle("Discord Bot Dashboard");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(Main.class);
    }

}
