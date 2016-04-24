package ua.onpu.project.gomus.activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;

import ua.onpu.project.gomus.R;

public class TourMapActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private static final int CAMERA_ZOOM = 15;
    private ArrayList<LatLng> markers;
    private ArrayList<LatLng> points;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tour_map);
        //Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_map_tour);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(R.string.tour_map_title);
        //Getting intent with extras
        Intent intent1 = getIntent();
        Bundle bd1 = intent1.getExtras();

        if (bd1 != null) {
            markers = (ArrayList<LatLng>) bd1.get("markers");
            points = (ArrayList<LatLng>) bd1.get("points");
        }

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map_tour_view);
        mapFragment.getMapAsync(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(markers.get(0), CAMERA_ZOOM));
        //Markers init
        for(LatLng marker : markers)
        {
            mMap.addMarker(new MarkerOptions().position(marker));
        }
        //Adding route
        PolylineOptions lineOptions = new PolylineOptions();
        lineOptions.addAll(points);
        lineOptions.width(3);
        lineOptions.color(Color.RED);
        mMap.addPolyline(lineOptions);
    }
}
