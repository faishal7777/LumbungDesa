package com.runupstdio.lumbungdesa;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
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
    EditText telpon;

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
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        mReg = findViewById(R.id.btnLoginReg);
        mReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent(SigninActivity.this, SignupActivity.class);
                startActivity(a);
                finish();
            }
        });

        telpon = findViewById(R.id.noTelpon2);

        //set listener
        telpon.addTextChangedListener(textWatcher);

        // run once to disable if empty
        checkFieldsForEmptyValues();
    }

    private  void checkFieldsForEmptyValues(){
        Button mLogin = findViewById(R.id.btnLogin);
        final String noTelpon = telpon.getText().toString();

        if(noTelpon.equals(""))
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
                    Toast.makeText(SigninActivity.this, noTelpon, Toast.LENGTH_LONG).show();
                    Intent b = new Intent(SigninActivity.this, NavigationBar.class);
                    startActivity(b);
                }
            });
        }
    }
}
