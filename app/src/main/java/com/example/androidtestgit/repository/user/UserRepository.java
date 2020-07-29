package com.example.androidtestgit.repository.user;

import com.example.androidtestgit.repository.user.utils.MD5Util;

import io.reactivex.Completable;
import io.reactivex.Maybe;

public class UserRepository {
    private UserDBDataSource userDBDataSource;

    public UserRepository(UserDBDataSource userDBDataSource) {
        this.userDBDataSource = userDBDataSource;
    }

    public Maybe<User> findByName(String name) {
        return userDBDataSource.findByName(name);
    }

    public Completable save(User user) {
        user.setPassword(MD5Util.md5(user.getPassword()));
        return userDBDataSource.save(user);
    }

}
