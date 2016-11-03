package com.example.erik.videogamesproject;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.Fragment;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

/**
 * Created by Marco on 21/10/2016.
 */

public class LastReleaseConsole extends Fragment {
    private RecyclerView recyclerViewConsole;
    private FirebaseRecyclerAdapter consoleAdapter;
    private DatabaseReference databaseReference;
    private LinearLayoutManager linearLayoutManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.tabs_layout, container, false);

        recyclerViewConsole = (RecyclerView) v.findViewById(R.id.recyclerViewTabs);
        recyclerViewConsole.addItemDecoration(new HorizontalDividerItemDecoration.Builder(getContext()).build());
        recyclerViewConsole.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setReverseLayout(true);//Inversione della lista, in modo da avere le ultime console uscite in cima la lista
        recyclerViewConsole.setLayoutManager(linearLayoutManager);

        databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://videogamesproject-cfd9f.firebaseio.com/Console");
        consoleAdapter = new FirebaseRecyclerAdapter<Console, ViewHolderConsole>(
                Console.class,
                R.layout.console_row_layout,
                ViewHolderConsole.class,
                databaseReference.orderByChild("releaseDate/year")

        ) {
            @Override
            protected void populateViewHolder(ViewHolderConsole viewHolder, Console model, final int position) {
                Picasso.with(getContext()).load(model.getImage()).resize(250, 150).into(viewHolder.imgConsole);
                viewHolder.txtName.setText(model.getName().toString());
                viewHolder.txtDeveloper.setText(model.getDeveloper().toString());
                viewHolder.txtPrice.setText(String.valueOf(model.getPrice()));


                viewHolder.itemView.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getContext(), "Posizione: " + position, Toast.LENGTH_LONG).show();
                    }
                });
            }

        };

        recyclerViewConsole.setAdapter(consoleAdapter);

        return v;
    }

    public static class ViewHolderConsole extends RecyclerView.ViewHolder {

        TextView txtName;
        TextView txtDeveloper;
        TextView txtPrice;
        ImageView imgConsole;

        public ViewHolderConsole(View itemView) {

            super(itemView);

            itemView.setSelected(true);

            txtName = (TextView) itemView.findViewById(R.id.txtNameConsole);
            txtDeveloper = (TextView) itemView.findViewById(R.id.txtDeveloperConsole);
            txtPrice = (TextView) itemView.findViewById(R.id.txtPriceConsole);
            imgConsole = (ImageView)itemView.findViewById(R.id.imgConsole);

        }
    }
}