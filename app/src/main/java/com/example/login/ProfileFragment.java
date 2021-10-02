package com.example.login;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileFragment extends Fragment {

    MenuItem menuItem;
    TextView name,phonenumber,email;
    DatabaseReference databaseReference;
    Intent intent;
    String Name,Phone,Email,UserName;
    ValueEventListener valueEventListener;
    SignUpDetails signUpDetails;
    ImageView imageView;
    Activity activity;
    FirebaseAuth firebaseAuth;
    FirebaseAuth.AuthStateListener authStateListener;
    String imageUrl;
    Uri uri;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        //SignUpDetails signUpDetails=(SignUpDetails) getActivity();
        //UserName=signUpDetails.returnUserName();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        //getActivity().getActionBar().hide();
        activity=getActivity();
        View v=(ScrollView)inflater.inflate(R.layout.profile_fragment,container,false);
        name=v.findViewById(R.id.profileName);
        phonenumber=v.findViewById(R.id.profilePhoneNumber);
        email=v.findViewById(R.id.profileEmailId);
        imageView=v.findViewById(R.id.profileImage);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImagePicker.Companion.with(ProfileFragment.this)
                        .start();


            }
        });

        Bundle bundle=getArguments();
        if(bundle!=null){
            UserName=bundle.getString("username");
        }
        else{
            Toast.makeText(getContext(),"Failed-null",Toast.LENGTH_LONG).show();
        }
        //signUpDetails=new SignUpDetails();
        //UserName=signUpDetails.returnUserName();


        try{
            databaseReference= FirebaseDatabase.getInstance().getReference().child("User").child(UserName);
            valueEventListener=new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    Name=snapshot.child("name").getValue().toString();
                    Phone=snapshot.child("phoneNUmber").getValue().toString();
                    Email=snapshot.child("emailId").getValue().toString();
                    imageUrl=snapshot.child("imageUrl").getValue().toString();
                    name.setText(Name);
                    phonenumber.setText(Phone);
                    email.setText(Email);
                    uri=Uri.parse(imageUrl);
                    Glide.with(getContext()).load(uri).into(imageView);
                    databaseReference.removeEventListener(valueEventListener);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            };
            databaseReference.addValueEventListener(valueEventListener);
        }catch (Exception e){
            Toast.makeText(getContext(),"Failed"+e,Toast.LENGTH_LONG).show();
        }


        return v;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        getActivity().getMenuInflater().inflate(R.menu.logout_menu,menu);
        MenuItem menuItem=menu.findItem(R.id.editProfile);
        MenuItem logout=menu.findItem(R.id.logOutButton);

        menuItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Intent intent=new Intent(getActivity(),EditProfileActivity.class);
                startActivity(intent);
                return true;
            }
        });

        logout.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                try {
                    FirebaseAuth.getInstance().signOut();
                    Intent i = new Intent(getContext(),MainActivity.class);
                    startActivity(i);
                }catch (Exception e){
                    Toast.makeText(getContext(),""+e,Toast.LENGTH_LONG).show();
                }

                return true;
            }
        });
        super.onCreateOptionsMenu(menu, inflater);
    }

}
