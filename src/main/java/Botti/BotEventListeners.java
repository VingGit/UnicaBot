package Botti;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import Config.*;

/**
 * Eventtien demokuunteluluokka sekä botin asetuksia sisältävä geneerinen kuunteluluokka..
 * @author Jani Uotinen
 */
public class BotEventListeners extends ListenerAdapter {
    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        //Ettei botit juttele loputtomiin keskenään. Tai itsensä kanssa.
        if (event.getAuthor().isBot()) {
            return;
        }

        //pilkkoo annetun komennon osiin, tämä mahdollistaa parametrien käytön
        String[] messageSplit = event.getMessage().getContentRaw().split(" ");

        //Tarkistetaan kuuluuko komento tälle event listenerille.
        if (messageSplit.length != 2) {
            return;
        }

        //Erotetaan käytetty prefiksi ja uusi prefiksi
        String prefix = messageSplit[0].substring(0,1);
        String newPrefix = messageSplit[1];

        /**
         * Prefiksin vaihtokäsky.
         * ensin tarkistetaaan parametrien pituus, ja sitten tehdään toiminto.
         * Pilkkoo viestistä prefixin. Tarkistaa onko se laillinen. Mikäli prefix on laillinen niin se kirjoitetaan
         * configgiin ja config ladataan uudelleen. Mikäli prefix on laiton, niin ilmoittaa siitä viestillä.
         * TODO: prefiksien sun muiden asetuksien tallentaminen config tiedostoon.
         * TODO: täytyy laittaa tukemaan vain tiettyjä prefixejä, ettei voi kirjottaa mitä tahansa
         * TODO: jokin !help komento tjsp joka kertoo saatavilla olevat komennot
         * TODO: tutustuminen embed viesteihin ja niiden layouttiin. sillä saataisiin paljon kauniimman näköiseksi
         * @author ingman & Jani Uotinen
         */
        //Tarkistetaan onko komento, eli onko käytössä prefiksi.
        if (!messageSplit[0].substring(0,1).equals(Botti.prefiksi)) {
            return;
        }

        //Tarkistetaan onko komento oikea.
        if (messageSplit[0].equals(Botti.prefiksi+"vaihdaPrefiksi")) {
            if (EditConfig.checkLegalPrefix(newPrefix)) {
                EditConfig.writeToConfigurationFile("prefix==" + newPrefix);
                EditConfig.readFromConfigurationFile();
                Botti.loadConfig();
                event.getChannel().sendMessage("Onnistui! Komentojen prefiksi on nyt: " + newPrefix).queue();
            } else {
                event.getChannel().sendMessage("Epäonnistui! Uusi prefiksi oli viallinen. Prefiksi on edelleen: " + prefix).queue();
            }
        } else {
            event.getChannel().sendMessage("BotEvent: Virheellinen komento.").queue();
        }
    }
}
