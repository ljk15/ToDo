package com.sinergia.todov2;

import android.content.Context;
import android.support.annotation.NonNull;
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
    Context context;
    public CustomAdapter(Context context,ArrayList<String> task){
        this.context = context;
         this.task = task;
       //  this.tid = tid;
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
        holder.tasks.setText((CharSequence) task.get(position));
        //holder.test.setText((CharSequence) tid.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (holder.test.getVisibility()== View.VISIBLE)
                    holder.test.setVisibility(View.GONE);
                else
                    holder.test.setVisibility(View.VISIBLE);
              Toast.makeText(context, task.get(position), Toast.LENGTH_SHORT).show();
            }
        });
        holder.del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               Task task1 = new Task();

              //  task1.setId(Long.parseLong(tid.get(position)));
                task1.setTitle(task.get(position));
                Toast.makeText(context, task.get(position)+" deleted ", Toast.LENGTH_SHORT).show();

                MainActivity.database.myDao().delTask(task1);


                task.remove(position);
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
    TextView test;
    Button del;
    public MyViewHolder(View taskView) {
        super(taskView);
        test = (TextView) itemView.findViewById(R.id.test);
        tasks = (TextView) itemView.findViewById(R.id.task_row);
        del = (Button) itemView.findViewById(R.id.del_btn);
    }
    }
}
