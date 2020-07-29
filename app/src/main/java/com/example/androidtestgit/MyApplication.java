package com.example.androidtestgit;

import android.app.Application;

import com.example.androidtestgit.repository.user.UserDatabase;
import com.example.androidtestgit.repository.user.UserRepository;

public class MyApplication extends Application {
    private UserRepository userRepository;

    @Override
    public void onCreate() {
        super.onCreate();
        UserDatabase userDatabase = UserDatabase.getUserInstance(getApplicationContext());
        userRepository = new UserRepository(userDatabase.getUserDBDataSource());
    }

    public UserRepository getUserRepository() {
        return userRepository;
    }
}
