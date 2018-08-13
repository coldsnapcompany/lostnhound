package com.coldsnap.lostnhound;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private Button login_button;
    private EditText email, password;
    private FirebaseAuth mAuth;
    private TextView register_button, forgot_password, logo_name;
    Typeface logo_font;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();
        logo_font = Typeface.createFromAsset(getAssets(),"fonts/Ubuntu-R.ttf");

        //not working - interfering with device back button functionality
        //to maintain a users log in, don't have to log in every time
        //will be added back when user getting logged in even after account deleted is fixed
        if (mAuth.getCurrentUser() != null) {
            // User is logged in
            Intent loginToMainIntent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(loginToMainIntent);
        }

        // assigning UI elements to variables
        login_button = findViewById(R.id.loginBtn);
        register_button = findViewById(R.id.registerTv);
        forgot_password = findViewById(R.id.forgotTv);
        email = findViewById(R.id.emailEt);
        password = findViewById(R.id.passwordEt);
        logo_name = findViewById(R.id.logoNameTv);
        logo_name.setTypeface(logo_font);

        //button to go to reg page
        register_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loginToRegisterIntent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(loginToRegisterIntent);
            }
        });

        //button to login with entered credentials
        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String emailStr = email.getText().toString().trim();
                String passwordStr = password.getText().toString().trim();

                //credential checks
                if (TextUtils.isEmpty(emailStr)) {
                    Toast.makeText(getApplicationContext(), "Please enter your email", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(passwordStr)) {
                    Toast.makeText(getApplicationContext(), "Please enter your password", Toast.LENGTH_SHORT).show();
                    return;
                }

                //log in with entered credentials
                mAuth.signInWithEmailAndPassword(emailStr, passwordStr)
                        .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                // If sign in fails, display a message to the user. If sign in succeeds
                                // the auth state listener will be notified and logic to handle the
                                // signed in user can be handled in the listener.
                                if (!task.isSuccessful()) {
                                    // there was an error
                                    if (password.length() < 6) {
                                        password.setError(getString(R.string.minimum_password)); //live message stating password too short
                                    } else {
                                        Toast.makeText(LoginActivity.this, getString(R.string.auth_failed), Toast.LENGTH_LONG).show();
                                    }
                                } else {
                                    Intent loginToMainIntent = new Intent(LoginActivity.this, MainActivity.class);
                                    startActivity(loginToMainIntent);
                                    finish();
                                }
                            }
                        });
            }
        });

        //button to get new password
        forgot_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loginToPassForgotIntent = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
                startActivity(loginToPassForgotIntent);

            }
        });
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed(); //commented this line in order to disable back press
        //Write your code here
        finish();
        System.exit(0);
    }
}
