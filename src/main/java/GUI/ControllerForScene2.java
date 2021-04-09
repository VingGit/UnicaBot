package GUI;

import JSONParse.JSONMapper;
import JSONParse.Restaurant;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;


/**
 * Kontrolleri-luokka Scene2 GUI-elementtien metodeille.
 * @author OWNER Jani Uotinen
 * @author DEVELOPER Sanna Volanen
 */
public class ControllerForScene2 {
    @FXML
    private Label label1;
    @FXML
    private Button backButton;
    @FXML
    private TextField inputUrl;
    @FXML
    private TextField inputName;
    @FXML
    private TextField inputCampus;
    @FXML
    private TextField statusMessage;
    @FXML
    private ToggleGroup availabilityGroup;
    @FXML
    private RadioButton isOpen;
    @FXML
    private RadioButton isClosed;
    @FXML
    private Button createButton;
    @FXML
    private Button editButton;
    @FXML
    private Button deleteButton;
    @FXML
    private Label info;
    private HashMap<String, String> inputValues;
    private Locations locations = new Locations();
    private ArrayList<Place> restaurants = locations.getRestaurants();

    /**
     *
     */
    public void handleCreateButton(ActionEvent actionEvent) throws IOException, NullPointerException {
        /*
        if (inputUrl.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, inputUrl.getScene().getWindow(), "URL", "Cannot create a new Unica restaurant without jsonUrl");
        }
        if (inputName.getText().i) {
            showAlert(Alert.AlertType.ERROR, inputName.getScene().getWindow(), "Location name", "Location must have a name");
            //actionEvent.consume();
        }
         */
        //else {
            getInputs();
            actionEvent.consume();
        //}
        // checking if the url is valid
        String urlValue = inputValues.get("url");
        if (urlValue != null && urlValue != "") {
            boolean valid = validateUrl(urlValue);
            if (!(valid)) {
                inputValues.put("url", "");
            }
        }
        Place newPlace = new Place(inputValues);
        locations.addPlace(newPlace);
        info.setText("New Location saved /n"+newPlace.toString());
    }

    public void handleEditButton(ActionEvent actionEvent) {
        restaurants = Locations.getRestaurants();
        if (inputName.getText().isEmpty()){
            showAlert(Alert.AlertType.ERROR, inputName.getScene().getWindow(), "Location name error", "Cannot edit location data without name");
            actionEvent.consume();
        }else{
            getInputs();
            actionEvent.consume();
            Place editing = Locations.getPlace(inputValues.get("name"));
            if (editing != null){
                for (String inputKey: inputValues.keySet()){

                }
            }
        }
    }
    public void handleDeleteButton(ActionEvent actionEvent){
        restaurants = Locations.getRestaurants();
        /*
        if (inputName.getText().isEmpty()){
            showAlert(Alert.AlertType.ERROR, inputName.getScene().getWindow(), "Location name error", "Cannot edit location data without name");
        }else {
        */
            getInputs();
            Place deleting = Locations.getPlace(inputValues.get("name"));
            if (deleting == null){
                info.setText("Location not found, deletion cancelled");
            }
        //}
    }

    private void getInputs(){
        if (inputUrl.getText() != null && !inputUrl.getText().isEmpty()){
            inputValues.put("url",inputUrl.getText());
        }else{ inputValues.put("url","");}
        if (inputName.getText() != null && !inputName.getText().isEmpty()) {
            inputValues.put("name", inputName.getText());
        }else{inputValues.put("name", ""); }
        inputValues.put("campus", inputCampus.getText());
        Toggle selected = availabilityGroup.getSelectedToggle();
        if (selected == isClosed){
            showAlert(Alert.AlertType.INFORMATION, isClosed.getScene().getWindow(), "Restaurant not available", "Add info message.");
        }
        inputValues.put("availability", selected.toString());
        inputValues.put("message",statusMessage.getText());
        System.out.println(inputValues);
    }

    public boolean validateUrl(String test) throws MalformedURLException {
        try{
            URL json = new URL(test);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }


    private void showAlert(Alert.AlertType alertType, Window owner, String title, String message){
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText("Input fail");
        alert.setContentText(message);
        alert.initOwner(owner);
        alert.show();
    }

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
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/login.fxml"));
        Stage window = (Stage) backButton.getScene().getWindow();
        Scene login = new Scene(root,300,275);
        login.getStylesheets().add(getClass().getResource("/css/login.css").toExternalForm());
        window.setScene(login);
    }



}
