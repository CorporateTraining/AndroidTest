package com.example.androidtestgit;

import android.app.Application;

import com.example.androidtestgit.repository.user.UserRepository;

import static org.mockito.Mockito.mock;

public class MyApplication extends Application {
    private UserRepository userRepository;

    public UserRepository getUserRepository() {
        if (userRepository == null) {
            userRepository = mock(UserRepository.class);
        }
        return userRepository;
    }
}
