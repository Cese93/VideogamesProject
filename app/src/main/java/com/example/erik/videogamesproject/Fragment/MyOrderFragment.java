package com.example.erik.videogamesproject.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.erik.videogamesproject.Model.Order;
import com.example.erik.videogamesproject.Activity.OrderInfoActivity;
import com.example.erik.videogamesproject.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Erik on 09/12/2016.
 */

public class MyOrderFragment extends Fragment {
    private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;
    private FirebaseRecyclerAdapter adapter;
    private RecyclerView ordersRecyclerView;
    private LinearLayoutManager linearLayoutManager;


    @Override
    public View onCreateView ( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState ) {
        View v = inflater.inflate(R.layout.my_order_fragment_layout, container, false);

        ordersRecyclerView = (RecyclerView) v.findViewById(R.id.ordersRecyclerView);
        ordersRecyclerView.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(getContext());
        ordersRecyclerView.setLayoutManager(linearLayoutManager);

        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://videogamesproject-cfd9f.firebaseio.com/User/" + firebaseAuth.getCurrentUser().getDisplayName());

        adapter = new FirebaseRecyclerAdapter<Order, ViewHolderOrders>(
                Order.class,
                R.layout.row_my_order_list_layout,
                ViewHolderOrders.class,
                databaseReference.child("Orders").child("orders").orderByChild("orderDate")

        ) {
            @Override
            protected void populateViewHolder ( final ViewHolderOrders viewHolder, final Order model, final int position ) {
                viewHolder.txtOrderDate.setText(model.getOrderDate());
                viewHolder.txtTotalPrice.setText(String.valueOf(model.getTotal()) + "€");
                viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick ( View v ) {
                        Intent intent = new Intent(getContext(), OrderInfoActivity.class);
                        intent.putExtra("POSITION", position);
                        startActivity(intent);
                    }
                });
            }

        };

        ordersRecyclerView.setAdapter(adapter);
        return v;
    }

    public static class ViewHolderOrders extends RecyclerView.ViewHolder {
        TextView txtOrderDate;
        TextView txtTotalPrice;

        public ViewHolderOrders ( View itemView ) {
            super(itemView);
            itemView.setSelected(true);
            txtOrderDate = (TextView) itemView.findViewById(R.id.txtOrderDate);
            txtTotalPrice = (TextView) itemView.findViewById(R.id.txtTotalPrice);
        }
    }
}
