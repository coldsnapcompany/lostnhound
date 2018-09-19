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
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseUser userDB; //is used
    private Button register_detailsBtn;
    private ImageButton back_button;
    private EditText full_name, email, password;
    private DatabaseReference databaseUsers;
    private String userDBUID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();
        databaseUsers = FirebaseDatabase.getInstance().getReference("users");

        // assigning UI elements to variables
        register_detailsBtn = findViewById(R.id.registerdetailsBtn);
        full_name = findViewById(R.id.nameregEt);
        email = findViewById(R.id.emailregEt);
        password = findViewById(R.id.passwordregEt);
        back_button = findViewById(R.id.backBtn);

        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent settingsToMainIntent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(settingsToMainIntent);
            }
        });

        //gets data fields, checks them and creates user in auth DB
        register_detailsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // taking EditText values and putting them into Strings
                final String nameStr = full_name.getText().toString().trim();
                final String emailStr = email.getText().toString().trim();
                final String passwordStr = password.getText().toString().trim();

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

                //adds user to auth DB
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

                                    //signs user in after creating the user in auth DB
                                    mAuth.signInWithEmailAndPassword(emailStr, passwordStr) //if reg is successful, signs user in
                                            .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                                                @Override
                                                public void onComplete(@NonNull Task<AuthResult> task) {
                                                    // If sign in fails, display a message to the user. If sign in succeeds
                                                    // the auth state listener will be notified and logic to handle the
                                                    // signed in user can be handled in the listener.
                                                    if (!task.isSuccessful()) {
                                                        // there was an error logging in
                                                        Toast.makeText(RegisterActivity.this, getString(R.string.auth_failed), Toast.LENGTH_LONG).show();

                                                    } else { //when log in is successful, adds profile to DB with auth UID not key

                                                        userDB = FirebaseAuth.getInstance().getCurrentUser(); //these 2 lines need to be as only now does a current user exist
                                                        userDBUID = FirebaseAuth.getInstance().getCurrentUser().getUid(); // can now get userID from current user as its just been created

                                                        User user = new User(nameStr, emailStr, userDBUID); //makes a User object
                                                        databaseUsers.child(userDBUID).setValue(user); //makes a child for "users" with auth userID as key and pops with user object

                                                        Intent registerToMainIntent = new Intent(RegisterActivity.this, MainActivity.class);
                                                        startActivity(registerToMainIntent);
                                                    }
                                                }
                                            });
                                    Toast.makeText(RegisterActivity.this, "Success!", Toast.LENGTH_SHORT).show();
                                    //finish();
                                }
                            }
                });

            }
        });

    }
}
