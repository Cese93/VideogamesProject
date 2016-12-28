package com.example.erik.videogamesproject.Model;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by Erik on 30/11/2016.
 */

public class Card implements Serializable {
    private String paymentMethod;
    private int code;
    private int pin;
    private Map<String, Integer> expiredDate;

    public Card () {

    }

    public String getPaymentMethod () {
        return paymentMethod;
    }

    public void setPaymentMethod ( String paymentMethod ) {
        this.paymentMethod = paymentMethod;
    }

    public int getPin () { return pin; }

    public void setPin ( int pin ) {
        this.pin = pin;
    }

    public int getCode () {
        return code;
    }

    public void setCode ( int code ) {
        this.code = code;
    }

    public Map<String, Integer> getExpiredDate () {
        return expiredDate;
    }

    public void setExpiredDate ( Map<String, Integer> expiredDate ) {
        this.expiredDate = expiredDate;
    }

}
