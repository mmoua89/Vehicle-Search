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
 * Use the {@link SongDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SongDetailFragment extends Fragment {

    public VehicleUtil.Vehicle newVehicle;

    public SongDetailFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     *
     * @return A new instance of fragment SongDetailFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SongDetailFragment newInstance(int selectedImage, int selectedMakeAndModel,
                        int selectedPrice, int selectedDescription, int selectedLastUpdatee) {
        SongDetailFragment fragment = new SongDetailFragment();
        Bundle args = new Bundle();
        args.putInt(VehicleUtil.VEHICLE_IMAGE, selectedImage);
        args.putInt(VehicleUtil.MAKE_MODEL, selectedMakeAndModel);
        args.putInt(VehicleUtil.PRICE, selectedPrice);
        args.putInt(VehicleUtil.DESCRIPTION, selectedDescription);
        args.putInt(VehicleUtil.LAST_UPDATE, selectedLastUpdatee);

        fragment.setArguments(args);
        return fragment;
    }

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

    private ImageView imageView;
    private TextView make_model;
    private TextView price;
    private  TextView description;
    private TextView lastUpdate;
    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.vehicle_detail, container, false);

        imageView = rootView.findViewById(R.id.imageView);
        make_model = rootView.findViewById(R.id.make_model);
        price = rootView.findViewById(R.id.price);
        description = rootView.findViewById(R.id.description);
        lastUpdate = rootView.findViewById(R.id.last_update);

        new DownloadImageTask(imageView).execute(newVehicle.imageURL);

        make_model.setText(newVehicle.vehicleMake + ", " + newVehicle.model);
        price.setText(newVehicle.priceFormat());
        description.setText(newVehicle.vehDescription);
        lastUpdate.setText(newVehicle.createdAt);

        return rootView;
    }

    private static class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

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

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }


}
