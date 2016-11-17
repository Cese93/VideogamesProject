package com.example.erik.videogamesproject;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import com.firebase.client.Firebase;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

/**
 * Created by Marco on 16/11/2016.
 */

public class CartActivity extends Fragment {


    private DatabaseReference databaseReference;
    private FirebaseRecyclerAdapter cartAdapter;
    private RecyclerView recyclerViewCart;
    private FirebaseAuth firebaseAuth;
    private LinearLayoutManager linearLayoutManager;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.cartactivity_layout, container, false);

        recyclerViewCart = (RecyclerView)v.findViewById(R.id.recyclerViewCart);

        recyclerViewCart.addItemDecoration(new HorizontalDividerItemDecoration.Builder(getContext()).build());
        recyclerViewCart.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerViewCart.setLayoutManager(linearLayoutManager);

        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();

        Log.v("UserInCart",user.getDisplayName());
        databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://videogamesproject-cfd9f.firebaseio.com/User/" + user.getDisplayName());

        cartAdapter = new FirebaseRecyclerAdapter<Videogame, CartViewHolder>(
                Videogame.class,
                R.layout.cartrow_layout,
                CartViewHolder.class,
                databaseReference.child("Cart")


        ) {
            @Override
            protected void populateViewHolder(final CartViewHolder viewHolder, final Videogame model, int position) {

                databaseReference.child("Cart").child(model.getTitle()).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        viewHolder.txtQuantity.setText(String.valueOf(dataSnapshot.child("quantity").getValue()));
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
                Picasso.with(getContext()).load(model.getImage()).resize(150, 200).into(viewHolder.imageView);
                Log.v("NameCart", model.getTitle());
                viewHolder.txtName.setText(model.getTitle().toString());
            }
        };

        recyclerViewCart.setAdapter(cartAdapter);
        return v;
    }

    public static class CartViewHolder extends RecyclerView.ViewHolder {

        TextView txtQuantity;
        TextView txtName;
        ImageView imageView;


        public CartViewHolder(View itemView) {
            super(itemView);
            itemView.setSelected(true);
            txtName = (TextView) itemView.findViewById(R.id.txtTitle);
            txtQuantity = (TextView) itemView.findViewById(R.id.txtQuantity);
            imageView = (ImageView) itemView.findViewById(R.id.imageProductCart);


        }
    }
}

