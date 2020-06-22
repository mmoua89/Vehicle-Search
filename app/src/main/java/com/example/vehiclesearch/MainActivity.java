package com.example.vehiclesearch;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Context context;
    private Spinner makeSpin;
    private Spinner modelSpin;
    private RecyclerView recyclerView;
    private TextView makeAndModel;
    private TextView price;
    private TextView description;
    private TextView lastUpdate;
    private ImageView imageView;

    private int makeID;
    private int vModelID;

    private boolean isHasTwoPane = false;


    //Variables for carmakes and carmakesVehicle
    private List<Integer> vehicleID;    // for 1st query    same as vehicle_make_id
    private List<String> vehicleMake;   // for 1st query

    private List<Integer> model_id;         // for 2nd query
    private List<String> vehicleModel;      // for 2nd query
    private List<Integer> vehicle_make_id;  // for 2nd query
    //HashMap<String, Integer> carIDVehicleMake = new HashMap<>();


    String makeURL = "https://thawing-beach-68207.herokuapp.com/carmakes";
    String modelURL = "https://thawing-beach-68207.herokuapp.com/carmodelmakes/";
    String availableURL = "https://thawing-beach-68207.herokuapp.com/cars/";
    int zipCode = 92603;
    String vehicleDetail = "https://thawing-beach-68207.herokuapp.com/cars/";

    private boolean hasTwoPane = false;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = getApplicationContext();

        makeSpin = findViewById(R.id.makeSpinner);
        modelSpin = findViewById(R.id.modelSpinner);
        recyclerView = findViewById(R.id.vehicle_list);
        makeAndModel = findViewById(R.id.make_model);
        price = findViewById(R.id.price);
        description = findViewById(R.id.description);
        lastUpdate = findViewById(R.id.last_update);
        imageView = findViewById(R.id.imageView);

        if (findViewById(R.id.right_pane) != null) {
            isHasTwoPane = true;
        }

        new GetMakes().execute();


    }



    private class GetMakes extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            HttpHandler sh = new HttpHandler();

            vehicleID = new ArrayList<>();
            vehicleMake = new ArrayList<>();

            String jsonStr = sh.makeServiceCall(makeURL);

            //if (jsonStr != null) {
            try {
                JSONArray jsonArray = new JSONArray(jsonStr);
                JSONObject jsonObject;

                for (int i = 0; i < jsonArray.length(); i++) {
                    jsonObject = jsonArray.getJSONObject(i);

                    int vID = jsonObject.getInt("id");
                    vehicleID.add(vID);

                    String vMake = jsonObject.getString("vehicle_make");
                    vehicleMake.add(vMake);

                    //carIDVehicleMake.put(vMake, vID);
                }
            } catch (JSONException e) {
                // if error, return 0 as false
                e.printStackTrace();
            }
            //}
            return null;
        }
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

            ArrayAdapter<String> adapter = new ArrayAdapter<>(
                    context, R.layout.my_custom_dropdown, vehicleMake);

            makeSpin.setAdapter(adapter);
            makeSpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    makeID = vehicleID.get(position);

                    new GetModel().execute();

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        }

    }


    private class GetModel extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            HttpHandler sh = new HttpHandler();

            model_id = new ArrayList<>();
            vehicleModel = new ArrayList<>();
            vehicle_make_id = new ArrayList<>();

            String newURL = modelURL + makeID;

            String jsonStr = sh.makeServiceCall(newURL);

            try {
                JSONArray jsonArray = new JSONArray(jsonStr);
                JSONObject jsonObject;

                for (int i = 0; i < jsonArray.length(); i++) {
                    jsonObject = jsonArray.getJSONObject(i);

                    int modelID = jsonObject.getInt("id");
                    model_id.add(modelID);

                    String vModel = jsonObject.getString("model");
                    vehicleModel.add(vModel);

                    int vmID = jsonObject.getInt("vehicle_make_id");
                    vehicle_make_id.add(vmID);

                }
            } catch (JSONException e) {
                // if error, return 0 as false
                e.printStackTrace();
            }

            return null;
        }
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

            ArrayAdapter<String> adapter = new ArrayAdapter<>(
                    context, R.layout.my_custom_dropdown, vehicleModel);
            //  adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            modelSpin.setAdapter(adapter);

            modelSpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    vModelID = model_id.get(position);

                    //String newURL = availableURL + ID + "/" + vMakeID + "/" +zipCode;

                    new GetAvailableVehicle().execute();

                    //Toast.makeText(context, newURL, Toast.LENGTH_LONG).show();

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        }

    }

    private class GetAvailableVehicle extends AsyncTask<Void, Void, Void> {

        String url;
        //RecyclerView recyl;
        public GetAvailableVehicle(){
            this.url = url;
            //this.recyl = recyl;
        }

        public String newURL;
        @Override
        protected Void doInBackground(Void... voids) {
            HttpHandler sh = new HttpHandler();

            VehicleUtil.Vehicle newVehicle;


            newURL = availableURL + makeID + "/" + vModelID + "/" + zipCode;


            String jsonStr = sh.makeServiceCall(newURL);
            if (jsonStr != null) {

                try {
                    JSONObject jsonObjectList = new JSONObject(jsonStr);
                    JSONArray jsonArray = jsonObjectList.getJSONArray("lists");
                    JSONObject jsonObject;

                    for (int i = 0; i < jsonArray.length(); i++) {
                        jsonObject = jsonArray.getJSONObject(i);

                        String color = jsonObject.getString("color");
                        String create_at = jsonObject.getString("created_at");
                        int listID = jsonObject.getInt("id");
                        String image_url = jsonObject.getString("image_url");
                        int mileage = jsonObject.getInt("mileage");
                        String model = jsonObject.getString("model");
                        int price = jsonObject.getInt("price");
                        String veh_description = jsonObject.getString("veh_description");
                        String vehicle_make = jsonObject.getString("vehicle_make");
                        String vehicle_url = jsonObject.getString("vehicle_url");
                        String vin_number = jsonObject.getString("vin_number");

                        newVehicle = new VehicleUtil.Vehicle(color, create_at, listID,
                                image_url, mileage, model, price, veh_description,
                                vehicle_make, vehicle_url, vin_number);

                        //Toast.makeText(context, vehicle_make, Toast.LENGTH_LONG).show();

                        newVehicle.setList(newVehicle);


                    }
                } catch (JSONException e) {
                    // if error, return 0 as false
                    e.printStackTrace();
                }
            }

            return null;
        }
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

            recyclerView.setAdapter(new MyRecyclerViewAdapter(VehicleUtil.VEHICLE_LIST, isHasTwoPane));

            //Toast.makeText(context, newURL, Toast.LENGTH_LONG).show();

        }
    }








    public static class MyRecyclerViewAdapter extends RecyclerView.Adapter <MyRecyclerViewAdapter.ViewHolder> {

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
                        int vehicleChoice = holder.getAdapterPosition();

                    }
                    else {*/
                        Context context = v.getContext();
                        Intent intent = new Intent(context, VehicleDetailActivity.class);
                        intent.putExtra(VehicleUtil.VEHICLE_ID_KEY, holder.getAdapterPosition());
                        context.startActivities(intent);
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
}
