package com.coldsnap.lostnhound;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.UUID;

public class FoundActivity extends AppCompatActivity {

    private ImageButton back_button;
    private ImageView preview;
    private Button submit, upload;
    private EditText name;
    private Spinner type, postcode, colour;
    private DatabaseReference databasePets;
    private Uri filePath;
    private final int PICK_IMAGE_REQUEST = 71; //can use any positive number
    private FirebaseStorage storage;
    private StorageReference storageReference;
    private String imageID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_found);

        name = findViewById(R.id.petNameEt);
        colour = findViewById(R.id.colourSpn);
        type = findViewById(R.id.typeSpn);
        postcode = findViewById(R.id.postcodeSpn);
        upload = findViewById(R.id.uploadBtn);
        submit = findViewById(R.id.submitBtn);
        preview = findViewById(R.id.previewImg);
        back_button = findViewById(R.id.backBtn);

        databasePets = FirebaseDatabase.getInstance().getReference("pets");
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();


        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent foundToMainIntent = new Intent(FoundActivity.this, MainActivity.class);
                startActivity(foundToMainIntent);
            }
        });

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImage();
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                uploadImageAndProfile();

                Intent foundToMainIntent = new Intent(FoundActivity.this, MainActivity.class);
                startActivity(foundToMainIntent);

                //Toast.makeText(getApplicationContext(), "Pet posted, thank you", Toast.LENGTH_SHORT).show(); //taken out as it overlaps with "Uploaded"

            }
        });


    }

    private void chooseImage() { //method for picking an image from device gallery
        Intent intent = new Intent();
        intent.setType("image/*"); //setting intent type to image
        intent.setAction(Intent.ACTION_GET_CONTENT); //action is set to get some content, creates the image chooser UI
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST); //used to receive the result
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) { //used to display the image, in this case as a preview
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK //checks to see if request code matches PICK_IMAGE_REQUEST
                && data != null && data.getData() != null )
        {
            filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                preview.setImageBitmap(bitmap);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    private void uploadImageAndProfile() {

        if(filePath != null)
        {
            final ProgressDialog progressDialog = new ProgressDialog(this); //progressdialog is a message to user thing for processes that can take time
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            imageID = UUID.randomUUID().toString();
            StorageReference ref = storageReference.child("images/"+ imageID + "." + getFileExtension(filePath)); //setting the storage location and file name

            // if having trouble with spamming uploads, https://youtu.be/lPfQN-Sfnjw?t=19m5s
            ref.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() { //this uploads the file
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss();
                            Toast.makeText(FoundActivity.this, "Uploaded Photo", Toast.LENGTH_SHORT).show();

                            //this was below uploadImage call, above

                            String nameStr = name.getText().toString().trim();
                            String typeStr = type.getSelectedItem().toString();
                            String postcodeStr = postcode.getSelectedItem().toString();
                            String colourStr = colour.getSelectedItem().toString();
                            String imageStr = taskSnapshot.getDownloadUrl().toString(); //this is getting the firebase URL to download the image
                            //String imageStr = imageID + "." + getFileExtension(filePath); //this just sets a random ID

                            String petId = databasePets.push().getKey();
                            Pet pet = new Pet(nameStr, typeStr, postcodeStr, colourStr, imageStr);
                            databasePets.child(petId).setValue(pet); //adds pet to database as child entry of "pets"

                            //end of pasted code

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() { //this is when upload fails
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(FoundActivity.this, "Failed "+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() { //this is just the uploaded message to user
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0*taskSnapshot.getBytesTransferred()/taskSnapshot
                                    .getTotalByteCount());
                            progressDialog.setMessage("Uploaded "+(int)progress+"%");
                        }
                    });
        }
    }

    private String getFileExtension(Uri uri) { //just gets file type
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }


}
