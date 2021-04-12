package GUI;

import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Luokka ravintolalistauksen eli locations.jsonin ylläpitoon
 * @author Sanna Volanen
 */
public class Locations extends HashMap {
    protected static ArrayList<Place> restaurants;
    //private ArrayList<Place> restaurants;
    private HashMap locations;
    //protected String [] campuses;
    //protected ArrayList<Place> restaurants;
    private final ObjectMapper objectmapper = new ObjectMapper();

    public Locations() {
        try {
            locations = objectmapper.readValue(Paths.get("src/main/resources/locations.json").toFile(), HashMap.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        setRestaurants();
        System.out.println("file read successfully");
        System.out.println("Saved locations: ");
        System.out.println(restaurants);
    }
    private void setRestaurants(){
        try{
            ArrayList<ArrayList<Place>> saved = new ArrayList<>(locations.values()); //
            //System.out.println("Saved" +saved);
            //System.out.println("locations is "+saved.getClass());
            if ( saved.size() >0) restaurants= saved.get(0);
            //System.out.println("Restaurants is " + restaurants.getClass());
        }catch (NullPointerException nullPer) {
            nullPer.printStackTrace();
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
        System.out.println("Trying to add new location...");
        Place newP = new Place(newPlace);
        System.out.println("New "+newP);
        if (!(restaurants.contains(newP))){
            restaurants.add(restaurants.size(), newP);
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
        // update local variable
        locations.put("Locations",restaurants);
        //create PrettyPrinter instance
        ObjectWriter writer = objectmapper.writer(new DefaultPrettyPrinter());
        try {
            writer.writeValue(new File("src/main/resources/locations.json"), locations);
        }catch (IOException io){
            io.printStackTrace();
            System.out.println("File not found");
        }
    }
    // tarvitaanko?
    private boolean exists(Place testPlace){
        return restaurants.contains(testPlace);
    }


    @Override
    public int size() {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public boolean containsKey(Object key) {
        return false;
    }

    @Override
    public boolean containsValue(Object value) {
        return false;
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
        return null;
    }

    @NotNull
    @Override
    public Collection values() {
        return null;
    }

    @NotNull
    @Override
    public Set<Entry> entrySet() {
        return null;
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