package com.example.vehiclesearch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
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
import java.util.List;

public class MainActivity extends AppCompatActivity {

    //Context variable that will be storing the current context
    private Context context;

    //Spinner variable that will consist of the Make types of vehicles
    private Spinner makeSpin;

    //Spinner variable that will consist of the Model types of vehicles
    private Spinner modelSpin;

    //RecyclerView variable that will be used to display all the specific Make and Model vehicles that are available
    private RecyclerView recyclerView;

    //TextView variable that will display the Make and Model in our fragment
    private TextView makeAndModel;

    //TextView variable that will display the price of the specific vehicle in our fragment
    private TextView price;

    //TextView variable that will display the description of the specific vehicle in our fragment
    private TextView description;

    //TextView variable that will display the date of the last update of the specific vehicle in our fragment
    private TextView lastUpdate;

    //TextView variable that will display the image of the specific vehicle in our fragment
    private ImageView imageView;

    //Integer variable that will be used to concat the make ID into our URL
    private int makeID;

    //Integer variable that will be used to concat the modelID into our URL
    private int vModelID;

    //Boolean variable that will check the current orientation of the device
    private boolean isHasTwoPane = false;



    //Integer ArrayList that will contain all the vehicle IDs of the specific make. This is used in our 1st query
    private List<Integer> vehicleID;

    //String ArrayList that will contain all the vehicle make types. This is used in our 1st query
    private List<String> vehicleMake;   // for 1st query

    //Integer ArrayList that will contain all the vehicle model ID. This is used in our 2nd query
    private List<Integer> model_id;

    //String ArrayList that will contain all the vehicle model ID. This is used in our 2nd query
    private List<String> vehicleModel;

    //Integer ArrayList that will contain all the vehicle make ID. This is used in our 2nd query < OPTIONAL >
    private List<Integer> vehicle_make_id;

    //String variable that consist of the first API url which will contain a list of available cars
    String makeURL = "https://thawing-beach-68207.herokuapp.com/carmakes";

    //String variable that consist of the second API url which will contain a list of models for a specific make
    String modelURL = "https://thawing-beach-68207.herokuapp.com/carmodelmakes/";

    //String variable that consist of the third API url which will contain a list of models for a specific make, model ID, and zipcode
    String availableURL = "https://thawing-beach-68207.herokuapp.com/cars/";

    //Integer variable that will not be changed. This is used to grab the available vehicle based on the make, model ID, and zipcode
    int zipCode = 92603;

    //String variable that consist of the fourth API url which will contain a detailed description of the vehicle
    String vehicleDetail = "https://thawing-beach-68207.herokuapp.com/cars/";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Initialized the current application context to context variable
        context = getApplicationContext();

        //makeSpin will be binding the xml view into java
        makeSpin = findViewById(R.id.makeSpinner);

        //modelSpin will be binding the xml view into java
        modelSpin = findViewById(R.id.modelSpinner);

        //recyclerView will be binding the xml view into java
        recyclerView = findViewById(R.id.vehicle_list);

        //makeAndModel will be binding the xml view into java
        makeAndModel = findViewById(R.id.make_model);

        //price will be binding the xml view into java
        price = findViewById(R.id.price);

        //description will be binding the xml view into java
        description = findViewById(R.id.description);

        //lastUpdate will be binding the xml view into java
        lastUpdate = findViewById(R.id.last_update);

        //imageView will be binding the xml view into java
        imageView = findViewById(R.id.imageView);

        //If statement that checks if the rightPane is currently not null, will enter if it's not null
        if (findViewById(R.id.right_pane) != null) {
            //Initialized isHasTwoPane to true
            isHasTwoPane = true;
        }

