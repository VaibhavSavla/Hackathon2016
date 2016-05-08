package com.example.vaibhav.hackathon.model;

import java.util.ArrayList;

/**
 * Created by Vaibhav Savla on 07/05/16.
 */
public class Offer {
    public int id;
    public Store store;
    public String offerTitle;
    public String offerDescription;
    public String offerTerms;
    public ArrayList<String> reviews;
    public String expiryDate;
    public int count;
    public double distance;

    public Offer(int id, Store store, String offerTitle, String offerDescription, String offerTerms, ArrayList<String> reviews, String expiryDate, int count, double distance) {
        this.id = id;
        this.store = store;
        this.offerTitle = offerTitle;
        this.offerDescription = offerDescription;
        this.offerTerms = offerTerms;
        this.reviews = reviews;
        this.expiryDate = expiryDate;
        this.count = count;
        this.distance = distance;
    }
}
