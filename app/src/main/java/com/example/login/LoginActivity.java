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
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    EditText loginEmail,loginPswd;
    Button loginbtn;
    FirebaseAuth mFirebaseAuth;
    FirebaseAuth.AuthStateListener mAuthStateListener;
    Intent intent;
    String username="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        intent=getIntent();
        mFirebaseAuth = FirebaseAuth.getInstance();
        loginbtn = findViewById(R.id.loginBtn);
        loginEmail = findViewById(R.id.loginId);
        loginPswd = findViewById(R.id.loginPswd);
        //Login = findViewById(R.id.textview);


        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser mFirebaseUser = mFirebaseAuth.getCurrentUser();
                if(mFirebaseUser != null) {
                    Toast.makeText(LoginActivity.this,"You are Logged In",Toast.LENGTH_LONG).show();
                    Intent home = new Intent(LoginActivity.this,HomeActivity.class);
                    startActivity(home);
                }
                else {
                    Toast.makeText(LoginActivity.this,"Please Login",Toast.LENGTH_LONG).show();
                }
            }
        };
        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = loginEmail.getText().toString();
                for(int i=0;i<email.length();i++) {
                    if (email.charAt(i) == '@') {
                        break;
                    } else {
                        username += email.charAt(i);
                    }
                }
                String pwd = loginPswd.getText().toString();
                if(email.isEmpty()) {
                    loginEmail.setError("Provide correct email id");
                    loginEmail.requestFocus();
                }
                else if(pwd.isEmpty()) {
                    loginPswd.setError("Provide correct password");
                    loginPswd.requestFocus();
                }
                else if(email.isEmpty() && pwd.isEmpty()) {
                    Toast.makeText(LoginActivity.this,"Provide correct email id and password",Toast.LENGTH_LONG).show();
                }
                else if(!(email.isEmpty() && pwd.isEmpty())) {
                    mFirebaseAuth.signInWithEmailAndPassword(email, pwd)
                            .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if(!task.isSuccessful()) {
                                        Toast.makeText(LoginActivity.this,"Login Unsuccessful, Please Login Again",Toast.LENGTH_LONG).show();
                                    }
                                    else {
                                        Intent i = new Intent(LoginActivity.this,HomeActivity.class);
                                        i.putExtra("username",username);
                                        startActivity(i);
                                    }
                                }
                            });
                }
                else {
                    Toast.makeText(LoginActivity.this,"Error Occured",Toast.LENGTH_LONG).show();
                }
            }
        });
        /*
        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });*/

    }

    @Override
    protected void onStart() {
        super.onStart();
        mFirebaseAuth.addAuthStateListener(mAuthStateListener);
    }
}