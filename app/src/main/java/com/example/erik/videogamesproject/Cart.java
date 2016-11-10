package com.example.erik.videogamesproject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by erik_ on 10/11/2016.
 */

public class Cart {
    List<Object> productCart;

    public Cart (){
        this.productCart = new ArrayList<>();
    }

    public void addProduct(Object product){
        productCart.add(product);
    }

    public void deleteProduct(Object product){
        productCart.remove(product);
    }

    public ArrayList<Object> getCart(){
        return (ArrayList<Object>) productCart;
    }
}
