package com.example.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class CreateFragment extends Fragment {

    Button post,project;
    Intent intent;
    Context context;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=(LinearLayout)inflater.inflate(R.layout.create_fragment,container,false);
        post=v.findViewById(R.id.addPost);
        project=v.findViewById(R.id.uploadProject);
        context=getContext();
        project.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent=new Intent(context,ProjectActivity.class);
                startActivity(intent);
            }
        });

        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent=new Intent(context,PostActivity.class);
                startActivity(intent);
            }
        });

        return v;
    }
}
