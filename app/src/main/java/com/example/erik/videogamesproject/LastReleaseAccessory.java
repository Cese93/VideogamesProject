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
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

/**
 * Created by erik_ on 24/10/2016.
 */

public class LastReleaseAccessory extends Fragment {
    private RecyclerView recyclerViewAccessory;
    private FirebaseRecyclerAdapter accessoryAdapter;
    private DatabaseReference databaseReference;
    private LinearLayoutManager linearLayoutManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.tabs_layout, container, false);

        recyclerViewAccessory = (RecyclerView) v.findViewById(R.id.recyclerViewTabs);
        recyclerViewAccessory.addItemDecoration(new HorizontalDividerItemDecoration.Builder(getContext()).build());
        recyclerViewAccessory.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setReverseLayout(true);//Inversione della lista, in modo da avere gli ultimi accessori usciti in cima
        linearLayoutManager.setStackFromEnd(true);
        recyclerViewAccessory.setLayoutManager(new LinearLayoutManager(getContext()));

        databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://videogamesproject-cfd9f.firebaseio.com/Accessory");
        accessoryAdapter = new FirebaseRecyclerAdapter<Accessory, ViewHolderAccessory>(
                Accessory.class,
                R.layout.accessory_row_layout,
                ViewHolderAccessory.class,
                databaseReference.orderByChild("releaseDate/year")

        ) {
            @Override
            protected void populateViewHolder(ViewHolderAccessory viewHolder, final Accessory model, final int position) {
                Picasso.with(getContext()).load(model.getImage()).resize(220, 250).into(viewHolder.imgAccessory);
                viewHolder.txtName.setText(model.getName().toString());
                viewHolder.txtDeveloper.setText(model.getProducer().toString());
                viewHolder.txtPrice.setText(String.valueOf(model.getPrice() + "€"));
                viewHolder.itemView.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
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

    public ViewHolderAccessory(View itemView) {

        super(itemView);

        itemView.setSelected(true);

        txtName = (TextView) itemView.findViewById(R.id.txtNameAccessory);
        txtDeveloper = (TextView) itemView.findViewById(R.id.txtProducerAccessory);
        txtPrice = (TextView) itemView.findViewById(R.id.txtPriceAccessory);
        imgAccessory = (ImageView) itemView.findViewById(R.id.imgAccessory);

    }
}
}
