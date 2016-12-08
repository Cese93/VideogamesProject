package com.example.erik.videogamesproject;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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
 * Created by Erik on 06/12/2016.
 */

public class CompleteOrderActivity extends AppCompatActivity {
    private ListView recapListView;
    private FirebaseListAdapter<Product> adapter;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    private TextView txtPaymentMethod;
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
    private Button btnConfirmOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete_order);

        recapListView = (ListView) findViewById(R.id.recapListView);

        txtPaymentMethod = (TextView) findViewById(R.id.txtPaymentMethod);
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
        btnConfirmOrder = (Button) findViewById(R.id.btnConfirmOrder);


        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://videogamesproject-cfd9f.firebaseio.com/User/" + firebaseAuth.getCurrentUser().getDisplayName());
        adapter = new FirebaseListAdapter<Product>(this, Product.class, android.R.layout.two_line_list_item, databaseReference.child("Cart").child("Cart")) {
            @Override
            protected void populateView(View v, Product model, int position) {
                TextView txtName = (TextView) v.findViewById(android.R.id.text1);
                txtName.setText(model.getName());
                TextView txtQuantity = (TextView) v.findViewById(android.R.id.text2);
                txtQuantity.setText("Quantità: " + String.valueOf(model.getQuantity()));
            }
        };
        recapListView.setAdapter(adapter);

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(final DataSnapshot dataSnapshot) {
                txtPaymentMethod.setText(dataSnapshot.child("payments").child("paymentMethod").getValue().toString());
                txtCode.setText(dataSnapshot.child("payments").child("code").getValue().toString());
                txtPIN.setText(dataSnapshot.child("payments").child("pin").getValue().toString());
                txtExpiredDate.setText(dataSnapshot.child("payments").child("expiredDate").child("month").getValue().toString() + "/" + dataSnapshot.child("payments").child("expiredDate").child("year").getValue().toString());
                txtName.setText(dataSnapshot.child("name").getValue().toString());
                txtSurname.setText(dataSnapshot.child("surname").getValue().toString());
                txtState.setText(dataSnapshot.child("state").getValue().toString());
                txtRegion.setText(dataSnapshot.child("region").getValue().toString());
                txtCity.setText(dataSnapshot.child("city").getValue().toString());
                txtAddress.setText(dataSnapshot.child("address").getValue().toString());
                txtStreetNumber.setText(dataSnapshot.child("streetNumber").getValue().toString());
                txtCAP.setText(dataSnapshot.child("cap").getValue().toString());
                txtTotalPrice.setText(dataSnapshot.child("Cart").child("totalPrice").getValue().toString() + "€");
                btnConfirmOrder.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        /*if(dataSnapshot.hasChild("Orders")){
                            Log.e("Provaaa", "Sono qui");
                            databaseReference.child("Orders").child("orderNumber").setValue(Integer.parseInt(dataSnapshot.child("Orders").child("orderNumber").getValue().toString()) + 1);
                        } else {*/

                        if(dataSnapshot.hasChild("Orders")){
                            databaseReference.child("Orders").child("order " + Integer.parseInt(dataSnapshot.child("Orders").child("orderNumber").getValue().toString()) + 1).child("Product").setValue(dataSnapshot.child("Cart").child("Cart").getValue());
                        } else {
                            databaseReference.child("Orders").child("orderNumber").setValue(1);
                            databaseReference.child("Orders").child("order " + 1).child("Product").setValue(dataSnapshot.child("Cart").child("Cart").getValue());
                        }

                            //databaseReference.child("Orders").child("totalPrice").setValue(dataSnapshot.child("Cart").child("totalPrice").getValue());


                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
