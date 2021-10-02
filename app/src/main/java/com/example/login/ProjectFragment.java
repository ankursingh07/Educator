package com.example.login;

import android.content.Context;
import android.media.Image;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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

public class ProjectFragment extends Fragment {
    RecyclerView recyclerViewProject;
    Context context;
    ArrayList<ProjectModel> projectModelArrayList;
    DatabaseReference databaseReference;
    ProjectModel projectModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v=(LinearLayout)inflater.inflate(R.layout.project_fragment_layout,container,false);
        databaseReference= FirebaseDatabase.getInstance().getReference().child("project");
        projectModelArrayList=new ArrayList<>();
        context=getContext();
        recyclerViewProject=v.findViewById(R.id.projectRecyclerView);
        RecyclerView.Adapter projectAdapter=new PostFeedAdapter(context,projectModelArrayList);
        LinearLayoutManager projectLayoutManager=new LinearLayoutManager(context,RecyclerView.VERTICAL,false);
        recyclerViewProject.setAdapter(projectAdapter);
        recyclerViewProject.setLayoutManager(projectLayoutManager);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                    projectModel=dataSnapshot.getValue(ProjectModel.class);
                    projectModelArrayList.add(projectModel);
                }
                projectAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(context,"Failed",Toast.LENGTH_LONG).show();
            }
        });
        return v;


    }
}
