package GUI;

import JSONParse.JSONMapper;
import JSONParse.Restaurant;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashMap;

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
        for (String key : input.keySet()) {
            if (key.equals("url")) {
                if (input.get(key) == null) {
                    this.url = "";
                } else {
                    Restaurant temp = JSONMapper.restaurantParser(url);
                    if (temp.getRestaurantName().equals(input.get("name"))) {
                        this.name = input.get("name");
                    } else {
                        this.name = temp.getRestaurantName();
                    }
                    this.url = temp.getRestaurantUrl();
                }
            } else if (key.equals("campus")) {
                this.campusArea = input.get("campus");
            }
                setStatus(input.get("availability"), input.get("message"));
        }
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
    public void edit(String param, String value){
        if (param.equals("url") && !value.equals(url) ){
            setUrl(value);
        }else if(param.equals("availability") && !value.equals(status.get(param))){
            setAvailability(value);
        }else if(param.equals("message") && !value.equals(status.get("message"))){
            setMessage(value);
        }
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

    @Override
    public String toString() {
        return "Place{" +
                "campusArea='" + campusArea + '\'' +
                ", name='" + name + '\'' +
                ", url='" + url + '\'' +
                ", status=" + status +
                '}';
    }
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Place that = (Place) o;
        return campusArea.equals(that.campusArea) && name.equals(that.name) && url.equals(that.url) && status.equals(that.status);
    }
}
