package com.example.erik.videogamesproject;


import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

/**
 * Created by Erik on 21/10/2016.
 */

public class TopSellerVideogames extends Fragment {
    private RecyclerView recyclerViewVideogames;
    private FirebaseRecyclerAdapter videogamesAdapter;
    private DatabaseReference databaseReference;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.tabs_layout, container, false);

        recyclerViewVideogames = (RecyclerView) v.findViewById(R.id.recyclerViewTabs);
        //Aggiunta decorator a ogni elemento della listview
        recyclerViewVideogames.addItemDecoration(new HorizontalDividerItemDecoration.Builder(getContext()).build());
        recyclerViewVideogames.setHasFixedSize(true);
        recyclerViewVideogames.setLayoutManager(new LinearLayoutManager(getContext()));

        databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://videogamesproject-cfd9f.firebaseio.com/Videogames");
        //Creazione adapter per la recyclerView


        videogamesAdapter = new FirebaseRecyclerAdapter<Videogame, LastReleaseVideogames.ViewHolderVideogames>(
                Videogame.class,
                R.layout.videogames_row_layout,
                LastReleaseVideogames.ViewHolderVideogames.class,
                databaseReference

        ) {
            @Override
            protected void populateViewHolder(LastReleaseVideogames.ViewHolderVideogames viewHolder,final Videogame model, final int position) {
                Picasso.with(getContext()).load(model.getImage()).resize(150, 200).into(viewHolder.imgVideogame);
                viewHolder.txtTitle.setText(model.getTitle().toString());
                viewHolder.txtPublisher.setText(model.getPublishers().toString());
                viewHolder.txtYear.setText(String.valueOf(model.getReleaseDate().getYear()));
                viewHolder.itemView.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {

                        Intent intent = new Intent(getActivity(),VideogameDisplay.class);
                        Intent intent1 = intent.putExtra("Item",  model);
                        startActivity(intent);

                        Toast.makeText(getContext(), "Posizione: " + position, Toast.LENGTH_LONG).show();
                    }
                });
            }

        };


        recyclerViewVideogames.setAdapter(videogamesAdapter);
        return v;
    }

    public static class ViewHolderVideogames extends RecyclerView.ViewHolder {
        TextView txtTitle;
        TextView txtPublisher;
        TextView txtYear;
        ImageView imgVideogame;

        public ViewHolderVideogames(View itemView) {
            super(itemView);
            itemView.setSelected(true);
            txtTitle = (TextView) itemView.findViewById(R.id.txtTitle);
            txtPublisher = (TextView) itemView.findViewById(R.id.txtPublisher);
            txtYear = (TextView) itemView.findViewById(R.id.txtYear);
            imgVideogame = (ImageView) itemView.findViewById(R.id.imgVideogame);

        }
    }

}
