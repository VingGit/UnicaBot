package Botti;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;


/**
 * Eventtien demokuunteluluokka sekä botin asetuksia sisältävä geneerinen kuunteluluokka..
 * @author Jani Uotinen
 */
public class BotEventListeners extends ListenerAdapter {
    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        //Ettei botit juttele loputtomiin keskenään. Tai itsensä kanssa.
        String[] args = event.getMessage().getContentRaw().split(" ");//pilkkoo annetun komennon osiin, tämä mahdollistaa parametrien käytön

        if (event.getAuthor().isBot()) {
            return;
        }

        /**
         * Prefiksin vaihtokäsky.
         * ensin tarkistetaaan parametrien pituus, ja sitten tehdään toiminto.
         * TODO: prefiksien sun muiden asetuksien tallentaminen config tiedostoon.
         * TODO: jokin !help komento tjsp joka kertoo saatavilla olevat komennot
         * TODO: tutustuminen embed viesteihin ja niiden layouttiin. sillä saataisiin paljon kauniimman näköiseksi
         * @author ingman
         */
        if(args.length == 2){
            if (args[0].equalsIgnoreCase(Botti.prefiksi+"vaihdaPrefiksi")) {
                Botti.setPrefiksi(args[1]);
                event.getChannel().sendMessage("Onnistui! Komentojen etuliito on tästedes: "+args[1]).queue();
            }

        }
    }
}
