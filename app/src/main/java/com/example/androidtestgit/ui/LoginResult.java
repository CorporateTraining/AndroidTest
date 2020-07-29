package com.example.androidtestgit.ui;

public class LoginResult {
    private Boolean result;
    private String message;

    public LoginResult(Boolean result, String message) {
        this.result = result;
        this.message = message;
    }

    public Boolean getResult() {
        return result;
    }

    public String getMessage() {
        return message;
    }
}
