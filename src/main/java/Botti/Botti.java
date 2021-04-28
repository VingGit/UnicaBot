package Botti;

import Config.*;
import GUI.Locations;
import JSONParse.JSONMapper;
import JSONParse.Restaurant;
import Server.Commands;
import de.cerus.jdasc.JDASlashCommands;
import de.cerus.jdasc.command.*;
import de.cerus.jdasc.gson.GsonUtil;
import de.cerus.jdasc.interaction.Interaction;
import de.cerus.jdasc.interaction.response.InteractionResponseOption;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.MessageBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import javax.security.auth.login.LoginException;

import static Botti.UnicaMenuEventListener.lokaatiot;


/**
 * Botti-luokka discord botin käynnistämiseen.
 * @author Jani Uotinen
 */
public class Botti {

    private static  String BOT_TOKEN;  //"";
    private static String APPLICATION_ID="836766909739040770";
    static String prefiksi; //"-";
    private static JDA jda;
    public static boolean oikeaToken =false;
    public static String getBotToken() {
        return BOT_TOKEN;
    }
    public static void setBotToken(String botToken) {
        BOT_TOKEN = botToken;
    }

    /**
     * Ladataan configuration file ennen botin käynnistämistä.
     * @author Jani Uotinen
     */
    public static void loadConfig() {
        Configuration config = new Configuration(EditConfig.readFromConfigurationFile());
        prefiksi = config.getPrefix();
        System.out.println("Prefiksi configissa: "+prefiksi);
    }


    /**
     * Metodi botin käynnistämiseen. Syötetään tokeni JDABuilderille ja lisätään sille EventListenerit ja yritetään
     * buildata.
     * @author Jani Uotinen
     */
    public static void launchBot(String token) throws Exception {
        //Ladataan config
        loadConfig();
        //System.out.println(prefiksi);
        //Luodaan JDABuilderi ja annetaan sille tokeni
        Botti.BOT_TOKEN = token;
        JDABuilder jdabuilder = JDABuilder.createDefault(BOT_TOKEN);

        //Luodaan botille event listenerit ja lisätään ne botille ennen buildaamista
        jdabuilder.addEventListeners(new BotEventListeners(),new UnicaMenuEventListener(),new unicaForwardListener(), new unicaBackwardsListener());

        //Buildataan botti.
        try {
            jda = jdabuilder.setRawEventsEnabled(true).build();
            lokaatiot = new Locations();
            System.out.println("trying to add slash commands");
            initCommands(jda);
            oikeaToken=true;
        } catch (LoginException e){

            throw e;
        }
    }

    public static void setPrefiksi(String prefiksi) {
        Botti.prefiksi = prefiksi;
    }


    public static void initCommands(JDA jda) throws InterruptedException {
        jda.awaitReady();
        System.out.println(jda.getGuilds()+"   the servers my bot is on");
        JDASlashCommands.initialize(jda, BOT_TOKEN, APPLICATION_ID);
        System.out.println(JDASlashCommands.getGlobalCommands());
        JDASlashCommands.submitGuildCommand(new CommandBuilder()
                .name("menu") // Set command name to '/test-command'
                .desc("Ask and thou shalt receive")
                .option(new CommandBuilder.SubCommandBuilder()
                                .name("muut") // Specify sub command 'animal' (/test-command some-group animal)
                                .desc("muita ruokaloita unicalta")
                                .choices( // Only allow certain values: Cat, Dog and Platypus
                                        ApplicationCommandOptionType.STRING, // Specify type of the choice: STRING or INTEGER
                                        "ruokala", // Note the lower case name - Names have to be lower case or else things could break
                                        "Vaihtelua kampuksiin",
                                        new ApplicationCommandOptionChoice("Kaivomestari", "kaivomestari"),
                                        new ApplicationCommandOptionChoice("Fabrik", "fabrik"),
                                        new ApplicationCommandOptionChoice("PiccuMaccia", "piccumaccia"),
                                        new ApplicationCommandOptionChoice("Ruokakello", "ruokakello")
                                )
                                .build())


                        .option(new CommandBuilder.SubCommandBuilder()
                                .name("Linnankadun_taidekampus") // Specify sub command 'animal' (/test-command some-group animal)
                                .desc("Ette koske niihin öljyväreihin prkl!")
                                .choices( // Only allow certain values: Cat, Dog and Platypus
                                        ApplicationCommandOptionType.STRING, // Specify type of the choice: STRING or INTEGER
                                        "ruokala", // Note the lower case name - Names have to be lower case or else things could break
                                        "Kumpi kammpi linnankadulta?",
                                        new ApplicationCommandOptionChoice("Sigyn", "sigyn"),
                                        new ApplicationCommandOptionChoice("Muusa", "muusa")
                                )
                                .build())

                        .option(new CommandBuilder.SubCommandBuilder()
                                .name("Kupittaan_kampus") // Specify sub command 'animal' (/test-command some-group animal)
                                .desc("kupittaa, tai kuten mää tykkään sanoa: cuckpit :DD")
                                .choices( // Only allow certain values: Cat, Dog and Platypus
                                        ApplicationCommandOptionType.STRING, // Specify type of the choice: STRING or INTEGER
                                        "ruokala", // Note the lower case name - Names have to be lower case or else things could break
                                        "Valitse ruokala kupitaalta",
                                        new ApplicationCommandOptionChoice("Dental", "dental"),
                                        new ApplicationCommandOptionChoice("DeliPharma", "delipharma"),
                                        new ApplicationCommandOptionChoice("Delica", "delica"),
                                        new ApplicationCommandOptionChoice("Linus", "linus"),
                                        new ApplicationCommandOptionChoice("Kisälli", "kisälli")
                                )
                                .build())



                        .option(new CommandBuilder.SubCommandBuilder()
                                .name("Yliopiston_kampus") // Specify sub command 'animal' (/test-command some-group animal)
                                .desc("Syö aivan koulun vieressä")
                                .choices( // Only allow certain values: Cat, Dog and Platypus
                                        ApplicationCommandOptionType.STRING, // Specify type of the choice: STRING or INTEGER
                                        "ruokala", // Note the lower case name - Names have to be lower case or else things could break
                                        "Missäs tänään syötäisiin?",
                                        new ApplicationCommandOptionChoice("Assari", "assari"),
                                        new ApplicationCommandOptionChoice("Macciavelli", "macciavelli"),
                                        new ApplicationCommandOptionChoice("Galilei", "galilei"),
                                        new ApplicationCommandOptionChoice("Kaara", "kaara")
                                )


                        .build())
                .build(),jda.getGuildById(819193495906287677L), new ApplicationCommandListener() {

            @Override
            public void onInteraction(final Interaction interaction) {
                System.out.println("We got an intsdfsdfsderaction! Yay!");
            }

            @Override
            public void handleArgument(final Interaction interaction, final String argumentName, final InteractionResponseOption option) {
                Restaurant ravintola = JSONMapper.unicaParser(lokaatiot.locations.get(option.getValue()).get("url"));
                switch (argumentName) {
                    case "ruokala":
                        MessageEmbed viesti=UnicaMenuEventListener.embedRestaurant(ravintola).build();
                        interaction.respond(false, viesti);
                        break;
                }
            }

        });
        System.out.println("slashcommands were  added successfully!");
    }


}