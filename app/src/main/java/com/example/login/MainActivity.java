package com.example.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    EditText emailEditText,passwordEditText;
    Button signUpBtn,loginBtn;
    FirebaseAuth mFirebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFirebaseAuth = FirebaseAuth.getInstance();
        signUpBtn = findViewById(R.id.signUp);
        emailEditText = findViewById(R.id.signUpEmailId);
        passwordEditText = findViewById(R.id.signUpPassword);
        loginBtn = findViewById(R.id.LoginSent);
        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailEditText.getText().toString();
                String pwd = passwordEditText.getText().toString();
                if(email.isEmpty()) {
                    emailEditText.setError("Provide correct email id");
                    passwordEditText.requestFocus();
                }
                else if(pwd.isEmpty()) {
                    passwordEditText.setError("Provide correct password");
                    passwordEditText.requestFocus();
                }
                else if(email.isEmpty() && pwd.isEmpty()) {
                    Toast.makeText(MainActivity.this,"Provide correct email id and password",Toast.LENGTH_LONG).show();
                }
                else if(!(email.isEmpty() && pwd.isEmpty())) {
                    mFirebaseAuth.createUserWithEmailAndPassword(email, pwd)
                            .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if(!task.isSuccessful()) {
                                        Toast.makeText(MainActivity.this,"SignUp Unsuccessful, Please Try Again",Toast.LENGTH_LONG).show();
                                    }
                                    else {
                                        Intent intent=new Intent(MainActivity.this,SignUpDetails.class);
                                        intent.putExtra("email",emailEditText.getText().toString());
                                        startActivity(intent);
                                    }
                                }
                            });
                }
                else {
                    Toast.makeText(MainActivity.this,"Error Occured",Toast.LENGTH_LONG).show();
                }
            }
        });
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,LoginActivity.class);
                startActivity(i);
            }
        });


    }
}