package ua.onpu.project.gomus.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import ua.onpu.project.gomus.R;
import ua.onpu.project.gomus.model.Location;

public class LocationViewActivity extends AppCompatActivity {

    private Location location;
    private ImageView location_image;
    private TextView location_text;
    private TextView location_name;
    private TextView location_address;
    private AppBarLayout appBarLayout;
    private Toolbar toolbar;
    private CollapsingToolbarLayout collapsingToolbarLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_view);
        //Getting intent with extras
        Intent intent = getIntent();
        Bundle bd = intent.getExtras();

        if(bd != null)
        {
            location = (Location)bd.get("best_location");
        }

        //AppBarLayout initialization
        appBarLayout = (AppBarLayout) findViewById(R.id.appBar_location);

        //Toolbar initialization
        toolbar = (Toolbar) findViewById(R.id.anim_toolbar);
        setSupportActionBar(toolbar);

        // CollapsingToolbar initialization
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsingToolbar_location);
        collapsingToolbarLayout.setTitle(location.getName());

        //Init resources
        location_image = (ImageView) findViewById(R.id.location_image);
        location_text = (TextView) findViewById(R.id.location_text);
        location_address = (TextView) findViewById(R.id.location_address);

        setActivityResources(location);
    }

    /**
     * Method that fill all the resources of activity
     * @param loc Object of Location class
     */
    private void setActivityResources(Location loc) {
        location_text.setText(loc.getInformation());
        location_address.setText(loc.getAddress());
        Picasso.with(LocationViewActivity.this).load(loc.getImage()).into(location_image);
    }
}
