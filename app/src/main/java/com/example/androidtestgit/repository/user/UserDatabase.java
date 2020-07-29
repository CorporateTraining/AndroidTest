package com.example.androidtestgit.repository.user;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {User.class}, version = 1, exportSchema = false)
public abstract class UserDatabase extends RoomDatabase {
    public abstract UserDBDataSource getUserDBDataSource();

    public static UserDatabase getUserInstance(Context context){
        return Room.databaseBuilder(context.getApplicationContext(), UserDatabase.class, "User").build();
    }
}
