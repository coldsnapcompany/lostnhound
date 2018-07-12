package com.coldsnap.lostnhound;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DeleteAccountActivity extends AppCompatActivity {

    private FirebaseUser currentUser;
    private Button delete_button;
    private ImageButton back_button;
    private DatabaseReference databaseUser;
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_account);

        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        delete_button = findViewById(R.id.deleteBtn);
        back_button = findViewById(R.id.backBtn);
        userId = currentUser.getUid();
        databaseUser = FirebaseDatabase.getInstance().getReference("users/" + userId); //reference to specific user using path: users/userID


        delete_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // delete user from DB
                databaseUser.removeValue();
                //deletes user from auth DB
                currentUser.delete();

                Intent deleteAccToLoginIntent = new Intent(DeleteAccountActivity.this, LoginActivity.class);
                startActivity(deleteAccToLoginIntent);
                Toast.makeText(getApplicationContext(), "Account successfully disabled.", Toast.LENGTH_SHORT).show();
            }
        });

        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent deleteAccToSettingsIntent = new Intent(DeleteAccountActivity.this, SettingsActivity.class);
                startActivity(deleteAccToSettingsIntent);
            }
        });


    }

    @Override
    public void onBackPressed() {
        Intent deleteAccToSettingsIntent = new Intent(DeleteAccountActivity.this, SettingsActivity.class);
        startActivity(deleteAccToSettingsIntent);
    }

}
