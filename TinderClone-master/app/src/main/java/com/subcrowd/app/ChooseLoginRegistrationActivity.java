package com.subcrowd.app;

import android.content.Intent;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TabHost;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ChooseLoginRegistrationActivity extends AppCompatActivity {

    private Button mLogin, mRegister;
    private FirebaseAuth mAuth;
    private ProgressBar spinner;
    public String TAG;
    private FirebaseAuth.AuthStateListener firebaseAuthStateListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_login_registration);
        TAG = "chooseLoginRegistration";
        mAuth = FirebaseAuth.getInstance();
        spinner = (ProgressBar)findViewById(R.id.pBar);
        spinner.setVisibility(View.GONE);
        if(mAuth != null){
            final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//            if (user !=null && user.isEmailVerified()){  //uncomment for production
            if (user !=null ){
                spinner.setVisibility(View.VISIBLE);
                Intent intent = new Intent(ChooseLoginRegistrationActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
                spinner.setVisibility(View.GONE);
                return;
            }
            else {
                Log.d(TAG, "user is null");
            }
        }


        mLogin = (Button) findViewById(R.id.login);
        mRegister = (Button) findViewById(R.id.register);

        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                spinner.setVisibility(View.VISIBLE);
                Intent intent = new Intent(ChooseLoginRegistrationActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
                spinner.setVisibility(View.GONE);
                return;
            }
        });

        mRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                spinner.setVisibility(View.VISIBLE);
                Intent intent = new Intent(ChooseLoginRegistrationActivity.this, RegistrationActivity.class);
                startActivity(intent);
                finish();
                spinner.setVisibility(View.GONE);
                return;
            }
        });
    }
}
