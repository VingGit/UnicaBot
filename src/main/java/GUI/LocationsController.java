package GUI;

import JSONParse.Location;
import JSONParse.Restaurant;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.type.TypeReference;
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
public class LocationsController{
    @JsonProperty("locations")
    public ArrayList<Location> locations;
    private Set keySet;
    protected ArrayList<String> keys;
    //HashMap<String, Restaurant> savedRestaurants;
    ArrayList<Restaurant> savedRestaurants;
    protected static ArrayList<Restaurant> restaurantArrayList;
    //protected ArrayList<Place> restaurants;
    private final ObjectMapper objectmapper = new ObjectMapper();

    public LocationsController() {
        try {
            this.locations = objectmapper.readValue(Paths.get("src/main/resources/locations.json").toFile(), new TypeReference<ArrayList<Location>>(){});
            //(assertThat(locations.get(0), instanceOf(Location.class));
            //objectmapper.configure(DeserializationFeature.USE_JAVA_ARRAY_FOR_JSON_ARRAY, true);
            //ArrayList<HashMap<String,Object>> list = new ArrayList<HashMap<String,Object>>(col);
            System.out.println(locations);
            /**
            for (Object o : list.get(0)) {
                System.out.println(o.getClass());
                System.out.println(o);
                Location loc = new Location(o);
                this.locations.add(loc);
                System.out.println(locations);
            }*/
        }catch(IOException e){
                e.printStackTrace();
            }
            System.out.println("locations.json read successfully");
            //setSavedRestaurants();
            //System.out.println("Saved locations: \n"+ locations);
            //System.out.println(restaurantArrayList);
            //keySet = locations.keySet();
            //keys = new ArrayList<>(keySet);
            //for (Object o: ){
            //  Restaurant r = null;
            //}
            //ArrayList<HashMap<String, Object>>saved = new ArrayList<HashMap<>>(locations.values()); //
        }
        public void setSavedRestaurants () {
            for (Location l: locations) {
                System.out.println(l);
                System.out.println(l.getCommand()+"\n"+l.getValues());

                HashMap<String, String> resValues = l.getValues();
                Restaurant r = new Restaurant();
                try{
                    r = new Restaurant(resValues);
                }catch (NullPointerException n){
                    n.printStackTrace();
                }
                savedRestaurants.add(r);
            }
            System.out.println(savedRestaurants.getClass());
            //updateRestaurantArrayList();
            System.out.println("Retrieved " + Integer.toString(savedRestaurants.size()) + " restaurants");
            //System.out.println(restaurantArrayList);
        }

    public void setKeys() {
        for(Location lo:locations){
            String command = lo.getCommand();
            keys.add(command);
        }
    }

    public ArrayList<Restaurant> getRestaurantList () {
            return savedRestaurants;
        }
        public ArrayList<String> getCommands () {
            return keys;
        }

        public List<String> getNames () {
            List<String> namesList = new ArrayList<>();
            //ObservableList<String> names = FXCollections.observableArrayList(namesList);
            for (Location lo : locations) {
                System.out.println(lo);
                namesList.add(lo.getValues().get("name"));
            }
            return namesList;
        }
        public Location getLocation (String name){
            for (Location lo : locations) {
                if (lo.getCommand().equals(name.toLowerCase() )&& (lo.getValues().get("name").equals(name))) {
                    return lo;
                }
            }
            return null;
        }
        public Restaurant getRestaurant (String name){
            for (Location lo: locations) {
                Restaurant re = new Restaurant(lo.getValues());
                if (re.getRestaurantName().equals(name)) {
                    return re;
                }
            }
            return null;
        }

        public void updateRestaurantArrayList () {
            System.out.println("Retrieving restaurants...");
            //savedRestaurants = new ArrayList<Restaurant>(locations.values()); //
            /**
             if (savedRestaurants.size() > 0) {
             restaurantArrayList = new ArrayList<>();
             for (Restaurant res : savedRestaurants) {
             //System.out.println(res);
             //Restaurant r = new Restaurant(res);
             //System.out.println("Restaurant from Hashmap: " + r.toString());
             if (r != null) {
             restaurantArrayList.add(r);
             }
             }
             }*/
        }

