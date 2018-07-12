package com.coldsnap.lostnhound;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ChangePasswordActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private ImageButton back_button;
    private Button changePassword;
    private EditText emailAddress;
    //private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        mAuth = FirebaseAuth.getInstance();
        changePassword = findViewById(R.id.changepassBtn);
        emailAddress = findViewById(R.id.passwordChangeEt);
        back_button = findViewById(R.id.backBtn);

        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent changePasswordToSettingsIntent = new Intent(ChangePasswordActivity.this, SettingsActivity.class);
                startActivity(changePasswordToSettingsIntent);
            }
        });


        //will add back in when it works, crashes app
        //could try use forgot password functionality
//        changePassword.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String emailStr = emailAddress.getText().toString().trim();
//
//                //forgot password is completely done on login page, checks for valid email entered
//                if (TextUtils.isEmpty(emailStr)) {
//                    Toast.makeText(getApplicationContext(), "Please enter your email above", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//
//                user.updatePassword(changePassword.getText().toString().trim())
//                        .addOnCompleteListener(new OnCompleteListener<Void>() {
//                            @Override
//                            public void onComplete(@NonNull Task<Void> task) {
//                                if (task.isSuccessful() && changePassword.length() > 5) {
//                                    Toast.makeText(ChangePasswordActivity.this, "Password is updated!", Toast.LENGTH_SHORT).show();
//                                } else {
//                                    Toast.makeText(ChangePasswordActivity.this, "Failed to update password!", Toast.LENGTH_SHORT).show();
//                                }
//                            }
//                        });
//            }
//        });

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(ChangePasswordActivity.this, SettingsActivity.class);
        startActivity(intent);
    }

}