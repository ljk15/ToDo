package com.sinergia.todov2;

import android.arch.persistence.room.Room;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    public static Database database;
    private EditText newTask;
    private Button btn;
    private RecyclerView tasksList;
    private ArrayList<String> tasks ;
    private ArrayList<String> ids;
    CustomAdapter customAdapter;

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

    }
        @Override
        public void onClick(View v) {

            switch(v.getId()){
                case R.id.add_btn:
                    String taskAdded = newTask.getText().toString();
                    tasks.add(taskAdded);

                    Task task = new Task();
                    task.setTitle(taskAdded);
                    database.myDao().addTask(task);
                   // ids.add(String.valueOf(task.getId()));
                    customAdapter.notifyItemInserted(tasks.size());
                    newTask.setText("");

                    List<Task> titles = database.myDao().getTasks();
                    ids.removeAll(ids);
                    for (Task t:titles) {

                        //tasks.add(t.getTitle());
                        ids.add(String.valueOf(t.getId()));
                    }


                    Toast.makeText(this, "Task Added", Toast.LENGTH_SHORT).show();

                    break;

            }
        }



}
