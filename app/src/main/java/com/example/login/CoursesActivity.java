package com.example.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class CoursesActivity extends AppCompatActivity {

    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courses);

        String[] array=getResources().getStringArray(R.array.course_menu);
        listView=findViewById(R.id.coursesList);
        ArrayAdapter<String> stringArrayAdapter=new ArrayAdapter<String>(CoursesActivity.this, android.R.layout.simple_list_item_1,array);
        listView.setAdapter(stringArrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                String url="";
                //Toast.makeText(getApplicationContext(),array[position],Toast.LENGTH_LONG).show();
                switch (array[position]){
                    case "Machine Learning": url="https://www.coursera.org/learn/machine-learning?";
                        break;
                    case "Server side Nodejs": url="https://www.coursera.org/learn/server-side-nodejs/home/welcome";
                        break;

                    case "Android App Development": url="https://www.udemy.com/course/complete-android-n-developer-course/";
                        break;

                    case  "DSA": url="https://www.udemy.com/share/101Wnc3@Ahpas6aTGQgwvlC0VloJVPKUI8RBRy9Rr9Y4HcjY2XK0XtxYPl0osrvhwQddeYThAw==/";
                        break;

                }

                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);

            }
        });
    }
}