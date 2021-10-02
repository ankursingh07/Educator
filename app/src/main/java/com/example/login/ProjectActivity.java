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

public class ProjectActivity extends AppCompatActivity {

    EditText heading,fullDescription,briefDescription,githubUrl,HostUrl;
    ImageView imageView;
    Uri imageUri;
    Button addImage,upload;
    DatabaseReference databaseReference;
    StorageReference storageReference;
    ProjectModel projectModel;
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project);

        getSupportActionBar().hide();
        intent=getIntent();
        databaseReference= FirebaseDatabase.getInstance().getReference().child("project");
        storageReference= FirebaseStorage.getInstance().getReference();

        heading=findViewById(R.id.projectEditText);
        fullDescription=findViewById(R.id.projectFullDescription);
        briefDescription=findViewById(R.id.projectBriefDescription);
        githubUrl=findViewById(R.id.githubUrl);
        HostUrl=findViewById(R.id.hostUrl);
        imageView=findViewById(R.id.projectImage);
        addImage=findViewById(R.id.projectImageAddButton);
        upload=findViewById(R.id.uploadProjectButton);

        addImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImagePicker.Companion.with(ProjectActivity.this)
                        .crop()	    			//Crop image(Optional), Check Customization for more option
                        .compress(1024)			//Final image size will be less than 1 MB(Optional)
                        .maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)
                        .start();

            }
        });

        upload.setOnClickListener(new View.OnClickListener() {
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
                        projectModel=new ProjectModel();
                        projectModel.setHeading(heading.getText().toString());
                        projectModel.setbDes(briefDescription.getText().toString());
                        projectModel.setfDes(fullDescription.getText().toString());
                        projectModel.setGit(githubUrl.getText().toString());
                        projectModel.setHost(HostUrl.getText().toString());
                        projectModel.setImageUrl(uri.toString());
                        databaseReference.push().setValue(projectModel);
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