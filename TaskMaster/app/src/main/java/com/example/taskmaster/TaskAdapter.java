package com.example.taskmaster;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder>{
    Context context;
    public ArrayList<Tasks> tasksList;

    public TaskAdapter(Context mContext, ArrayList<Tasks> tasksList) {
        this.context = mContext;
        this.tasksList = tasksList;
    }

    public static class TaskViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public Tasks tasks;
        public View itemView;
        ItemClickListener itemClickListener;

        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);
            this.itemView = itemView;
            this.itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            this.itemClickListener.onItemClick(v, getLayoutPosition());
        }

        public void setItemClickListener(ItemClickListener listener){
            this.itemClickListener = listener;
        }

    }


    @NonNull
    @Override
    public TaskAdapter.TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_task_model, parent,false);
        return new TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        holder.tasks = tasksList.get(position);
        TextView title = holder.itemView.findViewById(R.id.fTitle);
        TextView body = holder.itemView.findViewById(R.id.fBody);
        TextView state = holder.itemView.findViewById(R.id.fState);

        title.setText(holder.tasks.getTitle());
        body.setText(holder.tasks.getBody());
        state.setText(holder.tasks.getState());

        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onItemClick(View v, int postion) {
                String title = tasksList.get(postion).getTitle();
                String body  = tasksList.get(postion).getBody();
                String state  = tasksList.get(postion).getState();

                Intent i= new Intent(context, TaskDetail.class);
                i.putExtra("title", title);
                i.putExtra("body", body);
                i.putExtra("state", state);
                context.startActivity(i);

            }
        });

    }

    @Override
    public int getItemCount() {
        return tasksList.size();
    }
}
