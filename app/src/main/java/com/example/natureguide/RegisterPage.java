package com.example.natureguide;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegisterPage extends AppCompatActivity {

    //fields
    EditText userEmail, userPass1, userPass2;//userPass1 and userPass2 exist to check pass identity
    Button btnRegister;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_page);

        //connect object to views
        //user info
        userEmail = findViewById(R.id.RegisterUserEmail);
        userPass1 = findViewById(R.id.RegisterUserPassword1);
        userPass2 = findViewById(R.id.RegisterUserPassword2);
        //buttons
        btnRegister = findViewById(R.id.RegisterBtnRegister);
        mAuth = FirebaseAuth.getInstance();

        //Registration
        (findViewById(R.id.RegisterBtnRegister)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /* Checking if first password field matches the second
                if (userPass1.getText().toString() != userPass2.getText().toString())
                 */
                mAuth.createUserWithEmailAndPassword(userEmail.getText().toString(), userPass1.getText().toString())
                        .addOnCompleteListener(RegisterPage.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    updateUI(user);
                                } else {
                                    // If sign in fails, display a message to the user.
                                    updateUI(null);
                                }

                                // ...
                            }
                        });
            }
        });

    }

    private void updateUI(FirebaseUser user) {
        if (user != null) {
            startActivity(new Intent(RegisterPage.this, MainActivity.class));

        } else {
            Toast.makeText(RegisterPage.this, "Authentication failed.",
                    Toast.LENGTH_SHORT).show();
        }
    }
}
