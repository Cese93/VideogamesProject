package com.example.erik.videogamesproject;

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
    private Cart<Object> cart;

    public Cart<Object> getCart() {
        return cart;
    }

    public void setCart(Cart<Object> cart) {
        this.cart = cart;
    }


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
}
