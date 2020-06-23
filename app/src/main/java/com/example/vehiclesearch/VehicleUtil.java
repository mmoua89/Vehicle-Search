package com.example.vehiclesearch;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.List;

public class VehicleUtil {
    // A list of vehicle object
    public static List<Vehicle> VEHICLE_LIST;

    // Alls key for the TextViews and ImageView
    public static final String VEHICLE_IMAGE = "imageView";
    public static final String MAKE_MODEL = "make_model";
    public static final String PRICE = "price";
    public static final String DESCRIPTION = "description";
    public static final String LAST_UPDATE = "last_update";

    //Default constructor that creates an object of VehicleUtil
    public VehicleUtil(){
        new VehicleUtil();
    }

    /**
     * A class of vehicle
     */
    public static class Vehicle{

        //Variables that will consist of all the information regarding the vehicle
        String color, createdAt, imageURL, model, vehDescription,
                vehicleMake, vehicleURL, vinNumber;
        int ID, mileage, price;

        //Default constructor, spare constructor
        public Vehicle(){
        }

        //Constructor that will be used to initialized all the information that was sent from the user
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

        //Setter function that will set the current list
        public void setList(Vehicle list){
            VEHICLE_LIST.add(list);
        }

        //Price format function that will correctly format the price for our fragment
        public String priceFormat(){
            NumberFormat priceFormat = NumberFormat.getCurrencyInstance();
            DecimalFormatSymbols setter = new DecimalFormatSymbols();
            setter.setCurrencySymbol("$");
            setter.setGroupingSeparator(',');
            setter.setMonetaryDecimalSeparator('.');
            ((DecimalFormat) priceFormat ).setDecimalFormatSymbols(setter);
            return priceFormat.format(price);
        }

        //Price format function that will set the correct format for our display bar
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
