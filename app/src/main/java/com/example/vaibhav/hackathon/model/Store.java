package com.example.vaibhav.hackathon.model;

/**
 * Created by Vaibhav Savla on 07/05/16.
 */
public class Store {
    public int id;
    public String brandName;
    public double latitude;
    public double longitude;
    public String category;

    public Store(int id, String brandName, double latitude, double longitude, String category) {
        this.id = id;
        this.brandName = brandName;
        this.latitude = latitude;
        this.longitude = longitude;
        this.category = category;
    }
}
