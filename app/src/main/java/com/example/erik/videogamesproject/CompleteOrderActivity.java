package com.example.erik.videogamesproject;

import android.content.Intent;
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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

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
    private Order order;
    private Map<String, Order> orders;
    private Integer orderNumber = 1;

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


        orders = new HashMap<>();
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

        order = new Order();

        databaseReference.addValueEventListener(new ValueEventListener() {
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
                order.setProducts((Map<String, Product>) dataSnapshot.child("Cart").child("Cart").getValue());
                order.setTotal(Double.parseDouble(dataSnapshot.child("Cart").child("totalPrice").getValue().toString()));
                Calendar c = Calendar.getInstance();
                SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
                order.setOrderDate(df.format(c.getTime()));

                btnConfirmOrder.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        //Salvataggio dei dati dell'utente
                        User user = new User();
                        Card paymentCard = new Card();
                        user.setName(dataSnapshot.child("name").getValue().toString());
                        user.setSurname(dataSnapshot.child("surname").getValue().toString());
                        user.setEmail(dataSnapshot.child("email").getValue().toString());
                        user.setState(dataSnapshot.child("state").getValue().toString());
                        user.setRegion(dataSnapshot.child("region").getValue().toString());
                        user.setCity(dataSnapshot.child("city").getValue().toString());
                        user.setAddress(dataSnapshot.child("address").getValue().toString());
                        user.setStreetNumber(dataSnapshot.child("streetNumber").getValue().toString());
                        user.setCAP(Integer.parseInt(dataSnapshot.child("cap").getValue().toString()));
                        paymentCard.setCode(Integer.parseInt(dataSnapshot.child("payments").child("code").getValue().toString()));
                        paymentCard.setPaymentMethod(dataSnapshot.child("payments").child("paymentMethod").getValue().toString());
                        paymentCard.setPin(Integer.parseInt(dataSnapshot.child("payments").child("pin").getValue().toString()));
                        paymentCard.setExpiredDate((Map<String, Integer>) dataSnapshot.child("payments").child("expiredDate").getValue());
                        user.setPayments(paymentCard);

                        if (dataSnapshot.child("Orders").hasChild("orders")) {
                            orders = (Map<String, Order>) dataSnapshot.child("Orders").child("orders").getValue();
                            orderNumber = Integer.parseInt(dataSnapshot.child("Orders").child("orderNumber").getValue().toString());
                            orderNumber++;
                            orders.put("order " + orderNumber, order);
                            databaseReference.child("Orders").child("orders").setValue(orders);
                            databaseReference.child("Orders").child("orders").child("order " + orderNumber).child("holder").setValue(user);
                            databaseReference.child("Orders").child("orderNumber").setValue(orderNumber);
                        } else {
                            orders.put("order " + orderNumber, order);
                            databaseReference.child("Orders").child("orders").setValue(orders);
                            databaseReference.child("Orders").child("orders").child("order " + orderNumber).child("holder").setValue(user);
                            databaseReference.child("Orders").child("orderNumber").setValue(orderNumber);
                        }
                        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                        startActivity(intent);
                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
