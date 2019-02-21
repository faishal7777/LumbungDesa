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

import org.w3c.dom.Text;

public class SigninActivity extends AppCompatActivity {

    TextView mReg;
    EditText telpon, pass;

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
        setContentView(R.layout.activity_signin);

        //ganti nama title
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Login");

        mReg = findViewById(R.id.btnLoginReg);


        mReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent(SigninActivity.this, SignupActivity.class);
                startActivity(a);
                finish();
            }
        });

        pass = findViewById(R.id.password);
        telpon = findViewById(R.id.noTelpon);

        //set listener
        pass.addTextChangedListener(textWatcher);
        telpon.addTextChangedListener(textWatcher);

        // run once to disable if empty
        checkFieldsForEmptyValues();
    }

    private  void checkFieldsForEmptyValues(){
        Button mLogin = findViewById(R.id.btnLogin);
        String noTelpon = telpon.getText().toString();
        final String mPassword = pass.getText().toString();

        if(noTelpon.equals("") || mPassword.equals(""))
        {
            mLogin.setEnabled(false);
            mLogin.setBackground(this.getResources().getDrawable(R.drawable.btn_login_disabled));
        }

        else
        {
            mLogin.setEnabled(true);
            mLogin.setBackground(this.getResources().getDrawable(R.drawable.btn_login));

            mLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(SigninActivity.this, mPassword, Toast.LENGTH_LONG).show();
                    Intent b = new Intent(SigninActivity.this, Signup2Activity.class);
                    startActivity(b);
                }
            });
        }
    }
}
