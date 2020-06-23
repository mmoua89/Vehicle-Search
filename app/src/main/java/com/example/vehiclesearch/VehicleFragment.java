package com.example.vehiclesearch;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link VehicleFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class VehicleFragment extends Fragment {

    public VehicleUtil.Vehicle newVehicle;

    public VehicleFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @param selectedImage key for an image
     * @param selectedMakeAndModel key for make and model string
     * @param selectedPrice key for price
     * @param selectedDescription key for description
     * @param selectedLastUpdatee key for latest update
     * @return A new instance of fragment SongDetailFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static VehicleFragment newInstance(int selectedImage, int selectedMakeAndModel,
                                              int selectedPrice, int selectedDescription, int selectedLastUpdatee) {
        VehicleFragment fragment = new VehicleFragment();
        Bundle args = new Bundle();
        // set arguments for all keys
        args.putInt(VehicleUtil.VEHICLE_IMAGE, selectedImage);
        args.putInt(VehicleUtil.MAKE_MODEL, selectedMakeAndModel);
        args.putInt(VehicleUtil.PRICE, selectedPrice);
        args.putInt(VehicleUtil.DESCRIPTION, selectedDescription);
        args.putInt(VehicleUtil.LAST_UPDATE, selectedLastUpdatee);

        // set to Vehicle fragment
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * load all arguments for the Vehicle fragment
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        assert getArguments() != null;
        if (getArguments().containsKey(VehicleUtil.VEHICLE_IMAGE)) {
            newVehicle = VehicleUtil.VEHICLE_LIST.get(getArguments()
                    .getInt(VehicleUtil.VEHICLE_IMAGE));
        }

        if (getArguments().containsKey(VehicleUtil.MAKE_MODEL)) {
            newVehicle = VehicleUtil.VEHICLE_LIST.get(getArguments()
                    .getInt(VehicleUtil.MAKE_MODEL));
        }

        if (getArguments().containsKey(VehicleUtil.PRICE)) {
            newVehicle = VehicleUtil.VEHICLE_LIST.get(getArguments()
                    .getInt(VehicleUtil.PRICE));
        }

        if (getArguments().containsKey(VehicleUtil.DESCRIPTION)) {
            newVehicle = VehicleUtil.VEHICLE_LIST.get(getArguments()
                    .getInt(VehicleUtil.DESCRIPTION));
        }

        if (getArguments().containsKey(VehicleUtil.LAST_UPDATE)) {
            newVehicle = VehicleUtil.VEHICLE_LIST.get(getArguments()
                    .getInt(VehicleUtil.LAST_UPDATE));
        }
    }

    /**
     * Setter method that will set the correct data
     * to the correct destination
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.vehicle_detail, container, false);

        //imageView will be binding the xml view into java
        ImageView imageView = rootView.findViewById(R.id.imageView);

        //make_model will be binding the xml view into java
        TextView make_model = rootView.findViewById(R.id.make_model);

        //price will be binding the xml view into java
        TextView price = rootView.findViewById(R.id.price);

        //description will be binding the xml view into java
        TextView description = rootView.findViewById(R.id.description);

        //last update will be binding the xml view into java
        TextView lastUpdate = rootView.findViewById(R.id.last_update);

        //DownloadImageTask method will be used to correctly display the image of the vehicle in our fragment
        new DownloadImageTask(imageView).execute(newVehicle.imageURL);

        //Sets the make_model text box to the vehicle make and model type
        make_model.setText("Make: " + newVehicle.vehicleMake + ", Model: " + newVehicle.model);

        //Sets the price text box to the price of the vehicle
        price.setText(newVehicle.priceFormat());

        //Sets the description text box to the description of the vehicle
        description.setText(newVehicle.vehDescription);

        //Sets the last updated date on the the last updated TextBox of the vehicle
        lastUpdate.setText(newVehicle.createdAt);

        //returns rootView to the caller
        return rootView;
    }

    /**
     * A download class and method that will download an image from an url link
     */
    private static class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        /**
         * Downloading an image from a url link
         * @param urls an image url link
         * @return an image
         */
        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        /**
         * Post out an image as the result
         * @param result a Bitmap result
         */
        protected void onPostExecute(Bitmap result) {
            if (result != null){
                bmImage.setImageBitmap(result);
            }   // if not null, send the result to the caller
            else{
                bmImage.setImageResource(R.drawable.no_image);
            }   // the no image available if there's no image found

        }
    }


}
