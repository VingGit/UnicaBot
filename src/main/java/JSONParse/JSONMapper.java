package JSONParse;


import com.fasterxml.jackson.databind.ObjectMapper;
//import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.io.IOException;
import java.net.URL;


/**
 * JSONMapper luokka erilaisten JSON-rakenteiden mappaamiseen.
 * @ Jani Uotinen
 */
public class JSONMapper {

    /**
     * Metodi unica-ravintoloiden parseemiseen.
     * @author Valtteri Ingman
     */
    public static String unicaParser(String jsonURL) {
        ObjectMapper mapper = new ObjectMapper();
        Restaurant ruoka = new Restaurant();
        try {
             ruoka = mapper.readValue(new URL(jsonURL), Restaurant.class);
            //System.out.println(ruoka);
            //return "Testi";
        } catch (IOException e) {
            e.printStackTrace();
            //return "Sivustoa ei ole saatavilla tai json url ei ollut oikea.";
        }
        return ruoka.toString();
    }

    /**
     * @param url
     * @return Restaurant
     */

    public static Restaurant restaurantParser(String url){
        ObjectMapper mapper = new ObjectMapper();
        Restaurant restaurant = new Restaurant();
        try {
            restaurant = mapper.readValue(new URL(url), Restaurant.class);
            //System.out.println(ruoka);
            //return "Testi";
        } catch (IOException e) {
            e.printStackTrace();
            //return "Sivustoa ei ole saatavilla tai json url ei ollut oikea.";
        }
        return restaurant;
    }
}
