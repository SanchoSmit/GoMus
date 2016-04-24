package ua.onpu.project.gomus.adapters;


import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import ua.onpu.project.gomus.R;
import ua.onpu.project.gomus.activities.LocationViewActivity;
import ua.onpu.project.gomus.model.Location;

public class LocationsInTourAdapter extends RecyclerView.Adapter<LocationsInTourAdapter.LocationsInTourViewHolder> {

    // List of locations
    private ArrayList<Location> data;
    private Context context;

    public LocationsInTourAdapter(ArrayList<Location> locations, Context context) {
        data = new ArrayList<>(locations);
        this.context = context;
    }

    @Override
    public LocationsInTourViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_location, parent, false);
        return new LocationsInTourViewHolder(view);
    }

    @Override
    public void onBindViewHolder(LocationsInTourViewHolder holder, int position) {
        final Location currentData = data.get(position);

        holder.name.setText(currentData.getName());

        // Image
        Picasso.with(context).load(currentData.getImage()).into(holder.image);

        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent locationIntent = new Intent(context,LocationViewActivity.class);
                locationIntent.putExtra("location_current", currentData);
                context.startActivity(locationIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class LocationsInTourViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        ImageView image;
        LinearLayout layout; // Container

        public LocationsInTourViewHolder(View itemView) {
            super(itemView);

            name = (TextView) itemView.findViewById(R.id.location_name);
            image = (ImageView) itemView.findViewById(R.id.location_image);
            layout = (LinearLayout) itemView.findViewById(R.id.location_layout);

        }
    }
}
