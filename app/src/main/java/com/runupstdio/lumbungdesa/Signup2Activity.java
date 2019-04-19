package com.runupstdio.lumbungdesa;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Signup2Activity extends AppCompatActivity {

    Button mbtnBuatAkun;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup2);

        mbtnBuatAkun = findViewById(R.id.btnBuatAkun);

        mbtnBuatAkun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // MY_PREFS_NAME - a static String variable like:
                //public static final String MY_PREFS_NAME = "MyPrefsFile";
                SharedPreferences.Editor editor = getSharedPreferences("Login", MODE_PRIVATE).edit();
                editor.putBoolean("isLogin", true);
                editor.apply();

                Intent daftar = new Intent(Signup2Activity.this, NavigationBar.class);
                startActivity(daftar);
            }
        });
    }
}
