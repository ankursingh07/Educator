package com.example.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ProjectMenuActivity extends AppCompatActivity {

    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_menu);
        listView=findViewById(R.id.projectList);
        String[] array=getResources().getStringArray(R.array.project_menu);

        ArrayAdapter<String> stringArrayAdapter=new ArrayAdapter<String>(ProjectMenuActivity.this, android.R.layout.simple_list_item_1,array);
        listView.setAdapter(stringArrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                String url="";
                //Toast.makeText(getApplicationContext(),array[position],Toast.LENGTH_LONG).show();
                switch (array[position]){
                    case "Group chat app": url="https://github.com/ahmedgulabkhan/GroupChatApp";
                        break;
                    case "Web development project": url="https://github.com/rishim9816/Web-Development-Project";
                        break;

                    case "Computer Vision": url="https://github.com/ashishpatel26/500-AI-Machine-learning-Deep-learning-Computer-vision-NLP-Projects-with-code";
                        break;

                    case  "College management system": url="https://github.com/samarth-p/College-ERP";
                        break;

                }

                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);

            }
        });
    }
}