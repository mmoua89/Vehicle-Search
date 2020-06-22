package com.example.vehiclesearch;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.Format;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

public class VehicleUtil {

    public static List<Vehicle> VEHICLE_LIST;

    public static final String VEHICLE_IMAGE = "imageView";
    public static final String MAKE_MODEL = "make_model";
    public static final String PRICE = "price";
    public static final String DESCRIPTION = "description";
    public static final String LAST_UPDATE = "last_update";


    public static class Vehicle{

        String color, createdAt, imageURL, model, vehDescription,
                vehicleMake, vehicleURL, vinNumber;
        int ID, mileage, price;

        public Vehicle(){
            VEHICLE_LIST = new ArrayList<>();
        }

        public Vehicle(String color, String createdAt, int listID, String image_url,
                          int mileage, String model, int price, String veh_description,
                          String vehicle_make, String vehicle_url, String vin_number){
            this.color = color;
            this.createdAt = createdAt;
            this.imageURL = image_url;
            this.model = model;
            this.vehDescription = veh_description;
            this.vehicleMake = vehicle_make;
            this.vehicleURL = vehicle_url;
            this.vinNumber = vin_number;
            this.ID = listID;
            this.mileage = mileage;
            this.price = price;

        }

        public void setList(List<Vehicle> list){
            VEHICLE_LIST = list;
        }

        public String getDisplayBar(){
            NumberFormat priceFormat = NumberFormat.getCurrencyInstance();
            DecimalFormatSymbols setter = new DecimalFormatSymbols();
            setter.setCurrencySymbol("$");
            setter.setGroupingSeparator(',');
            setter.setMonetaryDecimalSeparator('.');
            ((DecimalFormat) priceFormat ).setDecimalFormatSymbols(setter);
            return vehicleMake + ", " + model + ", " + color + "\n" + priceFormat.format(price);
        }
    }


}
