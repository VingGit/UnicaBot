import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;

import javax.security.auth.login.LoginException;

public class TestiBotti {

    public static void main(String args[]) throws Exception {
        //Luodaan JDABuilderi ja annetaan sille tokeni
        JDABuilder jdabuilder = JDABuilder.createDefault("syötä tähän tokenisi");

        JDA jda;
        PingPong pingPong = new PingPong();
        jdabuilder.addEventListeners(pingPong);
        try {
            jda = jdabuilder.build();
        } catch (LoginException e){
            e.printStackTrace();
        }
    }
}
