package JSONParse;

import java.util.ArrayList;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)

@JsonPropertyOrder({
        "RestaurantName",
        "RestaurantUrl",
        "PriceHeader",
        "Footer",
        "MenusForDays",
        "ErrorText",
        "campus",
        "availability",
        "message"
})

/**
 * Tulostaa ruokalan nimen, urlin, sekä allekkaain menusForDay olion menut. menut tulevat SetMenu luokasta
 * tätä voidaan ajatella ikäänkuin sisäkkäisenä funktiona. ei ehkä kaunein ratkaisu mutta se toimii
 *, ehkä joku osaavampi osaa/jaksaa ydhistää nämä yhdeksi luokaksi. toisaalta, se ei liene kannattavaa
 * jatko kehityksen kannalta.
 * menusForDays
 * @author Valtteri Ingman
 * @editor Sanna Volanen
 */
public class Restaurant {
    // propertys from Unica json API
    @JsonProperty("RestaurantName")
    private String restaurantName;
    @JsonProperty("RestaurantUrl")
    private String restaurantUrl;
    @JsonProperty("PriceHeader")
    private Object priceHeader;
    @JsonProperty("Footer")
    private String footer;
    @JsonProperty("MenusForDays")
    private List<MenusForDay> menusForDays = null;//lista jokaisesta päivästä useine menuineen.
    @JsonProperty("ErrorText")
    private Object errorText;
    private String errorMessage;
    private   StringBuilder restaurantBuilder;

    /**
     * additional properties for locations.json
     * @author Sanna Volanen
     */
    @JsonProperty("Campus")
    private String campus;
    @JsonProperty("Availability")
    private boolean availability;
    @JsonProperty("InfoMessage")
    private String infoMessage;

    private int day = 0;
    public Restaurant(){}
    /**
     * second contructor for setting a new restaurant through GUI
     * @author Sanna Volanen
     */
    public Restaurant (HashMap<String, String> input){
        String url = input.get("url");
        String name = input.get("name");
        String availability = input.get("availability");
        String campus = input.get("campus");
        String message = input.get("message");
        if (url.contains("https://www.unica.fi/modules/json")){
            JSONMapper.unicaParser(url);
        }else{
            this.restaurantName = name;
            this.restaurantUrl = url;
        }
        this.campus = campus;
        if (availability.equals("open")){
            this.availability = true;
        } else {
            this.availability = false;
        }
        this.infoMessage = message;
    }

    // GETTERIT
    public String getRestaurantUrl() {
        return restaurantUrl;
    }
    public String getRestaurantName(){
        return restaurantName;
    }
    public String getErrorMessage() {
        return errorMessage;
    }
    public String getAvailability(){
        if (availability){
            return "open";
        }
        else{
            return "closed";
        }
    }

    public String getCampus() {
        return campus;
    }
    public String getInfoMessage(){
        return infoMessage;
    }
    public ArrayList<StringBuilder> getRestaurantMenuArray(){

        return menusForDays.get(0).getMenu();
    }

    public ArrayList<StringBuilder> getRestaurantMenuArray(int i) {

        return menusForDays.get(i).getMenu();
    }

    public List<MenusForDay> getMenusForDays() {
        return menusForDays;
    }
    //SETTERIT
    public void setAvailability(boolean newAv){
        this.availability = newAv;
    }
    public void setInfoMessage(String newMsg){
        this.infoMessage = newMsg;
    }
    private void setUrl(String newValue) {
    }
    @Override
    public String toString() {
        return "Place{" +
                "campusArea='" + campus + '\'' +
                ", name='" + restaurantName + '\'' +
                ", url='" + restaurantUrl + '\'' +
                ", status=" + availability + '\''+ infoMessage +
                '}';
    }
    public boolean equals(Object o) {
        Restaurant that = (Restaurant) o;
        if (this == that) return true;
        if (that == null || getClass() != that.getClass()) return false;
        return campus.equals(that.campus) && restaurantName.equals(that.restaurantName) && restaurantUrl.equals(that.restaurantUrl) && availability==that.availability && infoMessage.equals(that.infoMessage);
    }
    public void edit(String key, String newValue){
        if (key.equals("url")) {
            if (restaurantUrl.equals("")) {
                if (!newValue.equals("")) {
                    setUrl(newValue);
                }
            } else if (!newValue.equals(restaurantUrl)) {
                setUrl(newValue);
            }
        }else if(key.equals("availability")) {
            if (newValue.equals("open") && !availability) {
                availability = true;
            } else if (newValue.equals("closed") && availability) {
                availability = false;
            }
        }else if(key.equals("message")){
            if(infoMessage.equals("")){
                if (!newValue.equals("")){
                    setInfoMessage(newValue);
                }
            }else if(!newValue.equals(infoMessage)){
                setInfoMessage(newValue);
            }
        }
    }


}
