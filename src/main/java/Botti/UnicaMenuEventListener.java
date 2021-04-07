package Botti;

import Config.EditConfig;
import JSONParse.JSONMapper;
import JSONParse.Restaurant;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 * Eventtien kuunteluluokka. käsittelee kaikki menujen hakemiseen liittyvät komennot. jos muita
 * ominaisuuksia halutaan lisätä, tulisi niille tehdä oma eventlistener-luokka.
 *
 * Minä tein nää komennot ja jani teki ton jsonmapperin  sekä siisti koodia luettavammaksi.
 * -ingman
 * @author Valtteri Ingman & Jani Uotinen
 */
public class UnicaMenuEventListener extends ListenerAdapter {
    //tälle arraylistalle voi olla käyttöä, säilytetään se.
    //ArrayList<String> ruokalat = new ArrayList<>(Arrays.asList("Yliopiston kampus", "   !assari", "   !macciavelli", "   !galilei", "   !kaara", "Kupittaan kampus", "   !dental", "   !delipharma", "   !delica", "   !linus", "   !kisälli", "Linnankadun taidekampus", "   !sigyn", "   !muusa", "Muut", "   !ruokakello", "   !kaivomestari", "   !fabrik", "   !piccumaccia"));

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        //Testataan aluksi onko komennon antaja botti
        if (event.getAuthor().isBot()) {
            return;
        }
        //Lataa config hashmapin.
        HashMap<String,String> config = EditConfig.readFromConfigurationFile();
        //Erottaa komennosta prefiksin pois
        String command = event.getMessage().getContentRaw().substring(1);

        /*
        if (args[0].equalsIgnoreCase(Botti.prefiksi + "ruokalat")) {// viesti on pilkottu osiin ja jos ensimmäinen osa == prefiksi+komentoX, tee asioita. pilkkominen on tehty siksi ettei isoil ja pienil kirjaimil ois välii
            event.getChannel().sendMessage("Yliopiston kampus\n" + "   "+Botti.prefiksi+"assari\n" + "   "+Botti.prefiksi+"macciavelli\n" + "   "+Botti.prefiksi+"galilei\n" + "   "+Botti.prefiksi+"kaara\n" + "Kupittaan kampus\n" + "   "+Botti.prefiksi+"dental\n" + "   "+Botti.prefiksi+"delipharma\n" + "   "+Botti.prefiksi+"delica\n" + "   "+Botti.prefiksi+"linus\n" + "   "+Botti.prefiksi+"kisälli\n" + "Linnankadun taidekampus\n" + "   "+Botti.prefiksi+"sigyn\n" + "   "+Botti.prefiksi+"muusa\n" + "Muut\n" + "   "+Botti.prefiksi+"ruokakello\n" + "   "+Botti.prefiksi+"kaivomestari\n" + "   "+Botti.prefiksi+"fabrik\n" + "   "+Botti.prefiksi+"piccumaccia\n").queue();

        }
         */

        /**
         * Tässä esimerkki miten uusi rakenne toimii yleiskäyttöisen JSONMapper-luokan kanssa.
         */
        //Tarkistetaan löytyykö komento cfg:stä ja että se on eri kuin prefix
        if(config.containsKey(command) && !command.equals("prefix")) {
            Restaurant restaurant = JSONMapper.unicaParser(config.get(command));

            if (restaurant.getErrorMessage() == null) {
                event.getChannel().sendMessage(restaurant.getRestaurantName()).queue();

            } else {
                event.getChannel().sendMessage(restaurant.getErrorMessage()).queue();
            }
        } else {
            event.getChannel().sendMessage("Virheellinen komento.");
        }

