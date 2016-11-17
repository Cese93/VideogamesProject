package com.example.erik.videogamesproject;

import java.io.Serializable;

/**
 * Created by Andrea on 10/11/2016.
 */

public class Accessory extends Product implements Serializable {

    private String producer;
    private String features;

    public Accessory() {
        super();
    }

    public String getProducer() {
        return producer;
    }

    public void setProducer(String producer) {
        this.producer = producer;
    }

    public String getFeatures() {
        return features;
    }

    public void setFeatures(String features) {
        this.features = features;
    }
}
