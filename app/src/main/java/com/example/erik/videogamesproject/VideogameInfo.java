package com.example.erik.videogamesproject;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ms.square.android.expandabletextview.ExpandableTextView;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Created by Marco on 26/10/2016.
 */

public class VideogameInfo extends YouTubeBaseActivity {
    private CoordinatorLayout coordinatorLayout;
    private CollapsingToolbarLayout collapsingToolbar;
    private YouTubePlayer.OnInitializedListener onInitializedListener;
    private DatabaseReference databaseReference;
    private ImageView cover;
    private ImageView imgTitle;
    private ExpandableTextView plot;
    private TextView development;
    private TextView publisher;
    private TextView price;
    private RatingBar communityRatingBar;
    private RatingBar userRatingBar;
    private TextView numOfReview;
    private TextView releaseDate;
    private TextView genres;
    private TextView consoleAvailable;
    private YouTubePlayerView youTubePlayerView;
    private Button btnAddToCart;
    private Float communityRating;
    private int totalRating;
    private Videogame videogame;
    private Cart<Videogame> cart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.videogameinformation_layout);

        Intent intent = getIntent();
        videogame = (Videogame) intent.getSerializableExtra("Videogame");

        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinationLayout);
        cover = (ImageView) findViewById(R.id.imgCover);
        imgTitle = (ImageView) findViewById(R.id.imgTitle);
        plot = (ExpandableTextView) this.findViewById(R.id.expandable_plot).findViewById(R.id.expand_text_view);
        development = (TextView) findViewById(R.id.txtDevelopperDisplay);
        publisher = (TextView) findViewById(R.id.textPublisherDisplay);
        price = (TextView) findViewById(R.id.txtPrice);
        youTubePlayerView = (YouTubePlayerView) findViewById(R.id.youtubeView);
        communityRatingBar = (RatingBar) findViewById(R.id.ratingBarCommunity);
        userRatingBar = (RatingBar) findViewById(R.id.ratingBarUser);
        numOfReview = (TextView) findViewById(R.id.numOfReview);
        releaseDate = (TextView) findViewById(R.id.txtDateRelease);
        genres = (TextView) findViewById(R.id.txtGenres);
        consoleAvailable = (TextView) findViewById(R.id.txtConsoleAvaible);
        btnAddToCart = (Button) findViewById(R.id.btnAddToCart);


        databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://videogamesproject-cfd9f.firebaseio.com/Videogames/" + videogame.getTitle());
        //Creazione adapter per la recyclerView

        onInitializedListener = new YouTubePlayer.OnInitializedListener() {

            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, final YouTubePlayer youTubePlayer, boolean b) {
                youTubePlayer.loadVideo(videogame.getTrailer());
                youTubePlayer.setPlayerStateChangeListener(new YouTubePlayer.PlayerStateChangeListener() {

                    @Override
                    public void onLoading() {
                    }

                    @Override
                    public void onLoaded(String s) {
                        youTubePlayer.pause();
                    }

                    @Override
                    public void onAdStarted() {
                    }

                    @Override
                    public void onVideoStarted() {
                    }

                    @Override
                    public void onVideoEnded() {
                    }

                    @Override
                    public void onError(YouTubePlayer.ErrorReason errorReason) {
                    }
                });

            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

            }
        };

        youTubePlayerView.initialize("AIzaSyABeoS0O_2ZG4kVb-6CcxWbCFHS1ys8LSo", onInitializedListener);

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
                    collapsingToolbar.setTitle(videogame.getTitle());
                    collapsingToolbar.setCollapsedTitleTextColor(Color.WHITE);
                    isShow = true;
                } else if (isShow) {
                    collapsingToolbar.setTitle(" ");
                    isShow = false;
                }

            }
        });


        Picasso.with(this).load(videogame.getImage()).resize(200, 300).into(cover);
        Picasso.with(this).load(videogame.getImageTitle()).resize(800, 400).into(imgTitle);


        plot.setText(videogame.getPlot());

        development.setText(videogame.getDeveloper());
        price.setText(String.valueOf(videogame.getPrice()) + "€");
        publisher.setText(videogame.getPublishers());


        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Calendar calendar = new GregorianCalendar(videogame.getReleaseDate().getYear(), videogame.getReleaseDate().getMonth(), videogame.getReleaseDate().getDate());

        releaseDate.setText(simpleDateFormat.format(calendar.getTime()));
        genres.setText(videogame.getGenres());

        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < videogame.getPlatforms().size(); i++) {

            if (i == videogame.getPlatforms().size() - 1) {

                builder.append(videogame.getPlatforms().get(i));

            } else {

                builder.append(videogame.getPlatforms().get(i) + ",");

            }
        }

        consoleAvailable.setText(builder.toString());

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
        private Spinner consoleSpinner;
        private FirebaseAuth firebaseAuth;
        List<Videogame> producCart;

        public SnackbarManagement() {
        }

        public void openSnackbar() {
            firebaseAuth = FirebaseAuth.getInstance();
            snackbar = Snackbar.make(coordinatorLayout, "", Snackbar.LENGTH_INDEFINITE)
                    .setAction("Aggiungi", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            cart = new Cart<>(firebaseAuth.getCurrentUser());
                            cart.addProduct(videogame, videogame.getTitle(),Integer.parseInt(btnQuantity.getNumber()));

                            Log.e("asdsdxxxxxx", String.valueOf(cart.updateCart()));

                    /*Snackbar snackbar1 = Snackbar.make(coordinatorLayout, "Message is restored!", Snackbar.LENGTH_SHORT);
                    snackbar1.show();*/
                        }
                    }).setActionTextColor(Color.WHITE);
            snackbarLayout = (Snackbar.SnackbarLayout) snackbar.getView();
            snackView = getLayoutInflater().inflate(R.layout.snackbarvideogame_layout, null);
            consoleSpinner = (Spinner) snackView.findViewById(R.id.consoleSpinner);
            btnQuantity = (ElegantNumberButton) snackView.findViewById(R.id.btnQuantity);

            ArrayAdapter<String> adapter = new ArrayAdapter<>(getBaseContext(), android.R.layout.simple_spinner_item, videogame.getPlatforms());
            consoleSpinner.setAdapter(adapter);
            consoleSpinner.setBackgroundColor(Color.WHITE);

            btnQuantity.setOnClickListener(new ElegantNumberButton.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String num = btnQuantity.getNumber();
                }
            });
            snackbarLayout.addView(snackView, 0);
        }


        public Snackbar getSnackbar() {
            return snackbar;
        }
    }

}

