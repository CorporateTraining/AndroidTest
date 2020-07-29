package com.example.androidtestgit.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.androidtestgit.MyApplication;
import com.example.androidtestgit.R;

public class LoginActivity extends AppCompatActivity {
    private Button insertButton, loginButton;
    private EditText usernameText, passwordText;
    private LoginViewModel loginViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        insertButton = findViewById(R.id.insert_button);
        loginButton = findViewById(R.id.login_button);
        usernameText = findViewById(R.id.username);
        passwordText = findViewById(R.id.password);
        initLoginViewModel();
        showLoginResult();
        insertButton.setOnClickListener(view -> {
            loginViewModel.save();
        });
        loginButton.setOnClickListener(view -> {
            String username = usernameText.getText().toString();
            String password = passwordText.getText().toString();
            loginViewModel.login(username, password);
        });
    }

    private void showLoginResult() {
        loginViewModel.observerLoginResult( this, loginResult -> {
            if (loginResult == null) {
                return;
            }
            if (loginResult.getResult()) {
                startActivity(new Intent(LoginActivity.this, HomeActivity.class));
            }
            Toast.makeText(getApplicationContext(), loginResult.getMessage(), Toast.LENGTH_SHORT).show();
        });
    }

    private void initLoginViewModel() {
        MyApplication myApplication = (MyApplication) getApplication();
        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        loginViewModel.setUserRepository(myApplication.getUserRepository());
    }
}