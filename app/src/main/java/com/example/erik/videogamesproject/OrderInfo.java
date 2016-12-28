package com.example.erik.videogamesproject;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by erik_ on 12/12/2016.
 */

public class OrderInfo extends Activity {
    private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;
    private FirebaseListAdapter<Product> productAdapter;
    ListView productsListView;
    TextView txtPaymentMethod;
    TextView txtName;
    TextView txtSurname;
    TextView txtState;
    TextView txtRegion;
    TextView txtCity;
    TextView txtAddress;
    TextView txtCAP;
    TextView txtTotalPrice;
    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_info_layout);

        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://videogamesproject-cfd9f.firebaseio.com/User/" + firebaseAuth.getCurrentUser().getDisplayName());

        productsListView = (ListView) findViewById(R.id.productsListView);
        txtPaymentMethod = (TextView) findViewById(R.id.txtPaymentMethod);
        txtName = (TextView) findViewById(R.id.txtName);
        txtSurname = (TextView) findViewById(R.id.txtSurname);
        txtState = (TextView) findViewById(R.id.txtState);
        txtRegion = (TextView) findViewById(R.id.txtRegion);
        txtCity = (TextView) findViewById(R.id.txtCity);
        txtAddress = (TextView) findViewById(R.id.txtAddress);
        txtCAP = (TextView) findViewById(R.id.txtCAP);
        txtTotalPrice = (TextView) findViewById(R.id.txtTotalPrice);


        position = getIntent().getExtras().getInt("POSITION");

        position++;

        productAdapter = new FirebaseListAdapter<Product>(this, Product.class, android.R.layout.two_line_list_item, databaseReference.child("Orders").child("orders").child("order " + position).child("products")) {
            @Override
            protected void populateView(View v, final Product model, int position) {
                TextView textView = (TextView) v.findViewById(android.R.id.text1);
                textView.setText(model.getName());
                TextView txtQuantity = (TextView) v.findViewById(android.R.id.text2);
                txtQuantity.setText("Quantità: " + String.valueOf(model.getQuantity()));
            }
        };

        databaseReference.child("Orders").child("orders").child("order " + position++).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                txtPaymentMethod.setText(dataSnapshot.child("holder").child("payments").child("paymentMethod").getValue().toString());
                txtName.setText(dataSnapshot.child("holder").child("name").getValue().toString());
                txtSurname.setText(dataSnapshot.child("holder").child("surname").getValue().toString());
                txtState.setText(dataSnapshot.child("holder").child("state").getValue().toString());
                txtRegion.setText(dataSnapshot.child("holder").child("region").getValue().toString());
                txtCity.setText(dataSnapshot.child("holder").child("city").getValue().toString());
                txtAddress.setText(dataSnapshot.child("holder").child("address").getValue().toString());
                txtCAP.setText(dataSnapshot.child("holder").child("cap").getValue().toString());
                txtTotalPrice.setText(dataSnapshot.child("total").getValue().toString() + "€");

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        productsListView.setAdapter(productAdapter);
    }
}
