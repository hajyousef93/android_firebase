package com.example.mvpfirebasetest1.Presenter.LoginPresenter;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.InputType;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.mvpfirebasetest1.MainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import androidx.annotation.NonNull;

public class LoginPresenter {
    private FirebaseAuth mAuth;
    private Context mContext;
    private ProgressDialog mprogressDialog;

    public LoginPresenter(Context mContext,FirebaseAuth mAuth,ProgressDialog mprogressDialog) {
        this.mContext=mContext;
        this.mAuth = mAuth;
        this.mprogressDialog=mprogressDialog;
    }
    public void loginUser(String email, String password) {
        mprogressDialog.setMessage("Loging In ...");
        mprogressDialog.show();
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener((Activity) mContext, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            mprogressDialog.dismiss();
                            FirebaseUser user = mAuth.getCurrentUser();
                            mContext.startActivity(new Intent(mContext, MainActivity.class));



                        } else {
                            // If sign in fails, display a message to the user.
                            mprogressDialog.dismiss();
                            Toast.makeText(mContext, "Authentication failed.",
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
    public void showRecoveryPasswordDialog() {
        AlertDialog.Builder mbuilder=new AlertDialog.Builder(mContext);
        mbuilder.setTitle("Recover Password");
        LinearLayout linearLayout=new LinearLayout(mContext);
        final EditText ET_emailRecovery=new EditText(mContext);
        ET_emailRecovery.setHint("Email");
        ET_emailRecovery.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
        ET_emailRecovery.setMinEms(16);

        linearLayout.addView(ET_emailRecovery);
        linearLayout.setPadding(10,10,10,10);

        mbuilder.setView(linearLayout);

        mbuilder.setPositiveButton("Recover", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String email=ET_emailRecovery.getText().toString().trim();
                brginRecovery(email);

            }
        });
        mbuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        mbuilder.create().show();
    }
    private void brginRecovery(String email) {
        mprogressDialog.setMessage("Sinding Email ...");
        mprogressDialog.show();
        mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    mprogressDialog.dismiss();
                    Toast.makeText(mContext, "Email send", Toast.LENGTH_SHORT).show();
                }else{
                    mprogressDialog.dismiss();
                    Toast.makeText(mContext, "Failed....", Toast.LENGTH_SHORT).show();

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
