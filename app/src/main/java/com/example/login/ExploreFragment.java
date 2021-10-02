package com.example.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

public class ExploreFragment extends Fragment {

    CardView learnCardView,projectCardView,coursesCardView;
    ImageView imageView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v=(LinearLayout)inflater.inflate(R.layout.explore_fragment,container,false);
        learnCardView=v.findViewById(R.id.learnCard);
        imageView=v.findViewById(R.id.exploreLearn);
        projectCardView=v.findViewById(R.id.exploreProjects);
        coursesCardView=v.findViewById(R.id.explore_course);
        learnCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getContext(),"Clicked",Toast.LENGTH_LONG).show();
                Intent intent=new Intent(getActivity(),LearnActivity.class);
                startActivity(intent);
            }
        });
        projectCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getContext(),"Clicked",Toast.LENGTH_LONG).show();
                Intent intent=new Intent(getActivity(),ProjectMenuActivity.class);
                startActivity(intent);
            }
        });
        coursesCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getContext(),"Clicked",Toast.LENGTH_LONG).show();
                Intent intent=new Intent(getActivity(),CoursesActivity.class);
                startActivity(intent);
            }
        });
        return v;
    }
}
