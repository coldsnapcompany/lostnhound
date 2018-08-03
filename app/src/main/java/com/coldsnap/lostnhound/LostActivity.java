package com.coldsnap.lostnhound;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class LostActivity extends AppCompatActivity {

    private ImageButton back_button;

    RecyclerView recyclerView;
    PetAdapter adapter;
    private DatabaseReference petsRef;

    List<Pet> petList;
    private StorageReference mStorageRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lost);

        back_button = findViewById(R.id.backBtn);
        petsRef = FirebaseDatabase.getInstance().getReference("pets");
        mStorageRef = FirebaseStorage.getInstance().getReference();

        petList = new ArrayList<>();
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this)); //for a vertical recycler view

        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent lostToMainIntent = new Intent(LostActivity.this, MainActivity.class);
                startActivity(lostToMainIntent);
            }
        });

        petsRef.addValueEventListener(new ValueEventListener() { //reads DB for pet entries
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) { //doesn't just read on data change, also reads on initial activity start

                for (DataSnapshot petSnapshot : dataSnapshot.getChildren()) { //goes through all children of db ref, in this case Pets

                    Pet pet = new Pet(); //new Pet object
                    pet.setName(petSnapshot.getValue(Pet.class).getName()); //sets info from each DB entry to the Pet object
                    pet.setType(petSnapshot.getValue(Pet.class).getType());
                    pet.setPostcode(petSnapshot.getValue(Pet.class).getPostcode());
                    pet.setColour(petSnapshot.getValue(Pet.class).getColour());
                    pet.setImage(petSnapshot.getValue(Pet.class).getImage());

                    petList.add(pet); //adds Pet object to List, then repeats for each DB entry

                }

                adapter = new PetAdapter(getApplicationContext(), petList); //sends the Pet List through the adapter
                recyclerView.setAdapter(adapter); //set this adapter to the recyclerview


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}