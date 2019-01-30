package com.sinergia.todov2;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AddTask extends AppCompatActivity implements View.OnClickListener {

    private EditText taskTitle;
    private EditText taskDescription;
    private Button add_task;
    private EditText task_Date;
    private EditText task_Time;
    private static String status;

    private SimpleDateFormat dateFormat, timeFormat;

    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private TimePickerDialog.OnTimeSetListener mTimeSetListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        dateFormat = new SimpleDateFormat("dd/MM/yyy");
        timeFormat = new SimpleDateFormat("HH:mm");

        taskTitle = findViewById(R.id.add_title);
        taskDescription = findViewById(R.id.add_description);
        task_Date = findViewById(R.id.add_date);
        task_Time = findViewById(R.id.add_time);
        add_task = findViewById(R.id.add_task);
        status = "TODO";

        add_task.setOnClickListener(this);
        task_Date.setOnClickListener(this);
        task_Time.setOnClickListener(this);

        Calendar cal = Calendar.getInstance();

        task_Date.setText(dateFormat.format(cal.getTime()));
        task_Time.setText(timeFormat.format(cal.getTime()));

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month +=1;
                String date = dayOfMonth + "/" + month + "/" + year;
                task_Date.setText(date);
            }
        };

        mTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                String time = String.format("%02d : %02d", hourOfDay , minute);
                task_Time.setText(time);
            }
        };
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.add_task:
                String taskAdded = taskTitle.getText().toString();
                String taskDesc = taskDescription.getText().toString();
                String taskDate = task_Date.getText().toString();
                String taskTime = task_Time.getText().toString();



                Task task = new Task();
                task.setTitle(taskAdded);
                task.setDescription(taskDesc);
                task.setDate(taskDate);
                task.setTime(taskTime);
                task.setStatus(status);



                Calendar cal1 = Calendar.getInstance();
                task_Date.setText(dateFormat.format(cal1.getTime()));
                task_Time.setText(timeFormat.format(cal1.getTime()));

                MainActivity.database.myDao().addTask(task);
                taskTitle.setText("");
                taskDescription.setText("");

                Snackbar.make(v, "Task Added :  " + taskAdded, Snackbar.LENGTH_LONG).setAction("Action", null).show();

                break;

            case R.id.add_date:
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dateDialog = new DatePickerDialog(this, android.R.style.Theme_Material_Dialog_NoActionBar_MinWidth,
                        mDateSetListener,year,month,dayOfMonth);

                dateDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dateDialog.show();


                break;


            case R.id.add_time:
                Calendar calTime = Calendar.getInstance();

                int hours = calTime.get(Calendar.HOUR_OF_DAY);
                int minutes = calTime.get(Calendar.MINUTE);

                TimePickerDialog timedialog = new TimePickerDialog(this, android.R.style.Theme_Material_Dialog_NoActionBar_MinWidth,
                        mTimeSetListener, hours, minutes, true);

                timedialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                timedialog.show();

                break;
        }
    }

    @Override
    public void onBackPressed()
    {
        startActivity(new Intent(this, MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
    }
}
