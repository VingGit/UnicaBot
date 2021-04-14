package JSONParse;

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
    private List<MenusForDay> menusForDays = null;
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
    public Restaurant (String url, String name, String campus, boolean availability, String message){
        if (url.contains("https://www.unica.fi/modules/json")){
            JSONMapper.unicaParser(url);
        }else{
            this.restaurantName = name;
            this.restaurantUrl = url;
        }
        this.campus = campus;
        this.availability = availability;
        this.infoMessage = message;
    }

    // GETTERIT
    public String getRestaurantUrl() {
        return restaurantUrl;
    }
    public String getErrorMessage() {
        return errorMessage;
    }
    //lisätty perusgetterit
    public String getRestaurantName(){
        return restaurantName;
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
    //muutettu nimeä, koska ei palauta nimeä vaan yhden päivän menun
    public String getRestaurantMenuToday(){
        this.restaurantBuilder=new StringBuilder();
            restaurantBuilder.append(menusForDays.get(day).getviikonMenu());
            /**
             * vaihtamalla 0 johonkin toiseen numeroon, se tulostaa eri päivän. nyt mitä pitäisi tehdä
            on muuttaa tämän metodin nimeksi esmi tulostaTämäPäivä, pistää se palauttamaan embed viesti
            sekä tehdä tästä toinen metodi, joka luo listan embed viestejä, jolloin sitä botti voi tulostaa
            yksi viesti kerrallaan listan embed viestejä joissa on niitä ruokalistoja. näin 2000 merkkiä ei ylity.
            säästin toStringit tulevaisuutta varten.
            meen huomenna ajaa yhen nopeen kuorma-auto keikan, eli en oo paikal.
             */
        return restaurantBuilder.toString();
    }
    //SETTERIT
    /* ei varmaan aleta muuttelemaan Unican APIn jsonien sisältöä
    public void setErrorMessage(String message) {
        errorMessage = message;
    }*/

    public boolean menuExists(){
        return menusForDays == null;
    }
    public List<MenusForDay> getMenusForDays() {
        return menusForDays;
    }

    @Override
    /* tää olisi normaali toString oliolle
    public String toString() {
        return "Place{" +
                "campusArea='" + campusArea + '\'' +
                ", name='" + name + '\'' +
                ", url='" + url + '\'' +
                ", status=" + status +
                '}';
    }*/

    public String toString() {
        StringBuilder ruuat= new StringBuilder();
        for (MenusForDay s:
                menusForDays) {
            ruuat.append(s).append("\n");
        }
        if(ruuat.toString().equals("")){
            ruuat = new StringBuilder("Ruokala on kiinni, lue lisää heidän sivuiltaan.");
        }

        return
                "Ruokala: " + restaurantName + '\n' +
                        "URL: " + restaurantUrl + '\n'+'\n' +
                        ruuat + '\n';
    }
}
