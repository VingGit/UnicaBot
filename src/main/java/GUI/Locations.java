package GUI;

import JSONParse.Restaurant;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.*;

/**
 * Luokka ravintolalistauksen eli locations.jsonin ylläpitoon
 * @author Sanna Volanen
 */
public class Locations extends HashMap {
    protected static ArrayList<Restaurant> restaurantArrayList;
    //private ArrayList<Place> restaurants;
    private HashMap locations;
    //protected String [] campuses;
    //protected ArrayList<Place> restaurants;
    private final ObjectMapper objectmapper = new ObjectMapper();

    public Locations() {
        this.locations = new HashMap();
        retrieveData();
    }

    private void retrieveData() {
        try {
            locations = objectmapper.readValue(Paths.get("src/main/resources/locations.json").toFile(), HashMap.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("file read successfully");
        //System.out.println("Saved locations: ");
        //System.out.println(restaurants);
        ArrayList<ArrayList<Restaurant>> saved = new ArrayList<>(locations.values()); //
        //System.out.println("Saved" +saved);
        //System.out.println("locations is "+saved.getClass());
        if (saved.size() > 0) restaurantArrayList = saved.get(0);
        //System.out.println("Restaurants is " + restaurants.getClass());
    }

    public ArrayList<Restaurant> getRestaurantList(){
        return restaurantArrayList;
    }

    public Restaurant getRestaurant(String name){
        for (Restaurant place: restaurantArrayList){
            if (place.getRestaurantName().equals(name)){
                return place;
            }
        }return null;
    }

    public void addPlace(HashMap<String, String> newPlace) {
        System.out.println("Trying to add new location...");
        System.out.println("Current list size "+ restaurantArrayList.size());
        System.out.println("Input hashmap: "+newPlace);
        Restaurant newP = new Restaurant(newPlace);
        System.out.println("New "+newP); //tulee oikein
        if (!(exists(newP))){
            restaurantArrayList.add(restaurantArrayList.size(), newP);
            System.out.println("Restaurants after addition: "+ restaurantArrayList);
            //setRestaurants();
            updateJson();
            System.out.println("Current list size "+ restaurantArrayList.size());
        }else {
            System.out.println("already in list");
        }
    }

    private boolean exists(Restaurant newP) {
        for (Restaurant p1: restaurantArrayList){
            if (p1.equals(newP)){
                return true;
            }
        }return false;
    }

    public void editPlace(HashMap<String, String> editValues) {
        Restaurant toBeEdited = getRestaurant(editValues.get("name"));
        int index = restaurantArrayList.indexOf(toBeEdited);
        if (toBeEdited != null) {
            System.out.println("Matched"+ toBeEdited);
            for (String key : editValues.keySet()) {
                if (!key.equals("name")) {
                    toBeEdited.edit(key, editValues.get(key));
                }
            }
            restaurantArrayList.set(index, toBeEdited);
            updateJson();
        }
    }


    /**
     * method to rewrite Locations.json
     *
     */
    public void updateJson() {
        // update local variable
        locations.put("Locations", restaurantArrayList);
        //create PrettyPrinter instance
        ObjectWriter writer = objectmapper.writer(new DefaultPrettyPrinter());
        try {
            writer.writeValue(new File("src/main/resources/locations.json"), locations);
        }catch (IOException io){
            io.printStackTrace();

            System.out.println("File not found");
        }
        System.out.println("Locations json updated.");
    }


    @Override
    public int size() {
        return restaurantArrayList.size();
    }

    @Override
    public boolean isEmpty() {
        return restaurantArrayList.isEmpty();
    }

    public boolean containsValue(Restaurant p1) {
        return restaurantArrayList.contains(p1);
    }

    @Override
    public Object get(Object key) {
        return null;
    }

    @Nullable
    @Override
    public Object put(Object key, Object value) {
        return null;
    }

    @Override
    public Object remove(Object key) {
        return null;
    }

    @Override
    public void putAll(@NotNull Map m) {

    }

    @Override
    public void clear() {

    }

    @NotNull
    @Override
    public Set keySet() {
        return locations.keySet();
    }



    @Nullable
    @Override
    public Object putIfAbsent(Object key, Object value) {
        return null;
    }

    @Override
    public boolean remove(Object key, Object value) {
        return false;
    }

    @Override
    public boolean replace(Object key, Object oldValue, Object newValue) {
        return false;
    }
}