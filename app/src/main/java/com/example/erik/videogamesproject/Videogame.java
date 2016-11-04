package com.example.erik.videogamesproject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Marco on 13/10/2016.
 */

public class Videogame implements Serializable {

    private String title;
    private String genres;
    private String developer;
    private String publishers;
    private ArrayList<String> platforms;
    private Date releaseDate;
    private double rating;
    private String plot;
    private double price;
    //private String description;
    private String trailer;
    private String image;
    private String imageTitle;

    public Videogame(){

    }


    public Videogame(String title, String genres, String developer, String publishers, ArrayList<String> platforms,
                     Date releaseDate, double rating, String plot, String trailer, String image, double price) {

        this.title = title;
        this.genres = genres;
        this.developer = developer;
        this.publishers = publishers;
        this.platforms = platforms;
        this.releaseDate = releaseDate;
        this.rating = rating;
        this.plot = plot;
        this.trailer = trailer;
        this.image = image;
        this.price = price;

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGenres() {
        return genres;
    }

    public void setGenres(String genres) {
        this.genres = genres;
    }

    public String getDeveloper() {
        return developer;
    }

    public void setDeveloper(String developer) {
        this.developer = developer;
    }

    public String getPublishers() {
        return publishers;
    }

    public void setPublishers(String publishers) {
        this.publishers = publishers;
    }

    public ArrayList<String> getPlatforms() {
        return platforms;
    }

    public void setPlatforms(ArrayList<String> platforms) {
        this.platforms = platforms;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getPlot() {
        return plot;
    }

    public void setPlot(String plot) {
        this.plot = plot;
    }

    public String getTrailer() {
        return trailer;
    }

    public void setTrailer(String trailer) {
        this.trailer = trailer;
    }

    public String getImage(){ return image; }

    public void setImage(String image) { this.image = image; }

    public void setPrice(double price){ this.price = price; }

    public double getPrice(){ return price; }

    public String getImageTitle() {
        return imageTitle;
    }

    public void setImageTitle(String imageTitle) {
        this.imageTitle = imageTitle;
    }



}
