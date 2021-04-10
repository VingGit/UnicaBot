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
        "ErrorText"
})

/**
 * Tulostaa ruokalan nimen, urlin, sekä allekkaain menusForDay olion menut. menut tulevat SetMenu luokasta
 * tätä voidaan ajatella ikäänkuin sisäkkäisenä funktiona. ei ehkä kaunein ratkaisu mutta se toimii
 *, ehkä joku osaavampi osaa/jaksaa ydhistää nämä yhdeksi luokaksi. toisaalta, se ei liene kannattavaa
 * jatko kehityksen kannalta.
 * menusForDays
 * @author Valtteri Ingman
 */
public class Restaurant {

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

  
  public boolean menuExists(){
        return menusForDays == null;
    }

    public String getRestaurantName(){
        this.restaurantBuilder=new StringBuilder();


            restaurantBuilder.append(menusForDays.get(0).getviikonMenu());
//vaihtamalla 0 johonkin toiseen numeroon, se tulostaa eri päivän. nyt mitä pitäisi tehdä
//on muuttaa tämän metodin nimeksi esmi tulostaTämäPäivä, pistää se palauttamaan embed viesti
//sekä tehdä tästä toinen metodi, joka luo listan embed viestejä, jolloin sitä botti voi tulostaa
//yksi viesti kerrallaan listan embed viestejä joissa on niitä ruokalistoja. näin 2000 merkkiä ei ylity.
//säästin toStringit tulevaisuutta varten.
//meen huomenna ajaa yhen nopeen kuorma-auto keikan, eli en oo paikal.
        return restaurantBuilder.toString();
    }
    public String getRestaurantUrl() {
        return restaurantUrl;
    }
    public List<MenusForDay> getMenusForDays() {
        return menusForDays;
    }
    public void setErrorMessage(String message) {
        errorMessage = message;
    }
    public String getErrorMessage() {
        return errorMessage;
    }

    @Override
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
