package es.upm.trailblazer.login.manager;


import android.app.Activity;
import android.text.TextUtils;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignUpManager {
    private FirebaseAuth mAuth;
    private Activity myActivity;

    public SignUpManager(Activity myActivity) {

        mAuth = FirebaseAuth.getInstance();
        this.myActivity = myActivity;
    }

    public void registerNewUser(String email, String password, String repeatedPassword) {

        if (areCreadentialsCorrectly(email, password, repeatedPassword)) {
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {

                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(myActivity, "Registration successful!",
                                        Toast.LENGTH_LONG).show();
                            } else {

                                Toast.makeText(myActivity, "Registration failed!!"
                                        + " Please try again later", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
        }
    }

    private boolean areCreadentialsCorrectly(String email, String password, String repatedPassword){

        boolean response = true;

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(myActivity, "Please enter email!!", Toast.LENGTH_LONG)
                    .show();
            response = false;
        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(myActivity, "Please enter password!!", Toast.LENGTH_LONG)
                    .show();
            response = false;;
        }

        if (TextUtils.isEmpty(repatedPassword)) {
            Toast.makeText(myActivity, "Please enter repeat Password!!", Toast.LENGTH_LONG)
                    .show();
            response = false;
        }
        return  response;
    }
}
