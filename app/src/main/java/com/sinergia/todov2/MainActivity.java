package com.sinergia.todov2;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.design.widget.FloatingActionButton;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static Database database;
    private EditText newTask;
    private ImageButton btn;
    private RecyclerView tasksList;
    private ArrayList<String> tasks;
    private ArrayList<String> ids;
    private ArrayList<String> tdes;
    private ArrayList<String> tdate;
    private ArrayList<String> ttime;
    private ArrayList<String> stats;

    CustomAdapter customAdapter;
    public static FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tasks = new ArrayList<>();
        ids = new ArrayList<>();
        tdes = new ArrayList<>();
        tdate = new ArrayList<>();
        ttime = new ArrayList<>();
        stats = new ArrayList<>();

        newTask = findViewById(R.id.srch_task);
        btn = findViewById(R.id.srch_btn);
        tasksList = findViewById(R.id.tasks_list);

        database = Room.databaseBuilder(getApplicationContext(), Database.class, "tasksdb").allowMainThreadQueries().build();

        final RecyclerView recyclerView = (RecyclerView) tasksList;
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager);


        List<Task> titles = database.myDao().getTasks();

        for (Task t : titles) {

            tasks.add(t.getTitle());
            ids.add(String.valueOf(t.getId()));
            tdes.add(t.getDescription());
            tdate.add(t.getDate());
            ttime.add(t.getTime());
            stats.add(t.getStatus());

        }
        customAdapter = new CustomAdapter(MainActivity.this, tasks, ids, tdes, tdate, ttime, stats);


        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

                int position = viewHolder.getAdapterPosition();
                Task task1 = new Task();
                task1.setId(Long.parseLong(customAdapter.tid.get(position)));
                Snackbar.make(recyclerView, "Task Deleted :  " + tasks.get(position), Snackbar.LENGTH_LONG).setAction("Action", null).show();
                database.myDao().delTask(task1);

                customAdapter.tid.remove(position);
                customAdapter.task.remove(position);
                customAdapter.tdes.remove(position);
                customAdapter.tdate.remove(position);
                customAdapter.ttime.remove(position);
                customAdapter.stats.remove(position);
                customAdapter.notifyItemRemoved(position);
                customAdapter.notifyItemRangeChanged(position, tasks.size());


            }
        });
        itemTouchHelper.attachToRecyclerView(recyclerView);


        recyclerView.setAdapter(customAdapter);


        fab = findViewById(R.id.fab);


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                switch (v.getId()) {
                    case R.id.fab:
                        Intent myIntent = new Intent(MainActivity.this, AddTask.class);
                        MainActivity.this.startActivity(myIntent);
                        break;


                }
            }

        });


        btn.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {


                List<Task> results = database.myDao().search(newTask.getText().toString());
                ArrayList<String> tle = new ArrayList<>();
                ArrayList<String> des = new ArrayList<>();
                ArrayList<String> idd = new ArrayList<>();
                ArrayList<String> dte = new ArrayList<>();
                ArrayList<String> tme = new ArrayList<>();
                ArrayList<String> sts = new ArrayList<>();
                for (Task r : results) {

                    tle.add(r.getTitle());
                    des.add(r.getDescription());
                    idd.add(String.valueOf(r.getId()));
                    dte.add(r.getDate());
                    tme.add(r.getTime());
                    sts.add(r.getStatus());


                    Log.d("Check", r.getTitle());

                }
                customAdapter = new CustomAdapter(MainActivity.this, tle, idd, des, dte, tme, sts);
recyclerView.setAdapter(customAdapter);

            }
        });
    }

}