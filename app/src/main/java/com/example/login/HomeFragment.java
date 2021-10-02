package com.example.login;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.zip.Inflater;

public class HomeFragment extends Fragment {

    RecyclerView recyclerView;
    Context context;
    ArrayList<PostModel> modelArrayList;

    DatabaseReference databaseReference;
    PostModel postModel;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View v=(LinearLayout)inflater.inflate(R.layout.home_fragment,container,false);
        context=getContext();
        databaseReference= FirebaseDatabase.getInstance().getReference().child("post");
        modelArrayList=new ArrayList<>();
        recyclerView=v.findViewById(R.id.homeRecyclerView);
        RecyclerView.Adapter homeAdapter=new HomeFeedAdapter(context,modelArrayList);
        LinearLayoutManager homeLayoutManager=new LinearLayoutManager(context,RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(homeLayoutManager);
        recyclerView.setAdapter(homeAdapter);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                    postModel=dataSnapshot.getValue(PostModel.class);

                    modelArrayList.add(postModel);
                }
                homeAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(context,"Failed",Toast.LENGTH_LONG).show();
            }
        });


        return v;


    }
}
