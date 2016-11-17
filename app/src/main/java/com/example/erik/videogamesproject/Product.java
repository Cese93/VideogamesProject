package com.example.erik.videogamesproject;

import java.io.Serializable;

/**
 * Created by erik_ on 17/11/2016.
 */

public class Product implements Serializable{
    private String name;
    private double price;
    private double rating;
    private String image;
    private String imageTitle;


    public Product() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getImageTitle() {
        return imageTitle;
    }

    public void setImageTitle(String imageTitle) {
        this.imageTitle = imageTitle;
    }


}
