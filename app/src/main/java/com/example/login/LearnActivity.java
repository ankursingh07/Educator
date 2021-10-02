package com.example.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class LearnActivity extends AppCompatActivity {

    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learn);
        String[] array=getResources().getStringArray(R.array.learn_menu);
        listView=findViewById(R.id.menuList);
        ArrayAdapter<String> stringArrayAdapter=new ArrayAdapter<String>(LearnActivity.this, android.R.layout.simple_list_item_1,array);
        listView.setAdapter(stringArrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String url="";
                //Toast.makeText(getApplicationContext(),array[position],Toast.LENGTH_LONG).show();
                switch (array[position]){
                    case "DBMS": url="https://www.geeksforgeeks.org/introduction-of-dbms-database-management-system-set-1/";
                            break;
                    case "Dsa": url="https://www.geeksforgeeks.org/data-structures/";
                        break;

                    case "Operating System": url="https://www.geeksforgeeks.org/introduction-of-operating-system-set-1/";
                        break;

                    case  "Artificial Intelligence": url="https://www.geeksforgeeks.org/artificial-intelligence-an-introduction/";
                            break;

                    case  "Computer Networks": url="https://www.geeksforgeeks.org/basics-computer-networking/";
                        break;

                    case "Cyber security": url="https://www.geeksforgeeks.org/cyber-security-types-and-importance/";
                        break;

                    case "Cloud Computing": url="https://www.geeksforgeeks.org/cloud-computing/";
                        break;
                }

                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);

            }
        });
    }
}