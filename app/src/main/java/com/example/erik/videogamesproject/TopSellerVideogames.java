package com.example.erik.videogamesproject;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

/**
 * Created by Erik on 21/10/2016.
 */

public class TopSellerVideogames extends Fragment {
    private RecyclerView recyclerViewVideogames;
    private FirebaseRecyclerAdapter videogamesAdapter;
    private DatabaseReference databaseReference;
    private LinearLayoutManager linearLayoutManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.tabs_layout, container, false);

        recyclerViewVideogames = (RecyclerView) v.findViewById(R.id.recyclerViewTabs);
        //Aggiunta decorator a ogni elemento della listview

        linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setReverseLayout(true);//Inversione della lista, in modo da avere le ultime console uscite in cima la lista
        linearLayoutManager.setStackFromEnd(true);
        recyclerViewVideogames.addItemDecoration(new HorizontalDividerItemDecoration.Builder(getContext()).build());
        recyclerViewVideogames.setHasFixedSize(true);
        recyclerViewVideogames.setLayoutManager(linearLayoutManager);

        databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://videogamesproject-cfd9f.firebaseio.com/Videogames");
        //Creazione adapter per la recyclerView


        videogamesAdapter = new FirebaseRecyclerAdapter<Videogame, LastReleaseVideogames.ViewHolderVideogames>(
                Videogame.class,
                R.layout.videogames_row_layout,
                LastReleaseVideogames.ViewHolderVideogames.class,
                databaseReference

        ) {
            @Override
            protected void populateViewHolder(LastReleaseVideogames.ViewHolderVideogames viewHolder, final Videogame model, final int position) {
                Picasso.with(getContext()).load(model.getImage()).resize(150, 200).into(viewHolder.imgVideogame);
                viewHolder.txtTitle.setText(model.getName().toString());
                viewHolder.txtPublisher.setText(model.getPublishers().toString());
                viewHolder.txtYear.setText(String.valueOf(model.getReleaseDate().getYear()));
                viewHolder.itemView.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getActivity(), VideogameInfo.class);
                        intent.putExtra("Videogame", model);
                        startActivity(intent);
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
