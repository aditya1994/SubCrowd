package com.cureApp.app;

import android.content.Intent;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";

    private ProgressBar spinner;
    private Button mLogin;
    private EditText mEmail, mPassword;
    private TextView mForgotPassword;
    private boolean loginBtnClicked;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener firebaseAuthStateListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginBtnClicked = false;
        spinner = (ProgressBar)findViewById(R.id.pBar);
        spinner.setVisibility(View.GONE);

        mAuth = FirebaseAuth.getInstance();
        mLogin = (Button) findViewById(R.id.login);
        mEmail = (EditText) findViewById(R.id.email);
        mPassword = (EditText) findViewById(R.id.password);
        mForgotPassword = (TextView) findViewById(R.id.forgotPasswordButton);

        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginBtnClicked = true;
                spinner.setVisibility(View.VISIBLE);
                final String email = mEmail.getText().toString();
                final String password = mPassword.getText().toString();

                if (isStringNull(email) || isStringNull(password)) {
                    Toast.makeText(LoginActivity.this, "You must fill out all the fields", Toast.LENGTH_SHORT).show();
                } else {
                    mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (!task.isSuccessful()) {
                                Toast.makeText(LoginActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            } else {
                               if(mAuth.getCurrentUser().isEmailVerified()) {
                                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                    return;
                                } else {
                                    Toast.makeText(LoginActivity.this, "Please verify your email", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    });

                }
                spinner.setVisibility(View.GONE);
            }
        });
        mForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spinner.setVisibility(View.VISIBLE);
                Intent intent = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
                startActivity(intent);
                spinner.setVisibility(View.GONE);
                finish();
                return;
            }
        });
        firebaseAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                //uncomment this for production
                if (user !=null && user.isEmailVerified() && !loginBtnClicked){
                    //if (user !=null) {
                    spinner.setVisibility(View.VISIBLE);
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                    spinner.setVisibility(View.GONE);
                    return;
                }
            }
        };
    }
    private boolean isStringNull(String string) {
        Log.d(TAG, "isStringNull: checking string if null.");

        return string.equals("");
    }


    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(firebaseAuthStateListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mAuth.removeAuthStateListener(firebaseAuthStateListener);
    }

    @Override
    public void onBackPressed() {
        Intent btnClick = new Intent(LoginActivity.this, ChooseLoginRegistrationActivity.class);
        startActivity(btnClick);
        finish();
        return;
    }
}