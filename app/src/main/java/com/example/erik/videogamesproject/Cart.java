package com.example.erik.videogamesproject;

import android.util.Log;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by erik_ on 10/11/2016.
 */

public class Cart {

    DatabaseReference databaseReference;
    double totalPrice;

    public Cart(FirebaseUser user) {
        databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://videogamesproject-cfd9f.firebaseio.com/User/" + user.getDisplayName());
    }

    public void addProduct(final Product product, final String name, final int quantity, final double price) {
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {

            private double totalPrice;


            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                databaseReference.child("Cart").child("Cart").child(name).setValue(product);
                databaseReference.child("Cart").child("Cart").child(name).child("quantity").setValue(quantity);

                if(dataSnapshot.child("Cart").child("totalPrice").getValue() == null){

                    double totalPriceCart = (price*quantity);

                    databaseReference.child("Cart").child("totalPrice").setValue(totalPriceCart);

                }else {



                    totalPrice = dataSnapshot.child("Cart").child("totalPrice").getValue(Double.class);

                    double totalPriceCart =((totalPrice) + (price*quantity));
                    double totalPrice = Math.round(totalPriceCart*100.0)/100.0;

                    databaseReference.child("Cart").child("totalPrice").setValue(totalPrice);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });

    }


    public void deleteProduct(final Product product) {


        databaseReference.child("Cart").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if(dataSnapshot.child("Cart").child(product.getName()).child("quantity").getValue() == null){

                    return;

                } else if(dataSnapshot.child("Cart").child(product.getName()).child("quantity").getValue(Integer.class) <= 1){

                    databaseReference.child("Cart").child("Cart").child(product.getName()).removeValue();

                }else{

                    databaseReference.child("Cart").child("Cart").child(product.getName())
                            .child("quantity").setValue(dataSnapshot.child("Cart")
                               .child(product.getName()).child("quantity").getValue(Integer.class)-1);

                }
                double totalPriceDecrease = dataSnapshot
                        .child("totalPrice").getValue(Double.class)-product.getPrice();
                 totalPriceDecrease = Math.round(totalPriceDecrease*100.0)/100.0;
                databaseReference.child("Cart").child("totalPrice").setValue(totalPriceDecrease);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

}
