package com.example.erik.videogamesproject;

import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.ListViewCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.FirebaseApp;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Erik on 12/10/2016.
 */

public class VideogamesFragment extends Fragment {
    private RecyclerView recyclerView;
    private Firebase rootRef;
    private FirebaseListAdapter<Videogame> prova;
    DatabaseReference df;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.videogames_fragment, container, false);


ListView listView = (ListView) v.findViewById(R.id.listViewVideogames);
        df = FirebaseDatabase.getInstance().getReferenceFromUrl("https://videogamesproject-cfd9f.firebaseio.com/").child("Videogames");
        prova = new FirebaseListAdapter<Videogame>(
                getActivity(),
                Videogame.class,
                android.R.layout.simple_list_item_1,
                df
        ) {
            @Override
            protected void populateView(View v, Videogame model, final int position) {

                TextView textView = (TextView) v.findViewById(android.R.id.text1);
                textView.setText(model.getTitle().toString());
                Log.e("PROVAAAAAAAAAAAA", model.getImage().toString());
                Log.e("PROVAAAAAAAAAAAA", String.valueOf(model.getReleaseDate().getMonth()));

                v.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getContext(), "Elemento in posizione: " + position, Toast.LENGTH_LONG).show();
                    }
                });
            }
        };

        listView.setAdapter(prova);

        return v;
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    public static class VideogamesViewHolder extends RecyclerView.ViewHolder {
        TextView textView;

        public VideogamesViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(android.R.id.text1);

        }
    }
}
