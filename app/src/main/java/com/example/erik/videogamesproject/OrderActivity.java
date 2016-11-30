package com.example.erik.videogamesproject;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.text.TextDirectionHeuristicCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

/**
 * Created by erik_ on 22/11/2016.
 */

public class OrderActivity extends AppCompatActivity {
    private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;
    private LinearLayout shippingInfoPanel;
    private RadioGroup radioGroupPayments;
    private TextView txtState;
    private TextView txtRegion;
    private TextView txtCity;
    private TextView txtAddress;
    private TextView txtStreetNumber;
    private TextView txtCAP;
    private TextView txtTotalPrice;
    private Button btnProceedToRecap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://videogamesproject-cfd9f.firebaseio.com/User/" + firebaseAuth.getCurrentUser().getDisplayName());

        shippingInfoPanel = (LinearLayout) findViewById(R.id.shippingInfoPanel);
        radioGroupPayments = (RadioGroup) findViewById(R.id.radioPayments);
        txtState = (TextView) findViewById(R.id.txtState);
        txtRegion = (TextView) findViewById(R.id.txtRegion);
        txtCity = (TextView) findViewById(R.id.txtCity);
        txtAddress = (TextView) findViewById(R.id.txtAddress);
        txtStreetNumber = (TextView) findViewById(R.id.txtStreetNumber);
        txtCAP = (TextView) findViewById(R.id.txtCAP);
        txtTotalPrice = (TextView) findViewById(R.id.txtTotalPrice);
        btnProceedToRecap = (Button) findViewById(R.id.btnProceedToRecap);


        txtTotalPrice.setText(getIntent().getExtras().getString("TOTAL") + "€");

        radioGroupPayments.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                shippingInfoPanel.setVisibility(View.VISIBLE);
            }
        });

        btnProceedToRecap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseReference.child("state").setValue(txtState.getText().toString().trim());
                databaseReference.child("region").setValue(txtRegion.getText().toString().trim());
                databaseReference.child("city").setValue(txtCity.getText().toString().trim());
                databaseReference.child("address").setValue(txtAddress.getText().toString().trim());
                databaseReference.child("streetNumber").setValue(txtStreetNumber.getText().toString().trim());
                databaseReference.child("cap").setValue(txtCAP.getText().toString().trim());
            }
        });

    }

}

