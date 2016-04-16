package ua.onpu.project.gomus.adapters;


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
import ua.onpu.project.gomus.model.Tour;

public class ToursAdapter extends RecyclerView.Adapter<ToursAdapter.ToursViewHolder>{

    // List of tours
    private ArrayList<Tour> data;

    public ToursAdapter(ArrayList<Tour> data) {
        this.data = data;
    }

    @Override
    public ToursViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tour, parent, false);
        return new ToursViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ToursViewHolder holder, int position) {
        final Tour currentData = data.get(position);

        holder.name.setText(currentData.getName());
        holder.description.setText(currentData.getDescription());

        // Image
        Picasso.with(holder.image.getContext()).load(currentData.getImage()).into(holder.image);

        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: tour onClick
            }
        });

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class ToursViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        TextView description;
        ImageView image;
        LinearLayout layout; // Container

        public ToursViewHolder(View itemView) {
            super(itemView);

            name = (TextView) itemView.findViewById(R.id.tour_name);
            description = (TextView) itemView.findViewById(R.id.tour_description);
            image = (ImageView) itemView.findViewById(R.id.tour_image);
            layout = (LinearLayout) itemView.findViewById(R.id.tour_layout);

        }
    }
}
