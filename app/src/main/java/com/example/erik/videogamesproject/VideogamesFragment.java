package com.example.erik.videogamesproject;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.client.Firebase;

/**
 * Created by Erik on 12/10/2016.
 */

public class VideogamesFragment extends Fragment {
    private RecyclerView recyclerView;
    private Firebase rootRef;
    private FirebaseRecyclerAdapter<String, RecyclerView.ViewHolder> adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.videogames_fragment, container, false);

        rootRef = new Firebase("https://videogamesproject-cfd9f.firebaseio.com/");
        recyclerView = (RecyclerView) v.findViewById(R.id.recyclerViewVideogames);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter = new FirebaseRecyclerAdapter<String, ViewHolder> (){
                    @Override
                    protected void populateViewHolder(ViewHolder viewHolder){

                    }
                }

        return v;
    }

    private static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;

        public ViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(android.R.id.text1);

        }
    }
}
