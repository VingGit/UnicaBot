package JSONParse;

import java.util.ArrayList;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.fasterxml.jackson.annotation.*;
import javafx.beans.Observable;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

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
public class Restaurant extends Location{
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
    private List<MenusForDay> menusForDays = new ArrayList<>();//lista jokaisesta päivästä useine menuineen.
    @JsonProperty("ErrorText")
    private Object errorText;
    private String errorMessage;

    //private   StringBuilder restaurantBuilder;

    /**
     * additional properties for locations.json
     * @author Sanna Volanen
     */
    @JsonProperty("command")
    private String command;
    //@JsonProperty("restaurant")
    //private HashMap<String,String> values;
    @JsonProperty("Campus")
    private String campus;
    @JsonProperty("Availability")
    private String availability;
    @JsonProperty("InfoMessage")
    private String infoMessage;
    private final int day = 0;
    //CONSTRUCTORS
    public Restaurant(){}
    /**
     * second constructor for setting a new restaurant through GUI
     * @author Sanna Volanen
     */
   public Restaurant(String url){
       if (url.contains("https://www.unica.fi/modules/json")){
           Restaurant r = JSONMapper.unicaParser(url);
           this.restaurantUrl = r.restaurantUrl;
           this.restaurantName = r.restaurantName;
           this.errorText = r.errorText;
           this.errorMessage = r.errorMessage;
           this.priceHeader = r.priceHeader;
           this.footer = r.footer;
           this.menusForDays = r.menusForDays;
       }else{
           this.restaurantUrl = url;
           this.restaurantName=super.getName();
           this.errorText = null;
           this.errorMessage = "";
           this.priceHeader = "";
           this.footer = "";
           this.menusForDays = null;
       }
   }
    //private final StringProperty resName = new SimpleStringProperty(this, "resName", "");
    public Restaurant (HashMap<String, String> input){
        super(input);
        //System.out.println("Input hashmap: \""+input);
        String url = input.get("url");
        if (url.contains("https://www.unica.fi/modules/json")){
            Restaurant r = JSONMapper.unicaParser(url);
            this.restaurantName = r.restaurantName;
            this.errorText = r.errorText;
            this.errorMessage = r.errorMessage;
            this.priceHeader = r.priceHeader;
            this.footer = r.footer;
        }else{
            this.restaurantName = super.getName();
            this.errorText = null;
            this.errorMessage = "";
            this.priceHeader = "";
            this.footer = "";
        }
        this.restaurantUrl = url;
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
    public boolean isAvailable(){
       String av = super.getAvailability();
            return av.equals("kyllä");
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
    public void setErrorMessage(String errorMessage) {this.errorMessage = errorMessage;}
    public void setAvailability(String newAv){
        this.availability = newAv;
    }
    public void setInfoMessage(String newMsg){
        this.infoMessage = newMsg;
    }

    private void setUrl(String newValue) {
    }
    public HashMap<String,String> toHashMap(){
        HashMap<String, String > params = new HashMap<>();
        params.put("name", restaurantName);
        params.put("url", restaurantUrl);
        params.put("campus", campus);
        params.put("availability", availability);
        params.put("infoMessage", infoMessage);
        return params;
    }
    @Override
    public String toString() {
        return "Restaurant{" +
                "campusArea='" + campus + '\'' +
                ", name='" + restaurantName + '\'' +
                ", url='" + restaurantUrl + '\'' +
                ", availability=" + availability + '\''+
                ", infoMessage=" + infoMessage +
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
            if (availability != null){
                if (!availability.equals(newValue)) {
                    availability = newValue ;
                }
            }else{
                availability = newValue;
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
