package com.example.finalprojectandroid1;

public class RestaurantModelClass {

    String address;
    String foodType;
    //add more later
    String currAddress;

    String id;


    public RestaurantModelClass(String address, String foodType, String id, String currAddress) {
        this.address = address;
        this.foodType = foodType;
        this.currAddress = currAddress;

        this.id = id;
    }

    public RestaurantModelClass() {
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getFoodType() {
        return foodType;
    }

    public void setFoodType(String foodType) {
        this.foodType = foodType;
    }


    public void setCurrAddress(String currAddress) {
        this.currAddress = currAddress;
    }

    public String getCurrAddress() {
        return currAddress;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
