package Config;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Scanner;

/**
 * Luokka botin cfg filun lukemiseen ja kirjoittamiseen.
 * @author Jani Uotinen
 */
public class EditConfig {

    /**
     * Lukee cfg-filun ja palauttaa sen HashMappina.
     * @author Jani Uotinen
     */
    public static HashMap<String,String> readFromConfigurationFile() {
        HashMap<String,String> configuration = new HashMap<>();

        try {
            File inputFile = new File("src/main/resources/cfg/cfg.txt");
            Scanner fileReader = new Scanner(inputFile);

            //Käydään läpi cfg-filun rivit.
            while (fileReader.hasNextLine()) {
                String cfgLine = fileReader.nextLine(); //Rivi cfg-filusta

                String[] cfgLineSplit = cfgLine.split("="); //Splitataan cfg rivi

                configuration.put(cfgLineSplit[0],cfgLineSplit[1]); //Tallennetaan rivi avaimena ja arvona
            }
            //Suljetaan fileReader
            fileReader.close();

        } catch (FileNotFoundException e) {
            System.out.println("File not found.");
            e.printStackTrace();
        }
        return configuration;
    }

    /**
     * Ylikirjoittaa halutun kohdan cfg-filusta.
     * @author Jani Uotinen
     */
    public static void writeToConfigurationFile(String input) {
        //Haetaan olemassaoleva cfg-file.
        HashMap<String,String> originalConfiguration = readFromConfigurationFile();

        //Splitataan syötetty ylikirjoitettava rivi HashMapille sopivaksi avaimeksi ja arvoksi.
        String[] inputSplit = input.split("=");
        String key = inputSplit[0];
        String value = inputSplit[1];

        //Etsitään sopiva avain ja korvataan arvo uudelle syötetyllä arvolla.
        for (String keyString : originalConfiguration.keySet()) {
            if (keyString.equals(key)) {
                originalConfiguration.put(key,value);
            }
        }

        try {
            BufferedWriter fileWriter = new BufferedWriter(new FileWriter("src/main/resources/cfg/cfg.txt"));

            for (String keyString : originalConfiguration.keySet()) {
                fileWriter.write(key+"="+originalConfiguration.get(key));
            }

            fileWriter.close();
        } catch (Exception e) {
            System.out.println("Error writing config.");
            e.printStackTrace();
        }
    }

    /**
     * Tarkistaa onko syötettävä prefix symboli kelvollinen.
     * @author Jani Uotinen
     */
    public static boolean checkLegalPrefix(String input) {
        String symbols = "*./!?,%&#";
        if (symbols.contains(input)) {
            return true;
        } else {
            return false;
        }
    }
}
