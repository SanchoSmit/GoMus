package ua.onpu.project.gomus.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

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
     * @return list of locations
     */
    public ArrayList<Location> getLocations() {
        ArrayList<Location> locations = new ArrayList<>();
        Cursor cursor = database.query("Location", null, null, null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Location location = cursorToLocation(cursor);
            locations.add(location);
            cursor.moveToNext();
        }
        cursor.close();
        return locations;
    }

    /**
     * Set fields of Location class from database
     * @param cursor
     * @return object of Location class
     */
    private Location cursorToLocation(Cursor cursor) {
        Location data = new Location();
        data.setId(cursor.getInt(cursor.getColumnIndex("id")));
        data.setName(cursor.getString(cursor.getColumnIndex("name")));
        data.setAddress(cursor.getString(cursor.getColumnIndex("address")));
        data.setPhone(cursor.getString(cursor.getColumnIndex("phone")));
        data.setWebsite(cursor.getString(cursor.getColumnIndex("websites")));
        data.setRating(cursor.getFloat(cursor.getColumnIndex("rating")));
        //Separation coordinates to latitude and longitude
        String[] coords = cursor.getString(cursor.getColumnIndex("coordinates")).split(",", 2);
        data.setLat(Double.parseDouble(coords[0]));
        data.setLon(Double.parseDouble(coords[1]));
        data.setInformation(cursor.getString(cursor.getColumnIndex("description")));
        //TODO set image

        return data;
    }

    /**
     * Return all tours from database
     * @return list of tours
     */
    public ArrayList<Tour> getTours() {
        ArrayList<Tour> tours = new ArrayList<>();
        Cursor cursor = database.query("Tour", null, null, null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Tour tour = cursorToTour(cursor);
            tours.add(tour);
            cursor.moveToNext();
        }
        cursor.close();
        return tours;
    }

    /**
     * Set fields of Tour class from database
     * @param cursor
     * @return object of Tour class
     */
    private Tour cursorToTour(Cursor cursor) {
        Tour data = new Tour();
        data.setId(cursor.getInt(cursor.getColumnIndex("id")));
        data.setName(cursor.getString(cursor.getColumnIndex("name")));
        data.setDescription(cursor.getString(cursor.getColumnIndex("description")));
        //TODO set image
        return data;
    }

    //TODO getting locations of tour
    /*
    private ArrayList<Location> getToursLocations(ArrayList<Location> allLocations,int tour_id) {
        ArrayList<Location>
    }
    */

    //TODO language choose function
    /*
    public String textLanguege(String text,String lang){
        //Pattern pattern = Pattern.compile("[[][r][u][:][\]]([\W]{0,})[\[][e][n][:][\]]([\W]{0,})");
        String[] results = pattern.split(text);
        if(lang.equals("ru")) return results[0];
        else return results[1];
    }
    */

}
