package com.example.erik.videogamesproject;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class HomeActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    private ImageView imgProfile;
    private TextView txtHeaderUsername;
    private TextView txtHeaderEmail;
    private ProgressDialog progressDialog;

    private  TextView nameLastVideogame;
    private  ImageView imageHome;


    private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser user;
    private StorageReference storageReference;

    private static final int GALLERY_INTENT = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Firebase.setAndroidContext(this);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(Color.WHITE);

        HomeFragment homeFragment = new  HomeFragment();
        setFragment(homeFragment);

        firebaseAuth = FirebaseAuth.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();
        progressDialog = new ProgressDialog(this);

        if (firebaseAuth.getCurrentUser() == null) {
            startActivity(new Intent(this, LoginActivity.class));
        }

        user = firebaseAuth.getCurrentUser();
        Toast.makeText(this, "Benvenuto " + user.getDisplayName(), Toast.LENGTH_SHORT).show();


       // final ImageView imgCover = (ImageView) findViewById(R.id.imgCoverHome);


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
                        HomeFragment homeFragment = new  HomeFragment();
                        setFragment(homeFragment);
                        return true;
                    case R.id.videogames:
                        VideogamesFragment videogamesFragment = new VideogamesFragment();
                        setFragment(videogamesFragment);
                        return true;
                    case R.id.console:
                        ConsoleFragment consoleFragment = new ConsoleFragment();
                        setFragment(consoleFragment);
                        return true;
                    case R.id.accessory:
                        AccessoryFragment accessoryFragment = new AccessoryFragment();
                        setFragment(accessoryFragment);
                        return true;
                    case R.id.myOrder:
                        MyOrderFragment myOrderFragment = new MyOrderFragment();
                        setFragment(myOrderFragment);
                        return true;
                    case R.id.cart:
                        CartFragment cartFragment = new CartFragment();
                        setFragment(cartFragment);
                        return true;
                    case R.id.trash:
                        Toast.makeText(getApplicationContext(), "Trash Selected", Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.logout:
                        firebaseAuth.signOut();
                        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
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
                imgProfile = (ImageView) findViewById(R.id.imgProfile);
                txtHeaderUsername = (TextView) findViewById(R.id.txtHeaderUsername);
                txtHeaderEmail = (TextView) findViewById(R.id.txtHeaderEmail);
                txtHeaderUsername.setText("Benvenuto " + user.getDisplayName());
                txtHeaderEmail.setText(user.getEmail());
                if(user.getPhotoUrl() == null){
                    imgProfile.setImageDrawable(getResources().getDrawable(R.drawable.profile1));
                }else {
                    Picasso.with(HomeActivity.this).load(user.getPhotoUrl()).fit().centerCrop().into(imgProfile);
                }

                imgProfile.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Intent.ACTION_PICK);
                        intent.setType("image/*");
                        startActivityForResult(intent, GALLERY_INTENT);
                    }
                });
            }
        };

        //Imposta l'actionbarToggle
        drawerLayout.addDrawerListener(actionBarDrawerToggle);

        //Chiamata al metodo syncState necessaria per visualizzare l'icona hamburger del Drawer
        actionBarDrawerToggle.syncState();

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GALLERY_INTENT && resultCode == RESULT_OK) {
            progressDialog.setMessage("Caricamento in corso...");
            progressDialog.show();
            Uri uri = data.getData();
            StorageReference filepath = storageReference.child("Photos").child(uri.getLastPathSegment());
            filepath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Uri downloadUri = taskSnapshot.getDownloadUrl();
                    UserProfileChangeRequest userProfileChangeRequest = new UserProfileChangeRequest.Builder().setPhotoUri(downloadUri).build();
                    user.updateProfile(userProfileChangeRequest);
                    Picasso.with(HomeActivity.this).load(user.getPhotoUrl()).fit().centerCrop().into(imgProfile);
                    progressDialog.dismiss();
                    drawerLayout.closeDrawers();
                    Toast.makeText(HomeActivity.this, "Immagine caricata", Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    //Metodo che server per settare un fragment, utilizzato nel navigation drawer quando si clicca su un elemento del menu
    public void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame, fragment);
        fragmentTransaction.commit();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id == R.id.search){
          return true;
        }

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
