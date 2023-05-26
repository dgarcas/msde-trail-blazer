package es.upm.trailblazer.login.manager;

import com.google.firebase.auth.FirebaseUser;

public interface Login {

    public void signIn (String ...args);

    void updateUI(FirebaseUser user);
    public void clickLoginButton();
}
