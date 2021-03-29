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

        String[] args = event.getMessage().getContentRaw().split(" ");//pilkkoo annetun komennon osiin, tämä mahdollistaa parametrien käytön

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
        if(args.length == 2){
            if (args[0].equalsIgnoreCase(Botti.prefiksi+"vaihdaPrefiksi")) {
                //Botti.setPrefiksi(args[1]);
                String prefix = args[1];
                if (EditConfig.checkLegalPrefix(prefix)) {
                    EditConfig.writeToConfigurationFile("prefix="+prefix);
                    EditConfig.readFromConfigurationFile();
                    Botti.loadConfig();
                    event.getChannel().sendMessage("Onnistui! Komentojen etuliito on tästedes: "+prefix).queue();
                } else {
                    event.getChannel().sendMessage("Epäonnistui! Viallinen komentojen etuliite.").queue();
                }

            }

        }
    }
}
