package com.example.erik.videogamesproject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by Erik on 10/10/2016.
 */

public class RegisterActivity extends AppCompatActivity {

    private EditText nameText;
    private EditText surnameText;
    private EditText emailText;
    private EditText passwordText;
    private EditText confirmPasswordTxt;

    private ProgressDialog progressDialog;

    private FirebaseAuth firebaserAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Firebase.setAndroidContext(this);
        firebaserAuth = FirebaseAuth.getInstance();

        progressDialog = new ProgressDialog(this);


    }

    public void clickRegister(View view){

        nameText = (EditText) findViewById(R.id.txtRegisterName);
        surnameText = (EditText) findViewById(R.id.txtRegisterSurname);
        emailText = (EditText) findViewById(R.id.txtRegisterEmail);
        passwordText = (EditText) findViewById(R.id.txtRegisterPassword);
        confirmPasswordTxt = (EditText) findViewById(R.id.txtRegisterConfirmPassword);


        String name = nameText.getText().toString().trim();
        String surname = surnameText.getText().toString().trim();
        String email = emailText.getText().toString().trim();
        String password = passwordText.getText().toString().trim();
        String confirmPassword = confirmPasswordTxt.getText().toString().trim();

        if(name.isEmpty()){
            nameText.setError("Inserire nome");
            return;

        }

        if(surname.isEmpty()){
            nameText.setError("Inserire cognome");
            return;
        }

        if(email.isEmpty()){

            nameText.setError("Email errata o già esistente");
            return;
        }

        if(password.isEmpty() || password.length() < 6){
            passwordText.setError("Password troppo corta, almeno 6 caratteri");
            return;
        }

        if(!password.equals(confirmPassword)){
            Toast.makeText(RegisterActivity.this, "Password e conferma Password errate", Toast.LENGTH_SHORT).show();
            return;
        }


        progressDialog.setMessage("Registrazione User");
        progressDialog.show();
        firebaserAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(this,new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful()){
                    progressDialog.dismiss();
                    Toast.makeText(RegisterActivity.this,"Utente Inserito",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(RegisterActivity.this,HomeActivity.class));
                }else{
                    progressDialog.dismiss();
                    Toast.makeText(RegisterActivity.this,"Utente Non Inserito",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
