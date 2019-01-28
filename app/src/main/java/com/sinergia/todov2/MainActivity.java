package com.sinergia.todov2;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.design.widget.FloatingActionButton;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    public static Database database;
    private EditText newTask;
    private ImageButton btn;
    private RecyclerView tasksList;
    private ArrayList<String> tasks ;
    private ArrayList<String> ids;
    CustomAdapter customAdapter;
    public static FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tasks = new ArrayList<>();
        ids = new ArrayList<>();
        newTask = findViewById(R.id.add_task);
        btn = findViewById(R.id.add_btn);
        tasksList = findViewById(R.id.tasks_list);

        database = Room.databaseBuilder(getApplicationContext(), Database.class, "tasksdb").allowMainThreadQueries().build();

        RecyclerView recyclerView = (RecyclerView) tasksList;
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        List<Task> titles = database.myDao().getTasks();

        for (Task t:titles) {

            tasks.add(t.getTitle());
            ids.add(String.valueOf(t.getId()));
        }
        customAdapter = new CustomAdapter(MainActivity.this, tasks, ids);
        recyclerView.setAdapter(customAdapter);
        btn.setOnClickListener(this);

        fab =  findViewById(R.id.fab);
        fab.setOnClickListener(this);

                fab.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick (View v){

            switch (v.getId()) {
                case R.id.fab:
                    Intent myIntent = new Intent(MainActivity.this, AddTask.class);
                    MainActivity.this.startActivity(myIntent);
                    break;
            }
                 }

        });

    }


    @Override
    public void onClick(View v) {

    }
}