        /**
         * @author Sanna Volanen
         * @param newPlace HashMap containing parameters of a Restaurant
         * @pre newPlace != null
         * @post restaurantArraylist.size()> OLD(restaurantArrayList.size()) && locations.values().contains(newPlace)
         */
        public void addPlace (HashMap < String, String > newPlace){
            //TODO: korjaa
             System.out.println("Trying to add new location...");
             System.out.println("Current list size "+ locations.size());
             //System.out.println("Input hashmap: "+newPlace);
             Location newLoc = new Location(newPlace);
             System.out.println("New "+newLoc); //tulee oikein
             if (!(savedRestaurants.contains(newLoc))){
             //keys.add(newP.getRestaurantName().toLowerCase());
             locations.add(newLoc);
             //restaurantArrayList.add(newP);
             updateRestaurantArrayList();
             setKeys();
             System.out.println("Restaurants after addition: "+ locations);
             updateJson();
             System.out.println("Current list size "+ locations.size());
             }else {
             System.out.println("already in list");
             }

        }

        private boolean exists (Restaurant newP){
            for (Restaurant p1 : restaurantArrayList) {
                if (p1.equals(newP)) {
                    return true;
                }
            }
            return false;
        }

        /**
         * Edits the values of an existing Restaurant
         * @author Sanna Volanen
         * @param editValues HashMap containing the name of a chosen Restaurant and the new values for that object to be inserted
         * @pre editValues != null & !editValues.isEmpty()
         * @post !toBeEdited.equals(OLD(toBeEdited)
         */
        public void editPlace (HashMap < String, String > editValues){
            Restaurant toBeEdited = getRestaurant(editValues.get("name"));
            int index;
            if (toBeEdited != null) {
                index = restaurantArrayList.indexOf(toBeEdited);
                System.out.println("Matched: \n " + toBeEdited);
                if (editValues.keySet().toArray().length >= 1) {
                    for (String key : editValues.keySet()) {
                        if (!key.equals("name")) {
                            toBeEdited.edit(key, editValues.get(key));
                        } else {
                            System.out.println("Trying to edit " + toBeEdited.getRestaurantName());
                        }
                    }
                    System.out.println(" with new values: \n" + toBeEdited);
                    restaurantArrayList.set(index, toBeEdited);
                    updateJson();
                } else {
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

        public void deletePlace (Restaurant toBedeleted){
            /**
             System.out.println("Now "+Integer.toString(restaurantArrayList.size())+ " restaurants. Trying deletion...");
             if(restaurantArrayList.contains(toBedeleted)){
             System.out.println(locations.size()+ " locations");
             locations.remove(toBedeleted.getRestaurantName().toLowerCase(), toBedeleted.toHashMap());
             System.out.println(locations.size()+ " locations");
             updateRestaurantArrayList();
             updateJson();
             }
             System.out.println(restaurantArrayList.size() + " restaurants left");
             */
        }

        /**
         * Rewrites Locations.json
         * @author Sanna Volanen
         * @pre EXISTS(locations.json)
         * @post EXISTS(locations.json)
         */
        public void updateJson () {
            // update local variable
            /**
             for (Restaurant r:restaurantArrayList) {
             for (Object key : keySet) {
             if ( key.equals(r.getRestaurantName().toLowerCase())) {
             locations.put(key.toString(), r);
             }
             }
             }*/
            HashMap<String, Object> newValues = new HashMap<>();
            newValues.put("Locations", locations);
            //create PrettyPrinter instance
            ObjectWriter writer = objectmapper.writer(new DefaultPrettyPrinter());
            try {
                writer.writeValue(new File("src/main/resources/locations.json"), newValues);
            } catch (IOException io) {
                io.printStackTrace();

                System.out.println("File not found");
            }
            System.out.println("Locations json updated.");
        }


        public int size () {
            return restaurantArrayList.size();
        }


        public boolean isEmpty () {
            return restaurantArrayList.isEmpty();
        }

        public boolean containsValue (Restaurant p1){
            return restaurantArrayList.contains(p1);
        }


        @Nullable

        public Object put (Object key, Object value){
            return null;
        }


        public Object remove (Object key){
            return null;
        }


        public void putAll (@NotNull Map m){

        }


        public void clear () {

        }


        public Set keySet () {
            /**
            List<String> keys = new ArrayList<>();
            for (Location l : locations) {
                keys.add(l.getCommand());
            }
            this.keySet = new HashSet(keys);
             */
            return keySet;
        }


        @Nullable

        public Object putIfAbsent (Object key, Object value){
            return null;
        }


        public boolean remove (Object key, Object value){
            return false;
        }


        public boolean replace (Object key, Object oldValue, Object newValue){
            return false;
        }
}