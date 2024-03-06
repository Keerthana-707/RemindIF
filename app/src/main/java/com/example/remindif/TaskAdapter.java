package com.example.remindif;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {

    private List<Task> taskList;
    private Context context;

    // Constructor to initialize the adapter with a list of tasks
    public TaskAdapter(List<Task> taskList,Context context) {
        this.taskList = taskList;
        this.context = context;
    }


    // ViewHolder class to hold references to the views in each list item
    public static class TaskViewHolder extends RecyclerView.ViewHolder {
        TextView titleTextView;
        TextView descriptionTextView;
        TextView dateTextView;
        TextView timeTextView;

        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.taskTitle);
            descriptionTextView = itemView.findViewById(R.id.taskDescription);
            dateTextView = itemView.findViewById(R.id.taskDate);
            timeTextView = itemView.findViewById(R.id.taskTime);
        }
    }

    // onCreateViewHolder method is called when a new ViewHolder is created.
    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the layout for each list item
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_item, parent, false);
        return new TaskViewHolder(view);
    }

    // onBindViewHolder method is called to update the contents of the ViewHolder
    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        if (holder != null) {
            Task task = taskList.get(position);
            if (task != null) {
                holder.titleTextView.setText(task.getTitle());
                holder.descriptionTextView.setText(task.getDescription());
                holder.dateTextView.setText(task.getDate());
                holder.timeTextView.setText(task.getTime());
            }
        }

    }

    // getItemCount method returns the total number of items in the data set
    @Override
    public int getItemCount() {
        return taskList.size();
    }
}

