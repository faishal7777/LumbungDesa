package com.runupstdio.lumbungdesa;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class WelcomeActivity extends AppCompatActivity {

    Button btnWelcomeSignup;
    TextView mLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        btnWelcomeSignup = findViewById(R.id.btnWelcomeSignup);
        btnWelcomeSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent(WelcomeActivity.this, SignupActivity.class);
                startActivity(a);
            }
        });

        mLogin = findViewById(R.id.loginWelcome);
        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent b = new Intent(WelcomeActivity.this, SigninActivity.class);
                startActivity(b);
            }
        });
    }
}
