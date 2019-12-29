package com.example.mvpfirebasetest1.Presenter.RegisterPresenter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.example.mvpfirebasetest1.MainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

import java.util.HashMap;
import java.util.Map;

import androidx.annotation.NonNull;

public class RegisterPresenter {
    private Context mContext;
    private FirebaseAuth mAuth;
    private DatabaseReference mReference;

    public RegisterPresenter(Context mContext, FirebaseAuth mAuth, DatabaseReference mReference) {
        this.mContext = mContext;
        this.mAuth = mAuth;
        this.mReference = mReference;
    }

    public void signUpUser(final String name, final int numberPhone, final String email, String password){
        final ProgressDialog progressDialog=new ProgressDialog(mContext);
        progressDialog.setMessage("Registering User..");
        progressDialog.setCancelable(false);
        progressDialog.show();
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener((Activity) mContext, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information

                            FirebaseUser user = mAuth.getCurrentUser();
                            Map<String,Object>creatUser=new HashMap<>();
                            creatUser.put("username",name);
                            creatUser.put("numberphone",numberPhone);
                            creatUser.put("email",email);
                            mReference.child("User").child(task.getResult().getUser().getUid()).updateChildren(creatUser);
                            progressDialog.dismiss();
                            mContext.startActivity(new Intent(mContext, MainActivity.class));

                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(mContext, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }

                        // ...
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(mContext, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
