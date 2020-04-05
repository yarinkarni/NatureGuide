package com.example.natureguide;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginPage extends AppCompatActivity {

    //fields
    EditText userEmail,userPass;
    Button btnLogin,btnMoveToRegister;
    private FirebaseAuth mAuth;

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null){
            // Checking if user signed in
            startActivity(new Intent(LoginPage.this, MainActivity.class));
        }
        else{
            // If not signed in doing nothing..
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);
        mAuth = FirebaseAuth.getInstance();

        //connect object to views
        //user info
        userEmail=findViewById(R.id.LoginUserEmail);
        userPass=findViewById(R.id.LoginUserPassword);
        //buttons
        btnLogin=findViewById(R.id.LoginBtnLogin);
        btnMoveToRegister=findViewById(R.id.LoginBtnMoveToRegister);

        btnMoveToRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginPage.this, RegisterPage.class));
            }
        });



    }
}
