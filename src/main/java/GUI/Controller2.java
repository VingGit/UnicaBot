package GUI;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;


/**
 * Kontrolleri-luokka Scene2 GUI-elementtien metodeille.
 * @author Jani Uotinen
 */
public class Controller2 {
    @FXML
    private Label label1;
    @FXML
    private Button button1;


    /**
     * Testimetodi toiminnallisuuden demoamiseksi.
     * @author Jani Uotinen
     */
    public void toiminnallisuus(ActionEvent actionEvent) {
        label1.setText("Jibii toiminnallisuutta!");
    }


    /**
     * Sammuttaa ohjelman.
     * @author Jani Uotinen
     */
    public void exit(ActionEvent actionEvent) {
        System.exit(0);
    }


    /**
     * Luo uuden login scenen ja vaihtaa siihen.
     * @author Jani Uotinen
     */
    public void toLoginScreen(ActionEvent actionEvent) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/scene1.fxml"));
        Stage window = (Stage) button1.getScene().getWindow();
        Scene login = new Scene(root,300,275);
        login.getStylesheets().add(getClass().getResource("/css/login.css").toExternalForm());
        window.setScene(login);
    }
}
