package com.example.login;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class HomeActivity extends AppCompatActivity implements NavigationBarView.OnItemSelectedListener {

    Button logOutBtn;
    FrameLayout frameLayout;
    Fragment fragment;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    BottomNavigationView bottomNavigationView;
    String UserName="";
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        //logOutBtn=findViewById(R.id.logOut);
        bottomNavigationView=findViewById(R.id.bottomNav);
        frameLayout=findViewById(R.id.fragContainer);
        intent=getIntent();
        UserName=intent.getStringExtra("username");

        fragment=new HomeFragment();
        fragmentManager=getSupportFragmentManager();
        fragmentTransaction=fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragContainer,fragment);
        fragmentTransaction.commit();

        bottomNavigationView.setOnItemSelectedListener(this);
        /*
        logOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent i = new Intent(HomeActivity.this,MainActivity.class);
                startActivity(i);
            }
        });*/
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.homeBottomNav:fragment=new HomeFragment();
                break;
            case R.id.exploreBottomNav:fragment=new ExploreFragment();
                break;

            case R.id.createBottomNav:fragment= new CreateFragment();
                break;

            case R.id.accountBottomNav:fragment=new ProfileFragment();
                break;

            case R.id.projectBottomNav:fragment=new ProjectFragment();
                    break;
        }
        fragmentManager=getSupportFragmentManager();
        fragmentTransaction=fragmentManager.beginTransaction();
        Bundle bundle=new Bundle();
        bundle.putString("username",UserName);
        fragment.setArguments(bundle);
        fragmentTransaction.replace(R.id.fragContainer,fragment);
        fragmentTransaction.commit();
        return true;
    }

    /*
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        Fragment fragment=new ProfileFragment();
        int fid=fragment.getId();
        FragmentManager fragmentManager=getSupportFragmentManager().findFragmentById(fid);

    }*/
}