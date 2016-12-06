package com.example.erik.videogamesproject;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Erik on 30/11/2016.
 */

public class Card implements Serializable{
    private String paymentMethod;
    private int code;
    private int pin;
    private ArrayList<String> expiredDate;

    public Card(){

    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }


    public int getPin() {
        return pin;
    }

    public void setPin(int pin) {
        this.pin = pin;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public ArrayList<String> getExpiredDate() {
        return expiredDate;
    }

    public void setExpiredDate(ArrayList<String> expiredDate) {
        this.expiredDate = expiredDate;
    }

}
