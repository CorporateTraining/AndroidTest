package com.example.androidtestgit.ui;

import android.util.Log;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.example.androidtestgit.repository.user.User;
import com.example.androidtestgit.repository.user.UserRepository;
import com.example.androidtestgit.repository.user.utils.MD5Util;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.Maybe;
import io.reactivex.MaybeObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class LoginViewModel extends ViewModel {
    private MutableLiveData<LoginResult> loginResultMutableLiveData = new MutableLiveData<>();
    private Disposable insertDisposable, loginDisposable;
    private UserRepository userRepository;
    private final static String TAG = "LoginViewModel";

    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void observerLoginViewModel(LifecycleOwner owner, Observer<LoginResult> observer){
        loginResultMutableLiveData.observe(owner, observer);
    }

    public void save() {
        User user = new User(1, "android", "123456");
        Completable completable = userRepository.save(user);
        completable.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d(TAG, "onSubscribe:");
                        insertDisposable = d;
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete: ");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "onError: ", e);
                    }
                });
    }

    public void login(String username, String password) {
        Maybe<User> userMaybe = userRepository.findByName(username);
        userMaybe.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new MaybeObserver<User>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d(TAG, "onSubscribe: ");
                        loginDisposable = d;
                    }

                    @Override
                    public void onSuccess(User user) {
                        Log.d(TAG, "onSuccess: ");
                        LoginResult loginResult;
                        if (user != null && MD5Util.md5(password).equals(user.getPassword())) {
                            loginResult = new LoginResult(true, "Login successfully");
                        }else{
                            loginResult = new LoginResult(false, "Password is invalid");
                        }
                        loginResultMutableLiveData.postValue(loginResult);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "onError: ", e);
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete: ");
                        loginResultMutableLiveData.postValue(new LoginResult(false, "Username does not exist"));
                    }
                });
    }
}
