package es.upm.trailblazer.login.manager;

import static com.facebook.FacebookSdk.getApplicationContext;

import android.text.TextUtils;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignUpManager {
    private FirebaseAuth mAuth;

    public SignUpManager() {
        mAuth = FirebaseAuth.getInstance();
    }

    public void registerNewUser(String email, String password, String repeatedPassword) {

        checkEmailAndPassword(email, password, repeatedPassword);

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {

                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "Registration successful!",
                                    Toast.LENGTH_LONG).show();
                        } else {

                            Toast.makeText(getApplicationContext(), "Registration failed!!"
                                            + " Please try again later", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    private void checkEmailAndPassword(String email, String password, String repatedPassword){
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(getApplicationContext(), "Please enter email!!", Toast.LENGTH_LONG)
                    .show();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(getApplicationContext(), "Please enter password!!", Toast.LENGTH_LONG)
                    .show();
            return;
        }

        if (TextUtils.isEmpty(repatedPassword)) {
            Toast.makeText(getApplicationContext(), "Please enter repeat Password!!", Toast.LENGTH_LONG)
                    .show();
            return;
        }
    }
}
