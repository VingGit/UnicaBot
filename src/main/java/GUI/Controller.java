package GUI;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Kontrolleri-luokka GUI-elementtien metodeille.
 * @author Jani Uotinen
 */
public class Controller {
    @FXML
    private VBox login;
    @FXML
    private Label errorLabel;
    @FXML
    private PasswordField inputToken;
    String discordToken = "ODIzOTE5OTczMjQ4MDA4MTk0.YFn1RQ.5RHMoDMkKU3ITzEzTqvUuKGCerU";

    @FXML
    public void login(ActionEvent actionEvent){
        String token = inputToken.getText();
        if (validToken(token)){
            errorLabel.setText("Oikea token saatu.");

        }
        // org
        try {
            Botti.Botti.launchBot(token);
        } catch (Exception e) {
            errorLabel.setText("Väärä token. Yritä uudelleen");
            inputToken.setText("");
        }
        login.setVisible(false);
        errorLabel.setText("Botti käynnissä...");
        //org

    }

    public void exit(ActionEvent actionEvent) {
        System.exit(0);
    }

    @FXML
    public void handleLoginSuccessful(ActionEvent event){
        System.out.println("Botin käynnistys onnistui!");
        //errorLabel.setText("Bot running...");
        // UI stagen vaihto
    }

    public boolean validToken(String givenToken){
        boolean value = false;
        if (givenToken.equals(discordToken)) {
            System.out.println("validated");
            value = true;
        }
        return value;
    }
}
