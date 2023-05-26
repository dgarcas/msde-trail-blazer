package es.upm.trailblazer.login.manager;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class GoogleLogin extends LoginManager implements Login {

    private GoogleSignInOptions gso;
    public static final int RC_SIGN_IN = 9001;
    private Fragment myFragment;
    public GoogleLogin(Activity myActivity, String serverClientId, Fragment myFragment) {

        super(myActivity);
        this.myFragment = myFragment;
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(serverClientId)
                .requestEmail()
                .build();
    }

    @Override
    public void signIn(String... args) {
        String idToken = args[0];
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(myActivity, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(myActivity.toString(), "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(myActivity.toString(), "signInWithCredential:failure", task.getException());
                            updateUI(null);
                        }
                    }
                });
    }
    @Override
    public void clickLoginButton(){
        Intent signInIntent = GoogleSignIn.getClient(myActivity, gso).getSignInIntent();
        myFragment.startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void updateUI(FirebaseUser user) {

    }

    public void manageGoogleLogin(int requestCode, Intent data) {
        if (requestCode == GoogleLogin.RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                Log.d(myActivity.toString(), "firebaseAuthWithGoogle:" + account.getId());
                signIn(account.getIdToken());
            } catch (ApiException e) {
                Log.w(myActivity.toString(), "Google sign in failed", e);
            }
        }
    }
}
