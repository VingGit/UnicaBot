package Config;

import java.util.ArrayList;
import java.util.HashMap;


/**
 * Luokka cfg-filujen arvojen käsittelemiseen ohjelmassa.
 * @author Jani Uotinen
 */
public class Configuration {
    //private ArrayList<String> inputConfiguration;
    private String prefix;
    private HashMap<String,String> config;
    private HashMap<String,String> restaurants;


    public Configuration(HashMap<String,String> input) {
        this.config = input;
        this.restaurants = new HashMap<>();
        this.prefix = config.get("prefix");
    }

    public String getPrefix() {
        return this.prefix;
    }
    public HashMap<String,String> getConfig() {
        return this.config;
    }
}
