package GUI;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

/**
 * Luokka ravintolalistauksen eli locations.jsonin ylläpitoon
 * @author Sanna Volanen
 */
public class Locations{
    private static ArrayList<Place> restaurants;
    //private ArrayList<Place> restaurants;
    private Map<String,Place> locations;
    //protected String [] campuses;
    //protected ArrayList<Place> restaurants;
    private final ObjectMapper objectmapper = new ObjectMapper();

    public Locations() {
        try {
            locations = objectmapper.readValue(new File("src/main/resources/locations.json"), Map.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        setRestaurants();
    }
    private void setRestaurants(){
        Collection<Place> list = null;
        try{
            list = locations.values();
        }catch (NullPointerException nullPer) {
            nullPer.printStackTrace();
        }
        restaurants = new ArrayList<>(list);
    }
    
    public static ArrayList<Place> getRestaurants(){
        return restaurants;
    }
    public static Place getPlace(String name){
        Place match = null;
        for (Place place: restaurants){
            if (place.getName().equals(name)){
                match = place;
            }
        }return match;
    }

    public void addPlace(Place newPlace) throws IOException {
        for (Place p : restaurants) {
            if (p.equals(newPlace)){
                System.out.println("already in list");
            } else {
                restaurants.add(newPlace);
                updateJson();
            }
        }
    }


    /**
     * method to rewrite Locations.json
     * @throws IOException
     */
    public void updateJson() throws IOException {
        for (Place place: restaurants) {
            locations.put(Integer.toString(restaurants.indexOf(place)), place);
        }
        try {
            objectmapper.writeValue(new File("src/main/resources/locations.json"), locations);
        }catch (IOException io){
            io.printStackTrace();
            System.out.println("File not found");
        }
    }

    private boolean exists(Place testPlace){
        if(getPlace(testPlace.getName()) != null){
            return true;
        }else{
            return false;
        }
    }


}
