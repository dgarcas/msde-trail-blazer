package es.upm.trailblazer;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.facebook.FacebookSdk;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "EmailPassword";
    private FirebaseAuth mAuth;
    private Button login, signup;
    private FragmentTransaction fragmentTransaction;
    private Fragment loginFragment;
    private Fragment signupFragment;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        loginFragment = new LoginFragment();
        signupFragment = new SignUpFragment();

        getSupportFragmentManager().beginTransaction().add(R.id.loginContainer, loginFragment).commit();

        login = findViewById(R.id.loginContext);
        signup = findViewById(R.id.signUpContext);

        login.setOnClickListener(v -> fragmentTransaction(R.id.loginContainer, loginFragment));
        signup.setOnClickListener(v -> fragmentTransaction(R.id.loginContainer, signupFragment));
    }

    private void fragmentTransaction(@IdRes int containerViewId, @NonNull Fragment fragment){

        getSupportFragmentManager().beginTransaction().replace(containerViewId, fragment).addToBackStack(null).commit();
    }
}