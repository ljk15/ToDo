package com.sinergia.todov2;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class AddTask extends AppCompatActivity implements View.OnClickListener {

    private EditText taskTitle;
    private EditText taskDescription;
    private Button add_task;
    private ArrayList<String> tasks;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);
        taskTitle = findViewById(R.id.add_title);
        taskDescription = findViewById(R.id.add_description);
        tasks = new ArrayList<>();
        add_task = findViewById(R.id.add_task);
        add_task.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

v.findViewById(R.id.add_task);

        String taskAdded = taskTitle.getText().toString();
        tasks.add(taskAdded);

        Task task = new Task();
        task.setTitle(taskAdded);
        MainActivity.database.myDao().addTask(task);
        taskTitle.setText("");

            Snackbar.make(v, "Task Added :  "+taskAdded, Snackbar.LENGTH_LONG).setAction("Action", null).show();

    }
    @Override
    public void onBackPressed()
    {
        startActivity(new Intent(this, MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
    }
}
