package com.example.erik.videogamesproject;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

/**
 * Created by Marco on 26/10/2016.
 */

public class VideogameDisplay extends YouTubeBaseActivity {

    private ImageView cover;
    private ImageView imgTitle;
    private TextView plot;
    private TextView development;
    private TextView publisher;
    private Toolbar toolbar;
    private CollapsingToolbarLayout collapsingToolbar;
    private TextView price;
    private NestedScrollView scrollView;
    private YouTubeBaseActivity youTubeBaseActivity;
    private YouTubePlayerView youTubePlayerView;
    private YouTubePlayer.OnInitializedListener onInitializedListener;
    private DatabaseReference databaseReference;
    private RatingBar communityRatingBar;
    private RatingBar userRatingBar;
    private TextView numOfReview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.videogameinformation_layout);


        Intent intent = getIntent();
        final Videogame item = (Videogame) intent.getSerializableExtra("Item");

        cover = (ImageView) findViewById(R.id.imgCover);
        imgTitle = (ImageView) findViewById(R.id.imgTitle);
        plot = (TextView) findViewById(R.id.plot);
        development = (TextView) findViewById(R.id.txtDevelopperDisplay);
        publisher = (TextView) findViewById(R.id.textPublisherDisplay);
        price = (TextView) findViewById(R.id.txtPrice);
        youTubePlayerView = (YouTubePlayerView) findViewById(R.id.youtubeView);
        communityRatingBar = (RatingBar) findViewById(R.id.ratingBarCommunity);
        userRatingBar = (RatingBar)findViewById(R.id.ratingBarUser);
        numOfReview = (TextView)findViewById(R.id.numOfReview);


        databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://videogamesproject-cfd9f.firebaseio.com/Videogames/" + item.getTitle());
        //Creazione adapter per la recyclerView


        onInitializedListener = new YouTubePlayer.OnInitializedListener() {

            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, final YouTubePlayer youTubePlayer, boolean b) {
                youTubePlayer.loadVideo(item.getTrailer());
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
                    collapsingToolbar.setTitle(item.getTitle());
                    collapsingToolbar.setCollapsedTitleTextColor(Color.WHITE);
                    isShow = true;
                } else if (isShow) {
                    collapsingToolbar.setTitle(" ");
                    isShow = false;
                }

            }
        });


        Picasso.with(this).load(item.getImage()).resize(200, 300).into(cover);
        Picasso.with(this).load(item.getImageTitle()).resize(800, 400).into(imgTitle);


        databaseReference.addValueEventListener(new ValueEventListener() {

            private Float communityRating;
            private Float ris;
            private int totalRating;

            @Override
            public void onDataChange(final DataSnapshot dataSnapshot) {

                userRatingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                    @Override
                    public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {

                        communityRating = Float.parseFloat(dataSnapshot.child("rating").getValue().toString());

                        totalRating = Integer.parseInt(dataSnapshot.child("totalRating").getValue().toString());

                        communityRating = ((communityRating * totalRating) + userRatingBar.getRating())/(totalRating + 1);

                        databaseReference.child("totalRating").setValue(totalRating+1);
                        numOfReview.setText(totalRating +" valutazioni");
                        databaseReference.child("rating").setValue(communityRating);
                        communityRatingBar.setRating(Float.parseFloat(dataSnapshot.child("rating").getValue().toString()));


                    }
                });



            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



        plot.setText(item.getPlot());
        development.setText(item.getDeveloper());
        price.setText(String.valueOf(item.getPrice()) + "€");
        publisher.setText(item.getPublishers());

    }
}
