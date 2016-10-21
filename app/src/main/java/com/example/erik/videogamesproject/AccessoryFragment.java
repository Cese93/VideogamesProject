package com.example.erik.videogamesproject;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

/**
 * Created by Marco on 18/10/2016.
 */

public class AccessoryFragment extends Fragment {

    private RecyclerView recyclerViewAccessory;
    private FirebaseRecyclerAdapter accessoryAdapter;
    private DatabaseReference databaseReference;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.products_fragment, container, false);

        /*recyclerViewAccessory = (RecyclerView) v.findViewById(R.id.recyclerViewTabs);
        recyclerViewAccessory.addItemDecoration(new HorizontalDividerItemDecoration.Builder(getContext()).build());
        recyclerViewAccessory.setHasFixedSize(true);
        recyclerViewAccessory.setLayoutManager(new LinearLayoutManager(getContext()));

        databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://videogamesproject-cfd9f.firebaseio.com/Accessory");
        accessoryAdapter = new FirebaseRecyclerAdapter<Accessory, ViewHolderAccessory>(
                Accessory.class,
                R.layout.accessory_row_layout,
                ViewHolderAccessory.class,
                databaseReference

        ) {
            @Override
            protected void populateViewHolder(ViewHolderAccessory viewHolder, Accessory model, final int position) {
                Picasso.with(getContext()).load(model.getImage()).resize(220,250).into(viewHolder.imgAccessory);
                viewHolder.txtName.setText(model.getName().toString());
                viewHolder.txtDeveloper.setText(model.getProducer().toString());
                viewHolder.txtPrice.setText(String.valueOf(model.getPrice()));


                viewHolder.itemView.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getContext(), "Posizione: " + position, Toast.LENGTH_LONG).show();
                    }
                });
            }

        };

        recyclerViewAccessory.setAdapter(accessoryAdapter);*/
        return v;
    }

    /*public static class ViewHolderAccessory extends RecyclerView.ViewHolder {

        TextView txtName;
        TextView txtDeveloper;
        TextView txtPrice;
        ImageView imgAccessory;

        public ViewHolderAccessory(View itemView) {

            super(itemView);

            itemView.setSelected(true);

            txtName = (TextView) itemView.findViewById(R.id.txtNameAccesory);
            txtDeveloper = (TextView) itemView.findViewById(R.id.txtDeveloperAccesory);
            txtPrice = (TextView) itemView.findViewById(R.id.txtPriceAccessory);
            imgAccessory = (ImageView)itemView.findViewById(R.id.imgAccessory);

        }
    }*/
}
