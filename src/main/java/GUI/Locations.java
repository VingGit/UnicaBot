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
    private HashMap<String, HashMap<String, String >> locations;
    private Set keySet;
    protected ArrayList<String> keys;
    ArrayList<HashMap<String, String>> savedRestaurants;
    protected static ArrayList<Restaurant> restaurantArrayList;
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
        System.out.println("locations.json read successfully");
        //System.out.println("Saved locations: \n"+ locations);
        //System.out.println(restaurantArrayList);
        savedRestaurants = new ArrayList<>(locations.values()); //
        keySet = locations.keySet();
        //System.out.println(saved.getClass());
        updateRestaurantArrayList();
        System.out.println("Retrieved "+Integer.toString(restaurantArrayList.size())+ " restaurants");
        //System.out.println(restaurantArrayList);
    }

    public ArrayList<Restaurant> getRestaurantList(){
        return restaurantArrayList;
    }
    public ArrayList<String> getKeys(){
        return keys;
    }

    public Restaurant getRestaurant(String name){
        for (Restaurant res: restaurantArrayList){
            if (res.getRestaurantName().equals(name)){
                return res;
            }
        }return null;
    }

    public void updateRestaurantArrayList() {
        System.out.println("Retrieving restaurants...");
        savedRestaurants = new ArrayList<>(locations.values()); //
        if (savedRestaurants.size() > 0) {
            restaurantArrayList = new ArrayList<>();
            for (HashMap<String, String> res : savedRestaurants) {
                //System.out.println(res);
                Restaurant r = new Restaurant(res);
                //System.out.println("Restaurant from Hashmap: " + r.toString());
                if (r != null) {
                    restaurantArrayList.add(r);
                }
            }
        }
    }

    public void setKeys(){
        ArrayList<String> keys = new ArrayList<>();
        for (Object o: keySet){
            String s = (String) o;
            keys.add(s);
        }
        this.keys = keys;
    }

    /**
     * @author Sanna Volanen
     * @param newPlace HashMap containing parameters of a Restaurant
     * @pre newPlace != null
     * @post restaurantArraylist.size()> OLD(restaurantArrayList.size()) && locations.values().contains(newPlace)
     */
    public void addPlace(HashMap<String, String> newPlace) {
        System.out.println("Trying to add new location...");
        System.out.println("Current list size "+ restaurantArrayList.size());
        //System.out.println("Input hashmap: "+newPlace);
        Restaurant newP = new Restaurant(newPlace);
        System.out.println("New "+newP); //tulee oikein
        if (!(restaurantArrayList.contains(newP))){
            //keys.add(newP.getRestaurantName().toLowerCase());
            locations.put(newP.getRestaurantName().toLowerCase(), newP.toHashMap());
            //restaurantArrayList.add(newP);
            updateRestaurantArrayList();
            setKeys();
            System.out.println("Restaurants after addition: "+ restaurantArrayList);
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

    /**
     * Edits the values of an existing Restaurant
     * @author Sanna Volanen
     * @param editValues HashMap containing the name of a chosen Restaurant and the new values for that object to be inserted
     * @pre editValues != null & !editValues.isEmpty()
     * @post !toBeEdited.equals(OLD(toBeEdited)
     */
    public void editPlace(HashMap<String, String> editValues) {
        Restaurant toBeEdited = getRestaurant( editValues.get("name"));
        int index;
        if (toBeEdited != null) {
            index =restaurantArrayList.indexOf(toBeEdited);
            System.out.println("Matched: \n "+ toBeEdited);
            if (editValues.keySet().toArray().length >= 1) {
                for (String key : editValues.keySet()) {
                    if (!key.equals("name")) {
                        toBeEdited.edit(key, editValues.get(key));
                    } else {
                        System.out.println("Trying to edit " + toBeEdited.getRestaurantName());
                    }
                }
                System.out.println(" with new values: \n"+toBeEdited);
                restaurantArrayList.set(index, toBeEdited);
                updateJson();
            }else{
                System.out.println(editValues);
            }
        }
    }

    /**
     * removes Restaurant from Locations and locations.json
     * @param toBedeleted Restaurant retrieved based on name
     * @pre toBeDeleted != null
     * @post !restaurantArrayList.contains(toBeDeleted)
     */

    public void deletePlace(Restaurant toBedeleted) {
        System.out.println("Now "+Integer.toString(restaurantArrayList.size())+ " restaurants. Trying deletion...");
        if(restaurantArrayList.contains(toBedeleted)){
            System.out.println(locations.size()+ " locations");
            locations.remove(toBedeleted.getRestaurantName().toLowerCase(), toBedeleted.toHashMap());
            System.out.println(locations.size()+ " locations");
            updateRestaurantArrayList();
            updateJson();
        }
        System.out.println(restaurantArrayList.size() + " restaurants left");
    }

    /**
     * Rewrites Locations.json
     * @author Sanna Volanen
     * @pre EXISTS(locations.json)
     * @post EXISTS(locations.json)
     */
    public void updateJson() {
        // update local variable
        for (Restaurant r:restaurantArrayList) {
            for (String key : locations.keySet()) {
                if ( key.equals(r.getRestaurantName().toLowerCase())) {
                    locations.put(key, r.toHashMap());
                }
            }
        }
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