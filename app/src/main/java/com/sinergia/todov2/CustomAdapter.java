package com.sinergia.todov2;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.StrikethroughSpan;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

    ArrayList<String> tid;
    ArrayList<String> task;
    ArrayList<String> tdes;
    ArrayList<String> tdate;
    ArrayList<String> ttime;
    ArrayList<String> stats;
    Context context;
    Snackbar snackbar;

    public CustomAdapter(Context context,ArrayList<String> task, ArrayList<String> tid,
                         ArrayList<String> tdes, ArrayList<String> tdate, ArrayList<String> ttime, ArrayList<String> stats){
        this.context = context;
        this.task = task;
        this.tid = tid;
        this.tdes = tdes;
        this.tdate = tdate;
        this.ttime = ttime;
        this.stats = stats;

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

        holder.descript.setSingleLine(true);
        holder.datetime.setVisibility(View.GONE);
        holder.tasks.setSingleLine(true);
        holder.tasks.setTextColor(Color.BLACK);



        holder.tasks.setText(task.get(position));
        holder.taskid.setText(tid.get(position));
        holder.taskstats.setText(stats.get(position));
        holder.descript.setText(tdes.get(position) + "\n");
        holder.datetime.setText(tdate.get(position)+"\t\t\t\t\t\t"+ ttime.get(position));


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (holder.datetime.getVisibility()== View.VISIBLE) {
                    holder.tasks.setSingleLine(true);
                    holder.descript.setSingleLine(true);
                    holder.datetime.setVisibility(View.GONE);

                }
                else
                {
                    holder.tasks.setSingleLine(false);
                    holder.descript.setSingleLine(false);
                    holder.datetime.setVisibility(View.VISIBLE);

                }
              Toast.makeText(context, task.get(position), Toast.LENGTH_SHORT).show();


            }
        });






        if (holder.taskstats.getText().equals("DONE"))
        {
            if (holder.tasks.getText().length()!=0)
            {
                holder.tasks.setTextColor(Color.RED);

                SpannableString str = new SpannableString(holder.tasks.getText());
                str.setSpan(new StrikethroughSpan(), 0, str.length(), 0);
                holder.tasks.setText(str);

            }
            holder.del.setEnabled(false);
        }


        holder.del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (holder.tasks.getText().length()!=0) {
                    holder.tasks.setTextColor(Color.RED);

                SpannableString str = new SpannableString(holder.tasks.getText());
                            str.setSpan(new StrikethroughSpan(), 0, str.length(), 0);
                            holder.tasks.setText(str);

                        }

                                 holder.taskstats.setText("DONE");
                                 stats.set(position,"DONE");
                                 notifyItemChanged(position);

                        snackbar.make(view, "Task Completed :  " + task.get(position), Snackbar.LENGTH_LONG).setAction("Action", null).show();

                         MainActivity.database.myDao().update("DONE", Long.parseLong(tid.get(position)));


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
    TextView taskstats;
    ImageButton del;
    public MyViewHolder(View taskView) {
        super(taskView);
        taskstats = (TextView) itemView.findViewById(R.id.task_status);
        taskid = (TextView) itemView.findViewById(R.id.task_id);
        descript = (TextView) itemView.findViewById(R.id.task_Desc);
        datetime = (TextView) itemView.findViewById(R.id.date_time);
        tasks = (TextView) itemView.findViewById(R.id.task_row);
        del = (ImageButton) itemView.findViewById(R.id.del_btn);
    }
    }
}
