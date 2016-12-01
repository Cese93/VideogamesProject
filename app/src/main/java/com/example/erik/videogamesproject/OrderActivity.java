package com.example.erik.videogamesproject;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Date;

/**
 * Created by erik_ on 22/11/2016.
 */

public class OrderActivity extends AppCompatActivity {
    private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;
    private LinearLayout shippingInfoPanel;
    private RadioGroup radioGroupPayments;
    private TextView txtCode;
    private TextView txtPIN;
    private TextView txtExpiredDate;
    private TextView txtName;
    private TextView txtSurname;
    private TextView txtState;
    private TextView txtRegion;
    private TextView txtCity;
    private TextView txtAddress;
    private TextView txtStreetNumber;
    private TextView txtCAP;
    private TextView txtTotalPrice;
    private Button btnProceedToRecap;

    String paymentMethod;
    int code;
    int pin;
    Date expiredDate;
    String name;
    String surname;
    String state;
    String region;
    String city;
    String address;
    String streetNumber;
    String cap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://videogamesproject-cfd9f.firebaseio.com/User/" + firebaseAuth.getCurrentUser().getDisplayName());

        shippingInfoPanel = (LinearLayout) findViewById(R.id.shippingInfoPanel);
        radioGroupPayments = (RadioGroup) findViewById(R.id.radioPayments);
        txtCode = (TextView) findViewById(R.id.txtCode);
        txtPIN = (TextView) findViewById(R.id.txtPIN);
        txtExpiredDate = (TextView) findViewById(R.id.txtExpiredDate);
        txtName = (TextView) findViewById(R.id.txtName);
        txtSurname = (TextView) findViewById(R.id.txtSurname);
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
                View radioButton = radioGroupPayments.findViewById(checkedId);
                RadioButton btn = (RadioButton) radioGroupPayments.getChildAt(radioGroupPayments.indexOfChild(radioButton));
                paymentMethod = (String) btn.getText();
            }
        });

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                txtName.setText(dataSnapshot.child("name").getValue().toString());
                txtSurname.setText(dataSnapshot.child("surname").getValue().toString());
                txtState.setText(dataSnapshot.child("state").getValue().toString());
                txtRegion.setText(dataSnapshot.child("region").getValue().toString());
                txtCity.setText(dataSnapshot.child("city").getValue().toString());
                txtAddress.setText(dataSnapshot.child("address").getValue().toString());
                txtStreetNumber.setText(dataSnapshot.child("streetNumber").getValue().toString());
                txtCAP.setText(dataSnapshot.child("cap").getValue().toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



        btnProceedToRecap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                code = Integer.parseInt(txtCode.getText().toString().trim());
                pin = Integer.parseInt(txtPIN.getText().toString().trim());
                name = txtName.getText().toString().trim();
                surname = txtSurname.getText().toString().trim();
                state = txtState.getText().toString().trim();
                region = txtRegion.getText().toString().trim();
                city = txtCity.getText().toString().trim();
                address = txtAddress.getText().toString().trim();
                streetNumber = txtStreetNumber.getText().toString().trim();
                cap = txtCAP.getText().toString().trim();
                if (state.isEmpty()) {
                    txtState.setError("Inserire lo stato");
                    return;
                }
                if (region.isEmpty()) {
                    txtRegion.setError("Inserire la regione");
                    return;
                }
                if (city.isEmpty()) {
                    txtCity.setError("Inserire la città");
                    return;

                }
                if (address.isEmpty()) {
                    txtAddress.setError("Inserire l'indirizzo");
                    return;

                }
                if (streetNumber.isEmpty()) {
                    txtStreetNumber.setError("Inserire il numero civico");
                    return;
                }
                if (cap.isEmpty()) {
                    txtCAP.setError("Inserire il CAP");
                    return;
                }

                Card paymentCard = new Card();
                paymentCard.setPaymentMethod(paymentMethod);
                paymentCard.setCode(code);
                paymentCard.setPin(pin);
                //paymentCard.setExpiredDate();
                //databaseReference.child("payments").setValue();
                databaseReference.child("state").setValue(state);
                databaseReference.child("region").setValue(region);
                databaseReference.child("city").setValue(city);
                databaseReference.child("address").setValue(address);
                databaseReference.child("streetNumber").setValue(streetNumber);
                databaseReference.child("cap").setValue(cap);

            }
        });

    }



}

