package com.example.erik.videogamesproject;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by erik_ on 12/12/2016.
 */

public class OrderInfo extends Activity {
    private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;
    private FirebaseListAdapter<Product> productAdapter;
    ListView productsListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_info_layout);

        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://videogamesproject-cfd9f.firebaseio.com/User/" + firebaseAuth.getCurrentUser().getDisplayName());

        productsListView = (ListView) findViewById(R.id.productsListView);

        /*productAdapter = new FirebaseListAdapter<Product>(getApplicationContext(), Product.class, android.R.layout.two_line_list_item, databaseReference.child("Orders").child("orders").child("order 1").child("products")) {
            @Override
            protected void populateView(View v, final Product model, int position) {
                TextView textView = (TextView) v.findViewById(android.R.id.text1);
                textView.setText(model.getName());
                TextView txtQuantity = (TextView) v.findViewById(android.R.id.text2);
                txtQuantity.setText("Quantità: " + String.valueOf(model.getQuantity()));

            }
        };*/

        productsListView.setAdapter(productAdapter);
    }
}
