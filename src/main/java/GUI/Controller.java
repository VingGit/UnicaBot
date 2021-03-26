package GUI;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;

import javax.security.auth.login.LoginException;


/**
 * Kontrolleri-luokka Login Scenen GUI-elementtien metodeille.
 * @author Jani Uotinen
 */
public class Controller {
    @FXML
    private Label errorLabel;
    @FXML
    private PasswordField inputToken;
    @FXML
    private Button login;


    /**
     * Metodi yrittää käynnistää botin käyttäjän syöttämän tokenin kanssa. Mikäli LoginException, niin pyytää
     * yrittämään uudelleen. Onnistuneen loginin jälkeen yrittää vaihtaa sceneä.
     * @author Jani Uotinen
     */
    @FXML
    public void login(ActionEvent actionEvent){
        String token = inputToken.getText();

        try {
            Botti.Botti.launchBot(token);
            changeToScene2();
        } catch (LoginException e) {
            errorLabel.setText("Väärä token. Yritä uudelleen");
            inputToken.setText("");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * Sammuttaa ohjelman.
     * @author Jani Uotinen
     */
    public void exit(ActionEvent actionEvent) {
        System.exit(0);
    }


    /**
     * Luo uuden Scenen scene2 ja vaihtaa siihen.
     * @author Jani Uotinen
     */
    public void changeToScene2() throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/scene2.fxml"));
        Stage window = (Stage) login.getScene().getWindow();
        Scene scene2 = new Scene(root, 750, 500);
        scene2.getStylesheets().add(getClass().getResource("/css/scene2.css").toExternalForm());
        window.setScene(scene2);
    }
}
