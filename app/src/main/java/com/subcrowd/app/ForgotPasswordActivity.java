package com.subcrowd.app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.SignInMethodQueryResult;

public class ForgotPasswordActivity extends AppCompatActivity {
    private Button mForgotPasswordButton;
    private EditText mEmail;
    private FirebaseAuth mAuth;
    private int flag;
    private String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+", email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        mAuth = FirebaseAuth.getInstance();
        flag = 0;
        mForgotPasswordButton = (Button) findViewById(R.id.resetPasswordButton);
        mEmail = (EditText) findViewById(R.id.ResetPasswordEmail);

        mForgotPasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email = mEmail.getText().toString();
                if (email.equals("")) {
                    Toast.makeText(ForgotPasswordActivity.this, "Email is empty.", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Below code checks if the email id is valid or not.
                if (!email.matches(emailPattern)) {
                    Toast.makeText(getApplicationContext(), "Invalid email address, enter valid email id.", Toast.LENGTH_SHORT).show();
                    return;

                }


                mAuth.fetchSignInMethodsForEmail(email).addOnCompleteListener(new OnCompleteListener<SignInMethodQueryResult>() {
                    @Override
                    public void onComplete(@NonNull Task<SignInMethodQueryResult> task) {
                        flag = 1;
                        mAuth.sendPasswordResetEmail(mEmail.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()) {
                                    Toast.makeText(ForgotPasswordActivity.this, "Password reset instructions sent to your email.", Toast.LENGTH_LONG).show();
                                } else {
                                    Toast.makeText(ForgotPasswordActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();

                                }
                            }
                        });
                    }
                });

                if(flag == 0)
                    Toast.makeText(getApplicationContext(), "Email address not found.", Toast.LENGTH_SHORT).show();


            }
        });

    }

    @Override
    public void onBackPressed() {
        Intent btnClick = new Intent(ForgotPasswordActivity.this, LoginActivity.class);
        startActivity(btnClick);
        super.onBackPressed();
        finish();
        return;
    }
}
