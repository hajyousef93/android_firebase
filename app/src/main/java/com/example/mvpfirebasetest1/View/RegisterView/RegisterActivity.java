package com.example.mvpfirebasetest1.View.RegisterView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.mvpfirebasetest1.Presenter.RegisterPresenter.RegisterPresenter;
import com.example.mvpfirebasetest1.R;
import com.example.mvpfirebasetest1.View.LoginView.LoginActivity;
import com.google.android.gms.common.server.converter.StringToIntConverter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    EditText mUesrname,mPhoneNumber,mEmail,mPassword,mConfermPassword;
    TextView mAlready_have_account;
    Button mRegister;
    FirebaseAuth mAuth;
    DatabaseReference mReference;
    RegisterPresenter mRegisterPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        ActionBar actionBar=getSupportActionBar();
        actionBar.setTitle("Register");
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        mUesrname=findViewById(R.id.ET_userName);
        mPhoneNumber=findViewById(R.id.ET_phoneNumber);
        mEmail=findViewById(R.id.ET_email);
        mPassword=findViewById(R.id.ET_password);
        mConfermPassword=findViewById(R.id.ET_conferm_password);
        mRegister=findViewById(R.id.But_register);
        mRegister.setOnClickListener(this);
        mAlready_have_account=findViewById(R.id.TV_AlreadyHaveAccount);
        mAlready_have_account.setOnClickListener(this);
        mAuth=FirebaseAuth.getInstance();
        mReference= FirebaseDatabase.getInstance().getReference();
        mRegisterPresenter=new RegisterPresenter(this,mAuth,mReference);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
        case R.id.But_register:
            String name=mUesrname.getText().toString().trim();
            int phone=Integer.valueOf(mPhoneNumber.getText().toString());
            String email=mEmail.getText().toString().trim();
            String password=mPassword.getText().toString().trim();
            String confermpass=mConfermPassword.getText().toString().trim();
            if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                mEmail.setError("Invalid Email");
                mEmail.setFocusable(true);
            }else if(mPassword.length()<6){
                mPassword.setError("Password length least 6 characters");
                mPassword.setFocusable(true);
            }else if(!password.equals(confermpass)){
                mPassword.setError("Password not matching");
            }else {
            mRegisterPresenter.signUpUser(name,phone,email,password);
            }
            break;
            case R.id.TV_AlreadyHaveAccount:
                startActivity(new Intent(this, LoginActivity.class));
                break;
        }
    }
}
