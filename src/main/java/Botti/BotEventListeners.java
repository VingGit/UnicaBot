package Botti;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;


/**
 * Eventtien demokuunteluluokka.
 * @author Jani Uotinen
 */
public class BotEventListeners extends ListenerAdapter {
    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        //Ettei botit juttele loputtomiin keskenään. Tai itsensä kanssa.
        if (event.getAuthor().isBot()) {
            return;
        }

        if (event.getMessage().getContentRaw().equals("!komento")) {
            event.getChannel().sendMessage("Toimii!").queue();
        }
    }
}
