package JSONParse;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.print.Collation;
import kotlin.reflect.KTypeProjection;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Luokka ravintolalistauksen eli locations.jsonin ylläpitoon
 * @author Sanna Volanen
 */
public class Locations {
    private Map locations;
    //protected String [] campuses;
    protected ArrayList<Place> restaurants;
    private final ObjectMapper objectmapper = new ObjectMapper();

    public Locations() {
        try {
            locations = objectmapper.readValue(new File("src/main/java/resources/locations.json"), Map.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Collection list = locations.values();
        for (Object entry : list) {
            restaurants.add((Place) entry);
        }
    }

    public ArrayList<Place> getPlaces() {
        return restaurants;
    }

    public void addPlace(String campus, String url) throws IOException {
        Restaurant newRes = JSONMapper.restaurantParser(url);
        if (newRes.menuExists()) {
            Place p = new Place(newRes.getRestaurantName(), newRes.getRestaurantUrl());
            if (restaurants.contains(p)) {
                System.out.println("already in list");
            } else {
                restaurants.add(p);
                locations.put("Locations",restaurants);
            objectmapper.writeValue(new File("src/main/java/resources/locations.json"), locations);
            }
        }
    }
    class Place{
        @JsonProperty("campusArea")
        private String campusArea;
        @JsonProperty("name")
        private String name;
        @JsonProperty("url")
        private String url;
        @JsonProperty("status")
        private HashMap<String, Object> status;

        public Place(String name, String url) {
            this.name = name;
            this.url = url;
            setStatus(true,null);
        }
        public void setStatus(boolean open, String message){
            this.status.put("open",open);
            this.status.put("message", message);
        }

        @Override
        public String toString() {
            return "Place{" +
                    "campusArea='" + campusArea + '\'' +
                    ", name='" + name + '\'' +
                    ", url='" + url + '\'' +
                    ", status=" + status +
                    '}';
        }
    }
}
