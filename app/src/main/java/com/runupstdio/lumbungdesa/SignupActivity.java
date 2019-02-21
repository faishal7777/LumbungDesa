package com.runupstdio.lumbungdesa;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import static android.app.PendingIntent.getActivity;

public class SignupActivity extends AppCompatActivity {

    EditText pass, kpass, nama, telpon;
    TextView mLogin;

    //TextWatcher
    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            checkFieldsForEmptyValues();
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        //ganti nama title
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Membuat Akun");

        mLogin = findViewById(R.id.loginReg);
        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent b = new Intent(SignupActivity.this, SigninActivity.class);
                startActivity(b);
                finish();
            }
        });

        pass = findViewById(R.id.password);
        kpass = findViewById(R.id.konfirmPassword);
        nama = findViewById(R.id.namaLengkap);
        telpon = findViewById(R.id.noTelpon);

        //set listener
        pass.addTextChangedListener(textWatcher);
        kpass.addTextChangedListener(textWatcher);
        nama.addTextChangedListener(textWatcher);
        telpon.addTextChangedListener(textWatcher);

        // run once to disable if empty
        checkFieldsForEmptyValues();
    }

    private  void checkFieldsForEmptyValues(){
        Button btnRegLanjut = findViewById(R.id.btnRegLanjut);
        String namaLengkap = nama.getText().toString();
        String noTelpon = telpon.getText().toString();
        final String mPassword = pass.getText().toString();
        final String mKonfirmPassword = kpass.getText().toString();

        if(namaLengkap.equals("") || noTelpon.equals("") || mPassword.equals("") || mKonfirmPassword.equals(""))
        {
            btnRegLanjut.setEnabled(false);
            btnRegLanjut.setBackground(this.getResources().getDrawable(R.drawable.btn_login_disabled));
        }

        else
        {
            btnRegLanjut.setEnabled(true);
            btnRegLanjut.setBackground(this.getResources().getDrawable(R.drawable.btn_login));

            btnRegLanjut.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!mPassword.equals(mKonfirmPassword)){
                        Toast.makeText(SignupActivity.this, "Password Harus Sama", Toast.LENGTH_LONG).show();
                    }
                    else {
                        Toast.makeText(SignupActivity.this, mPassword, Toast.LENGTH_LONG).show();
                        Intent a = new Intent(SignupActivity.this, Signup2Activity.class);
                        startActivity(a);
                    }
                }
            });

        }
    }
}
