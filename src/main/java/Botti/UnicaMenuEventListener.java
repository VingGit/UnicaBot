package Botti;

import Config.EditConfig;
import GUI.Locations;
import JSONParse.JSONMapper;
import JSONParse.Restaurant;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.MessageBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Locale;

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

    /**
     * Metodi tarkistamaan onko kyseessä lauantai tai sunnuntai.
     * @author Jani Uotinen
     */
    public boolean isNowSaturdayOrSunday(LocalDate localDate) {
        DayOfWeek day = localDate.getDayOfWeek();
        return day==DayOfWeek.SATURDAY || day== DayOfWeek.SUNDAY;
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {

        /**
         * Testataan aluksi onko komennon antaja botti. Tärkeä tarkistin, ettei botit jää ikuisesti keskustelemaan
         * keskenään.
         */
        if (event.getAuthor().isBot()) {
            return;
        }

        //Pilkkoo annetun komennon osiin.
        String[] messageSplit = event.getMessage().getContentRaw().split(" ");

        //Tarkistetaan kuuluuko komento tälle event listenerille.
        if (messageSplit.length > 1) {
            return;
        }

        //Lataa config hashmapin.
        HashMap<String, String> config = EditConfig.readFromConfigurationFile();

        //Erotetaan viestistä komento
        String command = messageSplit[0].substring(1);

        //Luodaan uusi locations ilmentymä, json ravintoloiden käsittelyyn.
        Locations lokaatiot = new Locations();

        /**
         * Tarkistetaan onko komento tälle eventListenerille sopiva ja onko komento "ravintolat".
         * Tulostaa kaikki locations.json tiedostoon lisätyt ravintolat.
         */
        if (messageSplit[0].equals(Botti.prefiksi + command)) {
            String tuloste = "";
            if (command.equals("ravintolat")) {
                for (String key : lokaatiot.locations.keySet()) {
                    tuloste += Botti.prefiksi + key + "\n";
                }
                event.getChannel().sendMessage(tuloste).queue();
            }
        }

        //Testataan onko kyseessä lauantai tai sunnuntai, jolloin Unican Menuja ei ole saatavilla.
        if (isNowSaturdayOrSunday(LocalDate.now())) {
            //System.out.println("Ravintolat eivät ole auki viikonloppuisin.");
            event.getChannel().sendMessage("Ravintolat eivät ole auki viikonloppuisin.").queue();
            return;
        }

        /*
        if (args[0].equalsIgnoreCase(Botti.prefiksi + "ruokalat")) {// viesti on pilkottu osiin ja jos ensimmäinen osa == prefiksi+komentoX, tee asioita. pilkkominen on tehty siksi ettei isoil ja pienil kirjaimil ois välii
            event.getChannel().sendMessage("Yliopiston kampus\n" + "   "+Botti.prefiksi+"assari\n" + "   "+Botti.prefiksi+"macciavelli\n" + "   "+Botti.prefiksi+"galilei\n" + "   "+Botti.prefiksi+"kaara\n" + "Kupittaan kampus\n" + "   "+Botti.prefiksi+"dental\n" + "   "+Botti.prefiksi+"delipharma\n" + "   "+Botti.prefiksi+"delica\n" + "   "+Botti.prefiksi+"linus\n" + "   "+Botti.prefiksi+"kisälli\n" + "Linnankadun taidekampus\n" + "   "+Botti.prefiksi+"sigyn\n" + "   "+Botti.prefiksi+"muusa\n" + "Muut\n" + "   "+Botti.prefiksi+"ruokakello\n" + "   "+Botti.prefiksi+"kaivomestari\n" + "   "+Botti.prefiksi+"fabrik\n" + "   "+Botti.prefiksi+"piccumaccia\n").queue();
        }
         */

        /**
         * Tässä esimerkki miten uusi rakenne toimii yleiskäyttöisen JSONMapper-luokan kanssa.
         * Vain jos JSON urlit on asetettu cfg-tiedostoon. Toiminnallisuus muuttuu hieman, jos saadaan urlit
         * locations.json filusta.
         *
         * Tarkistetaan onko prefiksi oikea ja sisältääkö config kutsutun komennon. Tarkistetaan vielä, että kutsuttu
         * komento on eri kuin "prefix".
         * @author Jani Uotinen
         */
        //Testataan onko komento tälle EventHandlerille sopiva
        if (messageSplit[0].equals(Botti.prefiksi + command)) {
            //Luodaan uusi Locations ilmentymä.

            //Tarkistetaan, että löytyykö haluttu ravintola locations.jsonin rakenteesta.
            if (lokaatiot.locations.containsKey(command) && !command.equals("prefix")) {
                //Luodaan uusi Restaurant ilmentymä locations.jsonin urlin perusteella.
                Restaurant restaurant = JSONMapper.unicaParser(lokaatiot.locations.get(command).get("url"));
                if (restaurant.getErrorMessage() == null) {
                    event.getChannel().sendMessage(embedviesti(restaurant).build()).queue();
                } else {
                    event.getChannel().sendMessage(restaurant.getErrorMessage()).queue();
                }
            } else {
                event.getChannel().sendMessage("UnicaMenu: Virheellinen komento.").queue();
            }
        }
    }

    private EmbedBuilder embedviesti(Restaurant restaurant){
        EmbedBuilder viesti = new EmbedBuilder();
        MessageBuilder builder= new  MessageBuilder();
        viesti
                .setAuthor(restaurant.getRestaurantName(), restaurant.getRestaurantUrl())
                .setThumbnail("https://www.unica.fi/contentassets/46a1e57100794a70b58c06f16a9acfb8/unica_catering_logo_450x450.png");
        for (StringBuilder a: restaurant.getRestaurantMenuArray()) {
            viesti.addField("🤔", a.toString(), true);
        }
        viesti.build();

        return viesti;
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

