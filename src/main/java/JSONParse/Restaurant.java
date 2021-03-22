package JSONParse;

import java.util.List;
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
 * Rakenneluokka JSON-parseemiseen.
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


    @Override
    public String toString() {
        String ruuat="";
        for (MenusForDay s:
                menusForDays) {
            ruuat+=s+"\n";
        }
        if(ruuat.equals("")){
            ruuat="Ruokala on kiinni, lue lisää heidän sivuiltaan.";
        }

        return
                "Ruokala: " + restaurantName + '\n' +
                        "URL: " + restaurantUrl + '\n'+'\n' +
                        ruuat + '\n';
    }
}
