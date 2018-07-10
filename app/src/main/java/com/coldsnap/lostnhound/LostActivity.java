package com.coldsnap.lostnhound;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class LostActivity extends AppCompatActivity {

    private ImageButton back_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lost);

        back_button = findViewById(R.id.backBtn);

        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LostActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }
}
