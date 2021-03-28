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


    public Configuration(HashMap<String,String> input) {
        //this.inputConfiguration = input;
        this.prefix = input.get("prefix");
    }
    public String getPrefix() {
        return this.prefix;
    }
}
