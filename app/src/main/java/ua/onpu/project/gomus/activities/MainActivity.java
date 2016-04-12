package ua.onpu.project.gomus.activities;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;

import ua.onpu.project.gomus.R;
import ua.onpu.project.gomus.adapter.ToursAdapter;
import ua.onpu.project.gomus.model.Tour;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ToursAdapter adapter;
    private Toolbar toolbar;
    private CollapsingToolbarLayout collapsingToolbarLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Toolbar initialization
        toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);

        // CollapsingToolbar initialization
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsingToolbar_main);
        collapsingToolbarLayout.setTitle("Tours");

        // RecyclerView initialization
        recyclerView = (RecyclerView) findViewById(R.id.recycleView_main);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        updateRecyclerView();
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
        adapter = new ToursAdapter(getTestData());
        recyclerView.setAdapter(adapter);
    }

    /**
     *
     * Test method for getting data.
     * Delete after implementing database!
     * @return ArrayList<Tour> list of tours
     *
     */
    private ArrayList<Tour> getTestData(){

        ArrayList<Tour> testList = new ArrayList<>();

        // Adding some data
        testList.add(new Tour("Opera House"));
        testList.add(new Tour("Deribasovskaya Street"));
        testList.add(new Tour("Long text for testinggggggggggg ggggggggggg"));
        testList.add(new Tour("Opera House"));
        testList.add(new Tour("Opera House"));
        testList.add(new Tour("Opera House"));
        testList.add(new Tour("Opera House"));
        testList.add(new Tour("Opera House"));
        testList.add(new Tour("Opera House"));
        testList.add(new Tour("Opera House"));

        return testList;
    }
}