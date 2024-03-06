package com.example.remindif;

import androidx.appcompat.app.AppCompatActivity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.PopupMenu;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.text.ParseException;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private List<Task> taskList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        taskList = new ArrayList<>();
        // Set the Toolbar as the ActionBar
        setSupportActionBar(findViewById(R.id.toolbar));
        // Set onClickListener for Floating Action Button
        FloatingActionButton fabAddTask = findViewById(R.id.AddTask);
        fabAddTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("MainActivity", "FloatingActionButton clicked");
                showAddTaskForm();
            }
        });
        // Set up RecyclerView
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Create an instance of your adapter and attach it to the RecyclerView
        TaskAdapter adapter = new TaskAdapter(taskList, this); // Replace YourAdapterClass with the actual adapter class you are using
        recyclerView.setAdapter(adapter);

    }


    public void showDatePickerDialog(View view) {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        EditText editTextDate = (EditText) view;
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        Log.d("MainActivity", "onDateSet called");
                        String selectedDate = year + "-" + (month + 1) + "-" + dayOfMonth;
                        Log.d("MainActivity", selectedDate);
                        // Ensure the EditText is found before setting the text
                        if (editTextDate != null) {
                            Log.d("MainActivity", "EditText found");
                            editTextDate.setText(selectedDate);
                        }
                    }
                },
                year,
                month,
                dayOfMonth
        );
        datePickerDialog.show();
    }
    public void showTimePickerDialog(View view) {
        final Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        // Pass the EditText as a parameter
        EditText editTextTime = (EditText) view;

        TimePickerDialog timePickerDialog = new TimePickerDialog(
                editTextTime.getContext(),
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        Log.d("MainActivity", "onTimeSet called");
                        String selectedTime = hourOfDay + ":" + minute;
                        editTextTime.setText(selectedTime);
                    }
                },
                hour,
                minute,
                true // 24-hour format
        );
        timePickerDialog.show();
    }
    private void showAddTaskForm() {
        // Inflate the dialog view
        View dialogView = LayoutInflater.from(this).inflate(R.layout.add_task, null);

        // Extracting form elements
        final EditText etTitle = dialogView.findViewById(R.id.etTitle);
        final EditText etDescription = dialogView.findViewById(R.id.etDescription);
        final EditText etDate = dialogView.findViewById(R.id.etDate);
        final EditText etTime = dialogView.findViewById(R.id.etTime);
        // Create and show the dialog
        new AlertDialog.Builder(this)
                .setTitle("Add Task")
                .setView(dialogView)
                .setPositiveButton("Add", (dialog, which) -> {

                    // Get data from the form
                    String title = etTitle.getText().toString();
                    String description = etDescription.getText().toString();
                    String date = etDate.getText().toString();
                    String time = etTime.getText().toString();

                    // Validate input (you can add more validation as needed)
                    if (!title.isEmpty() && !time.isEmpty() && !date.isEmpty() && !description.isEmpty()) {
                        // Valid input, create a new task
                        Task newTask = new Task(title,description,date,time,false);
                        if(newTask != null)
                            taskList.add(newTask);
                        showTaskWithMenu(newTask, taskList.size() - 1);
                    }
                    else {
                        Toast.makeText(MainActivity.this, "Please fill in required fields", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Cancel", null)
                .show();
    }
    // Function to display a task with three dots menu
    private void showTaskWithMenu(Task task, int position) {
        View taskView = LayoutInflater.from(this).inflate(R.layout.task_item, null);

        // Populate task details
        TextView titleTextView = taskView.findViewById(R.id.taskTitle);
        TextView descriptionTextView = taskView.findViewById(R.id.taskDescription);
        TextView dateTextView = taskView.findViewById(R.id.taskDate);
        TextView timeTextView = taskView.findViewById(R.id.taskTime);

        titleTextView.setText(task.getTitle());
        descriptionTextView.setText(task.getDescription());
        dateTextView.setText(task.getDate());
        timeTextView.setText(task.getTime());

        // Three dots menu
        ImageView dotsMenu = taskView.findViewById(R.id.dotsMenu);
        dotsMenu.setOnClickListener(v -> showPopupMenu(v, position));

        // Add the taskView to your layout
        // Assuming you have a LinearLayout as a container
        RecyclerView container = findViewById(R.id.recyclerView);
        container.addView(taskView);
    }

    // Function to show the three dots menu
    private void showPopupMenu(View view, int position) {
        PopupMenu popupMenu = new PopupMenu(this, view);
        popupMenu.getMenuInflater().inflate(R.menu.task_menu, popupMenu.getMenu());

        // Set the item click listener
        popupMenu.setOnMenuItemClickListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.menuComplete) {
                //updateTaskStatus(position, true);
                return true;
            } else if (itemId == R.id.menuEdit) {
                // Implement your edit logic
                return true;
            } else if (itemId == R.id.menuDelete) {
                //deleteTask(position);
                return true;
            } else {
                return false;
            }
        });

        popupMenu.show();
    }
}