        //Executes the GetMakes class
        new GetMakes().execute();
    }

    /**
     * An async task that will get the MAKE-ID and MAKE-NAME of the vehicles
     */
    private class GetMakes extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            //Creates an object of the HttpHandler class that will be handling the URLs of our api
            HttpHandler sh = new HttpHandler();

            //Initializes vehicleArrayList to a new ArrayList
            vehicleID = new ArrayList<>();

            //Initializes vehicleMake to a new ArrayList
            vehicleMake = new ArrayList<>();

            //Calls the makeServiceCall function from HttpHandler and sends it the makeURL (1st API)
            String jsonStr = sh.makeServiceCall(makeURL);

            //Try statement
            try {
                //Grabs the JSONArray
                JSONArray jsonArray = new JSONArray(jsonStr);

                //JSONObject variable that will be used to iterate to all the objects in the API
                JSONObject jsonObject;

                //For loop that loops until it reaches the end of the jsonArray
                for (int i = 0; i < jsonArray.length(); i++) {
                    //jsonObject is initialized to the current index of the jsonArray
                    jsonObject = jsonArray.getJSONObject(i);

                    //Grabs the ID from the jsonObject and adds it to the array list of vehicleID
                    int vID = jsonObject.getInt("id");
                    vehicleID.add(vID);

                    //Grabs the ID from the jsonObject and adds it to the array list of vehicleMake
                    String vMake = jsonObject.getString("vehicle_make");
                    vehicleMake.add(vMake);
                }
                //If the try does not work, it will do a catch
            } catch (JSONException e) {
                // if error, return 0 as false
                e.printStackTrace();
            }
            //returns null to the caller
            return null;
        }
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

            //ArrayAdapter that will be used to set the strings of the drop down menu for make
            ArrayAdapter<String> adapter = new ArrayAdapter<>(
                    context, R.layout.my_custom_dropdown, vehicleMake);

            //makeSpin will now consist of all the make types
            makeSpin.setAdapter(adapter);

            //Listener that will listen to the user input
            makeSpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                //Will enter this method if the user chooses something
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    //Grabs the vehicleID from the array list and stores it into makeID which will be used in the GetModel class
                    makeID = vehicleID.get(position);

                    //Executes the GetModel class
                    new GetModel().execute();

                }

                @Override
                //onNothingSelected will not contain any code
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        }

    }

    /**
     * An async task that will get the MODEL-ID, MODEL, and MAKE-ID of the vehicles
     */
    private class GetModel extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            HttpHandler sh = new HttpHandler();

            //Initializes modelID to a new ArrayList
            model_id = new ArrayList<>();

            //Initializes vehicleModel to a new ArrayList
            vehicleModel = new ArrayList<>();

            //Initializes vehicle_make_id to a new ArrayList
            vehicle_make_id = new ArrayList<>();

            //Concat the second api URL with the makeID
            String newURL = modelURL + makeID;

            //Calls the makeServiceCall function from HttpHandler and sends it the newly concatenated url
            String jsonStr = sh.makeServiceCall(newURL);

            //Try statement
            try {
                //Grabs the JSONArray
                JSONArray jsonArray = new JSONArray(jsonStr);

                //JSONObject variable that will be used to iterate to all the objects in the API
                JSONObject jsonObject;

                //For loop that loops until it reaches the end of the jsonArray
                for (int i = 0; i < jsonArray.length(); i++) {
                    //jsonObject is initialized to the current index of the jsonArray
                    jsonObject = jsonArray.getJSONObject(i);

                    //Grabs the mID from the jsonObject and adds it to the array list of model ID
                    int modelID = jsonObject.getInt("id");
                    model_id.add(modelID);

                    //Grabs the model from the jsonObject and adds it to the array list of vehicleModels
                    String vModel = jsonObject.getString("model");
                    vehicleModel.add(vModel);

                    //Grabs the vehicle make id from the jsonObject and adds it to the array list of vehicle_make_id
                    int vmID = jsonObject.getInt("vehicle_make_id");
                    vehicle_make_id.add(vmID);

                }
                //Catches if the try fails
            } catch (JSONException e) {
                // if error, return 0 as false
                e.printStackTrace();
            }

            //returns null to the caller
            return null;
        }
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

            //ArrayAdapter that will be used to set the strings of the drop down menu for model
            ArrayAdapter<String> adapter = new ArrayAdapter<>(
                    context, R.layout.my_custom_dropdown, vehicleModel);

            //makeSpin will now consist of all the model types for the specific make vehicle
            modelSpin.setAdapter(adapter);

            //Listener that will listen to the user input of the model spinner
            modelSpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                //Will enter this method if the user chooses an item from the model spinner
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    //vModelID integer variable will now be initialized to the model_id
                    vModelID = model_id.get(position);

                    //Executes the GetAvailableVehicle class
                    new GetAvailableVehicle().execute();
                }

                @Override
                //This method will not contain code
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        }

    }

    /**
     * An async task that will get the AVAILABLE-CAR base on the MAKE-ID / MODEL-ID / ZIPCODE of the vehicles
     */
    private class GetAvailableVehicle extends AsyncTask<Void, Void, Void> {

        //String variable that will consist of the new concatenated URL
        String newURL;

        //Method that will initialized the URL
        public GetAvailableVehicle(){

        }

        @Override
        protected Void doInBackground(Void... voids) {
            //Creates an object of the HttpHandler class that will be handling the URLs of our api
            HttpHandler sh = new HttpHandler();

            //Concatenate the URL with makeID, vModelID, and the zipcode
            newURL = availableURL + makeID + "/" + vModelID + "/" + zipCode;

            // <<<<< THIS WILL HOLD THE CURRENT SELECTION OF THE MAKE AND MODEL
            VehicleUtil.VEHICLE_LIST = new ArrayList<>();

            String jsonStr = sh.makeServiceCall(newURL);
            if (jsonStr != null) {

                try {
                    //JSONObject variable that is initialized to the JSONObject and as parameter the URL of the API
                    JSONObject jsonObjectList = new JSONObject(jsonStr);

                    //jsonArray is initialized to the object of the jsonArray "lists"
                    JSONArray jsonArray = jsonObjectList.getJSONArray("lists");

                    //jsonObject will be used to grab the information from the API
                    JSONObject jsonObject;

                    //For loop that loops until it reaches the end of the jsonArray
                    for (int i = 0; i < jsonArray.length(); i++) {
                        jsonObject = jsonArray.getJSONObject(i);

                        //Initializes the color from the jsonObject
                        String color = jsonObject.getString("color");

                        //Initializes the createdAt from the jsonObject
                        String create_at = jsonObject.getString("created_at");

                        //Initializes the ID from the jsonObject
                        int listID = jsonObject.getInt("id");

                        //Initializes the image url from the jsonObject
                        String image_url = jsonObject.getString("image_url");

                        //Initializes the mileage from the jsonObject
                        int mileage = jsonObject.getInt("mileage");

                        //Initializes the model from the jsonObject
                        String model = jsonObject.getString("model");

                        //Initializes the price from the jsonObject
                        int price = jsonObject.getInt("price");

                        //Initializes the veh_Description from the jsonObject
                        String veh_description = jsonObject.getString("veh_description");

                        //Initializes the vehicle_make from the jsonObject
                        String vehicle_make = jsonObject.getString("vehicle_make");

                        //Initializes the vehicle_url from the jsonObject
                        String vehicle_url = jsonObject.getString("vehicle_url");

                        //Initializes the vin_number from the jsonObject
                        String vin_number = jsonObject.getString("vin_number");

                        //Creates a new vehicle object that will have color, create_at, listID, image_url
                        //mileage, model, price, veh_description, vehicle_make, vehicle_url, vin_number
                        //as parameters
                        VehicleUtil.Vehicle newVehicle = new VehicleUtil.Vehicle(color, create_at, listID,
                                image_url, mileage, model, price, veh_description,
                                vehicle_make, vehicle_url, vin_number);

                        //Sets the new vehicle object
                        newVehicle.setList(newVehicle);

                    }
                    //Catches if it encounters an error
                } catch (JSONException e) {
                    // if error, return 0 as false
                    e.printStackTrace();
                }
            }

            return null;
        }
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

            //RecyclerView adapter will call the setAdapter function that will send in MyReclycerViewAdapter
            //and contain vehiclelist and a boolean variable as paramter
            recyclerView.setAdapter(new MyRecyclerViewAdapter(VehicleUtil.VEHICLE_LIST, isHasTwoPane));

            //Display a toast message of the URL
            //Toast.makeText(context, newURL, Toast.LENGTH_LONG).show();
        }
    }
}
