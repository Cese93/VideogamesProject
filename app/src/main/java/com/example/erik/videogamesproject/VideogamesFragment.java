package com.example.erik.videogamesproject;

import android.graphics.Color;
import android.icu.util.Calendar;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.collection.LLRBNode;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import org.w3c.dom.Text;


/**
 * Created by Erik on 12/10/2016.
 */

public class VideogamesFragment extends Fragment {
    private RecyclerView recyclerViewVideogames;
    private FirebaseRecyclerAdapter videogamesAdapter;
    private DatabaseReference databaseReference;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.videogames_fragment, container, false);

        recyclerViewVideogames = (RecyclerView) v.findViewById(R.id.recyclerViewVideogames);
        //Aggiunta decorator a ogni elemento della listview
        recyclerViewVideogames.addItemDecoration(new HorizontalDividerItemDecoration.Builder(getContext()).build());
        recyclerViewVideogames.setHasFixedSize(true);
        recyclerViewVideogames.setLayoutManager(new LinearLayoutManager(getContext()));

        databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://videogamesproject-cfd9f.firebaseio.com/Videogames");
        //Creazione adapter per la recyclerView
        videogamesAdapter = new FirebaseRecyclerAdapter<Videogame, ViewHolderVideogames>(
                Videogame.class,
                R.layout.videogames_row_layout,
                ViewHolderVideogames.class,
                databaseReference

        ) {
            @Override
            protected void populateViewHolder(ViewHolderVideogames viewHolder, Videogame model, final int position) {
                Picasso.with(getContext()).load(model.getImage()).resize(150, 200).into(viewHolder.imgVideogame);
                viewHolder.txtTitle.setText(model.getTitle().toString());
                viewHolder.txtPublisher.setText(model.getPublishers().toString());
                viewHolder.txtYear.setText(String.valueOf(model.getReleaseDate().getYear()));
                viewHolder.itemView.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
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
