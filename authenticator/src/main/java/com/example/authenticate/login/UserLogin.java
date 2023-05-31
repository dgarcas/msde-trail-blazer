package com.example.authenticate.login;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;

public class UserLogin extends LoginManager implements Login {

    private EditText emailEditText;
    private EditText passwordEditText;
    private Intent intent;
    public UserLogin(Activity myActivity, EditText emailEditText, EditText passwordEditText, Intent intent) {
        super(myActivity);

        this.emailEditText = emailEditText;
        this.passwordEditText = passwordEditText;
        this.intent = intent;
    }

    @Override
    public void signIn(String... args) {
        String email = args[0];
        String password = args[1];

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(myActivity, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(myActivity.toString(), "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(myActivity, "Authentication success.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(user);
                        } else {
                            Log.w(myActivity.toString(), "signInWithEmail:failure", task.getException());
                            Toast.makeText(myActivity, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
    @Override
    public void clickLoginButton(){
        String email = emailEditText.getText().toString();
        String password = passwordEditText.getText().toString();

        if (!email.isBlank() && !password.isBlank()) {
            signIn(emailEditText.getText().toString(), passwordEditText.getText().toString());
        } else {
            //TODO
        }
    }
    @Override
    public void updateUI(FirebaseUser user) {
        myActivity.startActivity(intent);
    }
}
