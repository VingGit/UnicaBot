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
        try {
            Restaurant ruoka = mapper.readValue(new URL(jsonURL), Restaurant.class);
            //System.out.println(ruoka);
            return ruoka.toString();
            //return "Testi";
        } catch (IOException e) {
            e.printStackTrace();
            return "Sivustoa ei ole saatavilla";
        }
    }
}
