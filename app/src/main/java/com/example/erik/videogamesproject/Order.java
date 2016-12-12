package com.example.erik.videogamesproject;

import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

/**
 * Created by Erik on 09/12/2016.
 */

public class Order {
    private Map<String, Product> products;
    private String orderDate;
    private double total;

    public Map<String, Product> getProducts() {
        return products;
    }

    public void setProducts(Map<String, Product> products) {
        this.products = products;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
}
