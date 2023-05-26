package com.example.authenticate.login;

import android.app.Activity;

import com.google.firebase.auth.FirebaseAuth;

public class LoginManager {

    Activity myActivity;
    FirebaseAuth mAuth;

    public LoginManager (Activity myActivity){
        this.myActivity = myActivity;
        this.mAuth = FirebaseAuth.getInstance();
    }
}
