package com.example.mvpfirebasetest1.Presenter.LoginPresenter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.example.mvpfirebasetest1.MainActivity;
import com.example.mvpfirebasetest1.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import androidx.annotation.NonNull;

public class LoginWithGooglePresenter {
    GoogleSignInOptions gso;
    GoogleSignInClient mGoogleSignInClient;
    FirebaseAuth mAuth;

    Context mContext;


    public LoginWithGooglePresenter(Context mContext,FirebaseAuth mAuth) {
        this.mAuth=mAuth;
        this.mContext=mContext;
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(mContext, gso);

    }



    public Intent singInWithGoogle(){
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
       // startActivityForResult(signInIntent, RC_SIGN_IN);
        return signInIntent;
    }
    public void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            //Toast.makeText(mContext, ""+account.getDisplayName()+" "+account.getIdToken(), Toast.LENGTH_SHORT).show();

            firebaseAuthwithGoogle(account);
            mContext.startActivity(new Intent(mContext, MainActivity.class));

            // Signed in successfully, show authenticated UI.

        } catch (ApiException e) {
            Toast.makeText(mContext, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void firebaseAuthwithGoogle(GoogleSignInAccount account) {
        //AuthCredential authCredential= GoogleAuthProvider.getCredential(account.getIdToken(),null);
        mAuth.signInWithEmailAndPassword(account.getEmail(),account.getId())
                .addOnCompleteListener((Activity) mContext, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information

                            FirebaseUser user = mAuth.getCurrentUser();
                            mContext.startActivity(new Intent(mContext, MainActivity.class));



                        } else {
                            // If sign in fails, display a message to the user.

                            Toast.makeText(mContext, "sign in with google is failed.",
                                    Toast.LENGTH_SHORT).show();

                        }

                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(mContext, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}
