package com.jerotoma.jfilemanager.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.jerotoma.jfilemanager.Model.Drive;
import com.jerotoma.jfilemanager.R;

import java.util.ArrayList;

/**
 * Created by Otoman on 02/04/2017.
 */

public class LocalDriveRecyclerViewAdapter extends RecyclerView.Adapter<LocalDriveRecyclerViewAdapter.ViewHolder> {

    private ArrayList<Drive> drives;
    private int progressStatus = 0;

    // Provide a suitable constructor (depends on the kind of dataset)
    public LocalDriveRecyclerViewAdapter(ArrayList<Drive> drives) {
        this.drives = drives;
    }
    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return drives.size();
    }

    // Create new views (invoked by the layout manager)
    @Override
    public LocalDriveRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.drive_storage_indicator_cardview, parent, false);
        // set the view's size, margins, paddings and layout parameters
        return new ViewHolder(v);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        Drive drive = drives.get(position);

        viewHolder.disk_content_numbers.setText(drive.getContent());
        viewHolder.category_collection_name.setText(drive.getDariveName());
        viewHolder.category_collection_icon.setImageResource(R.drawable.ic_sd_storage);
        viewHolder.progress_storage_drive.setProgress(drive.getProgressStatus());
    }


    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
      static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        private TextView disk_content_numbers;
        private TextView category_collection_name;
        private ImageView category_collection_icon;
        private ProgressBar  progress_storage_drive;
        private ViewHolder(View v) {
            super(v);
            disk_content_numbers     = (TextView)v.findViewById(R.id.disk_content_numbers);
            category_collection_name = (TextView)v.findViewById(R.id.category_collection_name);
            category_collection_icon = (ImageView)v.findViewById(R.id.category_collection_icon);
            progress_storage_drive   = (ProgressBar)v.findViewById(R.id.progress_storage_drive);

        }
    }
}