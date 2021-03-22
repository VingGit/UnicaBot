package Botti;

import JSONParse.JSONMapper;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Eventtien kuunteluluokka
 * @author Valtteri Ingman & Jani Uotinen
 */
public class UnicaMenuEventListener extends ListenerAdapter {

    ArrayList<String> ruokalat =  new ArrayList<String>(Arrays.asList("Yliopiston kampus" ,"   !assari", "   !macciavelli", "   !galilei", "   !kaara","Kupittaan kampus", "   !dental", "   !delipharma", "   !delica", "   !linus","   !kisälli","Linnankadun taidekampus","   !sigyn", "   !muusa","Muut","   !ruokakello", "   !kaivomestari", "   !fabrik", "   !piccumaccia"));

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if(event.getAuthor().isBot()) {
            return;
        }

        if (event.getMessage().getContentRaw().equals("!ruokalat")) {
            event.getChannel().sendMessage("Yliopiston kampus\n" +"   !assari\n"+ "   !macciavelli\n"+ "   !galilei\n"+ "   !kaara\n"+"Kupittaan kampus\n"+ "   !dental\n"+ "   !delipharma\n"+ "   !delica\n"+ "   !linus\n"+"   !kisälli\n"+"Linnankadun taidekampus\n"+"   !sigyn\n"+ "   !muusa\n"+"Muut\n"+"   !ruokakello\n"+ "   !kaivomestari\n"+ "   !fabrik\n"+ "   !piccumaccia\n").queue();
        }
        /**
         * Tässä esimerkki miten uusi rakenne toimii yleiskäyttöisen JSONMapper-luokan kanssa.
         */
        if (event.getMessage().getContentRaw().equals("!assari")) {
            //tulostaMenu("https://www.unica.fi/modules/json/json/Index?costNumber=1920&language=fi", event );
            System.out.println("Triggeröikö eventti");
            event.getChannel().sendMessage(JSONMapper.unicaParser("https://www.unica.fi/modules/json/json/Index?costNumber=1920&language=fi")).queue();
        }

        if (event.getMessage().getContentRaw().equals("!macciavelli")) {
            //tulostaMenu("https://www.unica.fi/modules/json/json/Index?costNumber=1970&language=fi", event );
        }

        if (event.getMessage().getContentRaw().equals("!galilei")) {
            //tulostaMenu("https://www.unica.fi/modules/json/json/Index?costNumber=1995&language=fi", event );
        }

        if (event.getMessage().getContentRaw().equals("!kaara")) {
            //tulostaMenu("https://www.unica.fi/modules/json/json/Index?costNumber=1910&language=fi", event );
        }

        if (event.getMessage().getContentRaw().equals("!dental")) {
            //tulostaMenu("https://www.unica.fi/modules/json/json/Index?costNumber=1980&language=fi", event );
        }

        if (event.getMessage().getContentRaw().equals("!delipharma")) {
            //tulostaMenu("https://www.unica.fi/modules/json/json/Index?costNumber=198501&language=fi", event );
        }

        if (event.getMessage().getContentRaw().equals("!delica")) {
            //tulostaMenu("https://www.unica.fi/modules/json/json/Index?costNumber=1985&language=fi", event );
        }

        if (event.getMessage().getContentRaw().equals("!linus")) {
            //tulostaMenu("https://www.unica.fi/modules/json/json/Index?costNumber=2000&language=fi", event );
        }

        if (event.getMessage().getContentRaw().equals("!kisälli")) {
            //tulostaMenu("https://www.unica.fi/modules/json/json/Index?costNumber=1900&language=fi", event );
        }

        if (event.getMessage().getContentRaw().equals("!sigyn")) {
           // tulostaMenu("https://www.unica.fi/modules/json/json/Index?costNumber=1965&language=fi", event );
        }

        if (event.getMessage().getContentRaw().equals("!muusa")) {
            event.getChannel().sendMessage("Menua ei ole saatavilla, koska jsonia ei ole saatavilla unican sivuilla.").queue();
        }

        if (event.getMessage().getContentRaw().equals("!ruokakello")) {
            //tulostaMenu("https://www.unica.fi/modules/json/json/Index?costNumber=1950&language=fi", event );
        }

        if (event.getMessage().getContentRaw().equals("!kaivomestari")) {
            event.getChannel().sendMessage("Menua ei ole saatavilla, koska jsonia ei ole saatavilla unican sivuilla.").queue();
        }

        if (event.getMessage().getContentRaw().equals("!fabrik")) {
            event.getChannel().sendMessage("Menua ei ole saatavilla, koska jsonia ei ole saatavilla unican sivuilla.").queue();
        }

        if (event.getMessage().getContentRaw().equals("!piccumaccia")) {
            event.getChannel().sendMessage("Menua ei ole saatavilla, koska jsonia ei ole saatavilla unican sivuilla.").queue();
        }

    }

}