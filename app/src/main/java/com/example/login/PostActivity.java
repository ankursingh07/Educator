package com.example.login;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class PostActivity extends AppCompatActivity {

    ImageView imageView;
    EditText text;
    Button setImage,post;
    DatabaseReference databaseReference;
    StorageReference storageReference;
    Uri imageUri;
    PostModel postModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        databaseReference= FirebaseDatabase.getInstance().getReference().child("post");
        storageReference= FirebaseStorage.getInstance().getReference();
        getSupportActionBar().hide();

        imageView=findViewById(R.id.postImage);
        setImage=findViewById(R.id.postAddImageButton);
        post=findViewById(R.id.postButton);
        text=findViewById(R.id.postEditText);

        setImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImagePicker.Companion.with(PostActivity.this)
                        .crop()	    			//Crop image(Optional), Check Customization for more option
                       .compress(1024)			//Final image size will be less than 1 MB(Optional)
                        .maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)
                        .start();

            }
        });

        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(imageUri!=null){
                    uploadToFireBase(imageUri);
                }else{
                    Toast.makeText(getApplicationContext(), "error", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        imageUri=data.getData();
        imageView.setImageURI(imageUri);
    }

    public void uploadToFireBase(Uri imageUri){
        StorageReference postFile = storageReference.child(System.currentTimeMillis()+"."+getFileExtension(imageUri));
        postFile.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                postFile.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        postModel=new PostModel();
                        postModel.setText(text.getText().toString());
                        postModel.setImageurl(uri.toString());
                        databaseReference.push().setValue(postModel);
                        Toast.makeText(getApplicationContext(),"Upload successfull",Toast.LENGTH_LONG).show();
                        Intent intent=new Intent(getApplicationContext(),HomeActivity.class);
                        startActivity(intent);
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(),"Upload unccessfull",Toast.LENGTH_LONG).show();
            }
        });
    }

    public String getFileExtension(Uri imageUri){
        ContentResolver cr=getContentResolver();
        MimeTypeMap mimeTypeMap=MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(cr.getType(imageUri));
    }
}