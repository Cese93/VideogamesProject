package com.example.erik.videogamesproject;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 * Created by Marco on 26/10/2016.
 */

public class VideogameDisplay extends AppCompatActivity {

    private ImageView cover;
    private ImageView imgTitle;
    private TextView plot;
    private TextView development;
    private TextView publisher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.videogameinformation_layout);

        Intent intent = getIntent();
        Videogame item = (Videogame) intent.getSerializableExtra("Item");

        cover = (ImageView) findViewById(R.id.imgCover);
        imgTitle = (ImageView) findViewById(R.id.imgTitle);
        plot = (TextView)findViewById(R.id.plot);
        development = (TextView)findViewById(R.id.txtDevelopmentDisplay);
        publisher = (TextView)findViewById(R.id.txtPubisherDisplay);

        Picasso.with(this).load(item.getImage()).resize(150,200).into(cover);
        Picasso.with(this).load(item.getImageTitle()).resize(1000,600).into(imgTitle);

        plot.setText(item.getPlot());
        development.setText(item.getDeveloper());
        publisher.setText(item.getPublishers());

    }
}
