package com.example.vehiclesearch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

public class VehicleDetailActivity extends AppCompatActivity {

    public VehicleUtil.Vehicle image;
    public VehicleUtil.Vehicle make_model;
    public VehicleUtil.Vehicle price;
    public VehicleUtil.Vehicle description;
    public VehicleUtil.Vehicle last_update;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicle_detail);

        // This activity displays the detail. In a real-world scenario,
        // get the data from a content repository.

        //Grabs the image information that was sent from the Adapter
        image = VehicleUtil.VEHICLE_LIST.get(getIntent()
                .getIntExtra(VehicleUtil.VEHICLE_IMAGE, 0));

        //Grabs the make and model information that was sent from the Adapter
        make_model = VehicleUtil.VEHICLE_LIST.get(getIntent()
                .getIntExtra(VehicleUtil.MAKE_MODEL, 0));

        //Grabs the price information that was sent from the Adapter
        price = VehicleUtil.VEHICLE_LIST.get(getIntent()
                .getIntExtra(VehicleUtil.PRICE, 0));

        //Grabs the description information that was sent from the Adapter
        description = VehicleUtil.VEHICLE_LIST.get(getIntent()
                .getIntExtra(VehicleUtil.DESCRIPTION, 0));

        //Grabs the last updated information that was sent from the Adapter
        last_update = VehicleUtil.VEHICLE_LIST
                .get(getIntent().getIntExtra(VehicleUtil.LAST_UPDATE, 0));

        if (savedInstanceState == null){
            int selectedLastUpdate = getIntent().getIntExtra(VehicleUtil.LAST_UPDATE, 0);
            int selectedDescription = getIntent().getIntExtra(VehicleUtil.DESCRIPTION, 0);
            int selectedPrice = getIntent().getIntExtra(VehicleUtil.PRICE, 0);
            int selectedMakeAndModel = getIntent().getIntExtra(VehicleUtil.MAKE_MODEL, 0);
            int selectedImage = getIntent().getIntExtra(VehicleUtil.VEHICLE_IMAGE, 0);

            // Creates a new instance of the VehicleFragment that will be used to display the image,
            // make and model type, price, description and last update
            VehicleFragment fragment = VehicleFragment.newInstance(selectedImage, selectedMakeAndModel,
                    selectedPrice, selectedDescription, selectedLastUpdate);

            //Initiates the fragment
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.song_detail_container, fragment).commit();
        }
    }

    /**
     * Performs action if the user selects the Up button.
     * @param item Menu item selected (Up button)
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            // This ID represents the Home or Up button. In the case of this
            // activity, the Up button is shown.
            // NavUtils allows users to navigate up one level in the
            // application structure.
            NavUtils.navigateUpTo(this, new Intent(this, MainActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
