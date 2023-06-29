package com.marcos.API.Model.Task;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

public class Task {

    private int Id;
    private String Title;

    private String Description;

    public Task(int id, String title, String description) {
        this.Id = id;
        Title = title;
        Description = description;
    }

    public Task() {
    }

    public int getId() {
        return Id;
    }

    public String getTitle() {
        return Title;
    }

    public String getDescription() {
        return Description;
    }

    public void setId(int id) {
        this.Id = id;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public static String toArrayJson(List<Task> tasks) {

        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();

        Gson gson = builder.create();

        return gson.toJson(tasks);

    }

    @Override
    public String toString() {
        return "Task{" +
                "Id=" + Id +
                ", Title='" + Title + '\'' +
                ", Description='" + Description + '\'' +
                '}';
    }
}
