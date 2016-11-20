package com.example.erik.videogamesproject;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.ms.square.android.expandabletextview.ExpandableTextView;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by erik_ on 09/11/2016.
 */

public class ConsoleInfo extends Activity {
    private CollapsingToolbarLayout collapsingToolbar;
    private AppBarLayout appBarLayout;
    private ImageView cover;
    private ImageView imgTitle;
    private ExpandableTextView description;
    private TextView development;
    private TextView releaseDate;
    private TextView price;
    private Button btnAddToCart;
    private DatabaseReference databaseReference;
    private RatingBar communityRatingBar;
    private RatingBar userRatingBar;
    private TextView numOfReview;
    private Float communityRating;
    private int totalRating;
    private CoordinatorLayout coordinatorLayout;
    private Console console;
    private Cart consoleCart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.consoleinformation_layout);

        Intent intent = getIntent();
        console = (Console) intent.getSerializableExtra("Console");

        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinationLayout);
        cover = (ImageView) findViewById(R.id.imgCover);
        imgTitle = (ImageView) findViewById(R.id.imgTitle);
        development = (TextView) findViewById(R.id.txtDevelopper);
        price = (TextView) findViewById(R.id.txtPrice);
        releaseDate = (TextView) findViewById(R.id.txtReleaseDate);
        description = (ExpandableTextView) this.findViewById(R.id.expandable_description).findViewById(R.id.expand_text_view);
        communityRatingBar = (RatingBar) findViewById(R.id.ratingBarCommunity);
        userRatingBar = (RatingBar) findViewById(R.id.ratingBarUser);
        numOfReview = (TextView) findViewById(R.id.numOfReview);
        btnAddToCart = (Button) findViewById(R.id.btnAddToCart);
        collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        appBarLayout = (AppBarLayout) findViewById(R.id.app_bar_layout);

        databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://videogamesproject-cfd9f.firebaseio.com/Console/" + console.getName());

        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {

            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {

                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    collapsingToolbar.setTitle(console.getName());
                    collapsingToolbar.setCollapsedTitleTextColor(Color.BLACK);
                    isShow = true;
                } else if (isShow) {
                    collapsingToolbar.setTitle(" ");
                    isShow = false;
                }

            }
        });


        Picasso.with(this).load(console.getImage()).resize(300, 200).into(cover);
        Picasso.with(this).load(console.getImageTitle()).resize(800, 400).into(imgTitle);
        development.setText(console.getDeveloper());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Calendar calendar = new GregorianCalendar(console.getReleaseDate().getYear(), console.getReleaseDate().getMonth(), console.getReleaseDate().getDate());
        releaseDate.setText(simpleDateFormat.format(calendar.getTime()));
        price.setText(String.valueOf(console.getPrice()) + "€");
        description.setText(console.getDescription());

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

        final SnackbarManagement snackbar = new SnackbarManagement();

        btnAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                snackbar.openSnackbar();
                snackbar.getSnackbar().show();
            }
        });

    }

    private class SnackbarManagement {

        private Snackbar.SnackbarLayout snackbarLayout;
        private Snackbar snackbar;
        private View snackView;
        private ElegantNumberButton btnQuantity;
        private FirebaseAuth firebaseAuth;

        public SnackbarManagement() {
        }

        public void openSnackbar() {
            firebaseAuth = FirebaseAuth.getInstance();
            snackbar = Snackbar.make(coordinatorLayout, "", Snackbar.LENGTH_INDEFINITE)
                    .setAction("Aggiungi", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            consoleCart = new Cart(firebaseAuth.getCurrentUser());
                            consoleCart.addProduct(console, console.getName(),Integer.parseInt(btnQuantity.getNumber()),console.getPrice());
                            Toast.makeText(ConsoleInfo.this, "Prodotto aggiunto nel carrello", Toast.LENGTH_SHORT).show();
                        }
                    }).setActionTextColor(Color.WHITE);
            snackbarLayout = (Snackbar.SnackbarLayout) snackbar.getView();
            snackView = getLayoutInflater().inflate(R.layout.snackbar_acc_cons_layout, null);
            btnQuantity = (ElegantNumberButton) snackView.findViewById(R.id.btnQuantity);
            snackbarLayout.addView(snackView, 0);
        }


        public Snackbar getSnackbar() {
            return snackbar;
        }
    }
}

