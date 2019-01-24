package com.sinergia.todo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    private EditText newTask;
    private Button btn;
    private ListView tasksList;

    private ArrayList<String> tasks;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        newTask = findViewById(R.id.add_task);
        btn = findViewById(R.id.add_btn);
        tasksList = findViewById(R.id.tasks_list);

        tasks = FileHelper.readData(this);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, tasks);
        tasksList.setAdapter(adapter);
        btn.setOnClickListener(this);

        tasksList.setOnItemClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch(v.getId()){
            case R.id.add_btn:
                String taskAdded = newTask.getText().toString();
                adapter.add(taskAdded);
                newTask.setText("");

                FileHelper.writeData(tasks, this);

                Toast.makeText(this, "Task Added", Toast.LENGTH_SHORT).show();
                
                break;
            
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        tasks.remove(position);

        adapter.notifyDataSetChanged();
        Toast.makeText(this, "Deleted", Toast.LENGTH_SHORT).show();
    }
}
