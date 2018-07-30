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

import java.util.ArrayList;
import java.util.List;

public class LostActivity extends AppCompatActivity {

    private ImageButton back_button;

    RecyclerView recyclerView;
    PetAdapter adapter;
    private DatabaseReference petsRef;

    List<Pet> petList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lost);

        back_button = findViewById(R.id.backBtn);
        petsRef = FirebaseDatabase.getInstance().getReference("pets");

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

        petList.add(
                new Pet(
                        "Butch",
                        "Cat",
                        "Dublin 13",
                        "Tan"));

        petList.add(
                new Pet(
                        "Dylan",
                        "Dog",
                        "Dublin 6W",
                        "Brindle"));

        petList.add(
                new Pet(
                        "Oscar",
                        "Bird",
                        "Dublin 4",
                        "Blue"));

        petList.add(
                new Pet(
                        "Oscar",
                        "Bird",
                        "Dublin 4",
                        "Blue"));

        petList.add(
                new Pet(
                        "Oscar",
                        "Bird",
                        "Dublin 4",
                        "Blue"));

        petList.add(
                new Pet(
                        "Oscar",
                        "Bird",
                        "Dublin 4",
                        "Blue"));

        petList.add(
                new Pet(
                        "Oscar",
                        "Bird",
                        "Dublin 4",
                        "Blue"));


        adapter = new PetAdapter(this, petList);
        recyclerView.setAdapter(adapter); //set this adapter to the recyclerview

    }

//    @Override
//    public void onStart(){
//
//        super.onStart();
//
//        petsRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//
//                petList.clear();
//
//                for(DataSnapshot petSnapshot: dataSnapshot.getChildren()){
//
//                    Pet pet = petSnapshot.getValue(Pet.class);
//
//                    petList.add(pet);
//
//                }
//
//                PetAdapter adapter = new PetAdapter(getApplicationContext(), petList);
//                recyclerView.setAdapter(adapter); //set this adapter to the recyclerview
//
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
//
//    }
}
