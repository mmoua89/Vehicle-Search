package com.example.vehiclesearch;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.Format;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

public class VehicleUtil {

    public static List<Vehicle> VEHICLE_LIST = new ArrayList<>();

    public static String VEHICLE_ID_KEY = "TESTING";

    public static class Vehicle{

        String color, createdAt, imageURL, model, vehDescription,
                vehicleMake, vehicleURL, vinNumber;
        int ID, mileage, price;

        public Vehicle(){}

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

        public void setList(Vehicle list){
            VEHICLE_LIST.add(list);
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
