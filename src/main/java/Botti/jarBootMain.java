package Botti;

import Server.Commands;

import java.util.Scanner;

public class jarBootMain {
    public static void main(String[] args) throws Exception {

        try{
            GUI.Main.main(args);
        }
        catch (Exception e){
            Scanner lukija = new Scanner(System.in);
            while(true) {
                System.out.println("Anna käyttäjätunnus: ");
                String tunnus = lukija.nextLine();
                System.out.println("Anna salasana: ");
                String pw = lukija.nextLine();
                System.out.println("Anna token: ");
                String token = lukija.nextLine();
                Botti.launchBot(token);
                Server.Commands.yhdista(tunnus,pw);
                if(Server.Commands.isOnline()){
                    break;
                }
            }
        }

    }
}
//jostain syystä jarin tekeminen ei onnistunut ilman tätä.
//https://stackoverflow.com/questions/52653836/maven-shade-javafx-runtime-components-are-missing
//nyt käyttäjä voi suoraan painaa jaria ja se käynnistyy.