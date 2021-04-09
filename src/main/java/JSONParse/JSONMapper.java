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
    public static Restaurant unicaParser(String jsonURL) { //String

        ObjectMapper mapper = new ObjectMapper();

       Restaurant ruoka = new Restaurant();

        try {
            ruoka = mapper.readValue(new URL(jsonURL), Restaurant.class);
            //System.out.println(ruoka);
            //return ruoka.toString();
            //return ruoka;
            //return "Testi";
        } catch (IOException e) {
            e.printStackTrace();
            ruoka.setErrorMessage("Sivustoa ei ole saatavilla tai json url ei ollut oikea.");
            //return ruoka;
            // return ruoka;//"Sivustoa ei ole saatavilla tai json url ei ollut oikea.";
        } finally {
            return ruoka;
        }
    }
}
