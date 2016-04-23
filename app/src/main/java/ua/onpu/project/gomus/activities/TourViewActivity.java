package ua.onpu.project.gomus.activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import ua.onpu.project.gomus.R;
import ua.onpu.project.gomus.adapters.LocationsInTourAdapter;
import ua.onpu.project.gomus.direction.DirectionJSONParser;
import ua.onpu.project.gomus.model.Location;
import ua.onpu.project.gomus.model.Tour;

public class TourViewActivity extends AppCompatActivity implements OnMapReadyCallback {

    private Toolbar toolbar;
    private Tour currentTour;
    private ArrayList<Location> locations;
    private RecyclerView recyclerView;
    private GoogleMap mMap;
    private static final int CAMERA_ZOOM = 14;
    private ArrayList<LatLng> markers;
    private static final String GOOGLE_API_KEY = "AIzaSyCjFgqDZ8jxhabcuDDw7K0erNKVfrQljTk";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tour_view);

        //Getting intent with extras
        Intent intent = getIntent();
        Bundle bd = intent.getExtras();

        if(bd != null)
            currentTour = (Tour) bd.get("tour_current");
        //Locations init
        locations = currentTour.getLocations();
        //Toolbar initialization
        toolbar = (Toolbar) findViewById(R.id.toolbar_tours);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(currentTour.getName());

        TextView descriptionText = (TextView) findViewById(R.id.tour_description);
        descriptionText.setText(currentTour.getDescription());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map_tour);
        mapFragment.getMapAsync(this);

        // RecyclerView initialization
        recyclerView = (RecyclerView) findViewById(R.id.recycleView_tour);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        updateRecyclerView();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Method to update RecyclerView
     */
    private void updateRecyclerView() {
        LocationsInTourAdapter adapter = new LocationsInTourAdapter(currentTour.getLocations(), this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        markers = convertCoords();
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(markers.get(markers.size()/2), CAMERA_ZOOM));

        //Markers init
        for(LatLng marker : markers)
        {
            mMap.addMarker(new MarkerOptions().position(marker));
        }
        //Route calculating
        String url = getDirectionsUrl(locations);
        DownloadTask downloadTask = new DownloadTask();
        downloadTask.execute(url);
    }

    /**
     * Method for converting Location lat and lon to LatLng ArrayList
     * @return ArrayList LatLng of markers
     */
    private ArrayList<LatLng> convertCoords(){
        ArrayList<LatLng> coords = new ArrayList<>();
        for (Location loc : locations)
        {
           LatLng coord = new LatLng(loc.getLat(), loc.getLon());
           coords.add(coord);
        }
        return coords;
    }

    // Fetches data from url passed
    private class DownloadTask extends AsyncTask<String, Void, String> {
        // Downloading data in non-ui thread
        @Override
        protected String doInBackground(String... url) {

            // For storing data from web service
            String data = "";

            try{
                // Fetching the data from web service
                data = downloadUrl(url[0]);
            }catch(Exception e){
                Log.d("Background Task",e.toString());
            }
            return data;
        }

        // Executes in UI thread, after the execution of
        // doInBackground()
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            ParserTask parserTask = new ParserTask();
            // Invokes the thread for parsing the JSON data
            parserTask.execute(result);
        }
    }

    /**
     * A class to parse the Google Places in JSON format
     */
    private class ParserTask extends AsyncTask<String, Integer, List<List<HashMap<String,String>>> >{

        // Parsing the data in non-ui thread
        @Override
        protected List<List<HashMap<String, String>>> doInBackground(String... jsonData) {

            JSONObject jObject;
            List<List<HashMap<String, String>>> routes = null;

            try{
                jObject = new JSONObject(jsonData[0]);
                DirectionJSONParser parser = new DirectionJSONParser();

                // Starts parsing data
                routes = parser.parse(jObject);
            }catch(Exception e){
                e.printStackTrace();
            }
            return routes;
        }

        // Executes in UI thread, after the parsing process
        @Override
        protected void onPostExecute(List<List<HashMap<String, String>>> result) {

            ArrayList<LatLng> points = null;
            PolylineOptions lineOptions = null;

            // Traversing through all the routes
            for(int i=0;i<result.size();i++){
                points = new ArrayList<LatLng>();
                lineOptions = new PolylineOptions();

                // Fetching i-th route
                List<HashMap<String, String>> path = result.get(i);

                // Fetching all the points in i-th route
                for(int j=0;j<path.size();j++){
                    HashMap<String,String> point = path.get(j);

                    double lat = Double.parseDouble(point.get("lat"));
                    double lng = Double.parseDouble(point.get("lng"));
                    LatLng position = new LatLng(lat, lng);

                    points.add(position);
                }

                // Adding all the points in the route to LineOptions
                lineOptions.addAll(points);
                lineOptions.width(3);
                lineOptions.color(Color.RED);
            }

            // Drawing polyline in the Google Map for the i-th route
            mMap.addPolyline(lineOptions);
        }
    }

    /**
     * Method for url generating
     * @param locations ArrayList of all locations
     * @return String url
     */
    private String getDirectionsUrl(ArrayList<Location> locations){

        // Origin of route
        String str_origin = "origin="+locations.get(0).getLat()+","+locations.get(0).getLon();
        // Destination of route
        String str_dest = "destination="+locations.get(locations.size()-1).getLat()+","+locations.get(locations.size()-1).getLon();
        // Sensor enabled
        String sensor = "sensor=false";
        // Mode
        String mode = "mode=walking";

        // Waypoints
        String waypoints = "";
        for(int i=1;i<locations.size()-1;i++){
            //LatLng point  = (LatLng) markerPoints.get(i);
            if(i==1) waypoints = "waypoints=";
            if(i==locations.size()-2) waypoints += locations.get(i).getLat() + "," + locations.get(i).getLon();
            else waypoints += locations.get(i).getLat() + "," + locations.get(i).getLon() + "|";
        }

        // Building the parameters to the web service
        String parameters = str_origin+"&"+str_dest+"&"+sensor+"&"+mode+"&"+waypoints;
        // Output format
        String output = "json";
        // Building the url to the web service
        String url = "https://maps.googleapis.com/maps/api/directions/"+output+"?"+parameters+"&key=" + GOOGLE_API_KEY;

        return url;
    }

    /**
     * A method to download json data from url
     * */
    private String downloadUrl(String strUrl) throws IOException {
        String data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;
        try{
            URL url = new URL(strUrl);

            // Creating an http connection to communicate with url
            urlConnection = (HttpURLConnection) url.openConnection();
            // Connecting to url
            urlConnection.connect();
            // Reading data from url
            iStream = urlConnection.getInputStream();

            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));
            StringBuffer sb  = new StringBuffer();

            String line = "";
            while( ( line = br.readLine())  != null){
                sb.append(line);
            }

            data = sb.toString();
            br.close();

        }catch(Exception e){
            Log.d("Exception download url", e.toString());
        }finally{
            iStream.close();
            urlConnection.disconnect();
        }
        return data;
    }

}
