package com.sinergia.todov2;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;


@Entity(tableName = "todolist")
public class Task {
    /*public Task(@NonNull String title, String description) {
        this.title = title;
        this.description = description;
    }
*/
    @PrimaryKey(autoGenerate = true)
    private long id;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


    @NonNull

    @ColumnInfo(name = "task_title")
    private String title;

    @ColumnInfo(name = "task_description")
    private String description;

    @ColumnInfo(name = "task_date")
    private String date;

    @ColumnInfo(name = "task_time")
    private String time;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

