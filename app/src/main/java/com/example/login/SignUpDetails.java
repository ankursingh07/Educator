package com.example.login;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class SignUpDetails extends AppCompatActivity {

    EditText phoneNumber,name;
    TextView username;
    TextView email;
    Intent intent;
    String emailId;
    Button save;
    DatabaseReference databaseReference;
    User user;
    String UserName="";
    FloatingActionButton fab;
    ImageView imageView;
    StorageReference storageReference;
    Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_details);
        imageView=findViewById(R.id.imageViewProfile);
        phoneNumber=findViewById(R.id.signUpActivityPhoneNumber);
        name=findViewById(R.id.signUpActivityName);
        save=findViewById(R.id.signUpActivitySave);
        email=findViewById(R.id.signUpActivityId);
        username=findViewById(R.id.signUpActivityUserName);
        fab=findViewById(R.id.floatingActionButton2);
        databaseReference= FirebaseDatabase.getInstance().getReference().child("User");
        storageReference= FirebaseStorage.getInstance().getReference();
        intent=getIntent();
        emailId=intent.getStringExtra("email");
        for(int i=0;i<emailId.length();i++){
            if(emailId.charAt(i)=='@'){
                break;
            }
            else{
                UserName+=emailId.charAt(i);
            }
        }

        username.setText(UserName);
        email.setText(emailId);



        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(imageUri!=null){
                    uploadToFireBase(imageUri,UserName);
                }else{
                    Toast.makeText(getApplicationContext(), "error", Toast.LENGTH_SHORT).show();
                }



            }
        });



        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ImagePicker.Companion.with(SignUpDetails.this)
                        .crop()                    //Crop image(Optional), Check Customization for more option
                        .compress(1024)            //Final image size will be less than 1 MB(Optional)
                        .maxResultSize(1080, 1080)    //Final image resolution will be less than 1080 x 1080(Optional)

                        .start();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        imageUri=data.getData();
        imageView.setImageURI(imageUri);
    }

    public void uploadToFireBase(Uri imageUri,String username){
        StorageReference postFile = storageReference.child(System.currentTimeMillis()+"."+getFileExtension(imageUri));
        postFile.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                postFile.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        user=new User();
                        user.setName(name.getText().toString());
                        user.setEmailId(email.getText().toString());
                        user.setPhoneNUmber(phoneNumber.getText().toString());
                        user.setUsername(username);
                        user.setImageUrl(uri.toString());
                        databaseReference.child(username).setValue(user);
                        Toast.makeText(getApplicationContext(),"Upload successfull",Toast.LENGTH_LONG).show();
                        Intent intent=new Intent(getApplicationContext(),HomeActivity.class);
                        intent.putExtra("username",username);
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
