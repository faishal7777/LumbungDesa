package com.runupstdio.lumbungdesa;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class OTPActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);

        //ganti nama title
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Verifikasi");
    }
}
