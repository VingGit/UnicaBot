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

    public String getRestaurantName(){
        return restaurantName;
    }
    public String getRestaurantUrl(){
        return restaurantUrl;
    }
    public boolean menuExists(){
        return menusForDays== null;
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
