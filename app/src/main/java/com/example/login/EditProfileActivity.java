package com.example.login;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class EditProfileActivity extends AppCompatActivity {


    ImageView cover;
    LinearLayout layoutlist;
    Button buttonAdd;
    List<String> teamList = new ArrayList<>();
    FloatingActionButton fab;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        layoutlist = findViewById(R.id.layout_list);
        buttonAdd=findViewById(R.id.button_add);

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addView();
            }
        });

        teamList.add("web");
        teamList.add("app");
        teamList.add("dsa");
        teamList.add("dbms");
        teamList.add("networking");
        teamList.add("sql");
        teamList.add("C++");
        teamList.add("C");

        cover=findViewById(R.id.imageView2);
        fab=findViewById(R.id.floatingActionButton2);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ImagePicker.Companion.with(EditProfileActivity.this)
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

        Uri uri=data.getData();
        cover.setImageURI(uri);
    }

    /*public void onClick(View v)
    {
        addView();
    }*/

    private void addView()
    {
        View newtag=getLayoutInflater().inflate(R.layout.row_add_tag,null,false);

        //EditText editText=(EditText)newtag.findViewById(R.id.newtext);
        AppCompatSpinner spinnerTeam=(AppCompatSpinner)newtag.findViewById(R.id.spinner_team);
        ImageView imageClose=(ImageView)newtag.findViewById(R.id.close_tag);

        ArrayAdapter arrayAdapter=new ArrayAdapter(this, android.R.layout.simple_spinner_item,teamList);
        spinnerTeam.setAdapter(arrayAdapter);

        imageClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removeView(newtag);
            }
        });

        layoutlist.addView(newtag);
    }

    private void removeView(View view)
    {
        layoutlist.removeView(view);
    }
}