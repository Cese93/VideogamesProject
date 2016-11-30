package com.example.erik.videogamesproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.snapshot.DoubleNode;
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

import org.w3c.dom.Text;

/**
 * Created by Marco on 16/11/2016.
 */

public class CartFragment extends Fragment {


    private DatabaseReference databaseReference;
    private FirebaseRecyclerAdapter cartAdapter;
    private RecyclerView recyclerViewCart;
    private FirebaseAuth firebaseAuth;
    private LinearLayoutManager linearLayoutManager;
    private TextView txtTotalPrice;
    private TextView txtProcessOrder;
    private double totalPrice;
    private Cart cart;
    int quantity;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.cart_fragment_layout, container, false);

        recyclerViewCart = (RecyclerView) v.findViewById(R.id.recyclerViewCart);
        txtTotalPrice = (TextView) v.findViewById(R.id.txtTotalPrice);
        txtProcessOrder = (TextView) v.findViewById(R.id.txtProcessOrder);

        txtProcessOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), OrderActivity.class);
                intent.putExtra("TOTAL", txtTotalPrice.getText());
                startActivity(intent);
            }
        });

        recyclerViewCart.addItemDecoration(new HorizontalDividerItemDecoration.Builder(getContext()).build());
        recyclerViewCart.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerViewCart.setLayoutManager(linearLayoutManager);

        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();

        cart = new Cart(firebaseAuth.getCurrentUser());

        Log.v("UserInCart", user.getDisplayName());
        databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://videogamesproject-cfd9f.firebaseio.com/User/" + user.getDisplayName());

        cartAdapter = new FirebaseRecyclerAdapter<Product, CartViewHolder>(
                Product.class,
                R.layout.cart_row_layout,
                CartViewHolder.class,
                databaseReference.child("Cart").child("Cart")


        ) {
            @Override
            protected void populateViewHolder(final CartViewHolder viewHolder, final Product model, int position) {

                databaseReference.child("Cart").child("Cart").child(model.getName()).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        viewHolder.txtQuantity.setText(String.valueOf(dataSnapshot.child("quantity").getValue()));
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

                //totalPrice += model.getPrice() * Double.parseDouble(viewHolder.txtQuantity.getText().toString());
                Picasso.with(getContext()).load(model.getImage()).resize(170, 210).into(viewHolder.imageView);
                viewHolder.txtName.setText(model.getName().toString());

                viewHolder.txtPrice.setText("Prezzo x1: " + String.valueOf(model.getPrice() + "€"));
                //txtTotalPrice.setText("Prezzo totale: " + totalPrice);
                viewHolder.imgDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        cart.deleteProduct(model);

                    }
                });
            }

        };

        databaseReference.child("Cart").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if(dataSnapshot.child("totalPrice").getValue() == null){

                    return;

                }else {


                    txtTotalPrice.setText("Prezzo totale:" + dataSnapshot.child("totalPrice").getValue());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        recyclerViewCart.setAdapter(cartAdapter);
        return v;
    }

    public static class CartViewHolder extends RecyclerView.ViewHolder {
        TextView txtQuantity;
        TextView txtName;
        TextView txtPrice;
        ImageView imgDelete;
        ImageView imageView;

        public CartViewHolder(View itemView) {
            super(itemView);
            itemView.setSelected(true);
            txtName = (TextView) itemView.findViewById(R.id.txtTitle);
            txtPrice = (TextView) itemView.findViewById(R.id.txtPrice);
            txtQuantity = (TextView) itemView.findViewById(R.id.txtQuantity);
            imgDelete = (ImageView) itemView.findViewById(R.id.deleteProductCar);
            imageView = (ImageView) itemView.findViewById(R.id.imageProductCart);


        }
    }
}

