package com.example.erik.videogamesproject;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v4.widget.TextViewCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;

import org.w3c.dom.Text;

/**
 * Created by Erik on 10/10/2016.
 */

public class LoginActivity extends AppCompatActivity {
    private EditText txtLoginEmail;
    private EditText txtLoginPassword;
    private Button btnLogin;
    private TextView txtRegisterLink;

    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Firebase.setAndroidContext(this);

        txtLoginEmail = (EditText) findViewById(R.id.txtLoginEmail);
        txtLoginPassword = (EditText) findViewById(R.id.txtLoginPassword);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        txtRegisterLink = (TextView) findViewById(R.id.txtRegisterLink);

        progressDialog = new ProgressDialog(this);

        firebaseAuth = FirebaseAuth.getInstance();

        if (firebaseAuth.getCurrentUser() != null) {
            startActivity(new Intent(LoginActivity.this, HomeActivity.class));
            finish();
        }

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClickLogin();
            }
        });

        txtRegisterLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenRegister();
            }
        });
    }

    private void ClickLogin() {
        String email = txtLoginEmail.getText().toString().trim();
        String password = txtLoginPassword.getText().toString().trim();

        progressDialog.setMessage("Attendere...");
        progressDialog.show();

        if (TextUtils.isEmpty(email)) {
            txtLoginEmail.setError("Email errata");
            progressDialog.dismiss();
        } else if (TextUtils.isEmpty(password)) {
            txtLoginPassword.setError("Password errata");
            progressDialog.dismiss();
        } else {
            firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {

                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if (task.isSuccessful()) {
                        startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                        finish();
                        progressDialog.dismiss();
                    } else {
                        try {
                            throw task.getException();
                        } catch (FirebaseAuthInvalidCredentialsException e) {
                            e.printStackTrace();
                            Toast.makeText(LoginActivity.this, "Credenziali errate", Toast.LENGTH_LONG).show();
                            progressDialog.dismiss();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                }
            });

        }

    }

    private void OpenRegister() {
        Intent i = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(i);
    }
}
