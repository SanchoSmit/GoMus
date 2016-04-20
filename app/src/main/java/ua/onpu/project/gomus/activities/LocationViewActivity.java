package ua.onpu.project.gomus.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import ua.onpu.project.gomus.R;
import ua.onpu.project.gomus.model.Location;

public class LocationViewActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private Location location;
    private ImageView locationImage;
    private TextView locationText;
    private TextView locationAddress;
    private TextView locationPhone;
    private TextView locationWebsite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_view);
        //Getting intent with extras
        Intent intent = getIntent();
        Bundle bd = intent.getExtras();

        if(bd != null)
            location = (Location)bd.get("best_location");

        //Toolbar initialization
        toolbar = (Toolbar) findViewById(R.id.anim_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        // CollapsingToolbar initialization
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsingToolbar_location);
        collapsingToolbarLayout.setTitle(location.getName());

        //Init resources
        locationImage = (ImageView) findViewById(R.id.location_image);
        locationText = (TextView) findViewById(R.id.location_text);
        locationAddress = (TextView) findViewById(R.id.location_address);
        locationPhone = (TextView) findViewById(R.id.location_phone);
        locationWebsite = (TextView) findViewById(R.id.location_website);

        setActivityResources(location);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_location, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:

                // Finish activity on back button pressed
                finish();
                break;
            case R.id.button_map:

                // TODO: Location Map Intent
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Method that fill all the resources of activity
     * @param loc Object of Location class
     */
    private void setActivityResources(Location loc) {
        locationText.setText(loc.getInformation());
        locationAddress.setText(loc.getAddress());
        locationPhone.setText(loc.getPhone());
        locationWebsite.setText(loc.getWebsite());
        Picasso.with(LocationViewActivity.this).load(loc.getImage()).into(locationImage);
    }
}
