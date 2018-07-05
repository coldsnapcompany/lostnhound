package com.coldsnap.lostnhound;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FoundActivity extends AppCompatActivity {

    private ImageButton back_button;
    private Button submit;
    private EditText name;
    private Spinner type, postcode, colour;
    private DatabaseReference databasePets;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_found);

        databasePets = FirebaseDatabase.getInstance().getReference("pets");
        name = findViewById(R.id.petNameEt);
        colour = findViewById(R.id.colourSpn);
        type = findViewById(R.id.typeSpn);
        postcode = findViewById(R.id.postcodeSpn);
        submit = findViewById(R.id.submitBtn);
        back_button = findViewById(R.id.backBtn);


        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FoundActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String nameStr = name.getText().toString().trim();
                String typeStr = type.getSelectedItem().toString();
                String postcodeStr = postcode.getSelectedItem().toString();
                String colourStr = colour.getSelectedItem().toString();

                String userId = databasePets.push().getKey();
                Pet pet = new Pet(nameStr, typeStr, postcodeStr, colourStr);
                databasePets.child(userId).setValue(pet);

                Intent registerIntent = new Intent(FoundActivity.this, MainActivity.class);
                startActivity(registerIntent);

                Toast.makeText(getApplicationContext(), "Pet posted, thank you", Toast.LENGTH_SHORT).show();

            }
        });


    }
}
