package com.example.erik.videogamesproject.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.erik.videogamesproject.Model.User;
import com.example.erik.videogamesproject.R;
import com.firebase.client.Firebase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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

    private String name;
    private String surname;
    private String email;
    private String password;
    private String confirmPassword;
    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Firebase.setAndroidContext(this);

        nameText = (EditText) findViewById(R.id.txtRegisterName);
        surnameText = (EditText) findViewById(R.id.txtRegisterSurname);
        emailText = (EditText) findViewById(R.id.txtRegisterEmail);
        passwordText = (EditText) findViewById(R.id.txtRegisterPassword);
        confirmPasswordText = (EditText) findViewById(R.id.txtRegisterConfirmPassword);
        usernameText = (EditText) findViewById(R.id.txtRegisterUsername);
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

        name = nameText.getText().toString().trim();
        surname = surnameText.getText().toString().trim();
        email = emailText.getText().toString().trim();
        password = passwordText.getText().toString().trim();
        confirmPassword = confirmPasswordText.getText().toString().trim();
        username = usernameText.getText().toString().trim();

        if (name.isEmpty()) {
            nameText.setError("Inserire nome");
            return;
        }

        if (surname.isEmpty()) {
            surnameText.setError("Inserire cognome");
            return;
        }

        if (email.isEmpty()) {

            emailText.setError("Email errata o già esistente");
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


        rootRef.addListenerForSingleValueEvent(new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot) {

                if (dataSnapshot.hasChild(username)) {
                    Toast.makeText(RegisterActivity.this, "Username Gia inserito", Toast.LENGTH_LONG).show();
                } else {
                    saveUser();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    public void saveUser() {

        progressDialog.setMessage("Registrazione...");
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

                    Toast.makeText(RegisterActivity.this, "Registrazione avvenuta con successo", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(RegisterActivity.this, HomeActivity.class));

                    rootRef.child(username).setValue(user);

                    UserProfileChangeRequest userProfileChangeRequest = new UserProfileChangeRequest.Builder().setDisplayName(user.getUsername()).build();
                    firebaserAuth.getCurrentUser().updateProfile(userProfileChangeRequest);

                    firebaserAuth.getCurrentUser().sendEmailVerification();
                    firebaserAuth.getCurrentUser().reload();

                } else {
                    progressDialog.dismiss();
                    Toast.makeText(RegisterActivity.this, "Utente non inserito", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}
