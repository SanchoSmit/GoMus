package ua.onpu.project.gomus.model;

import java.util.ArrayList;

public class Tour {
    private String name;
    private String image;
    private ArrayList<Location> locations = new ArrayList<>();

    // Empty constructor
    public Tour() {
    }

    /**
     * One param constructor
     * @param name  String name of tour
     */
    public Tour(String name){
        this.name = name;
    }

    /**
     * Getter for name of the tour
     * @return  Name of the tour
     */
    public String getName() {
        return name;
    }

    /**
     * Setter for name of the tour
     * @param name  String name of tour
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Getter for tour image path
     * @return  String image path
     */
    public String getImage() {
        return image;
    }

    /**
     * Setter for tour image path
     * @param image  String path of image
     */
    public void setImage(String image) {
        this.image = image;
    }

    /**
     * Getter for the list of locations
     * @return  ArrayList of locations
     */
    public ArrayList<Location> getLocations() {
        return locations;
    }

    /**
     * Setter for list of locations
     * @param locations  ArrayList of locations
     */
    public void setLocations(ArrayList<Location> locations) {
        this.locations = locations;
    }

    /**
     * Modified toString() method
     * @return  String value of class fields
     */
    @Override
    public String toString() {
        return "Tour{" +
                "name='" + name + '\'' +
                ", image='" + image + '\'' +
                ", locations=" + locations +
                '}';
    }
}
