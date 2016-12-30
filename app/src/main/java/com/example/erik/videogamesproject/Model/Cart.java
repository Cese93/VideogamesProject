package com.example.erik.videogamesproject.Model;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import com.example.erik.videogamesproject.Adapter.MapAdapter;
import com.example.erik.videogamesproject.R;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


/**
 * Created by erik_ on 10/11/2016.
 */

public class Cart {

    DatabaseReference databaseReference;


    public Cart ( FirebaseUser user ) {
        databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://videogamesproject-cfd9f.firebaseio.com/User/" + user.getDisplayName());
    }

    public void addProduct (final Product product,final int quantity) {
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {

            private double totalPrice;

            @Override
            public void onDataChange ( DataSnapshot dataSnapshot ) {

                if (dataSnapshot.child("Cart").child("Cart").hasChild(product.getName())) {

                    databaseReference.child("Cart").child("Cart").child(product.getName()).child("quantity")
                            .setValue(dataSnapshot.child("Cart").child("Cart").child(product.getName()).child("quantity")
                                    .getValue(Integer.class) + quantity);


                    totalPrice = dataSnapshot.child("Cart").child("totalPrice").getValue(Double.class);
                    setTotalPrice(totalPrice, product.getPrice(), quantity);


                } else {

                    databaseReference.child("Cart").child("Cart").child(product.getName()).setValue(product);
                    databaseReference.child("Cart").child("Cart").child(product.getName()).child("quantity").setValue(quantity);


                    if (dataSnapshot.child("Cart").child("totalPrice").getValue() == null) {

                        double totalPriceCart = (product.getPrice() * quantity);

                        databaseReference.child("Cart").child("totalPrice").setValue(totalPriceCart);

                    } else {


                        totalPrice = dataSnapshot.child("Cart").child("totalPrice").getValue(Double.class);
                        setTotalPrice(totalPrice, product.getPrice(), quantity);
                    }
                }
            }

            @Override
            public void onCancelled ( DatabaseError databaseError ) {

            }

        });

    }

    public void deleteProduct ( final Product product, final Context context, final LayoutInflater inflater ) {

        databaseReference.child("Cart").addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange ( final DataSnapshot dataSnapshot ) {

                if (Integer.parseInt(dataSnapshot.child("Cart").child(product.getName()).child("quantity").getValue().toString()) == 0 ||
                        dataSnapshot.child("Cart").child(product.getName()).child("quantity").getValue(Integer.class) <= 0) {

                    databaseReference.child("Cart").child("Cart").child(product.getName()).removeValue();

                } else if (dataSnapshot.child("Cart").child(product.getName()).hasChild("platforms")) {

                        final View alertLayout = inflater.inflate(R.layout.alertdialog_videogame, null);
                        final AlertDialog.Builder alertProduct = new AlertDialog.Builder(context)
                                .setTitle("Modifica la quantità");

                        alertProduct.setView(alertLayout);

                        final MapAdapter adapter = new MapAdapter((Map<String, Integer>) dataSnapshot.child("Cart").child(product.getName())
                                .child("platforms").getValue());

                        alertProduct.setAdapter(adapter, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick ( final DialogInterface dialog, int which ) {

                            }
                        });

                        alertProduct.setPositiveButton("Conferma", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick ( final DialogInterface dialog, int which ) {

                        Map<String, Integer> mapToPut = adapter.getMap();

                        databaseReference.child("Cart").child("Cart").child(product.getName())
                                .child("platforms").setValue(mapToPut);

                        double totalPrice = dataSnapshot.child("totalPrice").getValue(Double.class);
                        int quantity = dataSnapshot.child("Cart")
                                .child(product.getName()).child("quantity").getValue(Integer.class);

                        if ((adapter.getCountQuantity() - quantity) <= 0) {

                            int quantityToAdd = adapter.getCountQuantity() - quantity;
                            setTotalPrice(totalPrice, product.getPrice(), quantityToAdd);

                            databaseReference.child("Cart").child("Cart").child(product.getName())
                                    .child("quantity").setValue(adapter.getCountQuantity());

                            dialog.dismiss();

                        } else {

                            int quantityToDelete = quantity - adapter.getCountQuantity();
                            decreaseTotalPrice(totalPrice, product.getPrice(), quantityToDelete);

                            databaseReference.child("Cart").child("Cart").child(product.getName())
                                    .child("quantity").setValue(adapter.getCountQuantity());

                            dialog.dismiss();

                        }
                    }
                });

                alertProduct.show();

            } else {


                databaseReference.child("Cart").child("Cart").child(product.getName())
                        .child("quantity").setValue(dataSnapshot.child("Cart")
                        .child(product.getName()).child("quantity").getValue(Integer.class) - 1);

                double totalPriceDecrease = dataSnapshot
                        .child("totalPrice").getValue(Double.class) - product.getPrice();
                totalPriceDecrease = Math.round(totalPriceDecrease * 100.0) / 100.0;
                databaseReference.child("Cart").child("totalPrice").setValue(totalPriceDecrease);


            }

        }

        @Override
        public void onCancelled ( DatabaseError databaseError ) {

        }
        });

    }

    public void setProductVideogame (final Videogame videogame, final String platform, final int quantity ) {

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {

            private double totalPrice;

            @Override
            public void onDataChange ( DataSnapshot dataSnapshot ) {

                if (!dataSnapshot.child("Cart").child("Cart").hasChild(videogame.getName())) {

                    HashMap<String, Integer> mapToCart = new HashMap<>();

                    mapToCart.put(platform, quantity);

                    databaseReference.child("Cart").child("Cart").child(videogame.getName()).setValue(videogame);
                    databaseReference.child("Cart").child("Cart").child(videogame.getName()).child("platforms").setValue(mapToCart);
                    databaseReference.child("Cart").child("Cart").child(videogame.getName()).child("quantity").setValue(quantity);


                    if (dataSnapshot.child("Cart").child("totalPrice").getValue() == null) {

                        double totalPriceCart = (videogame.getPrice() * quantity);

                        databaseReference.child("Cart").child("totalPrice").setValue(totalPriceCart);

                    } else {

                        totalPrice = dataSnapshot.child("Cart").child("totalPrice").getValue(Double.class);
                        setTotalPrice(totalPrice, videogame.getPrice(), quantity);

                    }
                } else {

                    databaseReference.child("Cart").child("Cart").child(videogame.getName()).child("quantity")
                            .setValue(dataSnapshot.child("Cart").child("Cart").child(videogame.getName()).child("quantity")
                                    .getValue(Integer.class) + quantity);

                    HashMap<String, Integer> mapToCart = (HashMap<String, Integer>) dataSnapshot.child("Cart").child("Cart")
                            .child(videogame.getName()).child("platforms").getValue();

                    Iterator iterator = mapToCart.entrySet().iterator();
                    int totalQuantity = quantity;
                    while (iterator.hasNext()) {

                        Map.Entry entry = (Map.Entry) iterator.next();

                        if (entry.getKey().equals(platform)) {

                            totalQuantity = (Integer.parseInt(entry.getValue().toString()) + quantity);
                        }
                    }

                    mapToCart.put(platform, totalQuantity);

                    databaseReference.child("Cart").child("Cart").child(videogame.getName())
                            .child("platforms").setValue(mapToCart);
                    totalPrice = dataSnapshot.child("Cart").child("totalPrice").getValue(Double.class);
                    setTotalPrice(totalPrice, videogame.getPrice(), quantity);

                }
            }

            @Override
            public void onCancelled ( DatabaseError databaseError ) {

            }

        });
    }

    public void setTotalPrice ( double totalPrice, double price, int quantity ) {


        double totalPriceCart = ((totalPrice) + (price * quantity));
        double formatTotalPrice = Math.round(totalPriceCart * 100.0) / 100.0;

        databaseReference.child("Cart").child("totalPrice").setValue(formatTotalPrice);

    }

    public void decreaseTotalPrice ( double totalPrice, double price, int quantity ) {

        double totalPriceCart = ((totalPrice) - (price * quantity));
        double formatTotalPrice = Math.round(totalPriceCart * 100.0) / 100.0;

        databaseReference.child("Cart").child("totalPrice").setValue(formatTotalPrice);

    }

}
