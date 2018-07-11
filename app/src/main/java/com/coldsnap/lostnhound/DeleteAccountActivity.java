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
    private DatabaseReference databaseUsers;
    private String key;
    private String emailAddress;
    private DatabaseReference certainUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_account);

        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        delete_button = findViewById(R.id.deleteBtn);
        back_button = findViewById(R.id.backBtn);
        databaseUsers = FirebaseDatabase.getInstance().getReference().child("users");
        key = currentUser.getUid();

        emailAddress = currentUser.getEmail(); //tested, works

        //will be put back when delete profile in DB is implemented
        delete_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // delete user from DB code goes here


//                //currently deletes all profile entries in DB
//                databaseUsers.child("email").orderByChild("email").equalTo(emailAddress).addListenerForSingleValueEvent(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(DataSnapshot dataSnapshot) {
//
//                            String userKey = dataSnapshot.getKey();
//                            Toast.makeText(getApplicationContext(), userKey, Toast.LENGTH_SHORT).show();
//                            //certainUser = FirebaseDatabase.getInstance().getReference().child("users").child(userKey); //maybe take out .child("users")
//
//                            dataSnapshot.getRef().removeValue();
//
//                    }
//
//                    @Override
//                    public void onCancelled(DatabaseError databaseError) {
//
//                    }
//                });


                currentUser.delete(); //deletes from auth DB

                Intent registerIntent = new Intent(DeleteAccountActivity.this, LoginActivity.class);
                startActivity(registerIntent);
                Toast.makeText(getApplicationContext(), "Account successfully disabled.", Toast.LENGTH_SHORT).show();
            }
        });

        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DeleteAccountActivity.this, SettingsActivity.class);
                startActivity(intent);
            }
        });



    }

}
