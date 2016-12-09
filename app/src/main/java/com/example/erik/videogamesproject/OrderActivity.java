package com.example.erik.videogamesproject;


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private TextView txtMonthExpiredDate;
    private TextView txtYearExpiredDate;
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

    private String paymentMethod;
    private String code;
    private String pin;
    private String monthExpiredDate;
    private String yearExpiredDate;
    private Map<String, Integer> expiredDate;
    private String name;
    private String surname;
    private String state;
    private String region;
    private String city;
    private String address;
    private String streetNumber;
    private String cap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://videogamesproject-cfd9f.firebaseio.com/User/" + firebaseAuth.getCurrentUser().getDisplayName());

        expiredDate = new HashMap<>();
        shippingInfoPanel = (LinearLayout) findViewById(R.id.shippingInfoPanel);
        radioGroupPayments = (RadioGroup) findViewById(R.id.radioPayments);
        txtCode = (TextView) findViewById(R.id.txtCode);
        txtPIN = (TextView) findViewById(R.id.txtPIN);
        txtMonthExpiredDate = (TextView) findViewById(R.id.txtMonthExpiredDate);
        txtYearExpiredDate = (TextView) findViewById(R.id.txtYearExpiredDate);
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
                if(dataSnapshot.child("payments").exists()){
                    txtCode.setText(dataSnapshot.child("payments").child("code").getValue().toString());
                    txtPIN.setText(dataSnapshot.child("payments").child("pin").getValue().toString());
                    txtMonthExpiredDate.setText(dataSnapshot.child("payments").child("expiredDate").child("month").getValue().toString());
                    txtYearExpiredDate.setText(dataSnapshot.child("payments").child("expiredDate").child("year").getValue().toString());
                } else {
                    dataSnapshot.child("payments");
                }
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
                code = txtCode.getText().toString().trim();
                pin = txtPIN.getText().toString().trim();
                monthExpiredDate = txtMonthExpiredDate.getText().toString().trim();
                yearExpiredDate = txtYearExpiredDate.getText().toString().trim();
                name = txtName.getText().toString().trim();
                surname = txtSurname.getText().toString().trim();
                state = txtState.getText().toString().trim();
                region = txtRegion.getText().toString().trim();
                city = txtCity.getText().toString().trim();
                address = txtAddress.getText().toString().trim();
                streetNumber = txtStreetNumber.getText().toString().trim();
                cap = txtCAP.getText().toString().trim();
                if (txtCode.getText().toString().trim().isEmpty() || !isNumber(txtCode.getText().toString().trim())) {
                    txtCode.setError("Inserire il codice della carta");
                    return;
                }
                if (txtPIN.getText().toString().trim().isEmpty() || !isNumber(txtPIN.getText().toString().trim())) {
                    txtPIN.setError("Inserire il PIN");
                    return;
                }
                if(txtYearExpiredDate.getText().toString().trim().isEmpty() || !isNumber(yearExpiredDate)) {
                    Toast.makeText(getBaseContext(), "Errore! Inserire l'anno di scadenza della propria carta", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(txtMonthExpiredDate.getText().toString().trim().isEmpty() || !isNumber(monthExpiredDate) ||
                        Integer.parseInt(monthExpiredDate) < 1 || Integer.parseInt(monthExpiredDate) > 12){
                    Toast.makeText(getBaseContext(), "Errore! Inserire il mese di scadenza della propria carta", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(name.isEmpty()){
                    txtName.setError("Inserire il nome");
                    return;
                }
                if(surname.isEmpty()){
                    txtSurname.setError("Inserire il cognome");
                    return;
                }
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
                expiredDate.put("month", Integer.valueOf(monthExpiredDate));
                expiredDate.put("year", Integer.valueOf(yearExpiredDate));
                paymentCard.setPaymentMethod(paymentMethod);
                paymentCard.setCode(Integer.parseInt(code));
                paymentCard.setPin(Integer.parseInt(pin));
                paymentCard.setExpiredDate(expiredDate);
                databaseReference.child("payments").setValue(paymentCard);
                //databaseReference.child("payments").child("expiredDate").child("year").setValue(yearExpiredDate);
                //databaseReference.child("payments").child("expiredDate").child("month").setValue(monthExpiredDate);
                databaseReference.child("name").setValue(name);
                databaseReference.child("surname").setValue(surname);
                databaseReference.child("state").setValue(state);
                databaseReference.child("region").setValue(region);
                databaseReference.child("city").setValue(city);
                databaseReference.child("address").setValue(address);
                databaseReference.child("streetNumber").setValue(streetNumber);
                databaseReference.child("cap").setValue(cap);

                Intent intent = new Intent(getBaseContext(), CompleteOrderActivity.class);
                startActivity(intent);

            }
        });

    }

    private static boolean isNumber(String number) {
        try {
            Integer.parseInt(number);
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}

