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


/**
 * Created by Marco on 06/12/2016.
 */

public class HomeFragment extends Fragment {


    private DatabaseReference databaseReference;
    private RecyclerView topRatedRecyclerView;
    private RecyclerView topSellerRecyclerView;
    private RecyclerView lastReleaseRecyclerView;
    private FirebaseRecyclerAdapter topRatedAdapter;
    private FirebaseRecyclerAdapter topSellerAdapter;
    private FirebaseRecyclerAdapter lastReleaseAdapter;

    @Override
    public View onCreateView ( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState ) {
        View v = inflater.inflate(R.layout.home_fragment_layout, container, false);

        topRatedRecyclerView = (RecyclerView) v.findViewById(R.id.TopRatedGames);
        topSellerRecyclerView = (RecyclerView) v.findViewById(R.id.lastReleGames);
        lastReleaseRecyclerView = (RecyclerView) v.findViewById(R.id.topSellerGames);

        databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://videogamesproject-cfd9f.firebaseio.com/");

        LinearLayoutManager layoutManagerTopRated = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);

        layoutManagerTopRated.setReverseLayout(true);
        layoutManagerTopRated.setStackFromEnd(true);
        topRatedRecyclerView.setLayoutManager(layoutManagerTopRated);

        topRatedAdapter = new FirebaseRecyclerAdapter<Videogame, ViewHolderHome>(
                Videogame.class,
                R.layout.row_home_list_layout,
                ViewHolderHome.class,
                databaseReference.child("Videogames").orderByChild("rating").limitToLast(3)) {

            @Override
            protected void populateViewHolder ( ViewHolderHome viewHolder, final Videogame model, int position ) {
                viewHolder.txtName.setText(model.getName().toString());
                viewHolder.txtPrice.setText(String.valueOf(model.getPrice()) + "€");
                viewHolder.txtDeveloper.setText(model.getDeveloper());
                Picasso.with(getContext()).load(model.getImage()).resize(200, 250).into(viewHolder.imgHome);
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

        topRatedRecyclerView.setAdapter(topRatedAdapter);

        LinearLayoutManager layoutManagerTopSeller = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        layoutManagerTopSeller.setReverseLayout(true);
        layoutManagerTopSeller.setStackFromEnd(true);
        topSellerRecyclerView.setLayoutManager(layoutManagerTopSeller);

        topSellerAdapter = new FirebaseRecyclerAdapter<Videogame, ViewHolderHome>(
                Videogame.class,
                R.layout.row_home_list_layout,
                ViewHolderHome.class,
                databaseReference.child("Videogames").orderByChild("soldQuantity").limitToLast(3)) {

            @Override
            protected void populateViewHolder ( ViewHolderHome viewHolder, final Videogame model, int position ) {
                viewHolder.txtName.setText(model.getName().toString());
                viewHolder.txtPrice.setText(String.valueOf(model.getPrice()) + "€");
                viewHolder.txtDeveloper.setText(model.getDeveloper());
                Picasso.with(getContext()).load(model.getImage()).resize(200, 250).into(viewHolder.imgHome);
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
        topSellerRecyclerView.setAdapter(topSellerAdapter);

        LinearLayoutManager layoutManagerLastRelease = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        layoutManagerLastRelease.setReverseLayout(true);
        layoutManagerLastRelease.setStackFromEnd(true);
        lastReleaseRecyclerView.setLayoutManager(layoutManagerLastRelease);

        lastReleaseAdapter = new FirebaseRecyclerAdapter<Videogame, ViewHolderHome>(
                Videogame.class,
                R.layout.row_home_list_layout,
                ViewHolderHome.class,
                databaseReference.child("Videogames").orderByChild("releaseDate/year").limitToLast(3)) {

            @Override
            protected void populateViewHolder ( ViewHolderHome viewHolder, final Videogame model, int position ) {
                viewHolder.txtName.setText(model.getName().toString());
                viewHolder.txtPrice.setText(String.valueOf(model.getPrice()) + "€");
                viewHolder.txtDeveloper.setText(model.getDeveloper());
                Picasso.with(getContext()).load(model.getImage()).resize(200, 250).into(viewHolder.imgHome);
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
        lastReleaseRecyclerView.setAdapter(lastReleaseAdapter);

        return v;
    }

    public static class ViewHolderHome extends RecyclerView.ViewHolder {

        TextView txtName;
        TextView txtPrice;
        TextView txtDeveloper;
        ImageView imgHome;

        public ViewHolderHome ( final View itemView ) {

            super(itemView);

            itemView.setSelected(true);

            txtName = (TextView) itemView.findViewById(R.id.row_home_title);
            txtPrice = (TextView) itemView.findViewById(R.id.row_home_price);
            txtDeveloper = (TextView) itemView.findViewById(R.id.row_home_developer);
            imgHome = (ImageView) itemView.findViewById(R.id.row_home_image);

        }
    }
}
