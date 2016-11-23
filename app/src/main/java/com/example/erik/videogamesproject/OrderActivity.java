package com.example.erik.videogamesproject;


import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

/**
 * Created by erik_ on 22/11/2016.
 */

public class OrderActivity extends AppCompatActivity {
    private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;
    private ListView recapListView;
    private FirebaseListAdapter<Product> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        recapListView = (ListView) findViewById(R.id.recapListView);

        firebaseAuth = FirebaseAuth.getInstance();

        databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://videogamesproject-cfd9f.firebaseio.com/" + firebaseAuth.getCurrentUser().getDisplayName() + "/Cart/Cart");

        Log.e("databasereference", String.valueOf(databaseReference));
        adapter = new FirebaseListAdapter<Product>(this, Product.class, android.R.layout.two_line_list_item, databaseReference) {
            @Override
            protected void populateView(View v, Product model, int position) {
                Log.e("Prova", String.valueOf(model));
                TextView textView1 = (TextView) findViewById(android.R.id.text1);
                textView1.setText(model.getName());
                TextView textView2 = (TextView) findViewById(android.R.id.text2);
                textView2.setText(model.getQuantity());
            }
        };
        recapListView.setAdapter(adapter);

    }

}

