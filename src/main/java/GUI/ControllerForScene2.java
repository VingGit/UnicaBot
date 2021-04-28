package GUI;

import JSONParse.Location;
import JSONParse.Restaurant;
import com.iwebpp.crypto.TweetNaclFast;
import com.sun.javafx.collections.ElementObservableListDecorator;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.Window;
import okhttp3.internal.ws.RealWebSocket;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;


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

    @FXML
    private RadioButton Open;
    @FXML
    private RadioButton Closed;
    @FXML
    private Button createButton;
    @FXML
    private Button editButton;
    @FXML
    private Button deleteButton;
    @FXML
    private Label info;
    private final HashMap<String, String> inputValues = new HashMap<>();
    private LocationsController locations = new LocationsController();
    @FXML
    private ListView<String> savedResList;
    private ObservableList<String> names = FXCollections.observableArrayList();
    private ArrayList<Restaurant> restaurants = locations.getRestaurantList();
    //private boolean nameFocus =false;
    private String selectedName = "";

    double x,y;

    @FXML
    void dragged(MouseEvent event) {
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.setX(event.getScreenX()-x);
        stage.setY(event.getScreenY()-y);
    }

    @FXML
    void pressed(MouseEvent event) {
        x = event.getSceneX();
        y = event.getSceneY();
    }
    public void initialize(){
        List<String> nameList =  locations.getNames();
        for(String name:nameList) {
            names.add(name);
        }
        names.add("New");
        savedResList.setItems(names);
        ChangeListener<? super String> choiceChangeListener;
        savedResList.getSelectionModel().selectedItemProperty().addListener(choiceChangeListener =(observable, oldChoice, newChoice)->{
                selectedName = newChoice;
                if (selectedName=="New"){
                    inputName.setText("");
                    inputUrl.setText("");
                    inputCampus.setText("");
                    infoMessage.setText("");
                    setEditables();
                }else{
                    Location selectedLoc = locations.getLocation(selectedName);
                    if (selectedLoc!= null) {
                        HashMap<String, String > values = selectedLoc.getValues();
                        inputName.setText(selectedName);
                        inputUrl.setText(values.get("url"));
                        inputCampus.setText(values.get("campus"));
                        infoMessage.setText(values.get("infoMessage"));
                        if (values.get("availability").equals("kyllä")){
                            availabilityGroup.selectToggle(Open);
                        }else{
                            availabilityGroup.selectToggle(Closed);
                        }
                        setEditables();
                    }else{
                        showAlert(Alert.AlertType.ERROR, inputUrl.getScene().getWindow(), "404", "Error in retrieving restaurant");
                    }
                }
            }
        );
    }
    //Bindings
    //createButton.disableProperty().bind(inputName.textProperty().isEmpty());
    /**
    savedResList.getSelectionModel().selectedItemProperty().addListener(
            restaurantChangeListener = (observable, oldValue, newValue)->{
                selecterRestaurant = newValue;
                modifiedProperty.set(false);
                if(newValue)
    })*/
    @FXML
    public void setEditables() {
            inputCampus.setEditable(true);
            inputUrl.setEditable(true);
            infoMessage.setEditable(true);
    }
    /**
     * handler method for Create new location button
     * @param actionEvent == buttonClick
     * @author Sanna Volanen
     */
    public void handleCreateButton(ActionEvent actionEvent) throws IOException {
        locations = new LocationsController(); //get current status
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
        //Place newPlace = new Place(inputValues);
        locations.addPlace(inputValues);
        clearInputs();
        locations = new LocationsController(); // update local variable
        info.setText("New Location saved "+"/n"+inputValues.toString());
    }
    /**
     * handler method for Edit location button
     * @param actionEvent == buttonClick on Edit button
     * @author Sanna Volanen
     */
    public void handleEditButton(ActionEvent actionEvent) {
        //locations = new Locations();
        if (inputName.getText().isEmpty()){
            showAlert(Alert.AlertType.ERROR, inputName.getScene().getWindow(), "Location name error", "Cannot edit location data without name");
            //actionEvent.consume();
        }else{
            getInputs();
            Restaurant old = locations.getRestaurant(inputValues.get("name"));
            if (old == null) {
                info.setText("Name not found, edit not possible");
            }else {
                actionEvent.consume();
                inputUrl.setText(old.getRestaurantUrl());
                inputCampus.setText(old.getCampus());
                infoMessage.setText(old.getInfoMessage());
                //clearInputs();
                locations.editPlace(inputValues);
            }
        }
    }

    /**
     * handler method for Delete location button
     * @param actionEvent == buttonClick
     * @author Sanna Volanen
     */
    public void handleDeleteButton(ActionEvent actionEvent){
        //locations = new Locations();
        restaurants = locations.getRestaurantList();
        /*
        if (inputName.getText().isEmpty()){
            showAlert(Alert.AlertType.ERROR, inputName.getScene().getWindow(), "Location name error", "Cannot edit location data without name");
        }else {
        */
        getInputs();
        clearInputs();
        Restaurant deleting = locations.getRestaurant(inputValues.get("name"));
        if (deleting == null){
            info.setText("Location not found, deletion cancelled");
        }else {
            locations.deletePlace(deleting);
        }

        //}
    }

    /**
     * input validation
     * @author Sanna Volanen
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
        if (selected.getText().equals("open")) {
            availability = "kyllä";
        }else{availability = "ei";}
        if (infoMessage.getText() != null && !infoMessage.getText().isEmpty()) {
            message = infoMessage.getText();
        }else{message = "";}
        inputValues.put("campus", campus);
        inputValues.put("name", name);
        inputValues.put("url", url);
        inputValues.put("availability", availability);
        inputValues.put("infoMessage", message);
        System.out.println("GUI values: "+inputValues);

    }

    /**
     * method for checking if given url is valid
     * @param test
     * @return valid= RETURN true and invalid=RETURN false
     */
    public boolean validateUrl(String test) {
        try{
            URL json = new URL(test);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            System.out.println("Invalid url");
            return false;
        }
        System.out.println("Valid url");
        return true;
    }
    /**
     * @author Sanna Volanen
     */
    public void clearInputs(){
        inputCampus.clear();
        inputName.clear();
        inputUrl.clear();
        infoMessage.clear();
        //actionEvent.consume();
    }

    /**
     * method for creating a pop up alert when something is wrong or needs attention in input
    * @author Sanna Volanen
     */
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
        Scene login = new Scene(root,300,350);
        login.getStylesheets().add(getClass().getResource("/css/login.css").toExternalForm());
        window.setScene(login);
    }



}

