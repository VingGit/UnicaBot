package GUI;

import JSONParse.JSONMapper;
import JSONParse.Restaurant;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.HashMap;

@JsonPropertyOrder({
        "campusArea",
        "name",
        "url",
        "status"
})

public class Place {
    @JsonProperty("campusArea")
    private String campusArea;
    @JsonProperty("name")
    private String name;
    @JsonProperty("url")
    private String url;
    @JsonProperty("status")
    private HashMap<String, Object> status = new HashMap<>();

    //CONSTRUCTORS
    public Place(HashMap<String,String > input) {
        System.out.println("Input for Place constructor: "+input);
        for (String key : input.keySet()) {
            if (key.equals("url")) {
                String tempUrl = input.get(key);
                if(tempUrl.startsWith("https://www.unica.fi/modules/json")){
                    Restaurant temp = JSONMapper.unicaParser(url);
                    if (temp.getRestaurantMenuToday().equals(input.get("name"))) {
                        this.name = input.get("name");
                    } else {
                        this.name = temp.getRestaurantMenuToday();
                    }
                    this.url = temp.getRestaurantUrl();
                }
                else{
                    this.url = tempUrl;
                }
            } else if (key.equals("campus")) {
                this.campusArea = input.get("campus");
            }else if(key.equals("name")){
                this.name = input.get("name");
            }
        }
        setStatus(input.get("availability"), input.get("message"));
    }

    //public Place(String name){this.name = name;}

    // SETTERS
    public void setUrl(String url) {
        this.url = url;
    }
    public void setAvailability(String newValue){
        status.put("availability", newValue);
    }
    public void setMessage(String newMessage){
        status.put("message", newMessage);
    }

    public void setStatus(String availability, String message){
        if (availability.equals("open")){
            this.status.put("availability",true);
        }else{
            this.status.put("availability", false);
        }
        this.status.put("message", message);
    }
    //GETTERS
    public String getName() {
        return name;
    }
    public String getURL() {
        return url;
    }
    public String getCampus(){
        return campusArea;
    }
    public String getAvailability(){
        return status.get("availability").toString();
    }
    public String getMessage(){
        return status.get("message").toString();
    }
    public HashMap<String, Object> getStatus() {
        return status;
    }

    public void edit(String key, String newValue){
        if (key.equals("url")) {
            if (url.equals("")) {
                if (!newValue.equals("")) {
                    setUrl(newValue);
                }
            } else if (!newValue.equals(url)) {
                setUrl(newValue);
            }
        }else if(key.equals("availability") && !newValue.equals(status.get(key))){
            setAvailability(newValue);
        }else if(key.equals("message")){
            if(status.get("message").equals("")){
                if (!newValue.equals("")){
                    setMessage(newValue);
                }
            }else if(!newValue.equals(status.get(key))){
                setMessage(newValue);
            }
        }
    }


    public boolean equals(Object o) {
        Place that = (Place) o;
        if (this == that) return true;
        if (that == null || getClass() != that.getClass()) return false;
        return campusArea.equals(that.campusArea) && name.equals(that.name) && url.equals(that.url) && status.equals(that.status);
    }
}