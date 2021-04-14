package JSONParse;

import java.util.ArrayList;
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
    private StringBuilder date; //paivamaara
    @JsonProperty("LunchTime")
    private StringBuilder lunchTime; //kuukaudenpaiva
    @JsonProperty("SetMenus")
    private List<SetMenu> Menu = null;//yhdessä päivässä on useampia ruokalaji vaihtoehtoja. tässä listassa on ne.

    private ArrayList<StringBuilder> MenuArray=new ArrayList<>();

    public StringBuilder getDate() {
        return date.append(date).delete(10,date.length()).append("\n");
    }

    public StringBuilder getLunchTime() {
        return lunchTime.append("\n"+"--------------------------------"+"\n").append(lunchTime).append("\n"+"--------------------------------"+"\n");
    }


    public ArrayList<StringBuilder> getMenu(){
        for (SetMenu s:
                Menu) {

            MenuArray.add(s.getRuokalajit());

        }
        return MenuArray;
    }
    @Override
    public String toString() {
        StringBuilder ruuat= new StringBuilder();
        for (SetMenu s:
                Menu) {
            ruuat.append(s).append("\n");


        }

        if(lunchTime.length()==0){
            lunchTime.replace(0,0, "ruokala on kiinni");
        }

        return
                "Päivä: " + date.substring(0,10) + '\n' +
                        "Aika: " + lunchTime +'\n'+  "-----------------------------------------------------" +'\n'+ ruuat ;
    }
}