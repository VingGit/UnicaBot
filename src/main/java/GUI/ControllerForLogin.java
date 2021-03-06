package GUI;

import Botti.Botti;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import javax.security.auth.login.LoginException;


/**
 * Kontrolleri-luokka Login Scenen GUI-elementtien metodeille.
 * @author Jani Uotinen
 */
public class ControllerForLogin {
    @FXML
    private Label errorLabel;
    @FXML
    private PasswordField inputToken;
    @FXML
    private TextField username;
    @FXML
    private PasswordField password;
    @FXML
    private Button login;


    /**
     * Metodi yrittää käynnistää botin käyttäjän syöttämän tokenin kanssa. Mikäli LoginException, niin pyytää
     * yrittämään uudelleen. Onnistuneen loginin jälkeen yrittää vaihtaa sceneä.
     * @author Jani Uotinen
     * @param actionEvent hiiren klikkaus Login-napilla
     */
    @FXML
    public void login(ActionEvent actionEvent){
        String token = inputToken.getText();
        String email = username.getText();
        String pw = password.getText();
        try {
            Botti.setBotToken(token);// asetetaan tokeni valmiiksi, ei vielä käynnistetä bottia.
            System.out.println(Botti.getBotToken());
            Server.Commands.yhdista(email,pw);//kirjautuu ja muodostaa ftp yhtyden//todo instanssit  serverillä ja koneella on erit joten salasana on lähetettävä.
            Server.Commands.teeJaKirjoitaTdstoon(token, "temp.txt");
            Server.Commands.laheta("temp.txt");
            Server.Commands.poistaTdsto("temp.txt");
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
     * @param actionEvent hiiren klikkaus Exit-napilla
     */
    public void exit(ActionEvent actionEvent) {
        System.exit(0);
    }


    /**
     * Luo uuden Scenen scene2 ja vaihtaa siihen.
     * @author Jani Uotinen
     * @throws Exception useampi eri
     * */
    public void changeToScene2() throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/scene2.fxml"));
        Stage window = (Stage) login.getScene().getWindow();
        Scene scene2 = new Scene(root, 750, 500);
        scene2.getStylesheets().add(getClass().getResource("/css/scene2.css").toExternalForm());
        window.setScene(scene2);
        root.requestFocus();
    }

    double x,y;

    @FXML
    void dragged(MouseEvent event) {
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.setX(event.getScreenX()-x);
        stage.setY(event.getScreenY()-y);
    }

    @FXML
    void pressed(MouseEvent event) {
        x=event.getSceneX();
        y=event.getSceneY();
    }

}
