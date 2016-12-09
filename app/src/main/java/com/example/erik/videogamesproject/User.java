package com.example.erik.videogamesproject;

import java.util.ArrayList;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;

/**
 * Created by Marco on 16/10/2016.
 */

public class User {

    private String name;
    private String surname;
    private String email;
    private String password;
    private String username;
    private String state;
    private String region;
    private String city;
    private String address;
    private String streetNumber;
    private int CAP;
    private Card payments;


    User(){}

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername(){ return username; }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUsername(String username) { this.username = username; }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getStreetNumber() {
        return streetNumber;
    }

    public void setStreetNumber(String streetNumber) {
        this.streetNumber = streetNumber;
    }

    public int getCAP() {
        return CAP;
    }

    public void setCAP(int CAP) {
        this.CAP = CAP;
    }

    public Card getPayments() {
        return payments;
    }

    public void setPayments(Card payments) {
        this.payments = payments;
    }

}
