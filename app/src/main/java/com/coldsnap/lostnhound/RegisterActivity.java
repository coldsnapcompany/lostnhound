package com.coldsnap.lostnhound;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegisterActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private Button register_detailsBtn;
    private EditText full_name, email, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();

        // assigning UI elements to variables
        register_detailsBtn = findViewById(R.id.registerdetailsBtn);
        full_name = findViewById(R.id.nameregEt);
        email = findViewById(R.id.emailregEt);
        password = findViewById(R.id.passwordregEt);

        register_detailsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // taking EditText values and putting them into Strings
                String nameStr = full_name.getText().toString().trim();
                String emailStr = email.getText().toString().trim();
                String passwordStr = password.getText().toString().trim();

                // checks that registration form is completed correctly
                if (TextUtils.isEmpty(nameStr)) {
                    Toast.makeText(getApplicationContext(), "Please enter your full name", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(emailStr)) {
                    Toast.makeText(getApplicationContext(), "Please enter your email", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(passwordStr)) {
                    Toast.makeText(getApplicationContext(), "Please enter a password", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (passwordStr.length() < 6) {
                    Toast.makeText(getApplicationContext(), "Password too short, enter minimum 6 characters!", Toast.LENGTH_SHORT).show();
                    return;
                }

                //adds user to Firebase auth DB
                mAuth.createUserWithEmailAndPassword(emailStr, passwordStr)
                        .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                // If sign in fails, display a message to the user. If sign in succeeds
                                // the auth state listener will be notified and logic to handle the
                                // signed in user can be handled in the listener.
                                if (!task.isSuccessful()) {
                                    Toast.makeText(RegisterActivity.this, "Email already in use or invalid format" ,
                                            Toast.LENGTH_SHORT).show();
                                } else {
                                    startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                                    Toast.makeText(RegisterActivity.this, "Please Log In", Toast.LENGTH_SHORT).show();
                                    finish();
                                }
                            }
                });


            }
        });

    }
}
