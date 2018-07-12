package com.coldsnap.lostnhound;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SettingsActivity extends AppCompatActivity {

    private Button change_password, change_email, delete_account, sign_out;
    private ImageButton back_button;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        mAuth = FirebaseAuth.getInstance();

        change_password = findViewById(R.id.changePassBtn);
        change_email = findViewById(R.id.changeEmailBtn);
        delete_account = findViewById(R.id.deleteAccBtn);
        back_button = findViewById(R.id.backBtn);
        sign_out = findViewById(R.id.signOutBtn);

        change_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent settingsToChngPassIntent = new Intent(SettingsActivity.this, ChangePasswordActivity.class);
                startActivity(settingsToChngPassIntent);
                finish();
            }
        });

        change_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent settingsToChngEmailIntent = new Intent(SettingsActivity.this, ChangeEmailActivity.class);
                startActivity(settingsToChngEmailIntent);
                finish();
            }
        });

        delete_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent settingsToDeleteAccIntent = new Intent(SettingsActivity.this, DeleteAccountActivity.class);
                startActivity(settingsToDeleteAccIntent);
                finish();
            }
        });

        sign_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut(); //no additional page, just signs out here
                Intent settingsToLoginIntent = new Intent(SettingsActivity.this, LoginActivity.class);
                startActivity(settingsToLoginIntent);
                Toast.makeText(getApplicationContext(), "Signed out", Toast.LENGTH_SHORT).show();
            }
        });

        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent settingsToMainIntent = new Intent(SettingsActivity.this, MainActivity.class);
                startActivity(settingsToMainIntent);
            }
        });

    }

    @Override
    public void onBackPressed() {
        Intent deleteAccToSettingsIntent = new Intent(SettingsActivity.this, LoginActivity.class);
        startActivity(deleteAccToSettingsIntent);
    }
}
