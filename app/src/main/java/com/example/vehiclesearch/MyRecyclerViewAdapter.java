package com.example.vehiclesearch;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MyRecyclerViewAdapter extends RecyclerView.Adapter <MyRecyclerViewAdapter.ViewHolder> {

    private final List<VehicleUtil.Vehicle> vehicleList;

    private boolean isHasTwoPane;

    //Context context;
    MyRecyclerViewAdapter(List<VehicleUtil.Vehicle> v, boolean isHasTwoPane) {
        this.vehicleList = v;
        this.isHasTwoPane = isHasTwoPane;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.vehicle_list_content, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.newVehicle = vehicleList.get(position);
        holder.viewID.setText(String.valueOf(position + 1));
        holder.contentView.setText(vehicleList.get(position).getDisplayBar());

        holder.view.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                    /*if (isHasTwoPane){
                        int vehicleChoice = holder.getAdapterPosition();        // >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> FOR TWO PANES START HERE

                    }
                    else {*/
                Context context = v.getContext();
                Intent intent = new Intent(context, VehicleDetailActivity.class);
                intent.putExtra(VehicleUtil.VEHICLE_IMAGE, holder.getAdapterPosition());
                intent.putExtra(VehicleUtil.MAKE_MODEL, holder.getAdapterPosition());
                intent.putExtra(VehicleUtil.PRICE, holder.getAdapterPosition());
                intent.putExtra(VehicleUtil.DESCRIPTION, holder.getAdapterPosition());
                intent.putExtra(VehicleUtil.LAST_UPDATE, holder.getAdapterPosition());
                context.startActivities(new Intent[]{intent});
                //}
            }
        });
    }

    @Override
    public int getItemCount() {
        return vehicleList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        final View view;
        final TextView viewID;
        final TextView contentView;
        VehicleUtil.Vehicle newVehicle;

        public ViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            viewID = view.findViewById(R.id.id);
            contentView = view.findViewById(R.id.content);
        }
    }
}