        /*
        if (args[0].equalsIgnoreCase(Botti.prefiksi + "assari")) {
          //event.getChannel().sendMessage(JSONMapper.unicaParser("https://www.unica.fi/modules/json/json/Index?costNumber=1920&language=fi")).queue();
            Restaurant restaurant = JSONMapper.unicaParser("https://www.unica.fi/modules/json/json/Index?costNumber=1920&language=fi");
            if (restaurant.getErrorMessage() == null) {
                event.getChannel().sendMessage(restaurant.getRestaurantName()).queue();
            } else {
                event.getChannel().sendMessage(restaurant.getErrorMessage()).queue();
            }

        }

        if (args[0].equalsIgnoreCase(Botti.prefiksi + "macciavelli")) {
            event.getChannel().sendMessage(JSONMapper.unicaParser("https://www.unica.fi/modules/json/json/Index?costNumber=1910&language=fi")).queue();

        }

        if (args[0].equalsIgnoreCase(Botti.prefiksi + "galilei")) {
            event.getChannel().sendMessage(JSONMapper.unicaParser("https://www.unica.fi/modules/json/json/Index?costNumber=1995&language=fi")).queue();

        }

        if (args[0].equalsIgnoreCase(Botti.prefiksi + "kaara")) {
            event.getChannel().sendMessage(JSONMapper.unicaParser("https://www.unica.fi/modules/json/json/Index?costNumber=1970&language=fi")).queue();

        }

        if (args[0].equalsIgnoreCase(Botti.prefiksi + "dental")) {
            event.getChannel().sendMessage(JSONMapper.unicaParser("https://www.unica.fi/modules/json/json/Index?costNumber=1980&language=fi")).queue();

        }

        if (args[0].equalsIgnoreCase(Botti.prefiksi + "delipharma")) {
            event.getChannel().sendMessage(JSONMapper.unicaParser("https://www.unica.fi/modules/json/json/Index?costNumber=198501&language=fi")).queue();

        }

        if (args[0].equalsIgnoreCase(Botti.prefiksi + "delica")) {
            event.getChannel().sendMessage(JSONMapper.unicaParser("https://www.unica.fi/modules/json/json/Index?costNumber=1985&language=fi")).queue();

        }

        if (args[0].equalsIgnoreCase(Botti.prefiksi + "linus")) {
            event.getChannel().sendMessage(JSONMapper.unicaParser("https://www.unica.fi/modules/json/json/Index?costNumber=2000&language=fi")).queue();

        }

        if (args[0].equalsIgnoreCase(Botti.prefiksi + "kisälli")) {
            event.getChannel().sendMessage("Ravintola kiinni toistaiseksi, ei menua eikä jsonia saatavilla").queue();

            // event.getChannel().sendMessage(JSONMapper.unicaParser("https://www.unica.fi/modules/json/json/Index?costNumber=1920&language=fi")).queue();

        }

        if (args[0].equalsIgnoreCase(Botti.prefiksi + "sigyn")) {
            event.getChannel().sendMessage(JSONMapper.unicaParser("https://www.unica.fi/modules/json/json/Index?costNumber=1965&language=fi")).queue();

         }

        if (args[0].equalsIgnoreCase(Botti.prefiksi + "muusa")) {
            event.getChannel().sendMessage("Ravintola kiinni toistaiseksi, ei menua eikä jsonia saatavilla").queue();
            // event.getChannel().sendMessage(JSONMapper.unicaParser("https://www.unica.fi/modules/json/json/Index?costNumber=1920&language=fi")).queue();


        }

        if (args[0].equalsIgnoreCase(Botti.prefiksi + "ruokakello")) {
            event.getChannel().sendMessage(JSONMapper.unicaParser("https://www.unica.fi/modules/json/json/Index?costNumber=1950&language=fi")).queue();

        }

        if (args[0].equalsIgnoreCase(Botti.prefiksi + "kaivomestari")) {
            event.getChannel().sendMessage("Ravintola kiinni toistaiseksi, ei menua eikä jsonia saatavilla").queue();
            //event.getChannel().sendMessage(JSONMapper.unicaParser("https://www.unica.fi/modules/json/json/Index?costNumber=1920&language=fi")).queue();


        }

        if (args[0].equalsIgnoreCase(Botti.prefiksi + "fabrik")) {
            event.getChannel().sendMessage("Ravintola kiinni toistaiseksi, ei menua eikä jsonia saatavilla").queue();
          //  event.getChannel().sendMessage(JSONMapper.unicaParser("https://www.unica.fi/modules/json/json/Index?costNumber=1920&language=fi")).queue();


        }

        if (args[0].equalsIgnoreCase(Botti.prefiksi + "piccumaccia")) {
            event.getChannel().sendMessage("Avoinna tilauksesta").queue();
          //  event.getChannel().sendMessage(JSONMapper.unicaParser("https://www.unica.fi/modules/json/json/Index?costNumber=1920&language=fi")).queue();

        }
        */
    }

}