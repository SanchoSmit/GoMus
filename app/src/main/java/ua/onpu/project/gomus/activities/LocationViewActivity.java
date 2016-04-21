package ua.onpu.project.gomus.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
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
                Intent intent = new Intent(LocationViewActivity.this,LocationMapActivity.class);
                intent.putExtra("location",location);
                startActivity(intent);
                break;
            case R.id.button_info:
                showInfoAlertDialog();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     *
     */
    private void showInfoAlertDialog() {
        LayoutInflater li = getLayoutInflater();
        View promptsView = li.inflate(R.layout.dialog_info, null);
        AlertDialog.Builder mDialogBuilder = new AlertDialog.Builder(this);
        TextView locationPhone = (TextView) promptsView.findViewById(R.id.location_phone);
        TextView locationWebsite = (TextView) promptsView.findViewById(R.id.location_website);
        mDialogBuilder.setView(promptsView);
        //TODO: Max bug fix
        mDialogBuilder.setTitle("Location Info");
        locationPhone.setText(location.getPhone());
        locationWebsite.setText(location.getWebsite());
        mDialogBuilder.setPositiveButton("Ok",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alertDialog = mDialogBuilder.create();
        alertDialog.show();


    }

    /**
     * Method that fill all the resources of activity
     * @param loc Object of Location class
     */
    private void setActivityResources(Location loc) {
        locationText.setText(loc.getInformation());
        locationAddress.setText(loc.getAddress());
        Picasso.with(LocationViewActivity.this).load(loc.getImage()).into(locationImage);
    }


}
