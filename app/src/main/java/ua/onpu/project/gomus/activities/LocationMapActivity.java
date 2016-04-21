package ua.onpu.project.gomus.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import ua.onpu.project.gomus.R;
import ua.onpu.project.gomus.model.Location;

public class LocationMapActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Location location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_map);
        //Getting intent with extras
        Intent intent1 = getIntent();
        Bundle bd1 = intent1.getExtras();

        if(bd1 != null)
        {
            location = (Location)bd1.get("location");
        }
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        // Add a marker in Location and move the camera
        LatLng location_coords = new LatLng(location.getLat(),location.getLon());
        mMap.addMarker(new MarkerOptions().position(location_coords).title(location.getName()));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(location_coords));
    }
}
