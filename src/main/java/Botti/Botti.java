package Botti;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;

import javax.security.auth.login.LoginException;

/**
 * Botti-luokka discord botin käynnistämiseen.
 * @author Jani Uotinen
 */
public class Botti {

    public static void launchBot(String token) throws Exception {
        //Luodaan JDABuilderi ja annetaan sille tokeni
        JDABuilder jdabuilder = JDABuilder.createDefault(token);

        //Luodaan botille event listenerit ja lisätään ne botille ennen buildaamista
        BotEventListeners botEvents = new BotEventListeners();
        UnicaMenuEventListener safka = new UnicaMenuEventListener();

        jdabuilder.addEventListeners(botEvents,safka);

        //Buildataan botti.
        try {
            JDA jda = jdabuilder.build();
        } catch (LoginException e){
            //e.printStackTrace();
            throw new Exception("Huah");
        }
    }

}
