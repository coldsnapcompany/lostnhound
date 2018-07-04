package com.coldsnap.lostnhound;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class DeleteAccountActivity extends AppCompatActivity {

    private FirebaseUser user;
    private Button delete_button;
    private ImageButton back_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_account);

        user = FirebaseAuth.getInstance().getCurrentUser();
        delete_button = findViewById(R.id.deleteBtn);
        back_button = findViewById(R.id.backBtn);

        delete_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user.delete();

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

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(DeleteAccountActivity.this, SettingsActivity.class);
        startActivity(intent);
    }

}
