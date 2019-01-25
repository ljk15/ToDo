package com.sinergia.todov2;

import android.arch.persistence.room.RoomDatabase;




@android.arch.persistence.room.Database(entities = {Task.class}, version = 1)
public abstract class Database extends RoomDatabase {

    public abstract MyDao myDao();
}
