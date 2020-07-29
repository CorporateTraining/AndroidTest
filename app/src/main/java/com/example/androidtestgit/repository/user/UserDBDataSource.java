package com.example.androidtestgit.repository.user;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import io.reactivex.Completable;
import io.reactivex.Maybe;

@Dao
public interface UserDBDataSource {
    @Query("select * from user where username = :name")
    Maybe<User> findByName(String name);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable save(User user);
}
