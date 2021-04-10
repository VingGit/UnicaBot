package GUI;

import JSONParse.JSONMapper;
import JSONParse.Restaurant;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;


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
    private String url;
    @FXML
    private TextField inputName;
    private String name;
    @FXML
    private TextField inputCampus;
    private String campus;
    @FXML
    private TextField infoMessage;
    private String message;
    @FXML
    private ToggleGroup availabilityGroup;
    private RadioButton selected;
    private String availability;
    //@FXML
    //private RadioButton Open;
    //@FXML
    //private RadioButton Closed;
    @FXML
    private Button createButton;
    @FXML
    private Button editButton;
    @FXML
    private Button deleteButton;
    @FXML
    private Label info;
    private HashMap<String, String> inputValues = new HashMap<>();
    private Locations locations = new Locations();
    private ArrayList<Place> restaurants = locations.getRestaurants();

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

    /**
     *
     */
    public void handleCreateButton(ActionEvent actionEvent) throws IOException {
        locations = new Locations(); //get current
        try {
            getInputs();
        }catch (NullPointerException nul){
            nul.printStackTrace();
        }
        if (url == "") {
            showAlert(Alert.AlertType.ERROR, inputUrl.getScene().getWindow(), "URL", "Cannot create a new Unica restaurant without jsonUrl");
        }
        if (name == "") {
            showAlert(Alert.AlertType.ERROR, inputName.getScene().getWindow(), "Location name", "Location must have a name");
            //actionEvent.consume();
        }
        //else {

            //actionEvent.consume();
        //}
        // checking if the url is valid
        if (url != null && url != ""){
            boolean valid = validateUrl(url);
            if (!(valid)) {
                inputValues.put("url", "");
            }
        }
        Place newPlace = new Place(inputValues);
        locations.addPlace(inputValues);
        locations = new Locations(); // updated
        info.setText("New Location saved /n"+newPlace.toString());
    }

    public void handleEditButton(ActionEvent actionEvent) {
        locations = new Locations();
        restaurants = locations.getRestaurants();
        getInputs();
        if (inputName.getText().isEmpty()){
            showAlert(Alert.AlertType.ERROR, inputName.getScene().getWindow(), "Location name error", "Cannot edit location data without name");
            //actionEvent.consume();
        }else{
            //actionEvent.consume();
            Place old = Locations.getPlace(inputValues.get("name"));
            if (old == null) {
                infoMessage.setText("Name not found, edit not possible");
            }else {
                HashMap<String, String> edits = new HashMap<>();
                for (String key:inputValues.keySet()){
                    String value = inputValues.get(key);
                    if (!value.equals("")) {
                        edits.put(key,value);
                    }
                }
                locations.editPlace(edits);
            }
        }
    }
    public void handleDeleteButton(ActionEvent actionEvent){
        locations = new Locations();
        restaurants = locations.getRestaurants();
        /*
        if (inputName.getText().isEmpty()){
            showAlert(Alert.AlertType.ERROR, inputName.getScene().getWindow(), "Location name error", "Cannot edit location data without name");
        }else {
        */
            getInputs();
            Place deleting = Locations.getPlace(inputValues.get("name"));
            if (deleting == null){
                info.setText("Location not found, deletion cancelled");
            }else {

            }

        //}
    }

    /**
     * input validation
     */
    private void getInputs() throws NullPointerException{
        if (inputUrl.getText() != null && !inputUrl.getText().isEmpty()) {
            url = inputUrl.getText();
        } else {
            url = "";
        }
        if (inputName.getText() != null && !inputName.getText().isEmpty()) {
            name = inputName.getText();
        } else {
            name = "";
        }
        if (inputCampus.getText() != null && !inputCampus.getText().isEmpty()) {
            campus = inputCampus.getText();
        } else {
            campus = "";
        }
        selected = (RadioButton) availabilityGroup.getSelectedToggle();
        /*
        if (selected == isClosed){
            showAlert(Alert.AlertType.INFORMATION, isClosed.getScene().getWindow(), "Restaurant not available", "Add info message.");
        }

         */
        availability = selected.getText();
        if (infoMessage.getText() != null && !infoMessage.getText().isEmpty()) {
            message = infoMessage.getText();
        }else{message = "";}
        inputValues.put("campus", campus);
        inputValues.put("name", name);
        //inputValues.put("url", url);
        inputValues.put("availability", availability);
        inputValues.put("message", message);
        System.out.println(inputValues);
    }
        public boolean validateUrl(String test) {
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
