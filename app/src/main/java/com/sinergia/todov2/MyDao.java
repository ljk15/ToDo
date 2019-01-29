package com.sinergia.todov2;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.ABORT;
import static android.arch.persistence.room.OnConflictStrategy.FAIL;
import static android.arch.persistence.room.OnConflictStrategy.IGNORE;
import static android.arch.persistence.room.OnConflictStrategy.REPLACE;
import static android.arch.persistence.room.OnConflictStrategy.ROLLBACK;

@Dao
public interface MyDao {
    @Insert(onConflict = IGNORE)
    public void addTask(Task task);

    @Query("select * from todolist")
    public List<Task> getTasks();

    @Delete
    public void delTask(Task task);
}
