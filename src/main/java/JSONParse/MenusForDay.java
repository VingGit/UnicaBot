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
    private String date; //paivamaara
    @JsonProperty("LunchTime")
    private String lunchTime; //kuukaudenpaiva
    @JsonProperty("SetMenus")
    private List<SetMenu> viikonMenu = null;
    private   StringBuilder menuBuilder;


    public String getDate() {
        return date;
    }

    public String getLunchTime() {
        return lunchTime;
    }

    public StringBuilder getviikonMenu(){
        this.menuBuilder=new StringBuilder();
        menuBuilder.append(date).delete(10,date.length()).append("\n");
        for (SetMenu s:
                viikonMenu) {
                    menuBuilder.append("\n"+"--------------------------------"+"\n").append(lunchTime).append("\n"+"--------------------------------"+"\n")
                    .append(s.getRuokalajit()).append("\n");
        }

        return menuBuilder;
    }
    @Override
    public String toString() {
        StringBuilder ruuat= new StringBuilder();
        for (SetMenu s:
                viikonMenu) {
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