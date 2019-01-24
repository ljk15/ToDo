package com.sinergia.todov2;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

    ArrayList<String> task;
    Context context;
    public CustomAdapter(Context context,ArrayList<String> task){
        this.context = context;
         this.task = task;
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

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              Toast.makeText(context, task.get(position), Toast.LENGTH_SHORT).show();
            }
        });
        holder.del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, task.get(position)+"deleted", Toast.LENGTH_SHORT).show();
                task.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, task.size());

                FileHelper.writeData(task, context);
            }
        });

    }

    @Override
    public int getItemCount() {
        return task.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
    TextView tasks;
    Button del;
    public MyViewHolder(View taskView) {
        super(taskView);
        tasks = (TextView) itemView.findViewById(R.id.task_row);
        del = (Button) itemView.findViewById(R.id.del_btn);
    }
    }
}
