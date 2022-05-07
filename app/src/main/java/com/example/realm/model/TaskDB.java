package com.example.realm.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class TaskDB extends RealmObject {
    @PrimaryKey
    private int id;

    private String task;

    public TaskDB(){}

    public TaskDB(int id, String task){
        this.id = id;
        this.task = task;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }
}
