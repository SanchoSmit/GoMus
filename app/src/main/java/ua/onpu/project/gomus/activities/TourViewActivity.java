package ua.onpu.project.gomus.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import ua.onpu.project.gomus.R;
import ua.onpu.project.gomus.adapters.LocationsInTourAdapter;
import ua.onpu.project.gomus.model.Tour;

public class TourViewActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private Tour currentTour;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tour_view);

        //Getting intent with extras
        Intent intent = getIntent();
        Bundle bd = intent.getExtras();

        if(bd != null)
            currentTour = (Tour) bd.get("tour_current");

        //Toolbar initialization
        toolbar = (Toolbar) findViewById(R.id.toolbar_tours);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(currentTour.getName());

        TextView descriptionText = (TextView) findViewById(R.id.tour_description);
        descriptionText.setText(currentTour.getDescription());

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
}
