package GUI;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * Luokka ravintolalistauksen eli locations.jsonin ylläpitoon
 * @author Sanna Volanen
 */
public class Locations{
    protected static ArrayList<Place> restaurants;
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
        Collection<Place> saved = null;
        try{
            saved = locations.values();
        }catch (NullPointerException nullPer) {
            nullPer.printStackTrace();
        }
        if (saved != null) {
            restaurants = new ArrayList<>(saved);
        }
    }

    public ArrayList<Place> getRestaurants(){
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

    public void addPlace(HashMap<String, String> newPlace) {
        Place newP = new Place(newPlace);
        if (!(restaurants.contains(newP))){
            restaurants.add(newP);
            updateJson();
            //setRestaurants();
        }else {
            System.out.println("already in list");
        }
    }

    public void editPlace(HashMap<String, String> editValues) {
        Place toBeEdited = getPlace(editValues.get("name"));
        int index = restaurants.indexOf(toBeEdited);
        if (toBeEdited != null) {
            for (String key : editValues.keySet()) {
                if (!key.equals("name")) {
                    toBeEdited.edit(key, editValues.get(key));
                }
            }
            restaurants.set(index, toBeEdited);
            updateJson();
        }
    }


    /**
     * method to rewrite Locations.json
     *
     */
    public void updateJson() {
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
    // tarvitaanko?
    private boolean exists(Place testPlace){
        return restaurants.contains(testPlace);
    }


}