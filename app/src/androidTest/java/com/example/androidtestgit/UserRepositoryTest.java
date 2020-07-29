package com.example.androidtestgit;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.room.Room;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.example.androidtestgit.repository.user.User;
import com.example.androidtestgit.repository.user.UserDatabase;
import com.example.androidtestgit.repository.user.UserRepository;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;

@RunWith(AndroidJUnit4.class)
public class UserRepositoryTest {
    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();
    public UserDatabase userDatabase;
    private UserRepository userRepository;

    @Before
    public void before() {
        userDatabase = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getInstrumentation().getTargetContext(),
                UserDatabase.class).build();
        userRepository = new UserRepository(userDatabase.getUserDBDataSource());
    }

    @Test
    public void should_return_correct_user() {
        User user = new User(1, "xiaoming", "123456");
        userDatabase.getUserDBDataSource().save(user).subscribeOn(Schedulers.io()).subscribe();
        userRepository.findByName("xiaoming")
                .test()
                .assertValue(userResult -> user.getId().equals(userResult.getId()));
    }

    @After
    public void after() {
        userDatabase.close();
    }
}
