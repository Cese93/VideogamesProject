package com.example.erik.videogamesproject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by Erik on 10/10/2016.
 */

public class RegisterActivity extends AppCompatActivity {

    private EditText nameText;
    private EditText surnameText;
    private EditText emailText;
    private EditText passwordText;
    private EditText confirmPasswordText;
    private EditText usernameText;
    private Button btnRegister;


    private DatabaseReference rootRef;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaserAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Firebase.setAndroidContext(this);

        btnRegister = (Button) findViewById(R.id.btnRegister);

        rootRef = FirebaseDatabase.getInstance().getReferenceFromUrl("https://videogamesproject-cfd9f.firebaseio.com/User");

        firebaserAuth = FirebaseAuth.getInstance();


        progressDialog = new ProgressDialog(this);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickRegister();
            }
        });
    }

    private void clickRegister() {

        nameText = (EditText) findViewById(R.id.txtRegisterName);
        surnameText = (EditText) findViewById(R.id.txtRegisterSurname);
        emailText = (EditText) findViewById(R.id.txtRegisterEmail);
        passwordText = (EditText) findViewById(R.id.txtRegisterPassword);
        confirmPasswordText = (EditText) findViewById(R.id.txtRegisterConfirmPassword);
        usernameText = (EditText) findViewById(R.id.txtRegisterUsername);



        final  String name = nameText.getText().toString().trim();
        final String surname = surnameText.getText().toString().trim();
        final String email = emailText.getText().toString().trim();
        final String password = passwordText.getText().toString().trim();
        final String confirmPassword = confirmPasswordText.getText().toString().trim();
        final String username = usernameText.getText().toString().trim();


        if (name.isEmpty()) {
            nameText.setError("Inserire nome");
            return;

        }

        if (surname.isEmpty()) {
            nameText.setError("Inserire cognome");
            return;
        }

        if (email.isEmpty()) {

            nameText.setError("Email errata o già esistente");
            return;
        }

        if (password.isEmpty() || password.length() < 6) {
            passwordText.setError("Password troppo corta, almeno 6 caratteri");
            return;
        }

        if (!password.equals(confirmPassword)) {
            Toast.makeText(RegisterActivity.this, "Password e conferma Password errate", Toast.LENGTH_SHORT).show();
            return;
        }



        progressDialog.setMessage("Registrazione User");
        progressDialog.show();
        firebaserAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) {
                    progressDialog.dismiss();
                    User user = new User();
                    user.setName(name);
                    user.setSurname(surname);
                    user.setEmail(email);
                    user.setPassword(password);
                    user.setUsername(username);

                    Toast.makeText(RegisterActivity.this, "Utente Inserito", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(RegisterActivity.this, HomeActivity.class));

                    rootRef.child(name).setValue(user);


                } else {
                    progressDialog.dismiss();
                    Toast.makeText(RegisterActivity.this, "Utente Non Inserito", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

}
