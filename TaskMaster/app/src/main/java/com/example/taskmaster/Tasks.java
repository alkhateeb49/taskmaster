package com.example.taskmaster;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity
public class Tasks {
    @PrimaryKey(autoGenerate = true)
    public int id;
    @ColumnInfo(name = "title")
    String title;
    @ColumnInfo(name = "body")
    String body;
    @ColumnInfo(name = "state")
    String state;

    public Tasks(String title, String body, String state) {
        this.title = title;
        this.body = body;
        this.state = state;
    }

    public Tasks() {
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
