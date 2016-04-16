package ua.onpu.project.gomus.activities;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import ua.onpu.project.gomus.R;
import ua.onpu.project.gomus.adapters.ToursAdapter;
import ua.onpu.project.gomus.database.DatabaseAccess;
import ua.onpu.project.gomus.model.Location;
import ua.onpu.project.gomus.model.Tour;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ToursAdapter adapter;
    private Toolbar toolbar;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private LinearLayout bestLocation1;
    private LinearLayout bestLocation2;
    private TextView bestLocationText1;
    private TextView bestLocationText2;
    private ArrayList<Tour> tours;
    private ArrayList<Location> locations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // Toolbar initialization
        toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);

        // CollapsingToolbar initialization
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsingToolbar_main);
        collapsingToolbarLayout.setTitle(getResources().getString(R.string.tours));

        // Best locations initialization
        bestLocation1 = (LinearLayout) findViewById(R.id.best_location1);
        bestLocation2 = (LinearLayout) findViewById(R.id.best_location2);
        bestLocationText1 = (TextView) findViewById(R.id.best_location_text1);
        bestLocationText2 = (TextView) findViewById(R.id.best_location_text2);

        // Database init
        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(this);
        databaseAccess.open();
        tours = databaseAccess.getTours("en");
        locations = databaseAccess.getLocations("en");
        databaseAccess.getToursLocations(locations,tours);
        databaseAccess.close();

        // RecyclerView initialization
        recyclerView = (RecyclerView) findViewById(R.id.recycleView_main);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        updateRecyclerView();
        updateBestLocations();

        // Best locations onClick listeners
        bestLocation1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: onClick
            }
        });
        bestLocation2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: onClick
            }
        });
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.button_search:
                // TODO: search
                return true;
            case R.id.button_settings:
                // TODO: settings
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    // Update recyclerView data
    private void updateRecyclerView() {
        adapter = new ToursAdapter(tours);
        recyclerView.setAdapter(adapter);
    }

    // Update best location data
    private void updateBestLocations() {
        for(Location location: locations) {
            if(location.getRating()==4.0){
                //TODO change image .into() resourse
                //Picasso.with(bestLocation1.getContext()).load(location.getImage()).into(bestLocation1);
                bestLocationText2.setText(location.getName());
            }
            else if(location.getRating()==5.0)
            {
                //Picasso.with(bestLocation2.getContext()).load(location.getImage()).into(bestLocation2);
                bestLocationText2.setText(location.getName());
            }

        }
    }

}