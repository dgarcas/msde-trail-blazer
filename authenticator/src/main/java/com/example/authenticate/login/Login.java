package com.example.authenticate.login;

import com.google.firebase.auth.FirebaseUser;

public interface Login {

    public void signIn (String ...args);

    void updateUI(FirebaseUser user);
    public void clickLoginButton();
}
