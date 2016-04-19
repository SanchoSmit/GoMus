package ua.onpu.project.gomus.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import ua.onpu.project.gomus.R;
import ua.onpu.project.gomus.model.Location;

public class LocationViewActivity extends AppCompatActivity {

    private Location location;
    private ImageView location_image;

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

        //Init resources
        location_image = (ImageView) findViewById(R.id.location_image);

        Picasso.with(LocationViewActivity.this).load(location.getImage()).into(location_image);
    }





}
