package com.example.remindif;

public class Task {
    int taskId;
    private String title;
    private String description;
    private String date;
    private String time;
    private boolean completed;

    public Task(String title, String description, String date, String time,boolean status) {
        this.title = title;
        this.description = description;
        this.date = date;
        this.time = time;
        this.completed = status;
    }
    // Getter methods
    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }
}