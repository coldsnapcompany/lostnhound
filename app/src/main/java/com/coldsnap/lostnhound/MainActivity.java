package com.coldsnap.lostnhound;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private ImageButton settings_button;
    private DatabaseReference databaseUsers;
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    private String userId;
    private String fullName;
    private Button lost_button, found_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        databaseUsers = FirebaseDatabase.getInstance().getReference("users"); //users DB reference
        settings_button = findViewById(R.id.settingsBtn);
        lost_button = findViewById(R.id.lostBtn);
        found_button = findViewById(R.id.foundBtn);
        userId = currentUser.getUid(); //not used


        settings_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mainToSettingsIntent = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(mainToSettingsIntent);
            }
        });

        lost_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mainToLostIntent = new Intent(MainActivity.this, LostActivity.class);
                startActivity(mainToLostIntent);
            }
        });

        found_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mainToFoundIntent = new Intent(MainActivity.this, FoundActivity.class);
                startActivity(mainToFoundIntent);
            }
        });

    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed(); //commented this line in order to disable back press
        //Write your code here
        Toast.makeText(getApplicationContext(), "Back press disabled!", Toast.LENGTH_SHORT).show();
    }

}
