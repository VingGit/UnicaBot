package Botti;

import de.cerus.jdasc.JDASlashCommands;
import de.cerus.jdasc.command.*;
import de.cerus.jdasc.interaction.Interaction;
import de.cerus.jdasc.interaction.response.InteractionResponseOption;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;

import javax.security.auth.login.LoginException;


/**
 * Botti-luokka discord botin käynnistämiseen.
 * @author Jani Uotinen
 */
public class Botti {

    private static  String BOT_TOKEN = "";
    private static final String APPLICATION_ID = "819578024214265866";
    static String prefiksi="-";
    private static JDA jda;
    /**
     * Metodi botin käynnistämiseen. Syötetään tokeni JDABuilderille ja lisätään sille EventListenerit ja yritetään
     * buildata.
     * @author Jani Uotinen
     */
    public static void launchBot(String token) throws Exception {
        //Luodaan JDABuilderi ja annetaan sille tokeni
        Botti.BOT_TOKEN =token;
        JDABuilder jdabuilder = JDABuilder.createDefault(BOT_TOKEN);

        //Luodaan botille event listenerit ja lisätään ne botille ennen buildaamista
        BotEventListeners botEvents = new BotEventListeners();
        UnicaMenuEventListener safka = new UnicaMenuEventListener();

        jdabuilder.addEventListeners(botEvents,safka);

        //Buildataan botti.
        try {
            jda = jdabuilder.setRawEventsEnabled(true).build();
            initCommands();

        } catch (LoginException e){
            throw e;
        }
    }

    public static void setPrefiksi(String prefiksi) {
        Botti.prefiksi = prefiksi;
    }
    public static void initCommands() {
        JDASlashCommands.initialize(jda, BOT_TOKEN, APPLICATION_ID);
        JDASlashCommands.submitGlobalCommand(new CommandBuilder()
                .name("test-command") // Set command name to '/test-command'
                .desc("My cool test command")
                .option(new CommandBuilder.SubCommandGroupBuilder()
                        .name("some-group") // Specify a group that can hold multiple sub commands
                        .desc("This is a wonderful group")
                        .option(new CommandBuilder.SubCommandBuilder()
                                .name("hello") // Specify sub command 'hello' (/test-command some-group hello)
                                .desc("Greet a user")
                                .option(new ApplicationCommandOption(
                                        ApplicationCommandOptionType.USER,
                                        "user", // Note the lower case name - Names have to be lower case or else things could break
                                        "Specify a user to greet",
                                        true
                                ))
                                .build())
                        .option(new CommandBuilder.SubCommandBuilder()
                                .name("animal") // Specify sub command 'animal' (/test-command some-group animal)
                                .desc("Show a animal picture")
                                .choices( // Only allow certain values: Cat, Dog and Platypus
                                        ApplicationCommandOptionType.STRING, // Specify type of the choice: STRING or INTEGER
                                        "animal", // Note the lower case name - Names have to be lower case or else things could break
                                        "Specify the animal",
                                        new ApplicationCommandOptionChoice("Cat", "cat"),
                                        new ApplicationCommandOptionChoice("Dog", "cat"),
                                        new ApplicationCommandOptionChoice("Platypus", "platypus")
                                )
                                .build())
                        .build())
                .build(), new ApplicationCommandListener() {

            @Override
            public void onInteraction(final Interaction interaction) {
                System.out.println("We got an interaction! Yay!");
            }

            @Override
            public void handleArgument(final Interaction interaction, final String argumentName, final InteractionResponseOption option) {
                switch (argumentName) {
                    case "user":
                        interaction.respond(false, "Hello, " + jda.getUserById(Long.parseLong(option.getValue())).getAsMention());
                        break;
                    case "animal":
                        interaction.respond(false, "Here's your imaginary picture of a " + option.getValue());
                        break;
                }
            }

        });
    }

}

