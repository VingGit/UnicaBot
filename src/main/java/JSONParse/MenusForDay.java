package JSONParse;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "Date",
        "LunchTime",
        "SetMenus"
})

/**
 * Rakenneluokka JSON-parseemiseen.
 * @author Valtteri Ingman
 */
public class MenusForDay {

    @JsonProperty("Date")
    private String date;
    @JsonProperty("LunchTime")
    private String lunchTime;
    @JsonProperty("SetMenus")
    private List<SetMenu> setMenus = null;

    @Override
    public String toString() {
        StringBuilder ruuat= new StringBuilder();
        for (SetMenu s:
                setMenus) {
            ruuat.append(s).append("\n");
        }

        if(lunchTime==null){
            lunchTime="Ruokala kiinni";
        }

        return
                "Päivä: " + date.substring(0,10) + '\n' +
                        "Aika: " + lunchTime +'\n'+  "-----------------------------------------------------" +'\n'+ ruuat ;
    }
}