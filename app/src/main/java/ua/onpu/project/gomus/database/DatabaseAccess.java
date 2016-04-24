package ua.onpu.project.gomus.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.regex.Pattern;

import ua.onpu.project.gomus.model.Location;
import ua.onpu.project.gomus.model.Tour;

public class DatabaseAccess {
    private SQLiteOpenHelper openHelper;
    private SQLiteDatabase database;
    private static DatabaseAccess instance;

    /**
     * Private constructor to avoid object creation from outside classes
     * @param context
     */
    private DatabaseAccess(Context context) {
        this.openHelper = new DatabaseOpenHelper(context);
    }

    /**
     * Return a singleton instance of DatabaseAccess
     * @param context the Context
     * @return the instance of DatabaseAccess
     */
    public static DatabaseAccess getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseAccess(context);
        }
        return instance;
    }

    /**
     * Open the database connection.
     */
    public void open() {
        this.database = openHelper.getWritableDatabase();
    }

    /**
     * Close the database connection.
     */
    public void close() {
        if (database != null) {
            this.database.close();
        }
    }

    /**
     * Return all locations from database
     * @param language
     * @return list of locations
     */
    public ArrayList<Location> getLocations(String language) {
        ArrayList<Location> locations = new ArrayList<>();
        Cursor cursor = database.query("Location", null, null, null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Location location = cursorToLocation(cursor,language);
            locations.add(location);
            cursor.moveToNext();
        }
        cursor.close();
        return locations;
    }

    /**
     * Set fields of Location class from database
     * @param cursor
     * @param language
     * @return object of Location class
     */
    private Location cursorToLocation(Cursor cursor,String language) {
        Location data = new Location();
        data.setId(cursor.getInt(cursor.getColumnIndex("id")));
        data.setName(textLanguage(cursor.getString(cursor.getColumnIndex("name")),language));
        data.setAddress(textLanguage(cursor.getString(cursor.getColumnIndex("address")),language));
        data.setPhone(cursor.getString(cursor.getColumnIndex("phone")));
        data.setWebsite(cursor.getString(cursor.getColumnIndex("websites")));
        data.setRating(cursor.getFloat(cursor.getColumnIndex("rating")));
        //Separation coordinates to latitude and longitude
        String[] coords = cursor.getString(cursor.getColumnIndex("coordinates")).split(",", 2);
        data.setLat(Double.parseDouble(coords[0]));
        data.setLon(Double.parseDouble(coords[1]));
        data.setInformation(textLanguage(cursor.getString(cursor.getColumnIndex("description")),language));
        data.setImage(cursor.getString(cursor.getColumnIndex("photo")));

        return data;
    }

    /**
     * Return all tours from database
     * @param language
     * @return list of tours
     */
    public ArrayList<Tour> getTours(String language) {
        ArrayList<Tour> tours = new ArrayList<>();
        Cursor cursor = database.query("Tour", null, null, null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Tour tour = cursorToTour(cursor,language);
            tours.add(tour);
            cursor.moveToNext();
        }
        cursor.close();
        return tours;
    }

    /**
     * Set fields of Tour class from database
     * @param cursor
     * @param language
     * @return object of Tour class
     */
    private Tour cursorToTour(Cursor cursor, String language) {
        Tour data = new Tour();
        data.setId(cursor.getInt(cursor.getColumnIndex("id")));
        data.setName(textLanguage(cursor.getString(cursor.getColumnIndex("name")),language));
        data.setDescription(textLanguage(cursor.getString(cursor.getColumnIndex("description")),language));
        data.setImage(cursor.getString(cursor.getColumnIndex("photo")));
        return data;
    }

    /**
     * Method that get location and put them to the tour
     * @param allLocations ArrayList of locations
     * @param allTours ArrayList of tours
     */
   public void getToursLocations(ArrayList<Location> allLocations,ArrayList<Tour> allTours) {
        int tourId;
        int locationId;

        Cursor cursor = database.query("Location_Tour", null, null, null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            tourId = cursor.getInt(cursor.getColumnIndex("tour_id"));
            locationId = cursor.getInt(cursor.getColumnIndex("location_id"));

            for(Tour tour :allTours){
                if(tour.getId()==tourId){
                    for(Location location : allLocations) {
                        if(location.getId()==locationId) {
                            tour.addLocation(location);
                        }
                    }
                }
            }
            cursor.moveToNext();
        }
        cursor.close();
    }

    /**
     * Text language parser
     * @param text Input text
     * @param lang Language "ru" - Russian, "en" - English
     * @return text in chosen language
     */
    public String textLanguage(String text,String lang) {
        String pattern = "[\\[][ruen:]{0,}[\\]]";
        Pattern p = Pattern.compile(pattern);
        String[] results = p.split(text);
        if(lang.equals("ru")) return results[1];
        else return results[2];
    }
}
