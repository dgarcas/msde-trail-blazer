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

import com.example.authenticate.login.GoogleLogin;
import com.example.authenticate.signup.SignUpManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import es.upm.trailblazer.MapActivity;
import es.upm.trailblazer.R;

public class SignUpFragment extends Fragment {

    private FirebaseAuth mAuth;
    private Button signUpBnt;
    private ImageButton googleBnt;
    private GoogleLogin googleLogin;
    private SignUpManager signUpManager;
    private EditText emailEditText;
    private EditText passwordEditText;
    private EditText repeatedPasswordEditText;
    private Intent mapIntent;

    public SignUpFragment(Intent intent) {
        this.mapIntent = intent;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        signUpManager = new SignUpManager(getActivity());

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
        return inflater.inflate(R.layout.fragment_sign_up, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initializePrivateVariables();

        signUpBnt.setOnClickListener(v -> signUpManager.registerNewUser(
                emailEditText.getText().toString(), passwordEditText.getText().toString(),
                repeatedPasswordEditText.getText().toString()));
        googleBnt.setOnClickListener(v -> googleLogin.clickLoginButton());
    }

    private void initializePrivateVariables() {
        signUpBnt = getActivity().findViewById(R.id.sign_up_button);
        googleBnt = getActivity().findViewById(R.id.google_bnt_sing_up);

        emailEditText = getActivity().findViewById(R.id.email_address_sign_up);
        passwordEditText = getActivity().findViewById(R.id.loginPassword);
        repeatedPasswordEditText = getActivity().findViewById(R.id.loginPasswordRepeat);

        googleLogin = new GoogleLogin(getActivity(), getString(R.string.default_web_client_id),
                this, mapIntent);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        googleLogin.manageGoogleLogin(requestCode, data);
    }
    private void reload() {
    }
}