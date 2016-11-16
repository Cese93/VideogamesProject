package com.example.erik.videogamesproject;

import android.util.Log;

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

public class Cart<Object> {
    DatabaseReference databaseReference;
    List<Object> productCart;
    List<Object> cart;

    public Cart(FirebaseUser user) {
        databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://videogamesproject-cfd9f.firebaseio.com/User/" + user.getDisplayName());
        productCart = new ArrayList<>();
    }

    public void addProduct(final Object product, final String name) {
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                databaseReference.child("Cart").child(name).setValue(product);
                productCart.add((Object) dataSnapshot.child("Cart").getValue());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void deleteProduct(java.lang.Object product) {
        //productCart.remove(product);
    }

    /*public ArrayList<Object> getCart() {
        databaseReference.child("Cart").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot object : dataSnapshot.getChildren()) {
                    productCart.add((Object) object.getValue());

                }
                Log.e("asdsadsadsadas", String.valueOf(productCart));
updateCart();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });
        return updateCart();
    }*/

    public ArrayList<Object> updateCart(){
        Log.e("qualcosa", String.valueOf(productCart));
        return (ArrayList<Object>) productCart;
    }

}
