package com.example.erik.videogamesproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

/**
 * Created by Erik on 21/10/2016.
 */

public class LastReleaseVideogames extends Fragment {
    private RecyclerView recyclerViewVideogames;
    private FirebaseRecyclerAdapter videogamesAdapter;
    private DatabaseReference databaseReference;
    private LinearLayoutManager linearLayoutManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.tabs_layout, container, false);

        recyclerViewVideogames = (RecyclerView) v.findViewById(R.id.recyclerViewTabs);
        SearchView searchView = (SearchView) v.findViewById(R.id.searchView);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit ( String query ) {
                return false;
            }

            @Override
            public boolean onQueryTextChange ( String newText ) {

                videogamesAdapter.cleanup();

                if(newText.isEmpty()){

                    //Aggiunta decorator a ogni elemento della listview
                    recyclerViewVideogames.addItemDecoration(new HorizontalDividerItemDecoration.Builder(getContext()).build());
                    recyclerViewVideogames.setHasFixedSize(true);
                    linearLayoutManager = new LinearLayoutManager(getContext());
                    linearLayoutManager.setReverseLayout(true);//Inversione della lista, in modo da avere gli ultimi videogiochi usciti in cima
                    linearLayoutManager.setStackFromEnd(true);
                    recyclerViewVideogames.setLayoutManager(linearLayoutManager);

                    databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://videogamesproject-cfd9f.firebaseio.com/Videogames");
                    //Creazione adapter per la recyclerView
                    videogamesAdapter = new FirebaseRecyclerAdapter<Videogame, ViewHolderVideogames>(
                            Videogame.class,
                            R.layout.videogames_row_layout,
                            ViewHolderVideogames.class,
                            databaseReference.orderByChild("releaseDate/year")

                    ) {
                        @Override
                        protected void populateViewHolder(ViewHolderVideogames viewHolder, final Videogame model, final int position) {
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
                    return true;

                }else {




                    Log.v("Sono il testo del cerca",newText);
                    //Aggiunta decorator a ogni elemento della listview
                    recyclerViewVideogames.addItemDecoration(new HorizontalDividerItemDecoration.Builder(getContext()).build());
                    recyclerViewVideogames.setHasFixedSize(true);
                    linearLayoutManager = new LinearLayoutManager(getContext());
                    //linearLayoutManager.setReverseLayout(true);//Inversione della lista, in modo da avere gli ultimi videogiochi usciti in cima
                    //linearLayoutManager.setStackFromEnd(true);
                    recyclerViewVideogames.setLayoutManager(linearLayoutManager);

                    databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://videogamesproject-cfd9f.firebaseio.com/Videogames");
                    //Creazione adapter per la recyclerView
                    videogamesAdapter = new FirebaseRecyclerAdapter<Videogame, ViewHolderVideogames>(
                            Videogame.class,
                            R.layout.videogames_row_layout,
                            ViewHolderVideogames.class,
                            databaseReference.orderByChild("name").equalTo(newText)

                    ) {
                        @Override
                        protected void populateViewHolder ( ViewHolderVideogames viewHolder, final Videogame model, final int position ) {
                            Picasso.with(getContext()).load(model.getImage()).resize(150, 200).into(viewHolder.imgVideogame);
                            viewHolder.txtTitle.setText(model.getName().toString());
                            viewHolder.txtPublisher.setText(model.getPublishers().toString());
                            viewHolder.txtYear.setText(String.valueOf(model.getReleaseDate().getYear()));
                            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {

                                @Override
                                public void onClick ( View v ) {
                                    Intent intent = new Intent(getActivity(), VideogameInfo.class);
                                    intent.putExtra("Videogame", model);
                                    startActivity(intent);
                                }
                            });
                        }

                    };

                    recyclerViewVideogames.setAdapter(videogamesAdapter);

                }

                return true;

            }
        });



        recyclerViewVideogames = (RecyclerView) v.findViewById(R.id.recyclerViewTabs);
        //Aggiunta decorator a ogni elemento della listview
        recyclerViewVideogames.addItemDecoration(new HorizontalDividerItemDecoration.Builder(getContext()).build());
        recyclerViewVideogames.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setReverseLayout(true);//Inversione della lista, in modo da avere gli ultimi videogiochi usciti in cima
        linearLayoutManager.setStackFromEnd(true);
        recyclerViewVideogames.setLayoutManager(linearLayoutManager);

        databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://videogamesproject-cfd9f.firebaseio.com/Videogames");
        //Creazione adapter per la recyclerView
        videogamesAdapter = new FirebaseRecyclerAdapter<Videogame, ViewHolderVideogames>(
                Videogame.class,
                R.layout.videogames_row_layout,
                ViewHolderVideogames.class,
                databaseReference.orderByChild("releaseDate/year")

        ) {
            @Override
            protected void populateViewHolder(ViewHolderVideogames viewHolder, final Videogame model, final int position) {
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
