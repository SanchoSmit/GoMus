package ua.onpu.project.gomus.model;

public class Location {
    private int id;
    private String name;
    private String information;
    private String address;
    private String phone;
    private String website;
    private double lat;
    private double lon;
    private String image;
    private float rating;

    // Empty constructor
    public Location() {
    }

    /**
     * Getter for location id
     * @return
     */
    public int getId() {
        return id;
    }

    /**
     * Setter for location id
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Getter for location name
     * @return String name of location
     */
    public String getName() {
        return name;
    }

    /**
     * Setter for location name
     * @param name String name of location
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Getter for information about location
     * @return String information
     */
    public String getInformation() {
        return information;
    }

    /**
     * Setter for information about location
     * @param information String information
     */
    public void setInformation(String information) {
        this.information = information;
    }

    /**
     * Getter for address of location
     * @return String address
     */
    public String getAddress() {
        return address;
    }

    /**
     * Setter for address of location
     * @param address String address
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Getter for phone number of location
     * @return String phone number
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Setter for phone number of location
     * @param phone String phone number
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * Getter for location website
     * @return String website URL
     */
    public String getWebsite() {
        return website;
    }

    /**
     * Setter for location website
     * @param website String website URL
     */
    public void setWebsite(String website) {
        this.website = website;
    }

    /**
     * Getter for latitude of location
     * @return double latitude value
     */
    public double getLat() {
        return lat;
    }

    /**
     * Setter for latitude of location
     * @param lat double latitude value
     */
    public void setLat(double lat) {
        this.lat = lat;
    }

    /**
     * Getter for longitude of location
     * @return double longitude value
     */
    public double getLon() {
        return lon;
    }

    /**
     * Setter for longitude of location
     * @param lon double longitude value
     */
    public void setLon(double lon) {
        this.lon = lon;
    }

    /**
     * Getter for location image path
     * @return String path of image
     */
    public String getImage() {
        return image;
    }

    /**
     * Setter for location image path
     * @param image String path of image
     */
    public void setImage(String image) {
        this.image = image;
    }

    /**
     * Getter for rating of location
     * @return float rating value
     */
    public float getRating() {
        return rating;
    }

    /**
     * Setter for rating of location
     * @param rating float rating value
     */
    public void setRating(float rating) {
        this.rating = rating;
    }

    /**
     * Modified toString() method
     * @return  String value of class fields
     */
    @Override
    public String toString() {
        return "Location{" +
                "name='" + name + '\'' +
                ", information='" + information + '\'' +
                ", address='" + address + '\'' +
                ", phone='" + phone + '\'' +
                ", website='" + website + '\'' +
                ", lat=" + lat +
                ", lon=" + lon +
                ", image='" + image + '\'' +
                ", rating=" + rating +
                '}';
    }
}
