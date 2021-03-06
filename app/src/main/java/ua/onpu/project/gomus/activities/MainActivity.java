package ua.onpu.project.gomus.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import ua.onpu.project.gomus.R;
import ua.onpu.project.gomus.adapters.ToursAdapter;
import ua.onpu.project.gomus.database.DatabaseAccess;
import ua.onpu.project.gomus.model.Location;
import ua.onpu.project.gomus.model.Tour;

public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    private RecyclerView recyclerView;
    private ToursAdapter adapter;
    private AppBarLayout appBarLayout;
    private Toolbar toolbar;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private RelativeLayout bestLocation1;
    private RelativeLayout bestLocation2;
    private ImageView bestLocationImage1;
    private ImageView bestLocationImage2;
    private TextView bestLocationText1;
    private TextView bestLocationText2;
    private ArrayList<Tour> tours = new ArrayList<>();
    private ArrayList<Location> locations = new ArrayList<>();
    private DatabaseAccess databaseAccess;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // AppBarLayout initialization
        appBarLayout = (AppBarLayout) findViewById(R.id.appBar_main);

        // Toolbar initialization
        toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);

        // CollapsingToolbar initialization
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsingToolbar_main);
        collapsingToolbarLayout.setTitle(getResources().getString(R.string.tours));

        // Best locations initialization
        bestLocation1 = (RelativeLayout) findViewById(R.id.best_location1);
        bestLocation2 = (RelativeLayout) findViewById(R.id.best_location2);
        bestLocationImage1 = (ImageView) findViewById(R.id.best_location_image1);
        bestLocationImage2 = (ImageView) findViewById(R.id.best_location_image2);
        bestLocationText1 = (TextView) findViewById(R.id.best_location_text1);
        bestLocationText2 = (TextView) findViewById(R.id.best_location_text2);

        // RecyclerView initialization
        recyclerView = (RecyclerView) findViewById(R.id.recycleView_main);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Database init
        databaseAccess = DatabaseAccess.getInstance(this);
        databaseAccess.open();
        tours = databaseAccess.getTours(getLanguage());
        locations = databaseAccess.getLocations(getLanguage());
        databaseAccess.getToursLocations(locations,tours);
        databaseAccess.close();

        //Best locations init
        final ArrayList<Location> best_locations = getBestLocations(locations);

        // Updating views
        updateRecyclerView();
        updateBestLocations(best_locations);

        // Best locations onClick listeners
        bestLocation1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(MainActivity.this,LocationViewActivity.class);
                intent1.putExtra("location_current",best_locations.get(0));
                startActivity(intent1);
            }
        });
        bestLocation2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(MainActivity.this,LocationViewActivity.class);
                intent2.putExtra("location_current",best_locations.get(1));
                startActivity(intent2);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        MenuItem searchItem = menu.findItem(R.id.button_search);

        // Search menu item expand listener
        MenuItemCompat.setOnActionExpandListener(searchItem, new MenuItemCompat.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {

                // Collapse AppBarLayout
                appBarLayout.setExpanded(false);
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {

                // Expand AppBarLayout
                appBarLayout.setExpanded(true);

                // Scroll to start position
                recyclerView.scrollToPosition(0);

                return true;
            }
        });

        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);

        // Search menu item text change listener
        searchView.setOnQueryTextListener(this);

        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {

        // Scroll to start position
        recyclerView.scrollToPosition(0);

        final ArrayList<Tour> filteredListData = filter(tours, newText);
        adapter.animateTo(filteredListData);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.button_settings){

            // Open SettingsActivity
            Intent settingsActivity = new Intent(getBaseContext(), SettingsActivity.class);
            startActivity(settingsActivity);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Method to update RecyclerView
     */
    private void updateRecyclerView() {
        adapter = new ToursAdapter(tours, this);
        recyclerView.setAdapter(adapter);
    }

    /**
     * Method to update Best Locations
     * @param location list of locations
     */
    private void updateBestLocations(ArrayList<Location> location) {

        //Best location #1
        Picasso.with(bestLocationImage1.getContext()).load(location.get(0).getImage()).into(bestLocationImage1);
        bestLocationText1.setText(location.get(0).getName());

        //Best location #2
        Picasso.with(bestLocationImage2.getContext()).load(location.get(1).getImage()).into(bestLocationImage2);
        bestLocationText2.setText(location.get(1).getName());
    }

    /**
     * Getting best locations method
     * @param location list of all locations
     * @return two best locations
     */
    private ArrayList<Location> getBestLocations(ArrayList<Location> location) {
        ArrayList<Location> best_locations = new ArrayList<>();
        for(Location loc: location) {
            if(loc.getRating()==4.0){
                best_locations.add(loc);
            }
            else if(loc.getRating()==5.0)
            {
                best_locations.add(loc);
            }
        }
        return best_locations;
    }

    /**
     * Filter method
     * @param items Default list of items
     * @param query Input text
     * @return Filtered list of data
     */
    private ArrayList<Tour> filter(ArrayList<Tour> items, String query) {
        query = query.toLowerCase();
        final ArrayList<Tour> filteredListData = new ArrayList<>();
        for (Tour data : items) {
            final String diaryText = data.getName().toLowerCase() + " " + data.getDescription().toLowerCase();
            if (diaryText.contains(query)){
                filteredListData.add(data);
            }
        }
        return filteredListData;
    }

    /**
     * Language getting method
     * @return application language
     */
    private String getLanguage(){
        SharedPreferences preferences = getSharedPreferences("GoMusSetting", MODE_PRIVATE);
        return preferences.getString("application_language", "en");
    }
}