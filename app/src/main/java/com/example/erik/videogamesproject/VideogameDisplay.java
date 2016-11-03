package com.example.erik.videogamesproject;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

/**
 * Created by Marco on 26/10/2016.
 */

public class VideogameDisplay extends AppCompatActivity {

    private ImageView cover;
    private ImageView imgTitle;
    private TextView plot;
    private TextView development;
    private TextView publisher;
    private Toolbar toolbar;
    private CollapsingToolbarLayout collapsingToolbar;
    private VideoView trailer;
    private TextView price;
    private NestedScrollView scrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.videogameinformation_layout);

        Intent intent = getIntent();
        final Videogame item = (Videogame) intent.getSerializableExtra("Item");



        scrollView = (NestedScrollView)findViewById(R.id.scroll);

        scrollView.post(new Runnable() {
            @Override
            public void run() {

                scrollView.fullScroll(NestedScrollView.FOCUS_UP);
            }
        });

        scrollView.startNestedScroll(NestedScrollView.FOCUS_UP);

        collapsingToolbar = (CollapsingToolbarLayout)findViewById(R.id.collapsing_toolbar);


        AppBarLayout appBarLayout = (AppBarLayout)findViewById(R.id.app_bar_layout);
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener(){


            boolean isShow = false;
            int scrollRange = -1 ;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {

                if(scrollRange == -1){
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if(scrollRange + verticalOffset == 0){
                    collapsingToolbar.setTitle(item.getTitle());
                    collapsingToolbar.setCollapsedTitleTextColor(Color.WHITE);
                    isShow = true;
                }else if(isShow){
                    collapsingToolbar.setTitle(" ");
                    isShow = false;
                }

            }
        });

        cover = (ImageView) findViewById(R.id.imgCover);
        imgTitle = (ImageView) findViewById(R.id.imgTitle);
        plot = (TextView)findViewById(R.id.plot);
        development = (TextView)findViewById(R.id.txtDevelopperDisplay);
        publisher = (TextView)findViewById(R.id.textPublisherDisplay);
        //trailer = (VideoView)findViewById(R.id.trailer);
        price = (TextView)findViewById(R.id.txtPrice);



        Picasso.with(this).load(item.getImage()).resize(200,300).into(cover);
        Picasso.with(this).load(item.getImageTitle()).resize(800,400).into(imgTitle);

        plot.setText(item.getPlot());
        development.setText(item.getDeveloper());
        price.setText(String.valueOf(item.getPrice()) +"€");
        publisher.setText(item.getPublishers());

    }
}
