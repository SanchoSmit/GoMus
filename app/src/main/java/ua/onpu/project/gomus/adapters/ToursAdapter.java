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

    public ToursAdapter(ArrayList<Tour> tours) {
        data = new ArrayList<>(tours);
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

    /**
     * Method that animate items in RecyclerView
     * @param newdata New list of data
     */
    public void animateTo(ArrayList<Tour> newdata) {
        applyAndAnimateRemovals(newdata);
        applyAndAnimateAdditions(newdata);
        applyAndAnimateMovedItems(newdata);
    }

    /**
     * Method that animate list after removing the item
     * @param newdata New list of data
     */
    private void applyAndAnimateRemovals(ArrayList<Tour> newdata) {
        for (int i = data.size() - 1; i >= 0; i--) {
            final Tour mData = data.get(i);
            if (!newdata.contains(mData)) {
                removeItem(i);
            }
        }
    }

    /**
     * Method that animate list after adding the item
     * @param newdata New list of data
     */
    private void applyAndAnimateAdditions(ArrayList<Tour> newdata) {
        for (int i = 0, count = newdata.size(); i < count; i++) {
            final Tour mData = newdata.get(i);
            if (!data.contains(mData)) {
                addItem(i, mData);
            }
        }
    }

    /**
     * Method that animate and move items
     * @param newdata New list of data
     */
    private void applyAndAnimateMovedItems(ArrayList<Tour> newdata) {
        for (int toPosition = newdata.size() - 1; toPosition >= 0; toPosition--) {
            final Tour mData = newdata.get(toPosition);
            final int fromPosition = data.indexOf(mData);
            if (fromPosition >= 0 && fromPosition != toPosition) {
                moveItem(fromPosition, toPosition);
            }
        }
    }

    /**
     * Method that add item to a list
     * @param position Item position in a list
     * @param tour_data New item
     */
    public void addItem(int position, Tour tour_data) {
        data.add(position, tour_data);
        notifyItemInserted(position);
    }

    /**
     * Method that move item in a list
     * @param fromPosition Old position of item
     * @param toPosition New position of item
     */
    public void moveItem(int fromPosition, int toPosition) {
        final Tour tour_data = data.remove(fromPosition);
        data.add(toPosition, tour_data);
        notifyItemMoved(fromPosition, toPosition);
    }

    /**
     * Method that remove item from a list
     * @param position Item position in a list
     * @return New list
     */
    public Tour removeItem(int position) {
        final Tour tour_data = data.remove(position);
        notifyItemRemoved(position);
        return tour_data;
    }
}
