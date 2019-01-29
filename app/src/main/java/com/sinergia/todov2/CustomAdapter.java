package com.sinergia.todov2;

import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

    ArrayList<String> tid;
    ArrayList<String> task;
    ArrayList<String> tdes;
    ArrayList<String> tdate;
    ArrayList<String> ttime;
    Context context;
    Snackbar snackbar;
    public CustomAdapter(Context context,ArrayList<String> task, ArrayList<String> tid,
                         ArrayList<String> tdes, ArrayList<String> tdate, ArrayList<String> ttime){
        this.context = context;
         this.task = task;
         this.tid = tid;
         this.tdes = tdes;
         this.tdate = tdate;
         this.ttime = ttime;


    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_rows, parent, false);
        MyViewHolder vh = new MyViewHolder(v);

        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        holder.tasks.setText(task.get(position));
        holder.taskid.setText(tid.get(position));
        holder.descript.setText(tdes.get(position) + "\n");
        holder.datetime.setText(tdate.get(position)+"\t\t\t\t\t\t"+ ttime.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (holder.descript.getVisibility()== View.VISIBLE) {
                    holder.descript.setVisibility(View.GONE);
                    holder.datetime.setVisibility(View.GONE);
                }
                else
                {
                    holder.descript.setVisibility(View.VISIBLE);
                    holder.datetime.setVisibility(View.VISIBLE);
                }
              Toast.makeText(context, task.get(position), Toast.LENGTH_SHORT).show();
            }
        });

        holder.del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               Task task1 = new Task();
                task1.setId(Long.parseLong(tid.get(position)));
                snackbar.make(view, "Task Deleted :  "+ task.get(position), Snackbar.LENGTH_LONG).setAction("Action", null).show();
                MainActivity.database.myDao().delTask(task1);

                tid.remove(position);
                task.remove(position);
                tdes.remove(position);
                tdate.remove(position);
                ttime.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, task.size());


                    }
        });

    }

    @Override
    public int getItemCount() {

        if (task.isEmpty()) {
            return 0;
        } else
            return task.size();
    }
    public class MyViewHolder extends RecyclerView.ViewHolder{
    TextView tasks;
    TextView descript;
    TextView taskid;
    TextView datetime;
    Button del;
    public MyViewHolder(View taskView) {
        super(taskView);
        taskid = (TextView) itemView.findViewById(R.id.task_id);
        descript = (TextView) itemView.findViewById(R.id.task_Desc);
        datetime = (TextView) itemView.findViewById(R.id.date_time);
        tasks = (TextView) itemView.findViewById(R.id.task_row);
        del = (Button) itemView.findViewById(R.id.del_btn);
    }
    }
}
