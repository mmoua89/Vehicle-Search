package com.example.vehiclesearch;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;

@SuppressLint("Registered")
class VehicleDetailActivity extends AppCompatActivity {
    public VehicleUtil.Vehicle newVehicle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vehicle_detail);
        /*Toolbar toolbar =  findViewById(R.id.detail_toolbar);
        setSupportActionBar(toolbar);

        // Show the Up button in the action bar.
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }*/

        // This activity displays the detail. In a real-world scenario,
        // get the data from a content repository.
        /*newVehicle = VehicleUtil.VEHICLE_LIST.get(getIntent().getIntExtra(xxxxx, 0));
        // Show the detail information in a TextView.

        if (savedInstanceState == null){
            int selectedSong = getIntent().getIntExtra(SongUtils.SONG_ID_KEY, 0);
            SongDetailFragment fragment = SongDetailFragment.newInstance(selectedSong);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.song_detail_container, fragment).commit();
        }*/
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
