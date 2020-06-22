package com.example.vehiclesearch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

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
        image = VehicleUtil.VEHICLE_LIST.get(getIntent()
                .getIntExtra(VehicleUtil.VEHICLE_IMAGE, 0));
        // Show the detail information in a TextView.

        /*if (savedInstanceState == null){
            int selectedSong = getIntent().getIntExtra(VehicleUtil.VEHICLE_IMAGE, 0);
            SongDetailFragment fragment = SongDetailFragment.newInstance(selectedSong);

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.song_detail_container, fragment).commit();
        }*/

        make_model = VehicleUtil.VEHICLE_LIST.get(getIntent()
                .getIntExtra(VehicleUtil.MAKE_MODEL, 0));


        /*if (savedInstanceState == null){
            int selectedMakeAndModel = getIntent().getIntExtra(VehicleUtil.MAKE_MODEL, 0);
            SongDetailFragment fragment = SongDetailFragment.newInstance(selectedMakeAndModel);

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.song_detail_container, fragment).commit();
        }*/

        price = VehicleUtil.VEHICLE_LIST.get(getIntent()
                .getIntExtra(VehicleUtil.PRICE, 0));


        /*if (savedInstanceState == null){
            int selectedPrice = getIntent().getIntExtra(VehicleUtil.PRICE, 0);
            SongDetailFragment fragment = SongDetailFragment.newInstance(selectedPrice);

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.song_detail_container, fragment).commit();
        }*/

        description = VehicleUtil.VEHICLE_LIST.get(getIntent()
                .getIntExtra(VehicleUtil.DESCRIPTION, 0));


        /*if (savedInstanceState == null){
            int selectedDescription = getIntent().getIntExtra(VehicleUtil.DESCRIPTION, 0);
            SongDetailFragment fragment = SongDetailFragment.newInstance(selectedDescription);

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.song_detail_container, fragment).commit();
        }*/

        last_update = VehicleUtil.VEHICLE_LIST
                .get(getIntent().getIntExtra(VehicleUtil.LAST_UPDATE, 0));


        if (savedInstanceState == null){
            int selectedLastUpdate = getIntent().getIntExtra(VehicleUtil.LAST_UPDATE, 0);
            int selectedDescription = getIntent().getIntExtra(VehicleUtil.DESCRIPTION, 0);
            int selectedPrice = getIntent().getIntExtra(VehicleUtil.PRICE, 0);
            int selectedMakeAndModel = getIntent().getIntExtra(VehicleUtil.MAKE_MODEL, 0);
            int selectedImage = getIntent().getIntExtra(VehicleUtil.VEHICLE_IMAGE, 0);

            SongDetailFragment fragment = SongDetailFragment.newInstance(selectedImage, selectedMakeAndModel,
                    selectedPrice, selectedDescription, selectedLastUpdate);

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.song_detail_container, fragment).commit();
        }

        /*if (mSong != null) {
            ((TextView) findViewById(R.id.song_detail))
                    .setText(mSong.details);
        }*/
    }



    /**
     * Performs action if the user selects the Up button.
     *
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
