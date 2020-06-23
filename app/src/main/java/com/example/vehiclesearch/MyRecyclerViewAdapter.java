package com.example.vehiclesearch;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class MyRecyclerViewAdapter extends RecyclerView.Adapter <MyRecyclerViewAdapter.ViewHolder> {

    //List of vehicle objects
    private final List<VehicleUtil.Vehicle> vehicleList;

    //Boolean variable that will be storing the status of the TwoPane
    private boolean isHasTwoPane;

    //Default constructor that will initialized the vehicleList to v and this thisHasTwoPane to isHasTwoPane
    MyRecyclerViewAdapter(List<VehicleUtil.Vehicle> v, boolean isHasTwoPane) {
        this.vehicleList = v;
        this.isHasTwoPane = isHasTwoPane;
    }

    @NonNull
    @Override
    //onCreateViewHolder function that will inflate the View and return the view to the caller
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.vehicle_list_content, parent, false);
        return new ViewHolder(view);
    }

    @Override
    //onBindViewHolder method that will be passing information to the fragments
    public void onBindViewHolder(final ViewHolder holder, int position) {

        //Initializes the newVehicle object to the current vehicleList object
        holder.newVehicle = vehicleList.get(position);

        //Sets the ID to the position incremented by 1
        holder.viewID.setText(String.valueOf(position + 1));

        //Sets the display bar of all the vehicles
        holder.contentView.setText(vehicleList.get(position).getDisplayBar());

        //If a vehicle is selected, it will enter this
        holder.view.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                if (isHasTwoPane){
                    int selectedImage = holder.getAdapterPosition();
                    int selectedMakeAndModel = holder.getAdapterPosition();
                    int selectedPrice = holder.getAdapterPosition();
                    int selectedDescription = holder.getAdapterPosition();
                    int selectedLastUpdate = holder.getAdapterPosition();
                    VehicleFragment fragment = VehicleFragment.newInstance(
                            selectedImage, selectedMakeAndModel, selectedPrice,
                            selectedDescription, selectedLastUpdate);
                    FragmentActivity myContext = null;
                    FragmentManager fragManager = myContext.getSupportFragmentManager();

                }
                else {
                    //Grabs the current context
                    Context context = v.getContext();

                    //Declares an Intent variable that will be used to communicate with the fragment
                    Intent intent = new Intent(context, VehicleDetailActivity.class);

                    //Intent will send the vehicle image to the fragment
                    intent.putExtra(VehicleUtil.VEHICLE_IMAGE, holder.getAdapterPosition());

                    //Intent will send the make and model to the fragment
                    intent.putExtra(VehicleUtil.MAKE_MODEL, holder.getAdapterPosition());

                    //Intent will send the price to the fragment
                    intent.putExtra(VehicleUtil.PRICE, holder.getAdapterPosition());

                    //Intent will send the description to the fragment
                    intent.putExtra(VehicleUtil.DESCRIPTION, holder.getAdapterPosition());

                    //Intent will send the last updated message to the fragment
                    intent.putExtra(VehicleUtil.LAST_UPDATE, holder.getAdapterPosition());

                    //Starts the activity
                    context.startActivities(new Intent[]{intent});
                }
            }
        });
    }

    // count the numbers of vehicles
    @Override
    public int getItemCount() {
        return vehicleList.size();
    }

    /**
     * ViewHolder class that extend the RecyclerView
     */
    static class ViewHolder extends RecyclerView.ViewHolder {
        final View view;
        final TextView viewID;
        final TextView contentView;
        VehicleUtil.Vehicle newVehicle;

        /**
         * Assigning keys
         * @param itemView
         */
        public ViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            viewID = view.findViewById(R.id.id);
            contentView = view.findViewById(R.id.content);
        }
    }


}
