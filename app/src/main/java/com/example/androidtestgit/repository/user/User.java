package com.example.androidtestgit.repository.user;

import android.bluetooth.BluetoothProfile;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class User {

    @PrimaryKey
    @NonNull
    private Integer id;
    private String username;
    private String password;

    public User(@NonNull Integer id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    @NonNull
    public Integer getId() {
        return id;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
