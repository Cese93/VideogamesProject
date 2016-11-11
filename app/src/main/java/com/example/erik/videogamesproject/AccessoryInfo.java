package com.example.erik.videogamesproject;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

/**
 * Created by Andrea on 10/11/2016.
 */

public class AccessoryInfo extends Activity {

    private ImageView imgTitle;
    private ImageView cover;
    private TextView features;
    private TextView producer;
    private CollapsingToolbarLayout collapsingToolbar;
    private TextView price;
    private DatabaseReference databaseReference;
    private RatingBar communityRatingBar;
    private RatingBar userRatingBar;
    private TextView numOfReview;

    private Float communityRating;
    private int totalRating;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.accessoryinformation_layout);


        Intent intent = getIntent();
        final Accessory accessory = (Accessory) intent.getSerializableExtra("Accessory");

        cover = (ImageView) findViewById(R.id.imgCover);
        imgTitle = (ImageView) findViewById(R.id.imgTitle);
        features = (TextView) findViewById(R.id.features);
        producer = (TextView) findViewById(R.id.txtProducer);
        price = (TextView) findViewById(R.id.txtPrice);
        communityRatingBar = (RatingBar) findViewById(R.id.ratingBarCommunity);
        userRatingBar = (RatingBar) findViewById(R.id.ratingBarUser);
        numOfReview = (TextView) findViewById(R.id.numOfReview);


        databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://videogamesproject-cfd9f.firebaseio.com/Accessory/" + accessory.getName());
        //Creazione adapter per la recyclerView

        collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);


        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.app_bar_layout);
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {


            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {

                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    collapsingToolbar.setTitle(accessory.getName());
                    collapsingToolbar.setCollapsedTitleTextColor(Color.BLACK);
                    isShow = true;
                } else if (isShow) {
                    collapsingToolbar.setTitle(" ");
                    isShow = false;
                }

            }
        });


        Picasso.with(this).load(accessory.getImage()).resize(200, 300).into(cover);
        Picasso.with(this).load(accessory.getImageTitle()).resize(800, 400).into(imgTitle);
        features.setText(accessory.getFeatures());
        producer.setText(accessory.getProducer());
        price.setText(String.valueOf(accessory.getPrice()) + "€");

        databaseReference.addValueEventListener(new ValueEventListener() {


            @Override
            public void onDataChange(final DataSnapshot dataSnapshot) {
                    communityRating = Float.parseFloat(dataSnapshot.child("rating").getValue().toString());

                totalRating = Integer.parseInt(dataSnapshot.child("totalRating").getValue().toString());
                numOfReview.setText(totalRating + " valutazioni");

                userRatingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                    @Override
                    public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {

                        communityRating = ((communityRating * totalRating) + userRatingBar.getRating()) / (totalRating + 1);

                        databaseReference.child("totalRating").setValue(totalRating + 1);

                        databaseReference.child("rating").setValue(communityRating);

                    }
                });

                communityRatingBar.setRating(Float.parseFloat(dataSnapshot.child("rating").getValue().toString()));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}
