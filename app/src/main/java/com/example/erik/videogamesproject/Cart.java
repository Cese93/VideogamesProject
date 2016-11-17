package com.example.erik.videogamesproject;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by erik_ on 10/11/2016.
 */

public class Cart<Product> {
    DatabaseReference databaseReference;
    List<Product> productCart;
    double totalPrice;

    public Cart(FirebaseUser user) {
        databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://videogamesproject-cfd9f.firebaseio.com/User/" + user.getDisplayName());
        productCart = new ArrayList<>();
    }

    public void addProduct(final Product product, final String name, final int quantity, double price) {
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                databaseReference.child("Cart").child(name).setValue(product);
                databaseReference.child("Cart").child(name).child("quantity").setValue(quantity);
                productCart.add((Product) dataSnapshot.child("Cart").getValue());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });
        totalPrice +=  quantity * price;
    }

    public double getTotal(){
        return totalPrice;
    }

    public void deleteProduct(java.lang.Object product) {
        //productCart.remove(product);
    }

}
