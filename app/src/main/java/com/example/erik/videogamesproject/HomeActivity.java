package com.example.erik.videogamesproject;

import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.Date;

public class HomeActivity extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;

    private Firebase rootRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Firebase.setAndroidContext(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(Color.WHITE);

        firebaseAuth = FirebaseAuth.getInstance();

        rootRef = new Firebase("https://videogamesproject-cfd9f.firebaseio.com/");

        if (firebaseAuth.getCurrentUser() == null) {
            startActivity(new Intent(this, LoginActivity.class));
        }

        FirebaseUser user = firebaseAuth.getCurrentUser();

        Toast.makeText(this, "Benvenuto " + user.getEmail(), Toast.LENGTH_SHORT).show();

        //Inizializzazione NavigationView
        navigationView = (NavigationView) findViewById(R.id.navigation_view);

        //Gestisce gli eventi di click suglie elementi del Drawer
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            //Metodo invocato dal click di un elemento del Drawer
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {


                //Controlla se un elemento è cliccato o meno, se non lo è lo imposta a true
                if (menuItem.isChecked()) menuItem.setChecked(true);
                else menuItem.setChecked(false);

                //Chiude il Drawer una volta cliccato un elemento
                drawerLayout.closeDrawers();

                //Controlla quale elemento viene cliccato ed esegue un azione appropriata per ogni elemento del Drawer
                switch (menuItem.getItemId()) {

                    //Lancia il fragment relativo ai videogiochi
                    case R.id.home:
                        Toast.makeText(getApplicationContext(), "Home", Toast.LENGTH_SHORT).show();
                        VideogamesFragment videogamesFragment = new VideogamesFragment();
                        setFragment(videogamesFragment);
                        return true;
                    case R.id.starred:
                        Toast.makeText(getApplicationContext(), "Stared Selected", Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.sent_mail:
                        Toast.makeText(getApplicationContext(), "Send Selected", Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.drafts:
                        Toast.makeText(getApplicationContext(), "Drafts Selected", Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.allmail:
                        Toast.makeText(getApplicationContext(), "All Mail Selected", Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.trash:
                        Toast.makeText(getApplicationContext(), "Trash Selected", Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.spam:
                        Toast.makeText(getApplicationContext(), "Spam Selected", Toast.LENGTH_SHORT).show();
                        return true;
                    default:
                        Toast.makeText(getApplicationContext(), "Errore", Toast.LENGTH_SHORT).show();
                        return true;

                }
            }
        });

        //Inizializzaione Drawer Layout e ActionBarToggle
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.openDrawer, R.string.closeDrawer) {

            @Override
            public void onDrawerClosed(View drawerView) {
                //Codice eseguito alla chiusura del Drawer
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                //Codice eseguito all'apertura del Drawer
                super.onDrawerOpened(drawerView);
            }
        };

        //Imposta l'actionbarToggle
        drawerLayout.addDrawerListener(actionBarDrawerToggle);

        //Chiamata al metodo syncState necessaria per visualizzare l'icona hamburger del Drawer
        actionBarDrawerToggle.syncState();


        rootRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Iterable<DataSnapshot> list = dataSnapshot.child("Videogames").getChildren();

                while (list.iterator().hasNext()) {

                    DataSnapshot data = list.iterator().next();

                    ArrayList listPlatform = (ArrayList) data.child("Platform").getValue();
                    Date releaseDate = new Date(Integer.parseInt(data.child("Release date").child("Anno").getValue().toString()),
                            Integer.parseInt(data.child("Release date").child("Mese").getValue().toString()),
                            Integer.parseInt(data.child("Release date").child("Giorno").getValue().toString()));


                    Videogame videogame = new Videogame(data.child("Title").getValue().toString(), data.child("Genres").getValue().toString(),
                            data.child("Developer").getValue().toString(), data.child("Publishers").getValue().toString(), listPlatform,
                            releaseDate, Double.parseDouble(data.child("Rating").getValue().toString()), data.child("Plot").getValue().toString(),
                            data.child("Trailer").getValue().toString(), data.child("Image").getValue().toString());

                    Log.e("Ciaooooooooo", videogame.getReleaseDate().toString());

                }
                //dataSnapshot.child("Videogames").child("Dragon Age Origins ");
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

    }

    //Metodo che server per settare un fragment, utilizzato nel navigation drawer quando si clicca su un elemento del menu
    public void setFragment(Fragment fragment) {
        android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }
        if (id == R.id.action_logout) {
            firebaseAuth.signOut();
            startActivity(new Intent(this, LoginActivity.class));
        }

        return super.onOptionsItemSelected(item);
    }
}
