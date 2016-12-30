package com.example.erik.videogamesproject.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.erik.videogamesproject.Activity.AccessoryInfo;
import com.example.erik.videogamesproject.Model.Accessory;
import com.example.erik.videogamesproject.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

/**
 * Created by andrea on 22/11/2016.
 */

public class TopRatedAccessory extends Fragment {
    private RecyclerView recyclerViewAccessory;
    private FirebaseRecyclerAdapter accessoryAdapter;
    private DatabaseReference databaseReference;
    private LinearLayoutManager linearLayoutManager;

    @Override
    public View onCreateView ( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState ) {
        View v = inflater.inflate(R.layout.tabs_layout, container, false);

        recyclerViewAccessory = (RecyclerView) v.findViewById(R.id.recyclerViewTabs);


        SearchView searchView = (SearchView) v.findViewById(R.id.searchView);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {


            @Override
            public boolean onQueryTextSubmit ( String query ) {
                return false;
            }

            @Override
            public boolean onQueryTextChange ( String newText ) {


                if (newText.isEmpty()) {


                    recyclerViewAccessory.addItemDecoration(new HorizontalDividerItemDecoration.Builder(getContext()).build());
                    recyclerViewAccessory.setHasFixedSize(true);
                    linearLayoutManager = new LinearLayoutManager(getContext());
                    linearLayoutManager.setReverseLayout(true);
                    linearLayoutManager.setStackFromEnd(true);
                    recyclerViewAccessory.setLayoutManager(new LinearLayoutManager(getContext()));

                    databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://videogamesproject-cfd9f.firebaseio.com/Accessory");
                    accessoryAdapter = new FirebaseRecyclerAdapter<Accessory, LastReleaseAccessory.ViewHolderAccessory>(
                            Accessory.class,
                            R.layout.row_accessory_layout,
                            LastReleaseAccessory.ViewHolderAccessory.class,
                            databaseReference.orderByChild("releaseDate/year")

                    ) {
                        @Override
                        protected void populateViewHolder ( LastReleaseAccessory.ViewHolderAccessory viewHolder, final Accessory model, final int position ) {
                            Picasso.with(getContext()).load(model.getImage()).resize(220, 250).into(viewHolder.imgAccessory);
                            viewHolder.txtName.setText(model.getName().toString());
                            viewHolder.txtDeveloper.setText(model.getProducer().toString());
                            viewHolder.txtPrice.setText(String.valueOf(model.getPrice() + "€"));
                            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {

                                @Override
                                public void onClick ( View v ) {
                                    Intent intent = new Intent(getActivity(), AccessoryInfo.class);
                                    intent.putExtra("Accessory", model);
                                    startActivity(intent);
                                }
                            });
                        }
                    };

                    recyclerViewAccessory.setAdapter(accessoryAdapter);

                    return true;

                } else {


                    recyclerViewAccessory.addItemDecoration(new HorizontalDividerItemDecoration.Builder(getContext()).build());
                    recyclerViewAccessory.setHasFixedSize(true);
                    linearLayoutManager = new LinearLayoutManager(getContext());
                    linearLayoutManager.setReverseLayout(true);
                    linearLayoutManager.setStackFromEnd(true);
                    recyclerViewAccessory.setLayoutManager(new LinearLayoutManager(getContext()));

                    databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://videogamesproject-cfd9f.firebaseio.com/Accessory");
                    accessoryAdapter = new FirebaseRecyclerAdapter<Accessory, LastReleaseAccessory.ViewHolderAccessory>(
                            Accessory.class,
                            R.layout.row_accessory_layout,
                            LastReleaseAccessory.ViewHolderAccessory.class,
                            databaseReference.orderByChild("name").equalTo(newText)

                    ) {
                        @Override
                        protected void populateViewHolder ( LastReleaseAccessory.ViewHolderAccessory viewHolder, final Accessory model, final int position ) {
                            Picasso.with(getContext()).load(model.getImage()).resize(220, 250).into(viewHolder.imgAccessory);
                            viewHolder.txtName.setText(model.getName().toString());
                            viewHolder.txtDeveloper.setText(model.getProducer().toString());
                            viewHolder.txtPrice.setText(String.valueOf(model.getPrice() + "€"));
                            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {

                                @Override
                                public void onClick ( View v ) {
                                    Intent intent = new Intent(getActivity(), AccessoryInfo.class);
                                    intent.putExtra("Accessory", model);
                                    startActivity(intent);
                                }
                            });
                        }
                    };

                    recyclerViewAccessory.setAdapter(accessoryAdapter);

                }


                return false;
            }
        });


        recyclerViewAccessory.addItemDecoration(new HorizontalDividerItemDecoration.Builder(getContext()).build());
        recyclerViewAccessory.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        recyclerViewAccessory.setLayoutManager(new LinearLayoutManager(getContext()));

        databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://videogamesproject-cfd9f.firebaseio.com/Accessory");
        accessoryAdapter = new FirebaseRecyclerAdapter<Accessory, LastReleaseAccessory.ViewHolderAccessory>(
                Accessory.class,
                R.layout.row_accessory_layout,
                LastReleaseAccessory.ViewHolderAccessory.class,
                databaseReference.orderByChild("releaseDate/year")

        ) {
            @Override
            protected void populateViewHolder ( LastReleaseAccessory.ViewHolderAccessory viewHolder, final Accessory model, final int position ) {
                Picasso.with(getContext()).load(model.getImage()).resize(220, 250).into(viewHolder.imgAccessory);
                viewHolder.txtName.setText(model.getName().toString());
                viewHolder.txtDeveloper.setText(model.getProducer().toString());
                viewHolder.txtPrice.setText(String.valueOf(model.getPrice() + "€"));
                viewHolder.itemView.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick ( View v ) {
                        Intent intent = new Intent(getActivity(), AccessoryInfo.class);
                        intent.putExtra("Accessory", model);
                        startActivity(intent);
                    }
                });
            }
        };

        recyclerViewAccessory.setAdapter(accessoryAdapter);

        return v;
    }

    public static class ViewHolderAccessory extends RecyclerView.ViewHolder {

        TextView txtName;
        TextView txtDeveloper;
        TextView txtPrice;
        ImageView imgAccessory;

        public ViewHolderAccessory ( View itemView ) {

            super(itemView);

            itemView.setSelected(true);

            txtName = (TextView) itemView.findViewById(R.id.txtNameAccessory);
            txtDeveloper = (TextView) itemView.findViewById(R.id.txtProducerAccessory);
            txtPrice = (TextView) itemView.findViewById(R.id.txtPriceAccessory);
            imgAccessory = (ImageView) itemView.findViewById(R.id.imgAccessory);

        }
    }
}
