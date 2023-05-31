package es.upm.trailblazer.login;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.firebase.auth.FirebaseAuth;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import es.upm.trailblazer.MapActivity;
import es.upm.trailblazer.R;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "EmailPassword";
    private FirebaseAuth mAuth;
    private Button login, signup;
    private FragmentTransaction fragmentTransaction;
    private Fragment loginFragment;
    private Fragment signupFragment;
    private Intent mapIntent;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mapIntent = new Intent(this, MapActivity.class);
        mAuth = FirebaseAuth.getInstance();
        loginFragment = new LoginFragment(mapIntent);
        signupFragment = new SignUpFragment(mapIntent);

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