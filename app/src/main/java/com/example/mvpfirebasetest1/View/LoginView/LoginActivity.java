package com.example.mvpfirebasetest1.View.LoginView;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


import com.example.mvpfirebasetest1.Presenter.LoginPresenter.LoginPresenter;
import com.example.mvpfirebasetest1.Presenter.LoginPresenter.LoginWithGooglePresenter;
import com.example.mvpfirebasetest1.R;
import com.example.mvpfirebasetest1.View.RegisterView.RegisterActivity;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {


    EditText mEmailedit,mPassedit;
    Button mLogin;
    TextView notHaveAccuontTv,mRecaveryPassword;
    private FirebaseAuth mAuth;
    ProgressDialog progressDialog;
    LoginPresenter mLoginPresenter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ActionBar actionBar=getSupportActionBar();
        actionBar.setTitle("Login");
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        mEmailedit=findViewById(R.id.email_edit);
        mPassedit=findViewById(R.id.pass_edit);
        mLogin=findViewById(R.id.login_but);
        mLogin.setOnClickListener(this);
        notHaveAccuontTv=findViewById(R.id.not_have_account_tv);
        notHaveAccuontTv.setOnClickListener(this);
        mRecaveryPassword=findViewById(R.id.TV_forgetPassword);
        mRecaveryPassword.setOnClickListener(this);
        mAuth = FirebaseAuth.getInstance();
        progressDialog=new ProgressDialog(this);
        mLoginPresenter=new LoginPresenter(this,mAuth,progressDialog);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.login_but:
                String Email=mEmailedit.getText().toString().trim();
                String Password=mPassedit.getText().toString().trim();
                if(!Patterns.EMAIL_ADDRESS.matcher(Email).matches()){
                    mEmailedit.setError("Invalid Email");
                    mEmailedit.setFocusable(true);
                }else if(mPassedit.length()<6){
                    mPassedit.setError("Password length least 6 characters");
                    mPassedit.setFocusable(true);
                }else{
                    mLoginPresenter.loginUser(Email,Password);
                }
                break;
            case R.id.TV_forgetPassword:
                mLoginPresenter.showRecoveryPasswordDialog();
                break;
            case R.id.not_have_account_tv:
                startActivity(new Intent(this, RegisterActivity.class));
                break;
        }
    }

}
