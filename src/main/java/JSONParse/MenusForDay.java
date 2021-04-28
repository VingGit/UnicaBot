package JSONParse;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

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


    public String getDate() {
        return date.toString().substring(0,10);
    }

    public StringBuilder getLunchTime() {
        return lunchTime.append("\n"+"--------------------------------"+"\n").append(lunchTime).append("\n"+"--------------------------------"+"\n");
    }


    public ArrayList<StringBuilder> getMenu(){
        ArrayList<StringBuilder> MenuArray=new ArrayList<>();
        for (SetMenu s:
                Menu) {

            MenuArray.add(s.getRuokalajit());

        }
        return MenuArray;
    }
    public String getDayStringNew() {
        Locale locale = new Locale("fi","FI");
        LocalDate date = LocalDate.parse(getDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        DayOfWeek day = date.getDayOfWeek();
        String viikonPaiva=day.getDisplayName(TextStyle.FULL, locale).toUpperCase();
        return viikonPaiva.substring(0,viikonPaiva.length()-2);
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