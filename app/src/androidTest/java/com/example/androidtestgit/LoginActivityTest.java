package com.example.androidtestgit;


import android.os.SystemClock;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.example.androidtestgit.repository.user.User;
import com.example.androidtestgit.repository.user.UserRepository;
import com.example.androidtestgit.repository.user.utils.MD5Util;
import com.example.androidtestgit.ui.LoginActivity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import io.reactivex.Maybe;
import io.reactivex.MaybeObserver;
import io.reactivex.internal.operators.maybe.MaybeCreate;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.RootMatchers.withDecorView;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static com.example.androidtestgit.repository.user.constant.HintMessage.LOGIN_SUCCESSFULLY;
import static com.example.androidtestgit.repository.user.constant.HintMessage.PASSWORD_IS_INVALID;
import static com.example.androidtestgit.repository.user.constant.HintMessage.USERNAME_DOES_NOT_EXIST;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.mockito.Mockito.when;

@RunWith(AndroidJUnit4.class)
public class LoginActivityTest {
    private UserRepository userRepository;
    @Rule
    public ActivityTestRule<LoginActivity> activityActivityTestRule = new ActivityTestRule<>(LoginActivity.class);

    @Before
    public void before() {
        MyApplication applicationContext = (MyApplication) InstrumentationRegistry.getInstrumentation().getTargetContext().getApplicationContext();
        userRepository = applicationContext.getUserRepository();
    }

    @Test
    public void should_successfully_when_login_given_correct_username_and_password() {
        User user = new User(1, "xiaoming", MD5Util.md5("123456"));
        when(userRepository.findByName("xiaoming")).thenReturn(new MaybeCreate(source -> source.onSuccess(user)));
        onView(withId(R.id.username)).perform(typeText("xiaoming"));
        onView(withId(R.id.password)).perform(typeText("123456"));
        onView(withId(R.id.login_button)).perform(click());
        onView(withText(LOGIN_SUCCESSFULLY))
                .inRoot(withDecorView(not(is(activityActivityTestRule
                        .getActivity()
                        .getWindow()
                        .getDecorView()))))
                .check(matches(isDisplayed()));
    }

    @Test
    public void should_return_password_is_invalid_when_login_given_correct_username_and_invalid_password() {
        User user = new User(1, "xiaoming", MD5Util.md5("123456"));
        when(userRepository.findByName("xiaoming")).thenReturn(new MaybeCreate(source -> source.onSuccess(user)));
        onView(withId(R.id.username)).perform(typeText("xiaoming"));
        onView(withId(R.id.password)).perform(typeText("234342  "));
        onView(withId(R.id.login_button)).perform(click());
        onView(withText(PASSWORD_IS_INVALID))
                .inRoot(withDecorView(not(is(activityActivityTestRule
                        .getActivity()
                        .getWindow()
                        .getDecorView()))))
                .check(matches(isDisplayed()));
    }
    @Test
    public void should_return_username_does_not_exist_when_login_given_invalid_username_and_invalid_password() {
        when(userRepository.findByName("zhangsan")).thenReturn(new Maybe<User>() {
            @Override
            protected void subscribeActual(MaybeObserver<? super User> observer) {
                observer.onComplete();
            }
        });
        onView(withId(R.id.username)).perform(typeText("zhangsan"));
        onView(withId(R.id.password)).perform(typeText("234342  "));
        onView(withId(R.id.login_button)).perform(click());
        onView(withText(USERNAME_DOES_NOT_EXIST))
                .inRoot(withDecorView(not(is(activityActivityTestRule
                        .getActivity()
                        .getWindow()
                        .getDecorView()))))
                .check(matches(isDisplayed()));
    }
}
