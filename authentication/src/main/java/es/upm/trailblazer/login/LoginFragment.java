package es.upm.trailblazer.login;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import es.upm.trailblazer.R;
import es.upm.trailblazer.login.manager.GoogleLogin;
import es.upm.trailblazer.login.manager.Login;
import es.upm.trailblazer.login.manager.UserLogin;

public class LoginFragment extends Fragment {

    private FirebaseAuth mAuth;
    private Button loginBnt;
    private ImageButton googleBnt;
    private GoogleLogin googleLogin;
    private UserLogin userLogin;

    private GoogleSignInClient mGoogleSignInClient;

    public LoginFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            reload();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initializePrivateVariables();

        loginBnt.setOnClickListener(v -> userLogin.clickLoginButton());
        googleBnt.setOnClickListener(v -> googleLogin.clickLoginButton());
    }

    private void initializePrivateVariables(){
        loginBnt = getActivity().findViewById(R.id.login_btn);
        googleBnt = getActivity().findViewById(R.id.google_bnt);

        EditText emailEditText = getActivity().findViewById(R.id.editTextTextEmailAddress);
        EditText passwordEditText = getActivity().findViewById(R.id.editTextTextPassword);
        googleLogin = new GoogleLogin(getActivity(), getString(R.string.default_web_client_id), this);
        userLogin = new UserLogin(getActivity(), emailEditText, passwordEditText);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        googleLogin.manageGoogleLogin(requestCode, data);
    }

    private void reload() {
    }

}