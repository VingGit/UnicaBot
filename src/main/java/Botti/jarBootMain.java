package Botti;

import Server.Commands;

import java.util.Scanner;

public class jarBootMain {
    public static void main(String[] args) throws Exception {

        try{
            if(!Server.Commands.isOnline()) {
                GUI.Main.main(args);
            }
            else{
                if(Server.Commands.onkoTdstoa("temp.txt")) {
                    System.out.println(Botti.getBotToken());
                    Botti.launchBot(Server.Commands.lueTxtTiedosto("temp.txt"));
                    Server.Commands.poistaTdsto("temp.txt");
                }
                else{GUI.Main.main(args);}
            }

        }
        catch (Exception e){
            e.printStackTrace();
            Scanner lukija = new Scanner(System.in);

                while(true) {
                    if(Commands.FTPonline && Botti.oikeaToken){
                        break;
                    }
                    System.out.println("Anna käyttäjätunnus: ");
                    String tunnus = lukija.nextLine();
                    System.out.println("Anna salasana: ");
                    String pw = lukija.nextLine();
                    System.out.println("Anna token: ");
                    String token = lukija.nextLine();
                    Botti.launchBot(token);
                    Server.Commands.yhdista(tunnus,pw);

                }

        }

    }
}
//jostain syystä jarin tekeminen ei onnistunut ilman tätä.
//https://stackoverflow.com/questions/52653836/maven-shade-javafx-runtime-components-are-missing
//nyt käyttäjä voi suoraan painaa jaria ja se käynnistyy.