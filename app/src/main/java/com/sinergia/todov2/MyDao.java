package com.sinergia.todov2;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;
@Dao
public interface MyDao {
    @Insert
    public void addTask(Task task);

    @Query("select * from todolist")
    public List<Task> getTasks();

    @Delete
    public void delTask(Task task);
}
