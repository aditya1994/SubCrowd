package com.subcrowd.app;

import android.app.Activity;


import android.content.Intent;
import android.os.Handler;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

    public class SplashScreen extends AppCompatActivity {

        @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {

                Intent i = new Intent(SplashScreen.this, ChooseLoginRegistrationActivity.class);
                startActivity(i);

                finish();
            }
        }, 2000);
    }
}
