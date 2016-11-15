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

    public Cart (FirebaseUser user){
        databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://videogamesproject-cfd9f.firebaseio.com/User/" + user.getDisplayName());
    }

    public void addProduct(final Object product, final String name){
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                databaseReference.child("Cart").child(name).setValue(product);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void deleteProduct(java.lang.Object product){
        //productCart.remove(product);
    }

    public ArrayList<Object> getCart(){
        final List<Object> productCart = new ArrayList<>();
        databaseReference.child("Cart").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot object : dataSnapshot.getChildren())
                {
                    productCart.add((Object) object.getValue());
                    Log.e("asdsadsad", String.valueOf(productCart.size()));
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return (ArrayList<Object>) productCart;
    }
}
